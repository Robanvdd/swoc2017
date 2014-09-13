/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Match = mongoose.model('Match');

exports.retrieveLatest = function(skip, callback) {
	Match.find({}).sort('-completedOn').skip(skip).limit(20).populate('blackBot', 'name').populate('whiteBot', 'name').populate('winnerBot', 'name').exec(callback);
}

exports.retrieveLatestFiltered = function(skip, botid,  callback) {
	Match.find({$or:[ {whiteBot:botid}, {blackBot:botid}]}).sort('-completedOn').skip(skip).limit(20).populate('blackBot', 'name').populate('whiteBot', 'name').populate('winnerBot', 'name').exec(callback);
}

exports.retrieveById = function(id, callback) {
	Match.findOne({_id:id}).populate('blackBot', 'name').populate('whiteBot', 'name').populate('winnerBot', 'name').exec(callback);
}
