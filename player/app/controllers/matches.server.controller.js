/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Match = mongoose.model('Match'),
    Bot = mongoose.model('Bot'),
    User = mongoose.model('User');

exports.retrieveLatest = function(skip, callback) {
	Match.find({}).sort('-time').skip(Number(skip)).limit(20).exec(callback);
}

function findNextMatchPage(filter, skip, callback) {
    Match.find(filter)
         .sort('-time')
         .skip(Number(skip))
         .limit(20)
         .exec(callback);
}

exports.retrieveLatestFiltered = function(skip, username, callback) {
    if (username) {
        User.findOne({username: username}).exec(function(err, user){
            console.log(user);
            if (err) {
                return callback(err);
            } else if (!user) {
                callback(null, []);
            } else {
                Bot.find({ user: user._id }, '_id').exec(function(err, userBots){
                    if (err) {
                        return callback(err);
                    } else if (!userBots) {
                        callback(null, []);
                    } else {
                        var botIds = [];
                        for(i = 0; i < userBots.length; i++) {
                            botIds.push(userBots[i]._id);
                        }
                        findNextMatchPage({ $or: [ {whiteBot: {$in: botIds}}, {blackBot: {$in: botIds}} ] }, skip, callback);
                    }
                })
            }
        })
    } else {
    	findNextMatchPage({}, skip, callback);
    }
}

exports.retrieveById = function(id, callback) {
	Match.findOne({_id:id}).populate('blackBot', 'name').populate('whiteBot', 'name').populate('winnerBot', 'name').exec(callback);
}
