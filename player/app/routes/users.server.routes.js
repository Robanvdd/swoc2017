/**
 * Module dependencies.
 */
var users = require('../../app/controllers/users');

module.exports = function(app) {

	app.post('/login', users.signin);
	app.get('/logout', users.requiresLogin, users.signout);
    app.post('/createuser', users.requiresAdmin, users.createuser);
	app.get('/user', users.requiresLogin, users.me);
};