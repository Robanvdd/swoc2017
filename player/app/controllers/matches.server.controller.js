/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Match = mongoose.model('Match');

exports.retrieveAll = function(callback) {
	Match.find({}.populate('blackBot', 'name').populate('whiteBot', 'name').populate('winnerBot', 'name'), callback);
}

exports.retrieveById = function(id, callback) {
	Match.findOne({_id:id}).populate('blackBot', 'name').populate('whiteBot', 'name').populate('winnerBot', 'name'), callback);
}
