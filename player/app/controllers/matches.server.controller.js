/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Game = mongoose.model('Match');

exports.retrieveAll = function(callback) {
	Match.find({}, callback);
}

exports.retrieveById = function(id, callback) {
	Match.findOne({_id:id}, callback);
}
