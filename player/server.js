//server.js
var application_root = __dirname,
	express = require('express'),
	path 	= require("path"),
	url 	= require("url"),
	request = require("request"),
	Game 	= require('./routes/game');

var app = express();

app.configure(function () {
	app.use(express.logger('dev'));
	app.use(express.json());
	app.use(express.urlencoded());
	app.use(app.router);
	app.use(express.static(path.join(application_root, "public")));
});


//-------------------- GAME --------------------
app.post('/api/game/create/', function(req, res){
	Game.CreateDoc(req, function(err, success) {
		if(err) throw err;
		else res.send(success);
	});
});

app.get('/api/game/retrieveall/', function(req, res) { 
	Game.RetrieveAll(function(err, success) {
		if(err) throw err;
		else res.send(success);
	});
});

app.get('/api/game/retrieveid/:id', function(req, res) { 
	Game.RetrieveById(/*"5383392a8b6dfb387e000001"*/req.params.id, function(err, success) {
		if(err) throw err;
		else res.send(success);
	});
});

app.post('/api/game/update', function(req, res) {
	Game.UpdateDoc(req, function(err, success) {
		if(err) throw err;
		else res.send(success);
	});
});

app.post('/api/game/delete', function(req, res) { 
	Game.DeleteDoc(req.body.id, function(err, success) {
		if(err) throw err;
		else res.send(success);
	});
});

//======================= START SERVER ======================
app.listen(8090);
console.log('testing server is listening on port 8090...');
