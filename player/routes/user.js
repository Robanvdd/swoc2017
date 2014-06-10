var db = require('../db');
var ModelUser = db.mongoose.model('User', db.SchemaUser);

/* 
* *****************
* MODULES TO EXPORT
* *****************
*/
module.exports.CreateDoc = CreateDoc;
module.exports.RetrieveAll = RetrieveAll;
module.exports.RetrieveById = RetrieveById;
module.exports.UpdateDoc = UpdateDoc;
module.exports.DeleteDoc = DeleteDoc;

/* 
* *********
* FUNCTIONS
* *********
*/
function CreateDoc(req, callback) {
	var instance = new ModelUser();
	instance.fullname = req.body.fullname;
	instance.email	  = req.body.email;
	instance.password = req.body.password;
	
	instance.save(function (err,doc) {
		if(err) callback(err);
		else callback(null, {"status":"OK","new_id":doc._id});
	});
}

function RetrieveAll(callback) {
	ModelUser.find({}, callback);
}

function RetrieveById(id, callback) {
	ModelUser.findOne({_id:id}, callback);
}

function UpdateDoc(req, callback) {
	var newValues = {
					"fullname":	req.body.fullname,
					"email":	req.body.email,
					"password":	req.body.password,
					};
	
	ModelUser.update( {_id:req.body.id}, {"$set":newValues}, function (err) {
		if(err) callback(err);
		else callback(null, {"status":"OK"});
	});
}

function DeleteDoc(id, callback) {
	ModelUser.remove( {_id:id}, function (err) {
		if(err) callback(err);
		else callback(null, {"status":"OK"});
	});
}