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
	time: {
		type: Date,
		required: 'A completion time must be given'
	},
	winner: {
		type: String,
		required: 'The winner of the match'
	},
	log: {
		type: String,
		required: 'the location of the log'
	}
});

mongoose.model('Match', MatchSchema); // uses a collection called 'match'