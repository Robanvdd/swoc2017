/**
 * Module dependencies.
 */
var users = require('../../app/controllers/users'),
	bots = require('../../app/controllers/bots'),
	path = require('path')
	fs = require('fs');

module.exports = function(app) {

	app.post('/api/bot/upload/', users.requiresLogin, bots.upload);
	
}