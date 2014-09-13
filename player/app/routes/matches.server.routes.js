/**
 * Module dependencies.
 */
var matches = require('../../app/controllers/matches');

module.exports = function(app) {

	app.get('/api/match/retrievelatest/:skip', function(req, res) { 
		matches.retrieveLatest(req.params.skip, function(err, success) {
			if(err) throw err;
			else res.send(success);
		});
	});

	app.get('/api/match/retrievelatest/:skip/:botid', function(req, res) { 
		matches.retrieveLatestFiltered(req.params.skip, req.params.botid, function(err, success) {
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