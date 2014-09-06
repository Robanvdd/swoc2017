/**
 * Module dependencies.
 */
var matches = require('../../app/controllers/matches');

module.exports = function(app) {

	app.get('/api/match/retrieveall/', function(req, res) { 
		matches.retrieveAll(function(err, success) {
			if(err) throw err;
			else res.send(success);
		});
	});

	app.get('/api/match/retrieveid/:id', function(req, res) { 
		matches.retrieveById(req.params.id, function(err, success) {
			if(err) throw err;
			else res.send(success);
		});
	});
};