//server.js
var application_root = __dirname,
	express = require('express'),
	passport = require('passport'),
	path 	= require("path"),
	url 	= require("url"),
	request = require("request"),
	exec    = require('child_process').execFile,
	fs      = require('fs');

var app = express();

// Bootstrap passport config
require('./config/passport')();

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
require('./app/routes/users.server.routes.js');

//---------------- BOT UPLOAD -------------------
require('./app/routes/bots.server.routes.js')(app)

//-------------------- GAME ---------------------
require('./app/routes/games.server.routes.js')(app)

//======================= START SERVER ======================
app.listen(8090);
console.log('testing server is listening on port 8090...');
