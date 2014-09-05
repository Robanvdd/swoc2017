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
		required: 'StdIn from white bot must be given'
	},
	blackStdin: {
		type: String,
		required: 'StdIn from white bot must be given'
	},
	whiteStdOut: {
		type: String,
		required: 'StdOut from white bot must be given'
	},
	blackStdOut: {
		type: String,
		required: 'StdOut from black bot must be given'
	},
	whiteStdErr: {
		type: String,
		required: 'StdErr from white bot must be given'
	},
	blackStdErr: {
		type: String,
		required: 'StdErr from white bot must be given'
	},
	moves: {
		type: String,
		required: 'The game moves must be given'
	}
});

mongoose.model('Match', MatchSchema); // uses a collection called 'match'