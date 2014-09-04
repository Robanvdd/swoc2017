var db = require('./db');
var ModelGame = db.mongoose.model('Game', db.SchemaGame);

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
	var instance = new ModelGame();
	instance.name = req.body.name;
	instance.bot1 = req.body.bot1;
	instance.bot2 = req.body.bot2;
	instance.startstate = req.body.startstate;
	instance.moves = req.body.moves;
	
	instance.save(function (err,doc) {
		if(err) callback(err);
		else callback(null, {"status":"OK","new_id":doc._id});
	});
}

function RetrieveAll(callback) {
	ModelGame.find({}, callback);
}

function RetrieveById(id, callback) {
	ModelGame.findOne({_id:id}, callback);
}

function UpdateDoc(req, callback) {
	var newValues = {
					"name":	req.body.name,
					"bot1":	req.body.bot1,
					"bot2":	req.body.bot2,
					"startstate":	req.body.startstate,
					"moves":	req.body.moves,
					};
	
	ModelGame.update( {_id:req.body.id}, {"$set":newValues}, function (err) {
		if(err) callback(err);
		else callback(null, {"status":"OK"});
	});
}

function DeleteDoc(id, callback) {
	ModelGame.remove( {_id:id}, function (err) {
		if(err) callback(err);
		else callback(null, {"status":"OK"});
	});
}
