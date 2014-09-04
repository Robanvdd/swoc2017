/**
 * Module dependencies.
 */
var games = require('../../app/controllers/games');

module.exports = function(app) {

	app.get('/api/game/retrieveall/', function(req, res) { 
		games.retrieveAll(function(err, success) {
			if(err) throw err;
			else res.send(success);
		});
	});

	app.get('/api/game/retrieveid/:id', function(req, res) { 
		games.retrieveById(req.params.id, function(err, success) {
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
};