/**
 * Module dependencies.
 */
var users = require('../controllers/users.server.controller');

module.exports = function(app) {

	app.post('/api/bot/upload/', users.requiresLogin, function(req, res) {
		var target_folder =  application_root + '/bots_upload/' + req.user.username;
			fs.mkdir(target_folder,function(e){
		    if(!e || (e && e.code === 'EEXIST')){
		        //do something with contents
		    } else {
		        //debug
		        console.log(e);
		    }
		});
		console.log("Upload from user:" + req.user.username + ", destination folder:" + target_folder + ", fileName:"  + req.files.file.name);
		setTimeout(
	        function () {
	            res.setHeader('Content-Type', 'text/html');
	            if (req.files.length == 0 || req.files.file.size == 0)
	                res.send({ msg: 'No file uploaded at ' + new Date().toString() });
	            else {
	                var file = req.files.file;
					var target_path = target_folder + '/' + file.name
					console.log('file.path:' + file.path + ' target_path:' + target_path);
	                fs.rename(file.path, target_path, function(err) {
						if (err)
							throw err;
						// Delete the temporary file, so that the explicitly set temporary upload dir does not get filled with unwanted files.
						fs.unlink(file.path, function() {
							if (err)
								throw err;
							//
						});
						res.send({ msg: '<b>"' + file.name + '"</b> uploaded to the server at ' + new Date().toString() });
					});
	            }
	        },
	        (req.param('delay', 'yes') == 'yes') ? 2000 : -1
	    );
	});
	
}