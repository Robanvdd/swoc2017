'use strict';

/**
 * Module dependencies.
 */
var should = require('should'),
	mongoose = require('mongoose'),
	User = mongoose.model('User'),
	Bot = mongoose.model('Bot');

/**
 * Globals
 */
var user, bot;

/**
 * Unit tests
 */
describe('Bot Model Unit Tests:', function() {
	beforeEach(function(done) {
		user = new User({
			firstName: 'Full',
			lastName: 'Name',
			displayName: 'Full Name',
			email: 'test@test.com',
			username: 'username',
			password: 'password'
		});

		user.save(function() {
			bot = new Bot({
				name: 'Bot Name',
				executablePath: 'Bot Path',
				user: user
			});

			done();
		});
	});

	describe('Method Save', function() {
		it('should be able to save without problems', function(done) {
			return bot.save(function(err) {
				should.not.exist(err);
				done();
			});
		});

		it('should be able to show an error when try to save without name', function(done) {
			bot.name = '';

			return bot.save(function(err) {
				should.exist(err);
				done();
			});
		});

		it('should be able to show an error when try to save without executablePath', function(done) {
			bot.executablePath = '';

			return bot.save(function(err) {
				should.exist(err);
				done();
			});
		});

		it('should be able to show an error when try to save without user', function(done) {
			bot.user = '';

			return bot.save(function(err) {
				should.exist(err);
				done();
			});
		});
	});

	afterEach(function(done) {
		Bot.remove().exec();
		User.remove().exec();
		done();
	});
});