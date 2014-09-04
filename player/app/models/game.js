var db = require('./db');
var ModelGame = db.mongoose.model('Game', db.SchemaGame);

/* 
* *****************
* MODULES TO EXPORT
* *****************
*/
module.exports.RetrieveAll = RetrieveAll;
module.exports.RetrieveById = RetrieveById;

/* 
* *********
* FUNCTIONS
* *********
*/
function RetrieveAll(callback) {
	ModelGame.find({}, callback);
}

function RetrieveById(id, callback) {
	ModelGame.findOne({_id:id}, callback);
}
