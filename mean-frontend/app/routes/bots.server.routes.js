'use strict';

/**
 * Module dependencies.
 */
var users = require('../../app/controllers/users'),
	bots = require('../../app/controllers/bots');

module.exports = function(app) {
	// Bot Routes
	app.route('/bots')
		.get(bots.list)
		.post(users.requiresLogin, bots.create);

	app.route('/bots/:botId')
		.get(bots.read);

	// Finish by binding the bot middleware
	app.param('botId', bots.botByID);
};