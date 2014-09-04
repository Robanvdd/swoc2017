var mongoose = require('mongoose'),
    bcrypt = require('bcryptjs'),
    SALT_WORK_FACTOR = 10;
var Schema = mongoose.Schema,
	ObjectId = Schema.ObjectId;

// SCHEMAS
var Game = new Schema({
	name: {type: String, required: true},
        bot1: {type: String, required: true},
        bot2: {type: String, required: true},
	startstate: {type: String, required: true},
	moves: {type: String, required: true}
	});
// User Schema
var userSchema = new Schema({
  username: { type: String, required: true, unique: true },
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true}
});
// Bcrypt middleware
userSchema.pre('save', function(next) {
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
userSchema.methods.comparePassword = function(candidatePassword, cb) {
	bcrypt.compare(candidatePassword, this.password, function(err, isMatch) {
		if(err) return cb(err);
		cb(null, isMatch);
	});
};

// Seed a user
var User = mongoose.model('User', userSchema);
var user = new User({ username: 'test', email: 'bob@example.com', password: 'test' });
user.save(function(err) {
  if(err) {
    console.log(err);
  } else {
    console.log('user: ' + user.username + " saved.");
  }
});
	
// EXPORTS
module.exports.mongoose 		= mongoose;
module.exports.SchemaGame 		= Game;
module.exports.SchemaUser		= userSchema;
