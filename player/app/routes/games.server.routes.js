/**
 * Module dependencies.
 */
var games = require('../../app/controllers/games'),
	execFile = require('child_process').execFile;

module.exports = function(app) {

	app.get('/api/game/retrieveall/', function(req, res) { 
		games.retrieveAll(function(err, success) {
			if(err) throw err;
			else res.send(success);
		});
	});

	app.get('/api/game/retrieveid/:id', function(req, res) { 
		games.retrieveById(req.params.id, function(err, success) {
			if(err) throw err;
			else res.send(success);
		});
	});
};