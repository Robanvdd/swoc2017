
/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
    bcrypt = require('bcryptjs'),
	Schema = mongoose.Schema,
    SALT_WORK_FACTOR = 10;

/**
 * User Schema
 */
var UserSchema = new Schema({
	username: { 
		type: String, 
		required: true, 
		unique: true 
	},
	email: { 
		type: String,
		required: true, 
		unique: true 
	},
	password: { 
		type: String, 
		required: true
	}
});


// Bcrypt middleware
UserSchema.pre('save', function(next) {
	var user = this;

	if(!user.isModified('password')) return next();

	bcrypt.genSalt(SALT_WORK_FACTOR, function(err, salt) {
		if(err) return next(err);

		bcrypt.hash(user.password, salt, function(err, hash) {
			if(err) return next(err);
			user.password = hash;
			next();
		});
	});
});

// Password verification
exports.comparePassword = function(candidatePassword, cb) {
	bcrypt.compare(candidatePassword, this.password, function(err, isMatch) {
		if(err) return cb(err);
		cb(null, isMatch);
	});
};

mongoose.model('User', UserSchema);