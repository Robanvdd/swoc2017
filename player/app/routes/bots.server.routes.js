/**
 * Module dependencies.
 */
var users = require('../../app/controllers/users'),
	bots = require('../../app/controllers/bots'),
	path = require('path'),
	fs = require('fs');

module.exports = function(app) {

	app.post('/api/bot/uploadmicro/', users.requiresLogin, bots.uploadmicro);
	app.post('/api/bot/uploadmacro/', users.requiresLogin, bots.uploadmacro);
	app.get('/api/bot/getactivebots/', bots.retrieveAllLatest);
    app.get('/api/bot/getoldbots/', users.requiresLogin, bots.retrieveOldBots)

    app.get('/api/bot/history/:username', function(req, res) {
        bots.retrieveHistory(req, res, req.params.username);
    });
}