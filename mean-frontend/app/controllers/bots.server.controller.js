'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	errorHandler = require('./errors'),
	Bot = mongoose.model('Bot'),
	_ = require('lodash');

/**
 * Show the current bot
 */
exports.read = function(req, res) {
	res.jsonp(req.bot);
};

/**
 * Create a bot
 */
exports.create = function(req, res) {
	var bot = new Bot(req.body);
	bot.save(function(err) {
		if (err) {
			return res.status(400).send({
				message: errorHandler.getErrorMessage(err)
			});
		} else {
			res.jsonp(bot);
		}
	});
};

/**
 * List of Bots
 */
exports.list = function(req, res) {
	Bot.find().sort('name').exec(function(err, bots) {
		if (err) {
			return res.status(400).send({
				message: errorHandler.getErrorMessage(err)
			});
		} else {
			res.jsonp(bots);
		}
	});
};

/**
 * Bot middleware
 */
exports.botByID = function(req, res, next, id) {
	Bot.findById(id).exec(function(err, bot) {
		if (err) return next(err);
		if (!bot) return next(new Error('Failed to load bot ' + id));
		req.bot = bot;
		next();
	});
};

/**
 * Bot authorization middleware
 */
exports.hasAuthorization = function(req, res, next) {
	if (req.bot.user.id !== req.user.id) {
		return res.status(403).send({
			message: 'User is not authorized'
		});
	}
	next();
};