'use strict';

/**
 * Module dependencies.
 */
var users = require('../../app/controllers/users'),
	matches = require('../../app/controllers/matches');

module.exports = function(app) {
	// Match Routes
	app.route('/matches')
		.get(matches.list)
		.post(users.requiresLogin, matches.create);

	app.route('/matches/:matchId')
		.get(matches.read);

	// Finish by binding the match middleware
	app.param('matchId', matches.matchByID);
};