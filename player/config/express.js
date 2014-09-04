var express = require('express'),
	config = require('./config'),
	cookieParser = require('cookie-parser'),
	morgan = require('morgan'),
	session = require('express-session'),
	mongoStore = require('connect-mongo')({
		session: session
	}),
	passport = require('passport'),
	path = require('path');

module.exports = function(db) {
	// Initialize express app
	var app = express();

	// // Remember Me middleware
	// app.use( function (req, res, next) {
	// 	console.log('checking cookie use');
	// 	if ( req.method == 'POST' && req.url == '/login' ) {
	// 		console.log('setting cookie use!');
	// 	    req.session.cookie.maxAge = 2592000000; // 30*24*60*60*1000 Remember 'me' for 30 days
	// 	}
	// 	next();
	// });

	// // Should be placed before express.static
	// app.use(compress({
	// 	filter: function(req, res) {
	// 		return (/json|text|javascript|css/).test(res.getHeader('Content-Type'));
	// 	},
	// 	level: 9
	// }));

	// Showing stack errors
	app.set('showStackError', true);

	// Enable logger (morgan) (SHOULD BE CHANGED IN PRODUCTION)
	app.use(morgan('dev'));

	// Disable views cache (SHOULD BE CHANGED IN PRODUCTION)
	app.set('view cache', false);


	// CookieParser should be above session
	app.use(cookieParser());

	// app.use(session({ secret: config.sessionSecret }));

	// Express MongoDB session storage
	app.use(session({
		saveUninitialized: true,
		resave: true,
		secret: config.sessionSecret,
		store: new mongoStore({
			db: db.connection.db,
			collection: config.sessionCollection
		})
	}));

	// use passport session
	app.use(passport.initialize());
	app.use(passport.session());

	app.use(express.urlencoded());
	app.use(express.json());
	app.use(express.bodyParser({
		uploadDir: path.resolve("./tmp/uploads"),
		keepExtensions: true
	}));
	// app.use(methodOverride());

	// Setting the app router and static folder
	app.use(express.static(path.resolve("./public")));

	// app.use(app.router);

	// Globbing routing files
	config.getGlobbedFiles('./app/routes/**/*.js').forEach(function(routePath) {
		require(path.resolve(routePath))(app);
	});

	return app;
}