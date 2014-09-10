/**
 * Module dependencies.
 */
var users = require('../../app/controllers/users');

module.exports = function(app) {

	app.post('/login', users.signin);
	app.get('/logout', users.requiresLogin, users.signout);
	app.get('/user', users.requiresLogin, users.me);
};