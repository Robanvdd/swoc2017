//server.js
var application_root = __dirname,
	express = require('express'),
	passport = require('passport'),
	LocalStrategy = require('passport-local').Strategy,
    bcrypt = require('bcryptjs'),
	path 	= require("path"),
	url 	= require("url"),
	request = require("request"),
	Game 	= require('./routes/game'),
	User 	= require('./routes/user'),
	exec    = require('child_process').execFile,
	fs      = require('fs');

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

var app = express();

app.configure(function () {
	app.use(express.logger('dev'));
	app.use(express.json());
    app.use(express.cookieParser());
  	app.use(express.session({ secret: 'Valar Morghulis' }));

	// Remember Me middleware
	app.use( function (req, res, next) {
		console.log('checking cookie use');
		if ( req.method == 'POST' && req.url == '/login' ) {
			console.log('setting cookie use!');
		    req.session.cookie.maxAge = 2592000000; // 30*24*60*60*1000 Remember 'me' for 30 days
		}
		next();
	});
	
	// Initialize Passport!  Also use passport.session() middleware, to support
	// persistent login sessions (recommended).
	app.use(passport.initialize());
	app.use(passport.session());
	app.use(express.urlencoded());
	app.use(express.bodyParser({
		uploadDir: application_root + '/tmp/uploads',
		keepExtensions: true
	}));
	app.use(express.static(path.join(application_root, "public")));
	app.use(app.router);
});

//---------------- USER -------------------------
require('./app/users.routes.server.js');

//---------------- BOT UPLOAD -------------------
require('./app/bots.routes.server.js')(app)

//-------------------- GAME ---------------------
require('./app/games.routes.server.js')(app)

//======================= START SERVER ======================
app.listen(8090);
console.log('testing server is listening on port 8090...');
