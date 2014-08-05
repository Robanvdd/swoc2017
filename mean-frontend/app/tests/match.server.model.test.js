'use strict';

/**
 * Module dependencies.
 */
var should = require('should'),
	mongoose = require('mongoose'),
	User = mongoose.model('User'),
	Bot = mongoose.model('Bot'),
	Match = mongoose.model('Match');

/**
 * Globals
 */
var user, bot, match;

/**
 * Unit tests
 */
describe('Match Model Unit Tests:', function() {
	beforeEach(function(done) {
		user = new User({
			firstName: 'Full',
			lastName: 'Name',
			displayName: 'Full Name',
			email: 'test@test.com',
			username: 'username',
			password: 'password'
		});

		bot = new Bot({
			name: 'Bot Name',
			executablePath: 'Bot Path',
			user: user
		});

		user.save(function() {
			bot.save(function(){
				match = new Match({
					startedOn: '2014-01-01 12:00:00',
					whiteBotId: bot,
					blackBotId: bot
				});

				done();
			})
		});

	});

	describe('Method Save', function() {
		it('should be able to save without problems', function(done) {
			return match.save(function(err) {
				should.not.exist(err);
				done();
			});
		});

		it('should be able to show an error when try to save without whiteBot', function(done) {
			match.whiteBotId = 0;

			return match.save(function(err) {
				should.exist(err);
				done();
			});
		});

		it('should be able to show an error when try to save without blackBot', function(done) {
			match.blackBotId = 0;

			return match.save(function(err) {
				should.exist(err);
				done();
			});
		});
	});

	afterEach(function(done) {
		Bot.remove().exec();
		User.remove().exec();
		Match.remove().exec();
		done();
	});
});