//server.js
var application_root = __dirname,
	express = require('express'),
	path 	= require("path"),
	url 	= require("url"),
	request = require("request"),
	Game 	= require('./routes/game'),
	exec    = require('child_process').execFile,
	fs      = require('fs');

var app = express();

app.configure(function () {
	app.use(express.logger('dev'));
	app.use(express.json());
	app.use(express.urlencoded());
	app.use(express.bodyParser({
        uploadDir: application_root + '/tmp/uploads',
        keepExtensions: true
    }))
	app.use(app.router);
	app.use(express.static(path.join(application_root, "public")));
});

//---------------- BOT UPLOAD -------------------

app.post('/api/bot/upload/', function(req, res){
	console.log("application_root:" + application_root + ", fileName:" + req.files.fileName + ", file:" + req.files.file);
	setTimeout(
        function () {
            res.setHeader('Content-Type', 'text/html');
            if (req.files.length == 0 || req.files.file.size == 0)
                res.send({ msg: 'No file uploaded at ' + new Date().toString() });
            else {
                var file = req.files.file;
				var target_path = application_root + '/bots_upload/' + file.name
				console.log('file.path:' + file.path + ' target_path:' + target_path);
                fs.rename(file.path, target_path, function(err) {
					if (err)
						throw err;
					// Delete the temporary file, so that the explicitly set temporary upload dir does not get filled with unwanted files.
					fs.unlink(file.path, function() {
						if (err)
							throw err;
						//
					});
					res.writeHead('200');
					res.end();
				});
            }
        },
        (req.param('delay', 'yes') == 'yes') ? 2000 : -1
    );
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
	Game.RetrieveById(req.params.id, function(err, success) {
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

app.post('/api/game/botmove', function(req, res) {
	console.log("botmove start on "+ process.platform);
	console.log("cols: " + req.body.cols);
	console.log("AI level:" + req.body.AILevel)
	var AIboard = [ [  0,   0,   0,   0,   0, 100, 100, 100, 100],
					[  0,   0,   0,   0,   0,   0, 100, 100, 100],
					[  0,   0,   0,   0,   0,   0,   0, 100, 100],
					[  0,   0,   0,   0,   0,   0,   0,   0, 100],
					[  0,   0,   0,   0, 100,   0,   0,   0,   0],
					[100,   0,   0,   0,   0,   0,   0,   0,   0],
					[100, 100,   0,   0,   0,   0,   0,   0,   0],
					[100, 100, 100,   0,   0,   0,   0,   0,   0],
					[100, 100, 100, 100,   0,   0,   0,   0,   0] ];
	console.log(AIboard);
	var AIboardStackHeight = [
					[  0,   0,   0,   0,   0,   0,   0,   0,   0],
					[  0,   0,   0,   0,   0,   0,   0,   0,   0],
					[  0,   0,   0,   0,   0,   0,   0,   0,   0],
					[  0,   0,   0,   0,   0,   0,   0,   0,   0],
					[  0,   0,   0,   0,   0,   0,   0,   0,   0],
					[  0,   0,   0,   0,   0,   0,   0,   0,   0],
					[  0,   0,   0,   0,   0,   0,   0,   0,   0],
					[  0,   0,   0,   0,   0,   0,   0,   0,   0],
					[  0,   0,   0,   0,   0,   0,   0,   0,   0] ];
	console.log(AIboardStackHeight);

	var startOffset = -4;

	for (var j=0; j <= 8; j++) {
		var column = req.body.cols[j];
		for (var i=0; i< column.size; i++) {
			var stone = column.stones[i];
			//console.log("stone: " + stone.name +  " color=" + stone.color + " type:" + stone.type);
			var index = i;
			if ( startOffset > 0) {	
				index = i + startOffset;
			}
			//console.log("col: " + index + " index:" + j);
			if ( stone.color == "white") {
				switch (stone.type) {
					case "TZAAR"   : AIboard[index][j] = 3; AIboardStackHeight[index][j] = stone.height; break;
					case "TZARRAS" : AIboard[index][j] = 2; AIboardStackHeight[index][j] = stone.height; break;
					case "TOTTS"   : AIboard[index][j] = 1; AIboardStackHeight[index][j] = stone.height; break;
				}

			} else if ( stone.color == "black") {
				switch (stone.type) {
					case "TZAAR"   : AIboard[index][j] = -3; AIboardStackHeight[index][j] = stone.height; break;
					case "TZARRAS" : AIboard[index][j] = -2; AIboardStackHeight[index][j] = stone.height; break;
					case "TOTTS"   : AIboard[index][j] = -1; AIboardStackHeight[index][j] = stone.height; break;
				}
			}
		}
		startOffset++;
	}
	//console.log(AIboard);
	//console.log(AIboardStackHeight);

	var fs  = require('fs');

	if ( process.platform == "linux" ) {
		//newlines are not strictly neccesary but will improve readability
		var gameText = req.body.CPUcolor;
		gameText = gameText + '\n';
		for ( var i = 0; i < 9; i++) {
			for (var j = 0; j< 9; j++) {
				gameText = gameText + AIboard[i][j] + ' ';
			}
			gameText = gameText + '\n';
		}
		gameText = gameText + '\n';
		for ( var i = 0; i < 9; i++) {
			for (var j = 0; j< 9; j++) {
				gameText = gameText + AIboardStackHeight[i][j] + ' ';
			}
			gameText = gameText + '\n';
		}

		fs.writeFile("test", gameText, function(err) {
			if (err) {
				console.log(err);
				res.send({result: "error, Could not write to file"});
			} else {
				exec('./tzaar',  ['-b', 'test', '-t', '2', '-a', req.body.AILevel], function(err, data) {  
					console.log(err)
					console.log(data.toString());
					fs.readFile('test', function(err, data){
						if (err) {							
							console.log(err);
							res.send({result: "error, Could not read file"});
						} else {
							var lines = String(data).split('\n');
							var firstMoves = lines[0].split(' ');
							var firstMove = firstMoves[0] + firstMoves[1];
							var secondMoves = lines[1].split(' ');
							var secondMove = '';
							if (secondMoves.length > 1) {
								secondMove = secondMoves[1] + secondMoves[2];
							} else if ( secondMoves[0] == "-1") {
								secondMove = "PASS";
							}
							res.send(firstMove + secondMove);    
						}
					});
				});                
	    	}

		});
	} else {
		console.log( "Windows not implemented yet" )
		res.send({result: "error, Windows not implemented yet"});
	}
});

//======================= START SERVER ======================
app.listen(8090);
console.log('testing server is listening on port 8090...');
