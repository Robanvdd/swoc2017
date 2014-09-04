
/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Schema = mongoose.Schema;

/**
 * Games Schema
 */
var GameSchema = new Schema({
	name: {
		type: String, 
		required: true
	},
    bot1: {
    	type: String, 
    	required: true
    },
    bot2: {
    	type: String, 
    	required: true
    },
	startstate: {
		type: String, 
		required: true
	},
	moves: {
		type: String, 
		required: true
	}
});

mongoose.model('Game', GameSchema);
