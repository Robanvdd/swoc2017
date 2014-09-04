/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Game = mongoose.model('Game');

exports.createDoc = function(req, callback) {
	var game = new Game();
	game.name = req.body.name;
	game.bot1 = req.body.bot1;
	game.bot2 = req.body.bot2;
	game.startstate = req.body.startstate;
	game.moves = req.body.moves;
	
	game.save(function (err,doc) {
		if(err) callback(err);
		else callback(null, {"status":"OK","new_id":doc._id});
	});
}

exports.retrieveAll = function(callback) {
	Game.find({}, callback);
}

exports.retrieveById = function(id, callback) {
	Game.findOne({_id:id}, callback);
}

exports.updateDoc = function(req, callback) {
	var newValues = {
					"name":	req.body.name,
					"bot1":	req.body.bot1,
					"bot2":	req.body.bot2,
					"startstate":	req.body.startstate,
					"moves":	req.body.moves,
					};
	
	Game.update( {_id:req.body.id}, {"$set":newValues}, function (err) {
		if(err) callback(err);
		else callback(null, {"status":"OK"});
	});
}

exports.deleteDoc = function(id, callback) {
	Game.remove( {_id:id}, function (err) {
		if(err) callback(err);
		else callback(null, {"status":"OK"});
	});
}