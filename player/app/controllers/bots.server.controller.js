/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Bot = mongoose.model('Bot'),
	User = mongoose.model('User'),
	path = require('path'),
	upload_folder_base = path.resolve('./bots_upload'),
	run_script = 'run.sh',
	child_process = require('child_process'),
	compile_script_path = path.join(upload_folder_base, 'compilebot.py'),
	fs = require('fs');


function findLastUserBot(user, kind) {
	return Bot.findOne({user: user._id, kind: kind}).sort({version: -1});
}

function getNewBotVersion(user, kind, callback) {
	findLastUserBot(user, kind).exec(function(err, lastBot) {
		if (err) {
			callback(err);
		} else if (!lastBot) {
			callback(null, 1, lastBot); // first bot
		} else {
			callback(null, lastBot.version + 1, lastBot);
		}
	});
}

function createTargetFolder(user, botVersion, kind, callback) {
	var target_folder = path.join(upload_folder_base, user.username);
	fs.mkdir(target_folder, function(err){
		if (err && err.code !== 'EEXIST') {
			callback(err);
		} else {
			var version_folder = path.join(target_folder, botVersion.toString());
			fs.mkdir(version_folder, function(err) {
				if(err && err.code !== 'EEXIST'){
					callback(err); 
				} else {
					var kind_folder = path.join(version_folder, kind);
					fs.mkdir(kind_folder, function(err) {
						if(err && err.code !== 'EEXIST'){
							callback(err); 
						} else {
							callback(null, kind_folder); // Success
						}
					});
				}
			});
		}
	});
}

function moveUploadToBotFolder(file, user, botVersion, kind, callback) {
	createTargetFolder(user, botVersion, kind, function(err, target_folder) {
		if (err) {
			callback(err);
		} else {
			var target_path = path.join(target_folder, file.name);		
			fs.rename(file.path, target_path, function(err) {
				if (err) {
					// Delete the temporary file, so that the explicitly set temporary upload dir does not get filled with unwanted files.
					fs.unlink(file.path, function(err) {
						if (err) {
							callback(err);
						} else {
							callback(null, target_folder);
						}
					});
				} else {
					callback(null, target_folder);
				}
			});
		}
	});
}

function runCompileScript(bot_folder, callback) {
	var command = 'python ' + compile_script_path + ' -p ' + bot_folder;
	child_process.exec(command, function(error, stdout, stderr) {
		if (error) {
			callback(new Error('Could not compile bot. ' + error));
		} else {
			callback(null);
		}
	})
}

function readRunCommand(bot_folder, callback) {
	var runShellScript = path.join(bot_folder, 'run.sh');
	fs.readFile(runShellScript, 'utf8', callback)
}

function addNewBotToDatabase(user, old_bot, version, bot_folder, run_command, kind, callback) {
	var executable_path = path.join(upload_folder_base, user.username, version.toString(), run_script);
	var oldRanking = (old_bot) ? old_bot.ranking : 1000;
	var newBot = new Bot({
		name: user.username + ' v' + version.toString(),
		version: version,
		ranking: oldRanking,
		workingDirectory: path.resolve(bot_folder),
		runCommand: run_command,
		user: user,
		kind: kind
	});
	newBot.save(callback);
}

function createBot(user, file, kind, callback) {
	console.log("Upload from user:" + user.username + ", fileName:"  + file.name + ", kind:" + kind);
	console.log('Retrieving new bot version number ...');
	getNewBotVersion(user, kind, function(err, newVersion, oldBot){
		if (err) {
			callback(err)
		} else {
			console.log('Moving upload to bot folder with version ' + newVersion.toString() + ' ...');
			moveUploadToBotFolder(file, user, newVersion, kind, function(err, bot_folder) {
				if (err) {
					callback(err, bot_folder);
				} else {
					console.log('Running compile script ...');
					runCompileScript(bot_folder, function(err) {
						if (err) {
							callback(err, bot_folder);
						} else {
							console.log('Reading command from run.sh ...');
							readRunCommand(bot_folder, function(err, run_command){
								if (err) {
									callback(err, bot_folder);
								} else {
									console.log('Adding new bot to database ...');
									addNewBotToDatabase(user, oldBot, newVersion, bot_folder, run_command, kind, function(err, bot) { 
										if (err) {
											callback(err, bot_folder)
										} else {
											console.log('Bot record created with version ' + newVersion.toString());
											callback(null, bot_folder); // success
										}
									});	
								}
							})	
						}		
					});
				}
			});
		}
	});
}

function readStdoutFile(bot_folder, callback) {
	var filename = path.join(bot_folder, 'compiler_stdout.txt')
	fs.readFile(filename, 'utf8', callback)
}

