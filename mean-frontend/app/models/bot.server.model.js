'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Schema = mongoose.Schema;

/**
 * Bot Schema
 */
var BotSchema = new Schema({
	name: {
		type: String,
		default: Date.now,
		required: 'A bot name must be given'
	},
	ranking: {
		type: Number,
		default: 1000
	},
	version: {
		type: Number,	
		default: 1
	},
	executablePath: {
		type: String,
		required: 'An executable path must be given'
	},
	user: {
		type: Schema.ObjectId,
		ref: 'User'
	}
});

mongoose.model('Bot', BotSchema);