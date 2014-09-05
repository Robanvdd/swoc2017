/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Bot = mongoose.model('Bot'),
	path = require('path'),
	upload_folder_base = path.resolve('./bots_upload'),
	run_script = 'run.sh',
	child_process = require('child_process'),
	compile_script_path = path.join(upload_folder_base, 'compilebot.py');


function findLastUserBot(user) {
	return Bot.findOne().sort({version: -1});
}

function addNewBotVersionToDatabase(user, callback) {
	findLastUserBot(user).exec(function(err, bot) {
		if (err) {
			callback(err, bot);
		} else {
			var version = 1;
			if (bot) {
				version = bot.version + 1; 
			}
			var executable_path = path.join(upload_folder_base, user.username, version.toString(), run_script);

			var newBot = new Bot({
				name: user.username + '.' + version.toString(),
				version: version,
				executablePath: executable_path,
				user: user
			});
			newBot.save(callback);
		}
	});
}

function createTargetFolder(user, bot, callback) {
	var target_folder = path.join(upload_folder_base, user.username);
	fs.mkdir(target_folder, function(err){
		if (err && err.code !== 'EEXIST') {
			callback(err);
		} else {
			var version_folder = path.join(target_folder, bot.version.toString());
			fs.mkdir(version_folder, function(err) {
				if(err && err.code !== 'EEXIST'){
					callback(err); 
				} else {
					callback(null, version_folder); // Success
				}
			});
		}
	});
}

function moveUploadToBotFolder(file, user, bot, callback) {
	createTargetFolder(user, bot, function(err, target_folder) {
		if (err) {
			callback(err);
		} else {
			var target_path = path.join(target_folder, file.name);
			console.log('file.path:' + file.path + ' --> target_path:' + target_path);				
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
	console.log('Compiling bot with command: ' + command);
	child_process.exec(command, function(error, stdout, stderr) {
		if (error) {
			callback(new Error('Could not compile bot. Error: ' + error));
		} else {
			callback();
		}
	})
}

function createBot(user, file, callback) {
	console.log("Upload from user:" + user.username + ", fileName:"  + file.name);
	addNewBotVersionToDatabase(user, function(err, bot) { 
		if (err) {
			callback(err)
		} else {
			console.log('Bot entry created in database. Version: ' + bot.version + ', ExecutablePath: ' + bot.executablePath);
			moveUploadToBotFolder(file, user, bot, function(err, bot_folder) {
				if (err) {
					callback(err);
				} else {
					runCompileScript(bot_folder, callback);
				}
			});
		}
	});
}

/**
 * Exported methods
 */

exports.upload = function(req, res) {
	res.setHeader('Content-Type', 'text/html');
	if (req.files.length == 0 || req.files.file.size == 0) {
		res.send({ msg: 'No file uploaded at ' + new Date().toString() });
	} else {
		createBot(req.user, req.files.file, function(err) {
			if (err) {
				console.log(err.message);
				console.log(err.stack);
				return res.status(400).send({ msg: 'Bot upload failed' });
			}
			else
				return res.send({ msg: 'File uploaded to the server and bot compiled at ' + new Date().toString() });
		});
	}
};