function readStderrFile(bot_folder, callback) {
	var filename = path.join(bot_folder, 'compiler_stderr.txt')
	fs.readFile(filename, 'utf8', callback)
}

function getBotStdoutStderr(bot_folder, callback) {
	readStdoutFile(bot_folder, function(err, stdout) {
		if (err) {
			callback(null, 'Could not find compiler stdout file.', err)
		} else {
			readStderrFile(bot_folder, function(err, stderr) {
				if (err) {
					callback(null, 'Could not find compiler stderr file.', err)
				} else {
					callback(null, stdout, stderr)
				}
			});
		}
	});
}

var deleteFolderRecursive = function(path) {
  if( fs.existsSync(path) ) {
    fs.readdirSync(path).forEach(function(file,index){
      var curPath = path + "/" + file;
      if(fs.lstatSync(curPath).isDirectory()) { // recurse
        deleteFolderRecursive(curPath);
      } else { // delete file
        fs.unlinkSync(curPath);
      }
    });
    fs.rmdirSync(path);
  }
};


/**
 * Exported methods
 */
 
 exports.uploadmicro = function(req, res) {
	 upload(req,res,"micro");
 }
 exports.uploadmacro = function(req, res) {
	 upload(req,res,"macro");
 }

upload = function(req, res, kind) {
	res.setHeader('Content-Type', 'text/html');
	if (req.files.length == 0 || req.files.file.size == 0) {
		res.send({ result: 'Failed', msg: 'No file given' });
	} else {
		createBot(req.user, req.files.file, kind, function(err, bot_folder) {
			if (err) {
				console.log(err);
				result = 'Failed';
			} else {
				result = 'Success';
			}
			if (bot_folder) {
				getBotStdoutStderr(bot_folder, function(err, stdout, stderr){
					if (err && bot_folder) {
						// if compile fails, remove the bot version folder
						console.log('Removing bot folder ...');
						deleteFolderRecursive(bot_folder);
					}
					return res.send({ result: result, msg: '', stdout: stdout, stderr: stderr });
				})
			} else {
				return res.send({ result: result, msg: 'Something went wrong, ask an admin', stdout: '', stderr: '' });
			}
		});
	}
};

exports.retrieveAll = function(callback) {
	Bot.find({}, callback);
}


exports.retrieveAllLatest = function(req, res) {
	User.find({}, function(err, users) {
		console.log('Getting bots for ' + users.length + ' users');
		function asyncLoop( index, foundBots, callback ) {
		    if (index != users.length) {
		    	var user = users[index];
				console.log('Getting first bot for user ' + user.username);
		    	Bot.findOne({user: user._id}).sort('-version').limit(1).exec(function(err, bot){
		    		if (err) {
		    			console.log('Error finding bot. ' + err);
		    			callback(err, foundBots);
		    		} else {
		    			if (bot) {
		    				console.log('Bot found')
		    				foundBots.push({ranking: bot.ranking, kind: bot.kind, version: bot.version, name: bot.name, username: user.username});
		    			} else {
		    				console.log('No bot found');
		    			}
		    			asyncLoop(index + 1, foundBots, callback);
		    		}
		    	});
		    } else {
		        callback(null, foundBots);
		    }
		}
		asyncLoop(0, [], function(err, bots) {
			if (err) {
				res.status(400).send(err);
			} else {
			    res.send(bots);
			}
		});
	});
}

exports.retrieveOldBots = function(req, res) {
	var user = req.user;
	Bot.find({user: user._id}, 'version ranking name').sort('version').exec(function(err, bots) {
		if (err) {
			res.status(400).send('Could not retrieve bots');
		} else {
			var oldBots = [];
			var prevRanking = 1000;
			for (var i = 0; i < bots.length; i++) {
				var bot = bots[i];
				var performance;
				if (bot.ranking > prevRanking) {
					performance = 'improved';
				} else if (bot.ranking == prevRanking) {
					performance = 'notchanged';
				} else {
					performance = 'degraded';
				}
				var nameParts = bot.name.split(" ");
				oldBots.push({
					version: bot.version,
					kind: bot.kind,
					ranking: bot.ranking,
					performance: performance,
					rankdiff: bot.ranking - prevRanking,
					username: nameParts[0]
				});
				prevRanking = bot.ranking;
			}
			res.send(oldBots);
		}
	});
}

exports.retrieveHistory = function(req, res, username) {
	User.findOne({username: username}).exec(function(err, user){
		if (err) {
			res.status(400).send('Could not find user by given username');
		} else {
			Bot.find({user: user._id}, 'version ranking').sort('version').exec(function(err, bots){		
				if (err) {
					res.status(400).send('Could not retrieve bots');
				} else if (!bots) {
					res.status(400).send('User has no bots');
				} else {
					res.send(bots);
				}
			})
		}
	});
}