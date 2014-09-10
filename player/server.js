/**
 * Module dependencies.
 */
var init = require('./config/init')(),
	config = require('./config/config'),
	mongoose = require('mongoose'),
    users = require('./app/controllers/users');;

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

// Init the express application
var app = require('./config/express')(db);

// Bootstrap passport config
require('./config/passport')();

// Start the app by listening on <port>
app.listen(config.port);

// Expose app
exports = module.exports = app;

// Logging initialization
console.log('testing server is listening on port ' + config.port);



// TEST CODE
var User = mongoose.model('User'),
    user1 = new User({username: 'Dumb', password: 'Dumb'}),
    user2 = new User({username: 'Dumber', password: 'Dumber'});
    user3 = new User({username: 'Dumbest', password: 'Dumbest'});
user1.save(function(err) {
    if (err) {
        console.log('Create Dumb failed. ' + err);
    } else {
        console.log('User Dumb created');
    }
})
user2.save(function(err) {
    if (err) {
        console.log('Create Dumber failed. ' + err);
    } else {
        console.log('User Dumber created');
    }
})
user3.save(function(err) {
    if (err) {
        console.log('Create Dumbest failed. ' + err);
    } else {
        console.log('User Dumbest created');
    }
})