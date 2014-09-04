var passport = require('passport'),
	LocalStrategy = require('passport-local').Strategy,
	mongoose = require('mongoose'),
	User = mongoose.model('User');


module.exports = function() {
	// Passport session setup.
	//   To support persistent login sessions, Passport needs to be able to
	//   serialize users into and deserialize users out of the session.  Typically,
	//   this will be as simple as storing the user ID when serializing, and finding
	//   the user by ID when deserializing.
	//
	//   Both serializer and deserializer edited for Remember Me functionality
	passport.serializeUser(function(user, done) {

	  	console.log('serializeUser, username:' + user.username);
	  done(null, user.username);
	});

	passport.deserializeUser(function(user, done) {
		console.log('deserializeUser pre, user:' + user);
		User.findOne( { username: user } , function (err, user) {
			if(err) {
				console.log('deserializeUser, err:' + err);
			} else {
				console.log('deserializeUser, no err:');
			}
			if(user) {
				
				console.log('deserializeUser, username:' + user.username);
			} else {
				console.log('deserializeUser, no user');
			}
		done(err, user);
		});
	});

	// Use the LocalStrategy within Passport.
	//   Strategies in passport require a `verify` function, which accept
	//   credentials (in this case, a username and password), and invoke a callback
	//   with a user object.  In the real world, this would query a database;
	//   however, in this example we are using a baked-in set of users.
	passport.use(new LocalStrategy(function(username, password, done) {
	  User.findOne({ username: username }, function(err, user) {
	    if (err) { return done(err); }
	    if (!user) { return done(null, false, { message: 'Unknown user ' + username }); }
	    user.comparePassword(password, function(err, isMatch) {
	      if (err) return done(err);
	      if(isMatch) {
	        return done(null, user);
	      } else {
	        return done(null, false, { message: 'Invalid password' });
	      }
	    });
	  });
	}));
}