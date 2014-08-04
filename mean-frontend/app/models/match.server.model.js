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
	whiteBotId: {
		type: Schema.ObjectId,
		ref: 'Bot',
		required: 'WhiteBotId must be given'
	},
	blackBotId: {
		type: Schema.ObjectId,
		ref: 'Bot',
		required: 'BlackBotId must be given'
	},
	winnerBotId: {
		type: Schema.ObjectId,
		ref: 'Bot'
	},
	matchLogId: {
		type: Schema.ObjectId,
		ref: 'MatchLog'
	}
});

mongoose.model('Match', MatchSchema);