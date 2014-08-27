'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Schema = mongoose.Schema;

/**
 * Match Schema
 */
var MatchSchema = new Schema({
	whiteBot: {
		type: Schema.ObjectId,
		ref: 'Bot',
		required: 'White bot id must be given'
	},
	blackBot: {
		type: Schema.ObjectId,
		ref: 'Bot',
		required: 'Black bot id must be given'
	},
	winnerBot: {
		type: Schema.ObjectId,
		ref: 'Bot',
		required: 'Winner id must be given'
	},
	startedOn: {
		type: Date,
		required: 'A starting date/time must be given'
	},
	completedOn: {
		type: Date,
		required: 'A completion date/time must be given'
	},
	whiteStdin: {
		type: String,
		required: 'Input from white bot must be given'
	},
	blackStdin: {
		type: String,
		required: 'Input from white bot must be given'
	},
	boardState: {
		type: String,
		required: 'A final board state must be set'
	}
});

mongoose.model('Match', MatchSchema);