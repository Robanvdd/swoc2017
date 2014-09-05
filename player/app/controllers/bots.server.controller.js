/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Bot = mongoose.model('Bot'),
	path = require('path'),
	upload_folder_base = path.resolve('./bots_upload'),
	run_script = 'run.sh';


// function createTargetFolder(user, bot) {
// 	var target_folder = path.resolve('./bots_upload/' + user.username);
// 	fs.mkdir(target_folder, function(e){
// 		if(!e || (e && e.code === 'EEXIST')){
// 			//do something with contents
// 		} else {
// 			//debug
// 			console.log(e);
// 		}
// 	});
// }

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

			var newBot = new Bot({
				name: user.username + '.' + version.toString(),
				version: version,
				executablePath: path.join(upload_folder_base, user.username, version.toString(), run_script),
				user: user
			});
			newBot.save(callback);
		}
	});
}

exports.upload = function(req, res) {
	setTimeout(function () {
		res.setHeader('Content-Type', 'text/html');
		if (req.files.length == 0 || req.files.file.size == 0) {
			res.send({ msg: 'No file uploaded at ' + new Date().toString() });
		}
		else {
			console.log("Upload from user:" + req.user.username + ", fileName:"  + req.files.file.name);

			addNewBotVersionToDatabase(req.user, function(err, bot) { 
				if (err) {
					throw err;
				} else {
					console.log('Bot entry created in database. Version: ' + bot.version + ', path: ' + bot.executablePath);
					var file = req.files.file;
					res.send({ msg: '<b>"' + file.name + '"</b> uploaded to the server at ' + new Date().toString() });
				}
				// createTargetFolder(req.user);

				// 
				// var target_path = target_folder + '/' + file.name
				// console.log('file.path:' + file.path + ' target_path:' + target_path);
				// fs.rename(file.path, target_path, function(err) {
				// 	if (err)
				// 		throw err;
				// 	// Delete the temporary file, so that the explicitly set temporary upload dir does not get filled with unwanted files.
				// 	fs.unlink(file.path, function() {
				// 		if (err)
				// 			throw err;
				// 		//
				// 	});
				// 	res.send({ msg: '<b>"' + file.name + '"</b> uploaded to the server at ' + new Date().toString() });
				// });

			});
		}
	}, (req.param('delay', 'yes') == 'yes') ? 2000 : -1);
};