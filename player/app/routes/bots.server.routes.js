/**
 * Module dependencies.
 */
var users = require('../../app/controllers/users'),
	bots = require('../../app/controllers/bots'),
	path = require('path'),
	fs = require('fs');

module.exports = function(app) {

	app.post('/api/bot/upload/', users.requiresLogin, bots.upload);
	app.get('/api/bot/getactivebots/', users.requiresAdmin, bots.retrieveAllLatest);
    app.get('/api/bot/getoldbots/', users.requiresLogin, bots.retrieveOldBots)

    app.get('/api/bot/history/:username', users.requiresAdmin, function(req, res) {
        bots.retrieveHistory(req, res, req.params.username);
    });
}