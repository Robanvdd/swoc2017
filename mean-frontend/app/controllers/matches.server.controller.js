'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	errorHandler = require('./errors'),
	Match = mongoose.model('Match'),
	_ = require('lodash');

/**
 * Show the current match
 */
exports.read = function(req, res) {
	res.jsonp(req.match);
};

/**
 * Create a match
 */
exports.create = function(req, res) {
	var match = new Match(req.body);
	match.save(function(err) {
		if (err) {
			return res.status(400).send({
				message: errorHandler.getErrorMessage(err)
			});
		} else {
			res.jsonp(match);
		}
	});
};

/**
 * List of Matches
 */
exports.list = function(req, res) {
	Match.find().sort('-startedOn').exec(function(err, matches) {
		if (err) {
			return res.status(400).send({
				message: errorHandler.getErrorMessage(err)
			});
		} else {
			res.jsonp(matches);
		}
	});
};

/**
 * Match middleware
 */
exports.matchByID = function(req, res, next, id) {
	Match.findById(id).exec(function(err, match) {
		if (err) return next(err);
		if (!match) return next(new Error('Failed to load match ' + id));
		req.match = match;
		next();
	});
};

/**
 * Match authorization middleware
 */
exports.hasAuthorization = function(req, res, next) {
	if (req.match.user.id !== req.user.id) {
		return res.status(403).send({
			message: 'User is not authorized'
		});
	}
	next();
};