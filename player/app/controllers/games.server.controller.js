/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Game = mongoose.model('Game');

exports.retrieveAll = function(callback) {
	Game.find({}, callback);
}

exports.retrieveById = function(id, callback) {
	Game.findOne({_id:id}, callback);
}
