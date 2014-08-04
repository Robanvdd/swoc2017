'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Schema = mongoose.Schema;

/**
 * MatchLog Schema
 */
var MatchLogSchema = new Schema({
	whiteStdin: {
		type: String,
		default: ''
	},
	blackStdin: {
		type: String,
		default: ''
	},
	boardState: {
		type: String,
		required: 'A final board state must be set'
	},
	winner: {
		type: String,	
		required: 'A winner must be set (white/black)'
	}
});

mongoose.model('MatchLog', MatchLogSchema);