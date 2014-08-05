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
	startedOn: {
		type: Date,
		default: Date.now
	},
	whiteBot: {
		type: Schema.ObjectId,
		ref: 'Bot',
		required: 'WhiteBotId must be given'
	},
	blackBot: {
		type: Schema.ObjectId,
		ref: 'Bot',
		required: 'BlackBotId must be given'
	},
	winnerBot: {
		type: Schema.ObjectId,
		ref: 'Bot'
	},
	matchLogId: {
		type: Schema.ObjectId,
		ref: 'MatchLog'
	}
});

mongoose.model('Match', MatchSchema);