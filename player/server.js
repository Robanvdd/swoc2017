/**
 * Module dependencies.
 */
var init = require('./config/init')(),
	config = require('./config/config'),
	mongoose = require('mongoose');

/**
 * Main application entry file.
 * Please note that the order of loading is important.
 */

// Bootstrap db connection
var db = mongoose.connect(config.db, function(err) {
	console.log('connected to database ' + config.db)
	if (err) {
		console.error('\x1b[31m', 'Could not connect to MongoDB!');
		console.log(err);
	}
});

// Initialize MySQL connection
// require('./config/connection.js')(function(){})

// Init the express application
var app = require('./config/express')(db);

// Bootstrap passport config
require('./config/passport')();

// Start the app by listening on <port>
app.listen(config.port);

// Expose app
exports = module.exports = app;

// Logging initialization
console.log('Swoc2017 server is listening on port ' + config.port);
setTimeout(function() {
	console.log('Swoc2017 server loaded, db: ' + JSON.stringify(db.connection.readyState));
	User = mongoose.model('User');
    mongoose = require('mongoose');
    passport = require('passport');
	var user = new User({
		username: 'admin',
		password: 'hue'
	}); //username, password, email

    // Then save the user 
    user.save(function(err) {
        if (err) {
            console.log('admin already exists (or there was an error in its creation)');
        } else {
            console.log('admin created succesfully');
        }
    });
}, 5000);
