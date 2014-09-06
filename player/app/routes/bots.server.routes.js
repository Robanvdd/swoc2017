/**
 * Module dependencies.
 */
var users = require('../../app/controllers/users'),
	bots = require('../../app/controllers/bots'),
	path = require('path')
	fs = require('fs');

module.exports = function(app) {

	app.post('/api/bot/upload/', users.requiresLogin, bots.upload);
	app.get('/api/bot/retrieveall/', function(req, res) { 
		bots.retrieveAll(function(err, success) {
			if(err) throw err;
			else res.send(success);
		});
	});
	
}