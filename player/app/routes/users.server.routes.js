/**
 * Module dependencies.
 */
var users = require('../../app/controllers/users');

module.exports = function(app) {

	app.post('/login', users.signin);
	app.get('/logout', users.requiresLogin, users.signout);
	app.get('/user', users.requiresLogin, users.me);
	app.get('/api/user/retrieveid/:id', function(req, res) { 
		users.retrieveById(req.params.id, function(err, success) {
			if(err) throw err;
			else res.send(success);
		});
	});
	app.get('/api/user/retrieveall/', function(req, res) { 
		users.retrieveAll(function(err, success) {
			if(err) throw err;
			else res.send(success);
		});
	});
};