/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Match = mongoose.model('Match');

exports.retrieveLatest = function(skip, callback) {
	Match.find({}).populate('blackBot', 'name').sort('completedOn: -1').limit(20).skip(skip).populate('whiteBot', 'name').populate('winnerBot', 'name').exec(callback);
}

exports.retrieveById = function(id, callback) {
	Match.findOne({_id:id}).populate('blackBot', 'name').populate('whiteBot', 'name').populate('winnerBot', 'name').exec(callback);
}
