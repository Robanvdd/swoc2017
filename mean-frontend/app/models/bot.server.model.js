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
	user: {
		type: Schema.ObjectId,
		ref: 'User',
		required: 'A user must be given'
	}
});

mongoose.model('Bot', BotSchema);