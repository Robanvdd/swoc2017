var mongoose = require('mongoose'),
	User = mongoose.model('User');
	
/**
 * Require login routing middleware
 */
exports.requiresLogin = function(req, res, next) {
	if (!req.isAuthenticated()) {
		return res.status(401).send({
			message: 'User is not logged in'
		});
	}

	next();
};

/**
 * User authorizations routing middleware
 */
exports.hasAuthorization = function(roles) {
	var _this = this;

	return function(req, res, next) {
		_this.requiresLogin(req, res, function() {
			if (_.intersection(req.user.roles, roles).length) {
				return next();
			} else {
				return res.status(403).send({
					message: 'User is not authorized'
				});
			}
		});
	};
};

exports.createDoc = function(req, res) {
	var instance = new ModelUser();
	instance.username = req.body.username;
	instance.email	  = req.body.email;
	instance.password = req.body.password;
	
	instance.save(function (err,doc) {
		if(err) res(err);
		else res(null, {"status":"OK","new_id":doc._id});
	});
}

exports.retrieveAll = function(res) {
	ModelUser.find({}, res);
}

exports.retrieveById = function(id, res) {
	ModelUser.findOne({_id:id}, res);
}


exports.retrieveByName = function(username, res) {
	console.log('RetrieveByName, username:' + username.username);
	ModelUser.findOne({"username":username.username}, res);
}

exports.updateDoc = function(req, res) {
	var newValues = {
					"username":	req.body.username,
					"email":	req.body.email,
					"password":	req.body.password,
					};
	
	ModelUser.update( {_id:req.body.id}, {"$set":newValues}, function (err) {
		if(err) res(err);
		else res(null, {"status":"OK"});
	});
}

exports.deleteDoc = function(id, res) {
	ModelUser.remove( {_id:id}, function (err) {
		if(err) res(err);
		else res(null, {"status":"OK"});
	});
}