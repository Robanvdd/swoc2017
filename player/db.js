var mongoose = require('mongoose');
var Schema = mongoose.Schema,
	ObjectId = Schema.ObjectId;

// SCHEMAS
var Game = new Schema({
	name: {type: String, required: true},
        bot1: {type: String, required: true},
        bot2: {type: String, required: true},
	startstate: {type: String, required: true},
	moves: {type: String, required: true}
	});
	
// EXPORTS
module.exports.mongoose 		= mongoose;
module.exports.SchemaGame 		= Game;

mongoose.connect('mongodb://localhost/belajarnode');
