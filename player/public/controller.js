var meanControllers = angular.module('meanControllers', ['ngAnimate']);

meanControllers.factory("GameLibrary", function() {
	var gameLibrary = {};
	gameLibrary.numberOfSides = 6;
	gameLibrary.size = 240;
	gameLibrary.Xcenter = 275;
	gameLibrary.Ycenter = 275;
	gameLibrary.stoneRadius = 20;
	
	gameLibrary.unit_distance = (gameLibrary.size / 4);
	// Vertical |
	// NW_SE \
	// NE_SW /
	// ((starting at top))
	gameLibrary.vertical_X = 0;
	gameLibrary.vertical_Y = gameLibrary.unit_distance;
	
	gameLibrary.NW_SE_X = gameLibrary.unit_distance * Math.cos(0.5* 2 * Math.PI / gameLibrary.numberOfSides);
	gameLibrary.NW_SE_Y = gameLibrary.unit_distance * Math.sin(0.5* 2 * Math.PI / gameLibrary.numberOfSides);

	gameLibrary.NE_SW_X = gameLibrary.unit_distance * Math.cos(5.5* 2 * Math.PI / gameLibrary.numberOfSides);
	gameLibrary.NE_SW_Y = gameLibrary.unit_distance * Math.sin(5.5* 2 * Math.PI / gameLibrary.numberOfSides);

	gameLibrary.colA = {
		name : "A",
		size : 5,
		Xcoord : gameLibrary.Xcenter +  (gameLibrary.size) * Math.cos((3.5)* 2 * Math.PI / gameLibrary.numberOfSides),
		Ycoord : gameLibrary.Ycenter +  (gameLibrary.size) * Math.sin((3.5)* 2 * Math.PI / gameLibrary.numberOfSides),
		stones: []
	};

	gameLibrary.colB = {
		name : "B",
		size : 6,
		Xcoord : gameLibrary.colA.Xcoord + gameLibrary.NE_SW_X,
		Ycoord : gameLibrary.colA.Ycoord + gameLibrary.NE_SW_Y,
		stones: []
	};

	gameLibrary.colC = {
		name : "C",
		size : 7,
		Xcoord : gameLibrary.colB.Xcoord + gameLibrary.NE_SW_X,
		Ycoord : gameLibrary.colB.Ycoord + gameLibrary.NE_SW_Y,
		stones: []
	};

	gameLibrary.colD = {
		name : "D",
		size : 8,
		Xcoord : gameLibrary.colC.Xcoord + gameLibrary.NE_SW_X,
		Ycoord : gameLibrary.colC.Ycoord + gameLibrary.NE_SW_Y,
		stones: []
	};

	gameLibrary.colE = {
		name : "E",
		size : 9,
		Xcoord : gameLibrary.colD.Xcoord + gameLibrary.NE_SW_X,
		Ycoord : gameLibrary.colD.Ycoord + gameLibrary.NE_SW_Y,
		stones: []
	};

	gameLibrary.colF = {
		name : "F",
		size : 8,
		Xcoord : gameLibrary.colE.Xcoord + gameLibrary.NW_SE_X,
		Ycoord : gameLibrary.colE.Ycoord + gameLibrary.NW_SE_Y,
		stones: []
	};

	gameLibrary.colG = {
		name : "G",
		size : 7,
		Xcoord : gameLibrary.colF.Xcoord + gameLibrary.NW_SE_X,
		Ycoord : gameLibrary.colF.Ycoord + gameLibrary.NW_SE_Y,
		stones: []
	};

	gameLibrary.colH = {
		name : "H",
		size : 6,
		Xcoord : gameLibrary.colG.Xcoord + gameLibrary.NW_SE_X,
		Ycoord : gameLibrary.colG.Ycoord + gameLibrary.NW_SE_Y,
		stones: []
	};

	gameLibrary.colI = {
		name : "I",
		size : 5,
		Xcoord : gameLibrary.colH.Xcoord + gameLibrary.NW_SE_X,
		Ycoord : gameLibrary.colH.Ycoord + gameLibrary.NW_SE_Y,
		stones: []
	};


	gameLibrary.cols = [gameLibrary.colA, gameLibrary.colB, gameLibrary.colC, gameLibrary.colD, gameLibrary.colE, gameLibrary.colF, gameLibrary.colG, gameLibrary.colH, gameLibrary.colI];
	angular.forEach(gameLibrary.cols, function(column, key) {
		for (var i=1; i<= column.size; i++) {
			column.stones.push({
				name : column.name + i,
				Xcoord : column.Xcoord,
				Ycoord : column.Ycoord + column.stones.length * gameLibrary.vertical_Y,
				color : null,
				height : null,
				type : null
			});
			console.log("added a stone to column " + column.name + ": " + column.stones[i-1].name 
			+ " at X:" + column.stones[i-1].Xcoord + " Y:" + column.stones[i-1].Ycoord);
		}
	});

	//NE_SW cols
	gameLibrary.NE_SWcolE1 = {name: "E1", size: 5, stones: [gameLibrary.colE.stones[0], gameLibrary.colD.stones[0], gameLibrary.colC.stones[0], gameLibrary.colB.stones[0], gameLibrary.colA.stones[0]]};
	gameLibrary.NE_SWcolF1 = {name: "F1", size: 6, stones: [gameLibrary.colF.stones[0], gameLibrary.colE.stones[1], gameLibrary.colD.stones[1], gameLibrary.colC.stones[1], gameLibrary.colB.stones[1], gameLibrary.colA.stones[1]]};
	gameLibrary.NE_SWcolG1 = {name: "G1", size: 7, stones: [gameLibrary.colG.stones[0], gameLibrary.colF.stones[1], gameLibrary.colE.stones[2], gameLibrary.colD.stones[2], gameLibrary.colC.stones[2], gameLibrary.colB.stones[2], gameLibrary.colA.stones[2]]};
	gameLibrary.NE_SWcolH1 = {name: "H1", size: 8, stones: [gameLibrary.colH.stones[0], gameLibrary.colG.stones[1], gameLibrary.colF.stones[2], gameLibrary.colE.stones[3], gameLibrary.colD.stones[3], gameLibrary.colC.stones[3], gameLibrary.colB.stones[3], gameLibrary.colA.stones[3]]};
	gameLibrary.NE_SWcolI1 = {name: "I1", size: 9, stones: [gameLibrary.colI.stones[0], gameLibrary.colH.stones[1], gameLibrary.colG.stones[2], gameLibrary.colF.stones[3], gameLibrary.colE.stones[4], gameLibrary.colD.stones[4], gameLibrary.colC.stones[4], gameLibrary.colB.stones[4], gameLibrary.colA.stones[4]]};
	gameLibrary.NE_SWcolI2 = {name: "I2", size: 8, stones: [gameLibrary.colI.stones[1], gameLibrary.colH.stones[2], gameLibrary.colG.stones[3], gameLibrary.colF.stones[4], gameLibrary.colE.stones[5], gameLibrary.colD.stones[5], gameLibrary.colC.stones[5], gameLibrary.colB.stones[5]]};
	gameLibrary.NE_SWcolI3 = {name: "I3", size: 7, stones: [gameLibrary.colI.stones[2], gameLibrary.colH.stones[3], gameLibrary.colG.stones[4], gameLibrary.colF.stones[5], gameLibrary.colE.stones[6], gameLibrary.colD.stones[6], gameLibrary.colC.stones[6]]};
	gameLibrary.NE_SWcolI4 = {name: "I4", size: 6, stones: [gameLibrary.colI.stones[3], gameLibrary.colH.stones[4], gameLibrary.colG.stones[5], gameLibrary.colF.stones[6], gameLibrary.colE.stones[7], gameLibrary.colD.stones[7]]};
	gameLibrary.NE_SWcolI5 = {name: "I5", size: 5, stones: [gameLibrary.colI.stones[4], gameLibrary.colH.stones[5], gameLibrary.colG.stones[6], gameLibrary.colF.stones[7], gameLibrary.colE.stones[8]]};
	gameLibrary.NE_SWcols = [gameLibrary.NE_SWcolE1, gameLibrary.NE_SWcolF1, gameLibrary.NE_SWcolG1, gameLibrary.NE_SWcolH1, gameLibrary.NE_SWcolI1, gameLibrary.NE_SWcolI2, gameLibrary.NE_SWcolI3, gameLibrary.NE_SWcolI4, gameLibrary.NE_SWcolI5];
	gameLibrary.NW_SEcolE1 = {name: "E1", size: 5, stones: [gameLibrary.colE.stones[0], gameLibrary.colF.stones[0], gameLibrary.colG.stones[0], gameLibrary.colH.stones[0], gameLibrary.colI.stones[0]]};
	gameLibrary.NW_SEcolD1 = {name: "D1", size: 6, stones: [gameLibrary.colD.stones[0], gameLibrary.colE.stones[1], gameLibrary.colF.stones[1], gameLibrary.colG.stones[1], gameLibrary.colH.stones[1], gameLibrary.colI.stones[1]]};
	gameLibrary.NW_SEcolC1 = {name: "C1", size: 7, stones: [gameLibrary.colC.stones[0], gameLibrary.colD.stones[1], gameLibrary.colE.stones[2], gameLibrary.colF.stones[2], gameLibrary.colG.stones[2], gameLibrary.colH.stones[2], gameLibrary.colI.stones[2]]};
	gameLibrary.NW_SEcolB1 = {name: "B1", size: 8, stones: [gameLibrary.colB.stones[0], gameLibrary.colC.stones[1], gameLibrary.colD.stones[2], gameLibrary.colE.stones[3], gameLibrary.colF.stones[3], gameLibrary.colG.stones[3], gameLibrary.colH.stones[3], gameLibrary.colI.stones[3]]};
	gameLibrary.NW_SEcolA1 = {name: "A1", size: 9, stones: [gameLibrary.colA.stones[0], gameLibrary.colB.stones[1], gameLibrary.colC.stones[2], gameLibrary.colD.stones[3], gameLibrary.colE.stones[4], gameLibrary.colF.stones[4], gameLibrary.colG.stones[4], gameLibrary.colH.stones[4], gameLibrary.colI.stones[4]]};
	gameLibrary.NW_SEcolA2 = {name: "A2", size: 8, stones: [gameLibrary.colA.stones[1], gameLibrary.colB.stones[2], gameLibrary.colC.stones[3], gameLibrary.colD.stones[4], gameLibrary.colE.stones[5], gameLibrary.colF.stones[5], gameLibrary.colG.stones[5], gameLibrary.colH.stones[5]]};
	gameLibrary.NW_SEcolA3 = {name: "A3", size: 7, stones: [gameLibrary.colA.stones[2], gameLibrary.colB.stones[3], gameLibrary.colC.stones[4], gameLibrary.colD.stones[5], gameLibrary.colE.stones[6], gameLibrary.colF.stones[6], gameLibrary.colG.stones[6]]};
	gameLibrary.NW_SEcolA4 = {name: "A4", size: 6, stones: [gameLibrary.colA.stones[3], gameLibrary.colB.stones[4], gameLibrary.colC.stones[5], gameLibrary.colD.stones[6], gameLibrary.colE.stones[7], gameLibrary.colF.stones[7]]};
	gameLibrary.NW_SEcolA5 = {name: "A5", size: 5, stones: [gameLibrary.colA.stones[4], gameLibrary.colB.stones[5], gameLibrary.colC.stones[6], gameLibrary.colD.stones[7], gameLibrary.colE.stones[8]]};
	gameLibrary.NW_SEcols = [gameLibrary.NW_SEcolE1, gameLibrary.NW_SEcolD1, gameLibrary.NW_SEcolC1, gameLibrary.NW_SEcolB1, gameLibrary.NW_SEcolA1, gameLibrary.NW_SEcolA2, gameLibrary.NW_SEcolA3, gameLibrary.NW_SEcolA4, gameLibrary.NW_SEcolA5];


	gameLibrary.loadGame = function() {
		console.log("Selected game state:" + gameLibrary.game.startstate);
		if (gameLibrary.game.startstate.length == 61) {
			var stoneIndex = 0;
			angular.forEach(gameLibrary.cols, function(column, key) {
				angular.forEach(column.stones, function(stone, key) {
					if ( stone.name != "E5" ) {
						stone.height = 1;
						var stoneState = gameLibrary.game.startstate[stoneIndex];
						switch ( stoneState ) {
							case "t" :
								stone.color = "white";
								stone.type = "TZAAR";
							break;
							case "z" :
								stone.color = "white";
								stone.type = "TZARRAS";
							break;
							case "o" :
								stone.color = "white";
								stone.type = "TOTTS";
							break;
							case "T" :
								stone.color = "black";
								stone.type = "TZAAR";
							break;
							case "Z" :
								stone.color = "black";
								stone.type = "TZARRAS";
							break;
							case "O" :
								stone.color = "black";
								stone.type = "TOTTS";
							break;
							default :
								stone.type = null;
								stone.color = null; 
							break;
						}
					}
					stoneIndex++;
				});
			});
		} else {
			console.log("invalid game state length, should be 61 chars long: " + gameLibrary.game.startstate.length);
		}
	}

	gameLibrary.loadMoves = function() {
		gameLibrary.game.moveStrings = [{ index: 0, moveString: "PASS", move_img: ""}];
		for ( var moveIndex = 0; moveIndex < (gameLibrary.game.moves.length/4); moveIndex++) {
			var move = gameLibrary.getMoveString(moveIndex);
			if (moveIndex == (gameLibrary.game.moves.length/4)-1) {
				//last move
				move.move_img = "./img/w.png"
			} else if (move.moveString != "PASS" ) {
				if (move.endstone.color == move.startstone.color) {
					move.move_img = "./img/shield_PNG1268.png";
					move.endstone.height = move.startstone.height + 1;
				} else {
					move.move_img = "./img/Crossed_gladii.png";
					move.endstone.height = move.startstone.height;
					move.endstone.color = move.startstone.color;
				}
				move.endstone.type = move.startstone.type;
				move.startstone.color = null;
				move.startstone.type = null;
			} else {
				move.move_img = "./img/clock-147257_640.png";
			}
			console.log("adding move " + move.index);
			gameLibrary.game.moveStrings.push(move);
		}
		//reset game
		gameLibrary.loadGame();
	};

	gameLibrary.getMoveString = function(moveIndex) {
		var move = gameLibrary.game.moves.substring(moveIndex*4, moveIndex*4 + 4)
		console.log("getMove: " + move);
		if (move != "PASS") {
			var startcol = gameLibrary.cols[move.charCodeAt(0) - "A".charCodeAt(0) ];
			//console.log("found startcol:" + startcol.name + "startcol:Xcoord" + startcol.Xcoord);
			var start = startcol.stones[move.charCodeAt(1) - "1".charCodeAt(0)];
			//console.log("found startstone: " + start.name + " X:" + start.Xcoord + " from move: " + move);

			var endcol = gameLibrary.cols[move.charCodeAt(2) - "A".charCodeAt(0)];
			var end = endcol.stones[move.charCodeAt(3) - "1".charCodeAt(0)];
			//console.log("found endstone: " + end.name + " from move: " + move);

			return { index: moveIndex+1, moveString: move, startstone: start, endstone: end };
		} else {
			return { index: moveIndex+1, moveString: move};
		}
	}

	gameLibrary.drawGame = function() {

		console.log("draw game");
		var cxt = document.getElementById('c').getContext('2d');
		cxt.clearRect(0,0,cxt.canvas.width,cxt.canvas.height);
		  
		//draw grey hexagon
		cxt.beginPath();
		cxt.moveTo (gameLibrary.Xcenter +  gameLibrary.size * Math.cos(0.5), gameLibrary.Ycenter +  gameLibrary.size *  Math.sin(0.5));          

		for (var i = 1; i <= gameLibrary.numberOfSides;i += 1) {
		    cxt.lineTo (gameLibrary.Xcenter + gameLibrary.size * Math.cos((i+0.5) * 2 * Math.PI / gameLibrary.numberOfSides), gameLibrary.Ycenter + gameLibrary.size * Math.sin((i+0.5) * 2 * Math.PI / gameLibrary.numberOfSides));
		}
		cxt.fillStyle = '#F0F0F0';
		cxt.strokeStyle = "#000000";
		cxt.lineWidth = 1;
		cxt.fill();
		//clear center piece
		cxt.fillStyle = '#FFFFFF';
		cxt.beginPath();
		cxt.moveTo (gameLibrary.Xcenter +  (gameLibrary.size/4) * Math.cos(0.5), gameLibrary.Ycenter +  (gameLibrary.size/4) *  Math.sin(0.5));          

		for (var i = 1; i <= gameLibrary.numberOfSides;i += 1) {
		    cxt.lineTo (gameLibrary.Xcenter + (gameLibrary.size/4) * Math.cos((i+0.5) * 2 * Math.PI / gameLibrary.numberOfSides), gameLibrary.Ycenter + (gameLibrary.size/4) * Math.sin((i+0.5) * 2 * Math.PI / gameLibrary.numberOfSides));
		}
		cxt.fill();


		//model = 
		//   A  B  C  D  E  F  G  H  I
		//              E1
		//           D1    F1
		//        C1    E2    G1
		//     B1    D2    F2    H1
		//  A1    C2    E3    G2    I1
		//     B2    D3    F3    H2
		//  A2    C3    E4    G3    I2
		//     B3    D4    F4    H3
		//  A3    C4    XX    G4    I3
		//     B4    D5    F5    H4
		//  A4    C5    E6    G5    I4
		//     B5    D6    F6    H5
		//  A5    C6    E7    G6    I5
		//     B6    D7    F7    H6
		//        C7    E8    G7
		//           D8    F8
		//              E9


		cxt.beginPath();
		//vertical lines
		cxt.moveTo (gameLibrary.colA.stones[0].Xcoord, gameLibrary.colA.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colA.stones[4].Xcoord, gameLibrary.colA.stones[4].Ycoord);
		cxt.moveTo (gameLibrary.colB.stones[0].Xcoord, gameLibrary.colB.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colB.stones[5].Xcoord, gameLibrary.colB.stones[5].Ycoord);
		cxt.moveTo (gameLibrary.colC.stones[0].Xcoord, gameLibrary.colC.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colC.stones[6].Xcoord, gameLibrary.colC.stones[6].Ycoord);
		cxt.moveTo (gameLibrary.colD.stones[0].Xcoord, gameLibrary.colD.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colD.stones[7].Xcoord, gameLibrary.colD.stones[7].Ycoord);

		cxt.moveTo (gameLibrary.colE.stones[0].Xcoord, gameLibrary.colE.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colE.stones[3].Xcoord, gameLibrary.colE.stones[3].Ycoord);
		cxt.moveTo (gameLibrary.colE.stones[5].Xcoord, gameLibrary.colE.stones[5].Ycoord);
		cxt.lineTo (gameLibrary.colE.stones[8].Xcoord, gameLibrary.colE.stones[8].Ycoord);

		cxt.moveTo (gameLibrary.colF.stones[0].Xcoord, gameLibrary.colF.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colF.stones[7].Xcoord, gameLibrary.colF.stones[7].Ycoord);
		cxt.moveTo (gameLibrary.colG.stones[0].Xcoord, gameLibrary.colG.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colG.stones[6].Xcoord, gameLibrary.colG.stones[6].Ycoord);
		cxt.moveTo (gameLibrary.colH.stones[0].Xcoord, gameLibrary.colH.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colH.stones[5].Xcoord, gameLibrary.colH.stones[5].Ycoord);
		cxt.moveTo (gameLibrary.colI.stones[0].Xcoord, gameLibrary.colI.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colI.stones[4].Xcoord, gameLibrary.colI.stones[4].Ycoord);

		//NW_SE lines
		cxt.moveTo (gameLibrary.colE.stones[0].Xcoord, gameLibrary.colE.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colI.stones[0].Xcoord, gameLibrary.colI.stones[0].Ycoord);
		cxt.moveTo (gameLibrary.colD.stones[0].Xcoord, gameLibrary.colD.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colI.stones[1].Xcoord, gameLibrary.colI.stones[1].Ycoord);
		cxt.moveTo (gameLibrary.colC.stones[0].Xcoord, gameLibrary.colC.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colI.stones[2].Xcoord, gameLibrary.colI.stones[2].Ycoord);
		cxt.moveTo (gameLibrary.colB.stones[0].Xcoord, gameLibrary.colB.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colI.stones[3].Xcoord, gameLibrary.colI.stones[3].Ycoord);

		cxt.moveTo (gameLibrary.colA.stones[0].Xcoord, gameLibrary.colA.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colD.stones[3].Xcoord, gameLibrary.colD.stones[3].Ycoord);
		cxt.moveTo (gameLibrary.colF.stones[4].Xcoord, gameLibrary.colF.stones[4].Ycoord);
		cxt.lineTo (gameLibrary.colI.stones[4].Xcoord, gameLibrary.colI.stones[4].Ycoord);

		cxt.moveTo (gameLibrary.colA.stones[1].Xcoord, gameLibrary.colA.stones[1].Ycoord);
		cxt.lineTo (gameLibrary.colH.stones[5].Xcoord, gameLibrary.colH.stones[5].Ycoord);
		cxt.moveTo (gameLibrary.colA.stones[2].Xcoord, gameLibrary.colA.stones[2].Ycoord);
		cxt.lineTo (gameLibrary.colG.stones[6].Xcoord, gameLibrary.colG.stones[6].Ycoord);
		cxt.moveTo (gameLibrary.colA.stones[3].Xcoord, gameLibrary.colA.stones[3].Ycoord);
		cxt.lineTo (gameLibrary.colF.stones[7].Xcoord, gameLibrary.colF.stones[7].Ycoord);
		cxt.moveTo (gameLibrary.colA.stones[4].Xcoord, gameLibrary.colA.stones[4].Ycoord);
		cxt.lineTo (gameLibrary.colE.stones[8].Xcoord, gameLibrary.colE.stones[8].Ycoord);

		//NE_SW lines
		cxt.moveTo (gameLibrary.colE.stones[0].Xcoord, gameLibrary.colE.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colA.stones[0].Xcoord, gameLibrary.colA.stones[0].Ycoord);
		cxt.moveTo (gameLibrary.colF.stones[0].Xcoord, gameLibrary.colF.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colA.stones[1].Xcoord, gameLibrary.colA.stones[1].Ycoord);
		cxt.moveTo (gameLibrary.colG.stones[0].Xcoord, gameLibrary.colG.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colA.stones[2].Xcoord, gameLibrary.colA.stones[2].Ycoord);
		cxt.moveTo (gameLibrary.colH.stones[0].Xcoord, gameLibrary.colH.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colA.stones[3].Xcoord, gameLibrary.colA.stones[3].Ycoord);

		cxt.moveTo (gameLibrary.colI.stones[0].Xcoord, gameLibrary.colI.stones[0].Ycoord);
		cxt.lineTo (gameLibrary.colF.stones[3].Xcoord, gameLibrary.colF.stones[3].Ycoord);
		cxt.moveTo (gameLibrary.colD.stones[4].Xcoord, gameLibrary.colD.stones[4].Ycoord);
		cxt.lineTo (gameLibrary.colA.stones[4].Xcoord, gameLibrary.colA.stones[4].Ycoord);

		cxt.moveTo (gameLibrary.colI.stones[1].Xcoord, gameLibrary.colI.stones[1].Ycoord);
		cxt.lineTo (gameLibrary.colB.stones[5].Xcoord, gameLibrary.colB.stones[5].Ycoord);
		cxt.moveTo (gameLibrary.colI.stones[2].Xcoord, gameLibrary.colI.stones[2].Ycoord);
		cxt.lineTo (gameLibrary.colC.stones[6].Xcoord, gameLibrary.colC.stones[6].Ycoord);
		cxt.moveTo (gameLibrary.colI.stones[3].Xcoord, gameLibrary.colI.stones[3].Ycoord);
		cxt.lineTo (gameLibrary.colD.stones[7].Xcoord, gameLibrary.colD.stones[7].Ycoord);
		cxt.moveTo (gameLibrary.colI.stones[4].Xcoord, gameLibrary.colI.stones[4].Ycoord);
		cxt.lineTo (gameLibrary.colE.stones[8].Xcoord, gameLibrary.colE.stones[8].Ycoord);
		cxt.stroke();


		//draw all stones
		angular.forEach(gameLibrary.cols, function(column, key) {
			angular.forEach(column.stones, function(stone, key) {
				if ( ! (gameLibrary.currentmove != null && gameLibrary.currentmove.startstone == stone) ) {
					gameLibrary.drawStone(stone.Xcoord, stone.Ycoord, stone.color, stone.type, stone.height);
				}
			});
		});


		if (gameLibrary.printlabels) {
			//label all points, with text to the right of the point
			cxt.font = "bold 14px sans-serif";
			cxt.fillStyle = '#C00000';

			angular.forEach(gameLibrary.cols, function(column, key) {
				angular.forEach(column.stones, function(stone, key) {
					if ( stone.name != "E5" ) {
						cxt.fillText(stone.name, stone.Xcoord+7, stone.Ycoord+7);
					}
				});
			});
		}
	};

	gameLibrary.drawStone = function(X, Y, color, type, height) {
		var cxt = document.getElementById('c').getContext('2d');
		var stripeHeight = height % 7; //14 is maximum legal height I believe 
		var bandHeight = (height - stripeHeight) / 7;
		var stripeColor = '#D0D0D0';
		if ( bandHeight >= 1) {
			stripeHeight++;
			stripeColor = '#FF6601'; //Sioux orange
			console.log("Orange!");
		}
		if ( bandHeight >= 2) {
			stripeColor = '#FFD700'; //Gold
			console.log("Gold!");
		}
		if ( gameLibrary.selectedStone != null &&  X == gameLibrary.selectedStone.Xcoord && Y == gameLibrary.selectedStone.Ycoord ) {
			stripeColor = '#0000FF'; //BLUE
		} else if ( gameLibrary.isValidTargetStone(X,Y) == "true" ) {
			stripeColor = '#00FF00'; //GREEN
		}
		cxt.strokeStyle = stripeColor;
		cxt.lineWidth = 3;
		var radius = gameLibrary.stoneRadius;	
		var mainColor = null;
		if ( color == "white" ) {
			//draw white stone
			mainColor = '#FFFFFF';
		} else if ( color == "black" ) {
			//draw black stone
			mainColor = '#000000';
		} else {
			return;
		}
		//draw shadow first
		cxt.globalAlpha = 0.5;
		cxt.fillStyle = '#000000';
		for (var i = 1; i <= height; i++) {
			cxt.beginPath();
			cxt.arc(X+(3*i), Y+(3*i), radius, 0, 2 * Math.PI, false);
			cxt.fill();
		}
		cxt.globalAlpha = 1.0;
		//draw stone
		cxt.fillStyle = mainColor;
		cxt.beginPath();
		cxt.arc(X, Y, radius, 0, 2 * Math.PI, false);
		cxt.fill(); cxt.stroke();
		switch ( type ) {
			case "TZAAR" :
				//console.log("TZAAR stone type at " + stone.name );
				//draw outer circle
				cxt.fillStyle = stripeColor;
				cxt.beginPath();
				cxt.arc(X, Y, radius*0.7, 0, 2 * Math.PI, false);
				cxt.fill();
				cxt.fillStyle = mainColor;
				cxt.beginPath();
				cxt.arc(X, Y, radius*0.5, 0, 2 * Math.PI, false);
				cxt.fill();
				//draw inner circle
				cxt.beginPath();
				cxt.fillStyle = stripeColor;
				cxt.arc(X, Y, radius*0.3, 0, 2 * Math.PI, false);
				cxt.fill();
			break;
			case "TZARRAS" :
				//console.log("TZARRAS stone type at " + stone.name );
				//draw inner circle
				cxt.beginPath();
				cxt.fillStyle = stripeColor;
				cxt.arc(X, Y, radius*0.4, 0, 2 * Math.PI, false);
				cxt.fill();
			break;
			case "TOTTS" :
				//console.log("TOTTS stone type at " + stone.name );
			break;
			default:
				console.log("Unknown stone type at " + stone.name );
			break;
		}
		if (stripeHeight > 1) {
			cxt.fillStyle = stripeColor;
			for (var i = 0; i < stripeHeight; i++) {
				//console.log(color + " stone @ X: " + X + ", Y:" + Y + ", height: " + height);
				var angle = (Math.PI*1.5) + i *( (Math.PI*2) / (stripeHeight) );
				var offset = Math.PI/15;
				cxt.beginPath();
				cxt.moveTo(X,Y);
				cxt.arc(X,Y, radius*0.9, angle - offset, angle + offset, false);
				cxt.fill()
			}
		}
	}

	gameLibrary.isValidTargetStone =  function(X, Y) {
		var result = "false";
		angular.forEach(gameLibrary.validTargetStones, function(stone, key) {
			if ( stone.Xcoord == X && stone.Ycoord == Y ) {
				result = "true";
			}
		});
		return result;
	}

	gameLibrary.processMoves = function(moveCount) {
		gameLibrary.loadGame();
		for ( var moveIndex = 0; moveIndex <= moveCount; moveIndex++) {
			var move = gameLibrary.game.moveStrings[moveIndex];
			console.log("processing move: " +  move.moveString);
			if (move.moveString != "PASS" ) {
				if (move.endstone.color == move.startstone.color) {
					move.endstone.height = move.endstone.height + move.startstone.height;
				} else {
					move.endstone.height = move.startstone.height;
					move.endstone.color = move.startstone.color;
				}
				move.endstone.type = move.startstone.type;
				move.startstone.color = null;
				move.startstone.type = null;
			}
		}
		gameLibrary.updateStoneCount();
		gameLibrary.checkVictory();
	};

	gameLibrary.checkVictory = function() {
		var victory = false;
		if ( gameLibrary.game.whiteTzaarCount == 0 || 
			gameLibrary.game.whiteTzarrasCount == 0 ||
			gameLibrary.game.whiteTottsCount == 0 ) {
			gameLibrary.winner = gameLibrary.game.bot2;
			victory = true;
		} else if (gameLibrary.game.blackTzaarCount == 0 ||
			gameLibrary.game.blackTzarrasCount == 0 ||
			gameLibrary.game.blackTottsCount == 0 ) {
			gameLibrary.winner = gameLibrary.game.bot1;
			victory = true;
		} else if ( false ) {//no more legal moves
			victory = true;
		}
		if ( victory ) {
			gameLibrary.game.moveStrings.push({ index: gameLibrary.game.moveStrings.length, moveString: "PASS", move_img: "./img/w.png"});
			gameLibrary.currentmoveIndex++;
		}
	}

	gameLibrary.updateStoneCount = function() {
		gameLibrary.game.whiteTzaarCount = 0;
		gameLibrary.game.whiteTzarrasCount = 0;
		gameLibrary.game.whiteTottsCount = 0;
		gameLibrary.game.blackTzaarCount = 0;
		gameLibrary.game.blackTzarrasCount = 0;
		gameLibrary.game.blackTottsCount = 0;
		angular.forEach(gameLibrary.cols, function(column, key) {
			angular.forEach(column.stones, function(stone, key) {
				if ( stone.color == "white" ) {
					switch ( stone.type ) {
						case "TZAAR" :
							gameLibrary.game.whiteTzaarCount++;
						break;
						case "TZARRAS" :
							gameLibrary.game.whiteTzarrasCount++;
						break;
						case "TOTTS" :
							gameLibrary.game.whiteTottsCount++;
						break;
					}
				} else if ( stone.color == "black") {
					switch ( stone.type ) {
						case "TZAAR" :
							gameLibrary.game.blackTzaarCount++;
						break;
						case "TZARRAS" :
							gameLibrary.game.blackTzarrasCount++;
						break;
						case "TOTTS" :
							gameLibrary.game.blackTottsCount++;
						break;
					}
				}
			});
		});
	};


	gameLibrary.updateActiveColor = function(applyFlag) {
		var color = "";
		if ( gameLibrary.winner != null ) {
			color = "Game Over";
		} else 	if ( (Math.floor((gameLibrary.currentmoveIndex+1)/2) % 2) == 0) {
				color = "white";
		} else {
				color = "black";
		}
		gameLibrary.activeColor = color;
		console.log("gameLibrary.currentmoveIndex : " + gameLibrary.currentmoveIndex + " activeColor: " + gameLibrary.activeColor);
	};

	gameLibrary.clickGame = function(event) {
		console.log("clickGame, event: " + event + " pageX: " + event.pageX + " pageY: " + event.pageY);
		var canvasX = event.pageX - event.target.offsetLeft;
		var canvasY = event.pageY - event.target.offsetTop;
		console.log("clickGame, event: " + event + " canvasX : " + canvasX  + " canvasY : " + canvasY );
		var performMove = false;
		var validTargetStone = null;
		//did we click on a valid target?
		angular.forEach(gameLibrary.validTargetStones, function(stone, key) {
			if ( Math.abs(stone.Xcoord - canvasX) < gameLibrary.stoneRadius && Math.abs(stone.Ycoord - canvasY) < gameLibrary.stoneRadius) {
				console.log("clicked valid target stone: " + stone.name);
				validTargetStone = stone;
				performMove = true;
			}
		});
		if (performMove == true) {
			var move = {};
			move.endstone = validTargetStone;
			move.startstone = gameLibrary.selectedStone;
			if (move.endstone.color != move.startstone.color) {
				move.move_img = "./img/Crossed_gladii.png";
			} else {
				move.move_img = "./img/shield_PNG1268.png";
			}
			move.index = gameLibrary.game.moveStrings.length;
			gameLibrary.game.moves = gameLibrary.game.moves + "" + move.startstone.name + move.endstone.name;
			gameLibrary.game.moveStrings.push(move);
			gameLibrary.selectedStone = null;
			gameLibrary.validTargetStones = [];
			
			gameLibrary.currentmoveIndex++;
			gameLibrary.currentmove = gameLibrary.game.moveStrings[gameLibrary.currentmoveIndex];
			var startTime = (new Date()).getTime();
			gameLibrary.isAnimating = true;
			gameLibrary.animate( startTime);
		} else {
			gameLibrary.selectedStone = null;
			gameLibrary.validTargetStones = [];
			var selectedVerticalColumn = null;
			angular.forEach(gameLibrary.cols, function(column, key) {
				angular.forEach(column.stones, function(stone, key) {
					if ( Math.abs(stone.Xcoord - canvasX) < gameLibrary.stoneRadius && Math.abs(stone.Ycoord - canvasY) < gameLibrary.stoneRadius) {
						console.log("clicked stone: " + stone.name);
						if ( stone.color == gameLibrary.activeColor) {
							gameLibrary.selectedStone = stone;
							selectedVerticalColumn = column;
						}
					}
				});
			});
			if (gameLibrary.selectedStone != null) {
				var targetX = gameLibrary.selectedStone.Xcoord;
				var targetY = gameLibrary.selectedStone.Ycoord;
				var mayDefend = ((gameLibrary.currentmoveIndex+1) % 2 == 1) && (gameLibrary.currentmoveIndex != 0);
				console.log("moveIndex: " + gameLibrary.currentmoveIndex + " mayDefend: " + mayDefend);
				//get vertical targets
				var verticalIndex = 0;
				for (var i = 0; i < selectedVerticalColumn.size; i++) {
					if ( selectedVerticalColumn.stones[i] == gameLibrary.selectedStone) {
						verticalIndex = i;
					}
				}
				for (var i = verticalIndex -1; i >= 0; i--) {
					var stone = selectedVerticalColumn.stones[i];
					if ( stone.name == "E5" ) break;
					if ( stone.color != null ) {
						if ((mayDefend && stone.color == gameLibrary.selectedStone.color) || (stone.height <= gameLibrary.selectedStone.height && stone.color != gameLibrary.selectedStone.color)) {
							console.log("valid target vertical stone: " + stone.name + " X:" + stone.Xcoord + " Y:" + stone.Ycoord);
							gameLibrary.validTargetStones.push(stone);
						}
						break;
					}
				}
				for (var i = verticalIndex +1; i < selectedVerticalColumn.size; i++) {
					var stone = selectedVerticalColumn.stones[i];
					if ( stone.name == "E5" ) break;
					if ( stone.color != null ) {
						if ((mayDefend && stone.color == gameLibrary.selectedStone.color) || (stone.height <= gameLibrary.selectedStone.height && stone.color != gameLibrary.selectedStone.color)) {
							console.log("valid target vertical stone: " + stone.name + " X:" + stone.Xcoord + " Y:" + stone.Ycoord);
							gameLibrary.validTargetStones.push(stone);
						}
						break;
					}
				}
				//get NE_SW targets
				var selectedNE_SWColumn = null;
				var NE_SWIndex = 0;

				angular.forEach(gameLibrary.NE_SWcols, function(column, key) {
					for (var stoneIndex = 0; stoneIndex < column.stones.length; stoneIndex++) {
						var stone = column.stones[stoneIndex];
						if ( stone == gameLibrary.selectedStone ) {
							selectedNE_SWColumn = column;
							NE_SWIndex = stoneIndex;
						}
					}
				});
				for (var i = NE_SWIndex -1; i >= 0; i--) {
					var stone = selectedNE_SWColumn.stones[i];
					if ( stone.name == "E5" ) break;
					if ( stone.color != null ) {
						if ((mayDefend && stone.color == gameLibrary.selectedStone.color) || (stone.height <= gameLibrary.selectedStone.height && stone.color != gameLibrary.selectedStone.color)) {
							console.log("valid target NE_SW stone: " + stone.name );
							gameLibrary.validTargetStones.push(stone);
						}
						break;
					}
				}
				for (var i = NE_SWIndex +1; i < selectedNE_SWColumn.size; i++) {
					var stone = selectedNE_SWColumn.stones[i];
					if ( stone.name == "E5" ) break;
					if ( stone.color != null ) {
						if ((mayDefend && stone.color == gameLibrary.selectedStone.color) || (stone.height <= gameLibrary.selectedStone.height && stone.color != gameLibrary.selectedStone.color)) {
							console.log("valid target NE_SW stone: " + stone.name );
							gameLibrary.validTargetStones.push(stone);
						}
						break;
					}
				}
				//get NW_SE targets
				var selectedNW_SEColumn = null;
				var NW_SEIndex = 0;

				angular.forEach(gameLibrary.NW_SEcols, function(column, key) {
					for (var stoneIndex = 0; stoneIndex < column.stones.length; stoneIndex++) {
						var stone = column.stones[stoneIndex];
						if ( stone == gameLibrary.selectedStone ) {
							selectedNW_SEColumn = column;
							NW_SEIndex = stoneIndex;
						}
					}
				});
				for (var i = NW_SEIndex -1; i >= 0; i--) {
					var stone = selectedNW_SEColumn.stones[i];
					if ( stone.name == "E5" ) break;
					if ( stone.color != null ) {
						if ((mayDefend && stone.color == gameLibrary.selectedStone.color) || (stone.height <= gameLibrary.selectedStone.height && stone.color != gameLibrary.selectedStone.color)) {
							console.log("valid target NW_SE stone: " + stone.name );
							gameLibrary.validTargetStones.push(stone);
						}
						break;
					}
				}
				for (var i = NW_SEIndex +1; i < selectedNW_SEColumn.size; i++) {
					var stone = selectedNW_SEColumn.stones[i];
					if ( stone.name == "E5" ) break;
					if ( stone.color != null ) {
						if ((mayDefend && stone.color == gameLibrary.selectedStone.color) || (stone.height <= gameLibrary.selectedStone.height && stone.color != gameLibrary.selectedStone.color)) {
							console.log("valid target NW_SE stone: " + stone.name );
							gameLibrary.validTargetStones.push(stone);
						}
						break;
					}
				}
			}
			gameLibrary.drawGame();
		}
	}

	gameLibrary.increaseMoveAndUpdate = function() {
		//update next move
        if ( gameLibrary.currentmoveIndex < (gameLibrary.game.moves.length/4)) {
			gameLibrary.currentmoveIndex++;
			gameLibrary.currentmove = gameLibrary.game.moveStrings[gameLibrary.currentmoveIndex];
			var startTime = (new Date()).getTime();
			gameLibrary.isAnimating = true;
			gameLibrary.drawGame();
			gameLibrary.animate( startTime);
		}
	};

	window.requestAnimFrame = (function(callback) {
        return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame ||
        function(callback) {
          window.setTimeout(callback, 1000 / 60);
        };
      })();

	gameLibrary.animate = function( startTime) {
		
		var cxt = document.getElementById('cmove').getContext('2d');
		cxt.clearRect(0,0,cxt.canvas.width,cxt.canvas.height);
		if (gameLibrary.currentmove == null || gameLibrary.currentmove.moveString == "PASS") {
			console.log("done animating, currentmoveIndex:" + gameLibrary.currentmoveIndex);
			/*gameLibrary.isAnimating = false;
			console.log("Calling processMoves from animate function start");
			gameLibrary.processMoves(gameLibrary.currentmoveIndex);
			gameLibrary.updateActiveColor(false);
			gameLibrary.drawGame();
			return;*/
		} else {

	        // update
	        var time = (new Date()).getTime() - startTime;
			var period = 1000;

			var startX = gameLibrary.currentmove.startstone.Xcoord;
			var startY = gameLibrary.currentmove.startstone.Ycoord;
			var endX = gameLibrary.currentmove.endstone.Xcoord;
			var endY = gameLibrary.currentmove.endstone.Ycoord;
			//console.log("animate:  Xcoord: " + gameLibrary.currentmove.startstone.Xcoord );
			//console.log("endX: " + endX + " startX: " + startX + " time: " + time + " time/period " + time/period);
			var currentX = startX - ((startX - endX) * (time/period)); 
			var currentY = startY - ((startY - endY) * (time/period));

			//gameLibrary.drawGame();
			
			//console.log("animate, currentmove=" + gameLibrary.currentmove.index + "X:" + currentX + " Y:" + currentY + " color:" + gameLibrary.currentmove.startstone.color);
			gameLibrary.drawStone(currentX, currentY, gameLibrary.currentmove.startstone.color, gameLibrary.currentmove.startstone.type, gameLibrary.currentmove.startstone.height, cxt);
		}
	        // request new frame
		if ( time < period ) {
			//console.log("do another animate");
			requestAnimFrame(function() {
			  gameLibrary.animate( startTime);
			});
		} else {
			console.log("done animating, currentmoveIndex:" + gameLibrary.currentmoveIndex);
			cxt.clearRect(0,0,cxt.canvas.width,cxt.canvas.height);
			gameLibrary.isAnimating = false;
			console.log("Calling processMoves from animate function end");
			gameLibrary.processMoves(gameLibrary.currentmoveIndex);
			gameLibrary.updateActiveColor(true);
			gameLibrary.drawGame();
			if(gameLibrary.isPlaying) {
				gameLibrary.increaseMoveAndUpdate();
			}
		}
	};

	gameLibrary.currentmoveIndex = 0;

	return gameLibrary;
});

meanControllers.controller('PlayvCPUCtrl', ['$scope', '$http', '$location', 'GameLibrary', function ($scope, $http, $location, GameLibrary) {
	$scope.getCPUMove = function(columns, cpu) {
		$http.post('/api/game/botmove/', {cols: columns, CPUcolor: cpu })
			.success(function(data){
				console.log("Cpu move(s):" + data);
				//GameLibrary.game.moves = GameLibrary.game.moves + data;
				$scope.game.moves = $scope.game.moves + data;
				GameLibrary.loadMoves();
				GameLibrary.increaseMoveAndUpdate();
			})
			.error(function(data) {
				console.log(data);
			});
	}
	GameLibrary.game = {startstate: "oOOOOozZZZooztTTzooztoOtzoOZTO otzoOZToOTZOOZttTZOOzzzZOooooO"};	
	$scope.game = GameLibrary.game;
	$scope.game.moveStrings = [{ index: 0, moveString: "PASS", move_img: ""}];
	GameLibrary.game.moves = "";
	$scope.game.moves = GameLibrary.game.moves;
	GameLibrary.loadGame();
	GameLibrary.drawGame();

	$scope.clickGame = function(event) {
		console.log("clickgame");
		GameLibrary.clickGame(event);
	};
	$scope.$watch('game', function() {
		console.log("activeColor changed");
		if ( GameLibrary.activeColor == "white") {
			$scope.getCPUMove(GameLibrary.cols, $scope.CPUcolor);
		}
	});
	$scope.game.bot1 = "CPU";
	$scope.game.bot2 = "Player"
	$scope.CPUcolor = 1;
	$scope.getCPUMove(GameLibrary.cols, $scope.CPUcolor);
}]);

//CONTROLLER FOR mod_list.html
meanControllers.controller('ListCtrl', ['$scope', '$http', '$location', function ($scope, $http, $location) {
	$scope.getAll = function() {
		$http.get('/api/game/retrieveall/')
			.success(function(data){
				$scope.games = data;
			})
			.error(function(data) {
				console.log(data);
			});
	}
	$scope.deleteSubmit = function($id) {
                console.log($id);
                var data = { id : $id};
		$http.post('/api/game/delete/', data).success(function(){
			$location.path("/");
			$scope.getAll();
		})
		.error(function(data) {
			console.log(data);
		});
	}
	$scope.getAll();
}]);

//CONTROLLER FOR mod_play.html
meanControllers.controller('PlayCtrl', ['$scope', '$http', '$location', function ($scope, $http, $location) {
    console.log("PlayCtrl");
	$scope.game = {startstate: "oOOOOozZZZooztTTzooztoOtzoOZTO otzoOZToOTZOOZttTZOOzzzZOooooO"};
	$scope.game.moveStrings = [{ index: 0, moveString: "PASS", move_img: ""}];
	$scope.game.moves = "";
	$scope.game.bot1 = "White";
	$scope.game.bot2 = "Black";
	$scope.game.name = "My Game";
    $scope.numberOfSides = 6;
    $scope.size = 240;
    $scope.Xcenter = 275;
    $scope.Ycenter = 275;
	$scope.stoneRadius = 20;
	
	$scope.unit_distance = ($scope.size / 4);
	// Vertical |
	// NW_SE \
	// NE_SW /
	// ((starting at top))
	$scope.vertical_X = 0;
	$scope.vertical_Y = $scope.unit_distance;
	
	$scope.NW_SE_X = $scope.unit_distance * Math.cos(0.5* 2 * Math.PI / $scope.numberOfSides);
	$scope.NW_SE_Y = $scope.unit_distance * Math.sin(0.5* 2 * Math.PI / $scope.numberOfSides);

	$scope.NE_SW_X = $scope.unit_distance * Math.cos(5.5* 2 * Math.PI / $scope.numberOfSides);
	$scope.NE_SW_Y = $scope.unit_distance * Math.sin(5.5* 2 * Math.PI / $scope.numberOfSides);

	$scope.colA = {
		name : "A",
		size : 5,
		Xcoord : $scope.Xcenter +  ($scope.size) * Math.cos((3.5)* 2 * Math.PI / $scope.numberOfSides),
		Ycoord : $scope.Ycenter +  ($scope.size) * Math.sin((3.5)* 2 * Math.PI / $scope.numberOfSides),
		stones: []
	};

	$scope.colB = {
		name : "B",
		size : 6,
		Xcoord : $scope.colA.Xcoord + $scope.NE_SW_X,
		Ycoord : $scope.colA.Ycoord + $scope.NE_SW_Y,
		stones: []
	};

	$scope.colC = {
		name : "C",
		size : 7,
		Xcoord : $scope.colB.Xcoord + $scope.NE_SW_X,
		Ycoord : $scope.colB.Ycoord + $scope.NE_SW_Y,
		stones: []
	};

	$scope.colD = {
		name : "D",
		size : 8,
		Xcoord : $scope.colC.Xcoord + $scope.NE_SW_X,
		Ycoord : $scope.colC.Ycoord + $scope.NE_SW_Y,
		stones: []
	};

	$scope.colE = {
		name : "E",
		size : 9,
		Xcoord : $scope.colD.Xcoord + $scope.NE_SW_X,
		Ycoord : $scope.colD.Ycoord + $scope.NE_SW_Y,
		stones: []
	};

	$scope.colF = {
		name : "F",
		size : 8,
		Xcoord : $scope.colE.Xcoord + $scope.NW_SE_X,
		Ycoord : $scope.colE.Ycoord + $scope.NW_SE_Y,
		stones: []
	};

	$scope.colG = {
		name : "G",
		size : 7,
		Xcoord : $scope.colF.Xcoord + $scope.NW_SE_X,
		Ycoord : $scope.colF.Ycoord + $scope.NW_SE_Y,
		stones: []
	};

	$scope.colH = {
		name : "H",
		size : 6,
		Xcoord : $scope.colG.Xcoord + $scope.NW_SE_X,
		Ycoord : $scope.colG.Ycoord + $scope.NW_SE_Y,
		stones: []
	};

	$scope.colI = {
		name : "I",
		size : 5,
		Xcoord : $scope.colH.Xcoord + $scope.NW_SE_X,
		Ycoord : $scope.colH.Ycoord + $scope.NW_SE_Y,
		stones: []
	};


	$scope.cols = [$scope.colA, $scope.colB, $scope.colC, $scope.colD, $scope.colE, $scope.colF, $scope.colG, $scope.colH, $scope.colI];
	angular.forEach($scope.cols, function(column, key) {
		for (var i=1; i<= column.size; i++) {
			column.stones.push({
				name : column.name + i,
				Xcoord : column.Xcoord,
				Ycoord : column.Ycoord + column.stones.length * $scope.vertical_Y,
				color : null,
				height : null,
				type : null
			});
			console.log("added a stone to column " + column.name + ": " + column.stones[i-1].name 
			+ " at X:" + column.stones[i-1].Xcoord + " Y:" + column.stones[i-1].Ycoord);
		}
	});
	//NE_SW cols
	$scope.NE_SWcolE1 = {name: "E1", size: 5, stones: [$scope.colE.stones[0], $scope.colD.stones[0], $scope.colC.stones[0], $scope.colB.stones[0], $scope.colA.stones[0]]};
	$scope.NE_SWcolF1 = {name: "F1", size: 6, stones: [$scope.colF.stones[0], $scope.colE.stones[1], $scope.colD.stones[1], $scope.colC.stones[1], $scope.colB.stones[1], $scope.colA.stones[1]]};
	$scope.NE_SWcolG1 = {name: "G1", size: 7, stones: [$scope.colG.stones[0], $scope.colF.stones[1], $scope.colE.stones[2], $scope.colD.stones[2], $scope.colC.stones[2], $scope.colB.stones[2], $scope.colA.stones[2]]};
	$scope.NE_SWcolH1 = {name: "H1", size: 8, stones: [$scope.colH.stones[0], $scope.colG.stones[1], $scope.colF.stones[2], $scope.colE.stones[3], $scope.colD.stones[3], $scope.colC.stones[3], $scope.colB.stones[3], $scope.colA.stones[3]]};
	$scope.NE_SWcolI1 = {name: "I1", size: 9, stones: [$scope.colI.stones[0], $scope.colH.stones[1], $scope.colG.stones[2], $scope.colF.stones[3], $scope.colE.stones[4], $scope.colD.stones[4], $scope.colC.stones[4], $scope.colB.stones[4], $scope.colA.stones[4]]};
	$scope.NE_SWcolI2 = {name: "I2", size: 8, stones: [$scope.colI.stones[1], $scope.colH.stones[2], $scope.colG.stones[3], $scope.colF.stones[4], $scope.colE.stones[5], $scope.colD.stones[5], $scope.colC.stones[5], $scope.colB.stones[5]]};
	$scope.NE_SWcolI3 = {name: "I3", size: 7, stones: [$scope.colI.stones[2], $scope.colH.stones[3], $scope.colG.stones[4], $scope.colF.stones[5], $scope.colE.stones[6], $scope.colD.stones[6], $scope.colC.stones[6]]};
	$scope.NE_SWcolI4 = {name: "I4", size: 6, stones: [$scope.colI.stones[3], $scope.colH.stones[4], $scope.colG.stones[5], $scope.colF.stones[6], $scope.colE.stones[7], $scope.colD.stones[7]]};
	$scope.NE_SWcolI5 = {name: "I5", size: 5, stones: [$scope.colI.stones[4], $scope.colH.stones[5], $scope.colG.stones[6], $scope.colF.stones[7], $scope.colE.stones[8]]};
	$scope.NE_SWcols = [$scope.NE_SWcolE1, $scope.NE_SWcolF1, $scope.NE_SWcolG1, $scope.NE_SWcolH1, $scope.NE_SWcolI1, $scope.NE_SWcolI2, $scope.NE_SWcolI3, $scope.NE_SWcolI4, $scope.NE_SWcolI5];
	$scope.NW_SEcolE1 = {name: "E1", size: 5, stones: [$scope.colE.stones[0], $scope.colF.stones[0], $scope.colG.stones[0], $scope.colH.stones[0], $scope.colI.stones[0]]};
	$scope.NW_SEcolD1 = {name: "D1", size: 6, stones: [$scope.colD.stones[0], $scope.colE.stones[1], $scope.colF.stones[1], $scope.colG.stones[1], $scope.colH.stones[1], $scope.colI.stones[1]]};
	$scope.NW_SEcolC1 = {name: "C1", size: 7, stones: [$scope.colC.stones[0], $scope.colD.stones[1], $scope.colE.stones[2], $scope.colF.stones[2], $scope.colG.stones[2], $scope.colH.stones[2], $scope.colI.stones[2]]};
	$scope.NW_SEcolB1 = {name: "B1", size: 8, stones: [$scope.colB.stones[0], $scope.colC.stones[1], $scope.colD.stones[2], $scope.colE.stones[3], $scope.colF.stones[3], $scope.colG.stones[3], $scope.colH.stones[3], $scope.colI.stones[3]]};
	$scope.NW_SEcolA1 = {name: "A1", size: 9, stones: [$scope.colA.stones[0], $scope.colB.stones[1], $scope.colC.stones[2], $scope.colD.stones[3], $scope.colE.stones[4], $scope.colF.stones[4], $scope.colG.stones[4], $scope.colH.stones[4], $scope.colI.stones[4]]};
	$scope.NW_SEcolA2 = {name: "A2", size: 8, stones: [$scope.colA.stones[1], $scope.colB.stones[2], $scope.colC.stones[3], $scope.colD.stones[4], $scope.colE.stones[5], $scope.colF.stones[5], $scope.colG.stones[5], $scope.colH.stones[5]]};
	$scope.NW_SEcolA3 = {name: "A3", size: 7, stones: [$scope.colA.stones[2], $scope.colB.stones[3], $scope.colC.stones[4], $scope.colD.stones[5], $scope.colE.stones[6], $scope.colF.stones[6], $scope.colG.stones[6]]};
	$scope.NW_SEcolA4 = {name: "A4", size: 6, stones: [$scope.colA.stones[3], $scope.colB.stones[4], $scope.colC.stones[5], $scope.colD.stones[6], $scope.colE.stones[7], $scope.colF.stones[7]]};
	$scope.NW_SEcolA5 = {name: "A5", size: 5, stones: [$scope.colA.stones[4], $scope.colB.stones[5], $scope.colC.stones[6], $scope.colD.stones[7], $scope.colE.stones[8]]};
	$scope.NW_SEcols = [$scope.NW_SEcolE1, $scope.NW_SEcolD1, $scope.NW_SEcolC1, $scope.NW_SEcolB1, $scope.NW_SEcolA1, $scope.NW_SEcolA2, $scope.NW_SEcolA3, $scope.NW_SEcolA4, $scope.NW_SEcolA5];


	$scope.positions = [ $scope.colA.stones[1].Ycoord, $scope.colA.stones[2].Ycoord];
	/* t = white Tsaar    **
	** z = white Tzarras  **
	** o = white Totts    **
	** T = black Tsaar    **
	** Z = black Tzarras  **
	** O = black Totts    **
	*/
	//initial values
	$scope.currentmoveIndex = 0;
	$scope.currentmove = null;
	$scope.activeColor = "white";
	$scope.printlabels = false;     
	$scope.isPlaying = false;
	$scope.isAnimating = false;


	$scope.game.whiteTzaarCount = 6;
	$scope.game.whiteTzarrasCount = 9;
	$scope.game.whiteTottsCount = 15;
	$scope.game.blackTzaarCount = 6;
	$scope.game.blackTzarrasCount = 9;
	$scope.game.blackTottsCount = 15;

	$scope.winner = null;


$scope.insertSubmit = function() {
	var data = {
		'name' : $scope.game.name, 
		'bot1' : $scope.game.bot1, 
		'bot2' : $scope.game.bot2, 
		'startstate' : $scope.game.startstate, 
		'moves' : $scope.game.moves, 
	};
	$http.post('/api/game/create/', data).success(function(){
		$location.path("/");
	});
};

$scope.drawGame = function() {

	console.log("draw game");
	var cxt = document.getElementById('c').getContext('2d');
	cxt.clearRect(0,0,cxt.canvas.width,cxt.canvas.height);
	  
	//draw grey hexagon
	cxt.beginPath();
	cxt.moveTo ($scope.Xcenter +  $scope.size * Math.cos(0.5), $scope.Ycenter +  $scope.size *  Math.sin(0.5));          

	for (var i = 1; i <= $scope.numberOfSides;i += 1) {
	    cxt.lineTo ($scope.Xcenter + $scope.size * Math.cos((i+0.5) * 2 * Math.PI / $scope.numberOfSides), $scope.Ycenter + $scope.size * Math.sin((i+0.5) * 2 * Math.PI / $scope.numberOfSides));
	}
	cxt.fillStyle = '#F0F0F0';
	cxt.strokeStyle = "#000000";
	cxt.lineWidth = 1;
	cxt.fill();
	//clear center piece
	cxt.fillStyle = '#FFFFFF';
	cxt.beginPath();
	cxt.moveTo ($scope.Xcenter +  ($scope.size/4) * Math.cos(0.5), $scope.Ycenter +  ($scope.size/4) *  Math.sin(0.5));          

	for (var i = 1; i <= $scope.numberOfSides;i += 1) {
	    cxt.lineTo ($scope.Xcenter + ($scope.size/4) * Math.cos((i+0.5) * 2 * Math.PI / $scope.numberOfSides), $scope.Ycenter + ($scope.size/4) * Math.sin((i+0.5) * 2 * Math.PI / $scope.numberOfSides));
	}
	cxt.fill();


	//model = 
	//   A  B  C  D  E  F  G  H  I
	//              E1
	//           D1    F1
	//        C1    E2    G1
	//     B1    D2    F2    H1
	//  A1    C2    E3    G2    I1
	//     B2    D3    F3    H2
	//  A2    C3    E4    G3    I2
	//     B3    D4    F4    H3
	//  A3    C4    XX    G4    I3
	//     B4    D5    F5    H4
	//  A4    C5    E6    G5    I4
	//     B5    D6    F6    H5
	//  A5    C6    E7    G6    I5
	//     B6    D7    F7    H6
	//        C7    E8    G7
	//           D8    F8
	//              E9


	cxt.beginPath();
	//vertical lines
	cxt.moveTo ($scope.colA.stones[0].Xcoord, $scope.colA.stones[0].Ycoord);
	cxt.lineTo ($scope.colA.stones[4].Xcoord, $scope.colA.stones[4].Ycoord);
	cxt.moveTo ($scope.colB.stones[0].Xcoord, $scope.colB.stones[0].Ycoord);
	cxt.lineTo ($scope.colB.stones[5].Xcoord, $scope.colB.stones[5].Ycoord);
	cxt.moveTo ($scope.colC.stones[0].Xcoord, $scope.colC.stones[0].Ycoord);
	cxt.lineTo ($scope.colC.stones[6].Xcoord, $scope.colC.stones[6].Ycoord);
	cxt.moveTo ($scope.colD.stones[0].Xcoord, $scope.colD.stones[0].Ycoord);
	cxt.lineTo ($scope.colD.stones[7].Xcoord, $scope.colD.stones[7].Ycoord);

	cxt.moveTo ($scope.colE.stones[0].Xcoord, $scope.colE.stones[0].Ycoord);
	cxt.lineTo ($scope.colE.stones[3].Xcoord, $scope.colE.stones[3].Ycoord);
	cxt.moveTo ($scope.colE.stones[5].Xcoord, $scope.colE.stones[5].Ycoord);
	cxt.lineTo ($scope.colE.stones[8].Xcoord, $scope.colE.stones[8].Ycoord);

	cxt.moveTo ($scope.colF.stones[0].Xcoord, $scope.colF.stones[0].Ycoord);
	cxt.lineTo ($scope.colF.stones[7].Xcoord, $scope.colF.stones[7].Ycoord);
	cxt.moveTo ($scope.colG.stones[0].Xcoord, $scope.colG.stones[0].Ycoord);
	cxt.lineTo ($scope.colG.stones[6].Xcoord, $scope.colG.stones[6].Ycoord);
	cxt.moveTo ($scope.colH.stones[0].Xcoord, $scope.colH.stones[0].Ycoord);
	cxt.lineTo ($scope.colH.stones[5].Xcoord, $scope.colH.stones[5].Ycoord);
	cxt.moveTo ($scope.colI.stones[0].Xcoord, $scope.colI.stones[0].Ycoord);
	cxt.lineTo ($scope.colI.stones[4].Xcoord, $scope.colI.stones[4].Ycoord);

	//NW_SE lines
	cxt.moveTo ($scope.colE.stones[0].Xcoord, $scope.colE.stones[0].Ycoord);
	cxt.lineTo ($scope.colI.stones[0].Xcoord, $scope.colI.stones[0].Ycoord);
	cxt.moveTo ($scope.colD.stones[0].Xcoord, $scope.colD.stones[0].Ycoord);
	cxt.lineTo ($scope.colI.stones[1].Xcoord, $scope.colI.stones[1].Ycoord);
	cxt.moveTo ($scope.colC.stones[0].Xcoord, $scope.colC.stones[0].Ycoord);
	cxt.lineTo ($scope.colI.stones[2].Xcoord, $scope.colI.stones[2].Ycoord);
	cxt.moveTo ($scope.colB.stones[0].Xcoord, $scope.colB.stones[0].Ycoord);
	cxt.lineTo ($scope.colI.stones[3].Xcoord, $scope.colI.stones[3].Ycoord);

	cxt.moveTo ($scope.colA.stones[0].Xcoord, $scope.colA.stones[0].Ycoord);
	cxt.lineTo ($scope.colD.stones[3].Xcoord, $scope.colD.stones[3].Ycoord);
	cxt.moveTo ($scope.colF.stones[4].Xcoord, $scope.colF.stones[4].Ycoord);
	cxt.lineTo ($scope.colI.stones[4].Xcoord, $scope.colI.stones[4].Ycoord);

	cxt.moveTo ($scope.colA.stones[1].Xcoord, $scope.colA.stones[1].Ycoord);
	cxt.lineTo ($scope.colH.stones[5].Xcoord, $scope.colH.stones[5].Ycoord);
	cxt.moveTo ($scope.colA.stones[2].Xcoord, $scope.colA.stones[2].Ycoord);
	cxt.lineTo ($scope.colG.stones[6].Xcoord, $scope.colG.stones[6].Ycoord);
	cxt.moveTo ($scope.colA.stones[3].Xcoord, $scope.colA.stones[3].Ycoord);
	cxt.lineTo ($scope.colF.stones[7].Xcoord, $scope.colF.stones[7].Ycoord);
	cxt.moveTo ($scope.colA.stones[4].Xcoord, $scope.colA.stones[4].Ycoord);
	cxt.lineTo ($scope.colE.stones[8].Xcoord, $scope.colE.stones[8].Ycoord);

	//NE_SW lines
	cxt.moveTo ($scope.colE.stones[0].Xcoord, $scope.colE.stones[0].Ycoord);
	cxt.lineTo ($scope.colA.stones[0].Xcoord, $scope.colA.stones[0].Ycoord);
	cxt.moveTo ($scope.colF.stones[0].Xcoord, $scope.colF.stones[0].Ycoord);
	cxt.lineTo ($scope.colA.stones[1].Xcoord, $scope.colA.stones[1].Ycoord);
	cxt.moveTo ($scope.colG.stones[0].Xcoord, $scope.colG.stones[0].Ycoord);
	cxt.lineTo ($scope.colA.stones[2].Xcoord, $scope.colA.stones[2].Ycoord);
	cxt.moveTo ($scope.colH.stones[0].Xcoord, $scope.colH.stones[0].Ycoord);
	cxt.lineTo ($scope.colA.stones[3].Xcoord, $scope.colA.stones[3].Ycoord);

	cxt.moveTo ($scope.colI.stones[0].Xcoord, $scope.colI.stones[0].Ycoord);
	cxt.lineTo ($scope.colF.stones[3].Xcoord, $scope.colF.stones[3].Ycoord);
	cxt.moveTo ($scope.colD.stones[4].Xcoord, $scope.colD.stones[4].Ycoord);
	cxt.lineTo ($scope.colA.stones[4].Xcoord, $scope.colA.stones[4].Ycoord);

	cxt.moveTo ($scope.colI.stones[1].Xcoord, $scope.colI.stones[1].Ycoord);
	cxt.lineTo ($scope.colB.stones[5].Xcoord, $scope.colB.stones[5].Ycoord);
	cxt.moveTo ($scope.colI.stones[2].Xcoord, $scope.colI.stones[2].Ycoord);
	cxt.lineTo ($scope.colC.stones[6].Xcoord, $scope.colC.stones[6].Ycoord);
	cxt.moveTo ($scope.colI.stones[3].Xcoord, $scope.colI.stones[3].Ycoord);
	cxt.lineTo ($scope.colD.stones[7].Xcoord, $scope.colD.stones[7].Ycoord);
	cxt.moveTo ($scope.colI.stones[4].Xcoord, $scope.colI.stones[4].Ycoord);
	cxt.lineTo ($scope.colE.stones[8].Xcoord, $scope.colE.stones[8].Ycoord);
	cxt.stroke();


	//draw all stones
	angular.forEach($scope.cols, function(column, key) {
		angular.forEach(column.stones, function(stone, key) {
			if ( ! ($scope.currentmove != null && $scope.currentmove.startstone == stone) ) {
				$scope.drawStone(stone.Xcoord, stone.Ycoord, stone.color, stone.type, stone.height);
			}
		});
	});


	if ($scope.printlabels) {
		//label all points, with text to the right of the point
		cxt.font = "bold 14px sans-serif";
		cxt.fillStyle = '#C00000';

		angular.forEach($scope.cols, function(column, key) {
			angular.forEach(column.stones, function(stone, key) {
				if ( stone.name != "E5" ) {
					cxt.fillText(stone.name, stone.Xcoord+7, stone.Ycoord+7);
				}
			});
		});
	}
};

$scope.drawStone = function(X, Y, color, type, height) {
	var cxt = document.getElementById('c').getContext('2d');
	var stripeHeight = height % 7; //14 is maximum legal height I believe 
	var bandHeight = (height - stripeHeight) / 7;
	var stripeColor = '#D0D0D0';
	if ( bandHeight >= 1) {
		stripeHeight++;
		stripeColor = '#FF6601'; //Sioux orange
		console.log("Orange!");
	}
	if ( bandHeight >= 2) {
		stripeColor = '#FFD700'; //Gold
		console.log("Gold!");
	}
	if ( $scope.selectedStone != null &&  X == $scope.selectedStone.Xcoord && Y == $scope.selectedStone.Ycoord ) {
		stripeColor = '#0000FF'; //BLUE
	} else if ( $scope.isValidTargetStone(X,Y) == "true" ) {
		stripeColor = '#00FF00'; //GREEN
	}
	cxt.strokeStyle = stripeColor;
	cxt.lineWidth = 3;
	var radius = $scope.stoneRadius;	
	var mainColor = null;
	if ( color == "white" ) {
		//draw white stone
		mainColor = '#FFFFFF';
	} else if ( color == "black" ) {
		//draw black stone
		mainColor = '#000000';
	} else {
		return;
	}
	//draw shadow first
	cxt.globalAlpha = 0.5;
	cxt.fillStyle = '#000000';
	for (var i = 1; i <= height; i++) {
		cxt.beginPath();
		cxt.arc(X+(3*i), Y+(3*i), radius, 0, 2 * Math.PI, false);
		cxt.fill();
	}
	cxt.globalAlpha = 1.0;
	//draw stone
	cxt.fillStyle = mainColor;
	cxt.beginPath();
	cxt.arc(X, Y, radius, 0, 2 * Math.PI, false);
	cxt.fill(); cxt.stroke();
	switch ( type ) {
		case "TZAAR" :
			//console.log("TZAAR stone type at " + stone.name );
			//draw outer circle
			cxt.fillStyle = stripeColor;
			cxt.beginPath();
			cxt.arc(X, Y, radius*0.7, 0, 2 * Math.PI, false);
			cxt.fill();
			cxt.fillStyle = mainColor;
			cxt.beginPath();
			cxt.arc(X, Y, radius*0.5, 0, 2 * Math.PI, false);
			cxt.fill();
			//draw inner circle
			cxt.beginPath();
			cxt.fillStyle = stripeColor;
			cxt.arc(X, Y, radius*0.3, 0, 2 * Math.PI, false);
			cxt.fill();
		break;
		case "TZARRAS" :
			//console.log("TZARRAS stone type at " + stone.name );
			//draw inner circle
			cxt.beginPath();
			cxt.fillStyle = stripeColor;
			cxt.arc(X, Y, radius*0.4, 0, 2 * Math.PI, false);
			cxt.fill();
		break;
		case "TOTTS" :
			//console.log("TOTTS stone type at " + stone.name );
		break;
		default:
			console.log("Unknown stone type at " + stone.name );
		break;
	}
	if (stripeHeight > 1) {
		cxt.fillStyle = stripeColor;
		for (var i = 0; i < stripeHeight; i++) {
			//console.log(color + " stone @ X: " + X + ", Y:" + Y + ", height: " + height);
			var angle = (Math.PI*1.5) + i *( (Math.PI*2) / (stripeHeight) );
			var offset = Math.PI/15;
			cxt.beginPath();
			cxt.moveTo(X,Y);
			cxt.arc(X,Y, radius*0.9, angle - offset, angle + offset, false);
			cxt.fill()
		}
	}
}

$scope.loadGame = function() {
	console.log("Selected game state:" + $scope.game.startstate);
	if ($scope.game.startstate.length == 61) {
		var stoneIndex = 0;
		angular.forEach($scope.cols, function(column, key) {
			angular.forEach(column.stones, function(stone, key) {
				if ( stone.name != "E5" ) {
					stone.height = 1;
					var stoneState = $scope.game.startstate[stoneIndex];
					switch ( stoneState ) {
						case "t" :
							stone.color = "white";
							stone.type = "TZAAR";
						break;
						case "z" :
							stone.color = "white";
							stone.type = "TZARRAS";
						break;
						case "o" :
							stone.color = "white";
							stone.type = "TOTTS";
						break;
						case "T" :
							stone.color = "black";
							stone.type = "TZAAR";
						break;
						case "Z" :
							stone.color = "black";
							stone.type = "TZARRAS";
						break;
						case "O" :
							stone.color = "black";
							stone.type = "TOTTS";
						break;
						default :
							stone.type = null;
							stone.color = null; 
						break;
					}
				}
				stoneIndex++;
			});
		});
	} else {
		console.log("invalid game state length, should be 61 chars long: " + $scope.game.startstate.length);
	}
};

$scope.getMoveString = function(moveIndex) {
	var move = $scope.game.moves.substring(moveIndex*4, moveIndex*4 + 4)
	console.log("getMove: " + move);
	if (move != "PASS") {
		var startcol = $scope.cols[move.charCodeAt(0) - "A".charCodeAt(0) ];
		//console.log("found startcol:" + startcol.name + "startcol:Xcoord" + startcol.Xcoord);
		var start = startcol.stones[move.charCodeAt(1) - "1".charCodeAt(0)];
		//console.log("found startstone: " + start.name + " X:" + start.Xcoord + " from move: " + move);

		var endcol = $scope.cols[move.charCodeAt(2) - "A".charCodeAt(0)];
		var end = endcol.stones[move.charCodeAt(3) - "1".charCodeAt(0)];
		//console.log("found endstone: " + end.name + " from move: " + move);

		return { index: moveIndex+1, moveString: move, startstone: start, endstone: end };
	} else {
		return { index: moveIndex+1, moveString: move};
	}
}

$scope.startPlaying = function() {
	console.log("called startPlaying");
	$scope.isPlaying = true;
}

$scope.pausePlaying = function() {
	console.log("called pausePlaying");
	$scope.isPlaying = false;
}

$scope.increaseMoveAndUpdate = function() {


	//update next move
        if ( $scope.currentmoveIndex < ($scope.game.moves.length/4)) {
		$scope.currentmoveIndex++;
		$scope.currentmove = $scope.game.moveStrings[$scope.currentmoveIndex];
		var startTime = (new Date()).getTime();
		$scope.isAnimating = true;
		$scope.animate( startTime);
	}

};

$scope.decreaseMoveAndUpdate = function() {


	//update next move
    if ( $scope.currentmoveIndex > 0) {
		$scope.currentmoveIndex--;
		$scope.currentmove = $scope.game.moveStrings[$scope.currentmoveIndex];
		console.log("calling processMoves from decreaseMoveAndUPdate");
		$scope.processMoves($scope.currentmoveIndex);
		$scope.updateActiveColor(false);
		$scope.drawGame();
		//$scope.drawCompletedMove();
	}

};

$scope.loadMoves = function() {
	$scope.game.moveStrings = [{ index: 0, moveString: "PASS", move_img: ""}];
	for ( var moveIndex = 0; moveIndex < ($scope.game.moves.length/4); moveIndex++) {
		var move = $scope.getMoveString(moveIndex);
		if (moveIndex == ($scope.game.moves.length/4)-1) {
			//last move
			move.move_img = "./img/w.png"
		} else if (move.moveString != "PASS" ) {
			if (move.endstone.color == move.startstone.color) {
				move.move_img = "./img/shield_PNG1268.png";
				move.endstone.height = move.startstone.height + 1;
			} else {
				move.move_img = "./img/Crossed_gladii.png";
				move.endstone.height = move.startstone.height;
				move.endstone.color = move.startstone.color;
			}
			move.endstone.type = move.startstone.type;
			move.startstone.color = null;
			move.startstone.type = null;
		} else {
			move.move_img = "./img/clock-147257_640.png";
		}
		console.log("adding move " + move.index);
		$scope.game.moveStrings.push(move);
	}
	//reset game
	$scope.loadGame();
};

$scope.processMoves = function(moveCount) {
	$scope.loadGame();
	for ( var moveIndex = 0; moveIndex <= moveCount; moveIndex++) {
		var move = $scope.game.moveStrings[moveIndex];
		console.log("processing move: " +  move.moveString);
		if (move.moveString != "PASS" ) {
			if (move.endstone.color == move.startstone.color) {
				move.endstone.height = move.endstone.height + move.startstone.height;
			} else {
				move.endstone.height = move.startstone.height;
				move.endstone.color = move.startstone.color;
			}
			move.endstone.type = move.startstone.type;
			move.startstone.color = null;
			move.startstone.type = null;
		}
	}
	$scope.updateStoneCount();
	$scope.checkVictory();
};

$scope.checkVictory = function() {
	var victory = false;
	if ( $scope.game.whiteTzaarCount == 0 || 
		$scope.game.whiteTzarrasCount == 0 ||
		$scope.game.whiteTottsCount == 0 ) {
		$scope.winner = $scope.game.bot2;
		victory = true;
	} else if ($scope.game.blackTzaarCount == 0 ||
		$scope.game.blackTzarrasCount == 0 ||
		$scope.game.blackTottsCount == 0 ) {
		$scope.winner = $scope.game.bot1;
		victory = true;
	} else if ( false ) {//no more legal moves
		victory = true;
	}
	if ( victory ) {
		$scope.game.moveStrings.push({ index: $scope.game.moveStrings.length, moveString: "PASS", move_img: "./img/w.png"});
		$scope.currentmoveIndex++;
	}
}

$scope.updateStoneCount = function() {
	$scope.game.whiteTzaarCount = 0;
	$scope.game.whiteTzarrasCount = 0;
	$scope.game.whiteTottsCount = 0;
	$scope.game.blackTzaarCount = 0;
	$scope.game.blackTzarrasCount = 0;
	$scope.game.blackTottsCount = 0;
	angular.forEach($scope.cols, function(column, key) {
		angular.forEach(column.stones, function(stone, key) {
			if ( stone.color == "white" ) {
				switch ( stone.type ) {
					case "TZAAR" :
						$scope.game.whiteTzaarCount++;
					break;
					case "TZARRAS" :
						$scope.game.whiteTzarrasCount++;
					break;
					case "TOTTS" :
						$scope.game.whiteTottsCount++;
					break;
				}
			} else if ( stone.color == "black") {
				switch ( stone.type ) {
					case "TZAAR" :
						$scope.game.blackTzaarCount++;
					break;
					case "TZARRAS" :
						$scope.game.blackTzarrasCount++;
					break;
					case "TOTTS" :
						$scope.game.blackTottsCount++;
					break;
				}
			}
		});
	});
};

$scope.updateActiveColor = function(applyFlag) {
	var color = "";
	if ( $scope.game.moveStrings[$scope.currentmoveIndex].move_img == "./img/w.png" ) {
		color = "Game Over";
	} else 	if ( (Math.floor(($scope.currentmoveIndex+1)/2) % 2) == 0) {
			color = "white";
	} else {
			color = "black";
	}
	if ( applyFlag) {
		$scope.$apply(function() {
			$scope.activeColor = color;
		});
	} else {
		$scope.activeColor = color;
	}
	console.log("$scope.currentmoveIndex : " + $scope.currentmoveIndex + " activeColor: " + $scope.activeColor);
};

$scope.clickGame = function(event) {
	console.log("clickGame, event: " + event + " pageX: " + event.pageX + " pageY: " + event.pageY);
	var canvasX = event.pageX - event.target.offsetLeft;
	var canvasY = event.pageY - event.target.offsetTop;
	console.log("clickGame, event: " + event + " canvasX : " + canvasX  + " canvasY : " + canvasY );
	var performMove = false;
	var validTargetStone = null;
	//did we click on a valid target?
	angular.forEach($scope.validTargetStones, function(stone, key) {
		if ( Math.abs(stone.Xcoord - canvasX) < $scope.stoneRadius && Math.abs(stone.Ycoord - canvasY) < $scope.stoneRadius) {
			console.log("clicked valid target stone: " + stone.name);
			validTargetStone = stone;
			performMove = true;
		}
	});
	if (performMove == true) {
		var move = {};
		move.endstone = validTargetStone;
		move.startstone = $scope.selectedStone;
		if (move.endstone.color != move.startstone.color) {
			move.move_img = "./img/Crossed_gladii.png";
		} else {
			move.move_img = "./img/shield_PNG1268.png";
		}
		move.index = $scope.game.moveStrings.length;
		$scope.game.moves = $scope.game.moves + "" + move.startstone.name + move.endstone.name;
		$scope.game.moveStrings.push(move);
		$scope.selectedStone = null;
		$scope.validTargetStones = [];
		
		$scope.currentmoveIndex++;
		$scope.currentmove = $scope.game.moveStrings[$scope.currentmoveIndex];
		var startTime = (new Date()).getTime();
		$scope.isAnimating = true;
		$scope.animate( startTime);
	} else {
		$scope.selectedStone = null;
		$scope.validTargetStones = [];
		var selectedVerticalColumn = null;
		angular.forEach($scope.cols, function(column, key) {
			angular.forEach(column.stones, function(stone, key) {
				if ( Math.abs(stone.Xcoord - canvasX) < $scope.stoneRadius && Math.abs(stone.Ycoord - canvasY) < $scope.stoneRadius) {
					console.log("clicked stone: " + stone.name);
					if ( stone.color == $scope.activeColor) {
						$scope.selectedStone = stone;
						selectedVerticalColumn = column;
					}
				}
			});
		});
		if ($scope.selectedStone != null) {
			var targetX = $scope.selectedStone.Xcoord;
			var targetY = $scope.selectedStone.Ycoord;
			var mayDefend = (($scope.currentmoveIndex+1) % 2 == 1) && ($scope.currentmoveIndex != 0);
			console.log("moveIndex: " + $scope.currentmoveIndex + " mayDefend: " + mayDefend);
			//get vertical targets
			var verticalIndex = 0;
			for (var i = 0; i < selectedVerticalColumn.size; i++) {
				if ( selectedVerticalColumn.stones[i] == $scope.selectedStone) {
					verticalIndex = i;
				}
			}
			for (var i = verticalIndex -1; i >= 0; i--) {
				var stone = selectedVerticalColumn.stones[i];
				if ( stone.name == "E5" ) break;
				if ( stone.color != null ) {
					if ((mayDefend && stone.color == $scope.selectedStone.color) || (stone.height <= $scope.selectedStone.height && stone.color != $scope.selectedStone.color)) {
						console.log("valid target vertical stone: " + stone.name + " X:" + stone.Xcoord + " Y:" + stone.Ycoord);
						$scope.validTargetStones.push(stone);
					}
					break;
				}
			}
			for (var i = verticalIndex +1; i < selectedVerticalColumn.size; i++) {
				var stone = selectedVerticalColumn.stones[i];
				if ( stone.name == "E5" ) break;
				if ( stone.color != null ) {
					if ((mayDefend && stone.color == $scope.selectedStone.color) || (stone.height <= $scope.selectedStone.height && stone.color != $scope.selectedStone.color)) {
						console.log("valid target vertical stone: " + stone.name + " X:" + stone.Xcoord + " Y:" + stone.Ycoord);
						$scope.validTargetStones.push(stone);
					}
					break;
				}
			}
			//get NE_SW targets
			var selectedNE_SWColumn = null;
			var NE_SWIndex = 0;

			angular.forEach($scope.NE_SWcols, function(column, key) {
				for (var stoneIndex = 0; stoneIndex < column.stones.length; stoneIndex++) {
					var stone = column.stones[stoneIndex];
					if ( stone == $scope.selectedStone ) {
						selectedNE_SWColumn = column;
						NE_SWIndex = stoneIndex;
					}
				}
			});
			for (var i = NE_SWIndex -1; i >= 0; i--) {
				var stone = selectedNE_SWColumn.stones[i];
				if ( stone.name == "E5" ) break;
				if ( stone.color != null ) {
					if ((mayDefend && stone.color == $scope.selectedStone.color) || (stone.height <= $scope.selectedStone.height && stone.color != $scope.selectedStone.color)) {
						console.log("valid target NE_SW stone: " + stone.name );
						$scope.validTargetStones.push(stone);
					}
					break;
				}
			}
			for (var i = NE_SWIndex +1; i < selectedNE_SWColumn.size; i++) {
				var stone = selectedNE_SWColumn.stones[i];
				if ( stone.name == "E5" ) break;
				if ( stone.color != null ) {
					if ((mayDefend && stone.color == $scope.selectedStone.color) || (stone.height <= $scope.selectedStone.height && stone.color != $scope.selectedStone.color)) {
						console.log("valid target NE_SW stone: " + stone.name );
						$scope.validTargetStones.push(stone);
					}
					break;
				}
			}
			//get NW_SE targets
			var selectedNW_SEColumn = null;
			var NW_SEIndex = 0;

			angular.forEach($scope.NW_SEcols, function(column, key) {
				for (var stoneIndex = 0; stoneIndex < column.stones.length; stoneIndex++) {
					var stone = column.stones[stoneIndex];
					if ( stone == $scope.selectedStone ) {
						selectedNW_SEColumn = column;
						NW_SEIndex = stoneIndex;
					}
				}
			});
			for (var i = NW_SEIndex -1; i >= 0; i--) {
				var stone = selectedNW_SEColumn.stones[i];
				if ( stone.name == "E5" ) break;
				if ( stone.color != null ) {
					if ((mayDefend && stone.color == $scope.selectedStone.color) || (stone.height <= $scope.selectedStone.height && stone.color != $scope.selectedStone.color)) {
						console.log("valid target NW_SE stone: " + stone.name );
						$scope.validTargetStones.push(stone);
					}
					break;
				}
			}
			for (var i = NW_SEIndex +1; i < selectedNW_SEColumn.size; i++) {
				var stone = selectedNW_SEColumn.stones[i];
				if ( stone.name == "E5" ) break;
				if ( stone.color != null ) {
					if ((mayDefend && stone.color == $scope.selectedStone.color) || (stone.height <= $scope.selectedStone.height && stone.color != $scope.selectedStone.color)) {
						console.log("valid target NW_SE stone: " + stone.name );
						$scope.validTargetStones.push(stone);
					}
					break;
				}
			}
		}
		$scope.drawGame();
	}
}

$scope.isValidTargetStone =  function(X, Y) {
var result = "false";
	angular.forEach($scope.validTargetStones, function(stone, key) {
		if ( stone.Xcoord == X && stone.Ycoord == Y ) {
			result = "true";
		}
	});
	return result;
}

    $scope.loadGame();
    $scope.drawGame();

    $scope.$watch('printlabels', function(value) {
      console.log(value);
      $scope.drawGame();
    });
    $scope.$watch('cols', function(value) {
      console.log("update to cols");
      $scope.drawGame();
      //$scope.drawCompletedMove();
    }, true);


      window.requestAnimFrame = (function(callback) {
        return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame ||
        function(callback) {
          window.setTimeout(callback, 1000 / 60);
        };
      })();

$scope.animate = function( startTime) {
	if ($scope.currentmove == null || $scope.currentmove.moveString == "PASS") {
		console.log("done animating, currentmoveIndex:" + $scope.currentmoveIndex);
		$scope.isAnimating = false;
		console.log("Calling processMoves from animate function start");
		$scope.processMoves($scope.currentmoveIndex);
		$scope.updateActiveColor(false);
		$scope.drawGame();
		return;
	}

        // update
        var time = (new Date()).getTime() - startTime;
	var period = 1000;

	var startX = $scope.currentmove.startstone.Xcoord;
	var startY = $scope.currentmove.startstone.Ycoord;
	var endX = $scope.currentmove.endstone.Xcoord;
	var endY = $scope.currentmove.endstone.Ycoord;
	//console.log("animate:  Xcoord: " + $scope.currentmove.startstone.Xcoord );
	//console.log("endX: " + endX + " startX: " + startX + " time: " + time + " time/period " + time/period);
	var currentX = startX - ((startX - endX) * (time/period)); 
	var currentY = startY - ((startY - endY) * (time/period));

	$scope.drawGame();
	//console.log("animate, currentmove=" + $scope.currentmove.index + "X:" + currentX + " Y:" + currentY + " color:" + $scope.currentmove.startstone.color);
	$scope.drawStone(currentX, currentY, $scope.currentmove.startstone.color, $scope.currentmove.startstone.type, $scope.currentmove.startstone.height);

        // request new frame
	if ( time < period ) {
		//console.log("do another animate");
		requestAnimFrame(function() {
		  $scope.animate( startTime);
		});
	} else {
		console.log("done animating, currentmoveIndex:" + $scope.currentmoveIndex);
		$scope.isAnimating = false;
		console.log("Calling processMoves from animate function end");
		$scope.processMoves($scope.currentmoveIndex);
		$scope.updateActiveColor(true);
		$scope.drawGame();
	}
      }
}]);
 
//CONTROLLER FOR mod_detail.html
meanControllers.controller('DetailCtrl', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
	$http.get('/api/game/retrieveid/'+$routeParams.gameId).success(function(data){
		$scope.game = data;		
		$scope.game.whiteTzaarCount = 6;
		$scope.game.whiteTzarrasCount = 9;
		$scope.game.whiteTottsCount = 15;
		$scope.game.blackTzaarCount = 6;
		$scope.game.blackTzarrasCount = 9;
		$scope.game.blackTottsCount = 15;
		$scope.loadGame();
		$scope.loadMoves();
		$scope.drawGame();
	});	

	
    $scope.numberOfSides = 6;
    $scope.size = 240;
    $scope.Xcenter = 275;
    $scope.Ycenter = 275;
	
	$scope.unit_distance = ($scope.size / 4);
	// Vertical |
	// NW_SE \
	// NE_SW /
	// ((starting at top))
	$scope.vertical_X = 0;
	$scope.vertical_Y = $scope.unit_distance;
	
	$scope.NW_SE_X = $scope.unit_distance * Math.cos(0.5* 2 * Math.PI / $scope.numberOfSides);
	$scope.NW_SE_Y = $scope.unit_distance * Math.sin(0.5* 2 * Math.PI / $scope.numberOfSides);

	$scope.NE_SW_X = $scope.unit_distance * Math.cos(5.5* 2 * Math.PI / $scope.numberOfSides);
	$scope.NE_SW_Y = $scope.unit_distance * Math.sin(5.5* 2 * Math.PI / $scope.numberOfSides);

	$scope.colA = {
		name : "A",
		size : 5,
		Xcoord : $scope.Xcenter +  ($scope.size) * Math.cos((3.5)* 2 * Math.PI / $scope.numberOfSides),
		Ycoord : $scope.Ycenter +  ($scope.size) * Math.sin((3.5)* 2 * Math.PI / $scope.numberOfSides),
		stones: []
	};

	$scope.colB = {
		name : "B",
		size : 6,
		Xcoord : $scope.colA.Xcoord + $scope.NE_SW_X,
		Ycoord : $scope.colA.Ycoord + $scope.NE_SW_Y,
		stones: []
	};

	$scope.colC = {
		name : "C",
		size : 7,
		Xcoord : $scope.colB.Xcoord + $scope.NE_SW_X,
		Ycoord : $scope.colB.Ycoord + $scope.NE_SW_Y,
		stones: []
	};

	$scope.colD = {
		name : "D",
		size : 8,
		Xcoord : $scope.colC.Xcoord + $scope.NE_SW_X,
		Ycoord : $scope.colC.Ycoord + $scope.NE_SW_Y,
		stones: []
	};

	$scope.colE = {
		name : "E",
		size : 9,
		Xcoord : $scope.colD.Xcoord + $scope.NE_SW_X,
		Ycoord : $scope.colD.Ycoord + $scope.NE_SW_Y,
		stones: []
	};

	$scope.colF = {
		name : "F",
		size : 8,
		Xcoord : $scope.colE.Xcoord + $scope.NW_SE_X,
		Ycoord : $scope.colE.Ycoord + $scope.NW_SE_Y,
		stones: []
	};

	$scope.colG = {
		name : "G",
		size : 7,
		Xcoord : $scope.colF.Xcoord + $scope.NW_SE_X,
		Ycoord : $scope.colF.Ycoord + $scope.NW_SE_Y,
		stones: []
	};

	$scope.colH = {
		name : "H",
		size : 6,
		Xcoord : $scope.colG.Xcoord + $scope.NW_SE_X,
		Ycoord : $scope.colG.Ycoord + $scope.NW_SE_Y,
		stones: []
	};

	$scope.colI = {
		name : "I",
		size : 5,
		Xcoord : $scope.colH.Xcoord + $scope.NW_SE_X,
		Ycoord : $scope.colH.Ycoord + $scope.NW_SE_Y,
		stones: []
	};


	$scope.cols = [$scope.colA, $scope.colB, $scope.colC, $scope.colD, $scope.colE, $scope.colF, $scope.colG, $scope.colH, $scope.colI];
	angular.forEach($scope.cols, function(column, key) {
		for (var i=1; i<= column.size; i++) {
			column.stones.push({
				name : column.name + i,
				Xcoord : column.Xcoord,
				Ycoord : column.Ycoord + column.stones.length * $scope.vertical_Y,
				color : null,
				height : null,
				type : null
			});
			console.log("added a stone to column " + column.name + ": " + column.stones[i-1].name 
			+ " at X:" + column.stones[i-1].Xcoord + " Y:" + column.stones[i-1].Ycoord);
		}
	});

	$scope.positions = [ $scope.colA.stones[1].Ycoord, $scope.colA.stones[2].Ycoord];
	/* t = white Tsaar    **
	** z = white Tzarras  **
	** o = white Totts    **
	** T = black Tsaar    **
	** Z = black Tzarras  **
	** O = black Totts    **
	*/
	//initial values
	$scope.currentmoveIndex = 0;
	$scope.currentmove = null;
	$scope.activeColor = "white";
	$scope.printlabels = false;     
	$scope.isPlaying = false;
	$scope.isAnimating = false;

	$scope.game = {};

	$scope.game.whiteTzaarCount = 6;
	$scope.game.whiteTzarrasCount = 9;
	$scope.game.whiteTottsCount = 15;
	$scope.game.blackTzaarCount = 6;
	$scope.game.blackTzarrasCount = 9;
	$scope.game.blackTottsCount = 15;

	$scope.winner = null;



$scope.drawGame = function() {

	console.log("draw game");
	var cxt = document.getElementById('c').getContext('2d');
	cxt.clearRect(0,0,cxt.canvas.width,cxt.canvas.height);
	  
	//draw grey hexagon
	cxt.beginPath();
	cxt.moveTo ($scope.Xcenter +  $scope.size * Math.cos(0.5), $scope.Ycenter +  $scope.size *  Math.sin(0.5));          

	for (var i = 1; i <= $scope.numberOfSides;i += 1) {
	    cxt.lineTo ($scope.Xcenter + $scope.size * Math.cos((i+0.5) * 2 * Math.PI / $scope.numberOfSides), $scope.Ycenter + $scope.size * Math.sin((i+0.5) * 2 * Math.PI / $scope.numberOfSides));
	}
	cxt.fillStyle = '#F0F0F0';
	cxt.strokeStyle = "#000000";
	cxt.lineWidth = 1;
	cxt.fill();
	//clear center piece
	cxt.fillStyle = '#FFFFFF';
	cxt.beginPath();
	cxt.moveTo ($scope.Xcenter +  ($scope.size/4) * Math.cos(0.5), $scope.Ycenter +  ($scope.size/4) *  Math.sin(0.5));          

	for (var i = 1; i <= $scope.numberOfSides;i += 1) {
	    cxt.lineTo ($scope.Xcenter + ($scope.size/4) * Math.cos((i+0.5) * 2 * Math.PI / $scope.numberOfSides), $scope.Ycenter + ($scope.size/4) * Math.sin((i+0.5) * 2 * Math.PI / $scope.numberOfSides));
	}
	cxt.fill();
	$scope.drawCompletedMove();


	//model = 
	//   A  B  C  D  E  F  G  H  I
	//              E1
	//           D1    F1
	//        C1    E2    G1
	//     B1    D2    F2    H1
	//  A1    C2    E3    G2    I1
	//     B2    D3    F3    H2
	//  A2    C3    E4    G3    I2
	//     B3    D4    F4    H3
	//  A3    C4    XX    G4    I3
	//     B4    D5    F5    H4
	//  A4    C5    E6    G5    I4
	//     B5    D6    F6    H5
	//  A5    C6    E7    G6    I5
	//     B6    D7    F7    H6
	//        C7    E8    G7
	//           D8    F8
	//              E9


	cxt.beginPath();
	//vertical lines
	cxt.moveTo ($scope.colA.stones[0].Xcoord, $scope.colA.stones[0].Ycoord);
	cxt.lineTo ($scope.colA.stones[4].Xcoord, $scope.colA.stones[4].Ycoord);
	cxt.moveTo ($scope.colB.stones[0].Xcoord, $scope.colB.stones[0].Ycoord);
	cxt.lineTo ($scope.colB.stones[5].Xcoord, $scope.colB.stones[5].Ycoord);
	cxt.moveTo ($scope.colC.stones[0].Xcoord, $scope.colC.stones[0].Ycoord);
	cxt.lineTo ($scope.colC.stones[6].Xcoord, $scope.colC.stones[6].Ycoord);
	cxt.moveTo ($scope.colD.stones[0].Xcoord, $scope.colD.stones[0].Ycoord);
	cxt.lineTo ($scope.colD.stones[7].Xcoord, $scope.colD.stones[7].Ycoord);

	cxt.moveTo ($scope.colE.stones[0].Xcoord, $scope.colE.stones[0].Ycoord);
	cxt.lineTo ($scope.colE.stones[3].Xcoord, $scope.colE.stones[3].Ycoord);
	cxt.moveTo ($scope.colE.stones[5].Xcoord, $scope.colE.stones[5].Ycoord);
	cxt.lineTo ($scope.colE.stones[8].Xcoord, $scope.colE.stones[8].Ycoord);

	cxt.moveTo ($scope.colF.stones[0].Xcoord, $scope.colF.stones[0].Ycoord);
	cxt.lineTo ($scope.colF.stones[7].Xcoord, $scope.colF.stones[7].Ycoord);
	cxt.moveTo ($scope.colG.stones[0].Xcoord, $scope.colG.stones[0].Ycoord);
	cxt.lineTo ($scope.colG.stones[6].Xcoord, $scope.colG.stones[6].Ycoord);
	cxt.moveTo ($scope.colH.stones[0].Xcoord, $scope.colH.stones[0].Ycoord);
	cxt.lineTo ($scope.colH.stones[5].Xcoord, $scope.colH.stones[5].Ycoord);
	cxt.moveTo ($scope.colI.stones[0].Xcoord, $scope.colI.stones[0].Ycoord);
	cxt.lineTo ($scope.colI.stones[4].Xcoord, $scope.colI.stones[4].Ycoord);

	//NW_SE lines
	cxt.moveTo ($scope.colE.stones[0].Xcoord, $scope.colE.stones[0].Ycoord);
	cxt.lineTo ($scope.colI.stones[0].Xcoord, $scope.colI.stones[0].Ycoord);
	cxt.moveTo ($scope.colD.stones[0].Xcoord, $scope.colD.stones[0].Ycoord);
	cxt.lineTo ($scope.colI.stones[1].Xcoord, $scope.colI.stones[1].Ycoord);
	cxt.moveTo ($scope.colC.stones[0].Xcoord, $scope.colC.stones[0].Ycoord);
	cxt.lineTo ($scope.colI.stones[2].Xcoord, $scope.colI.stones[2].Ycoord);
	cxt.moveTo ($scope.colB.stones[0].Xcoord, $scope.colB.stones[0].Ycoord);
	cxt.lineTo ($scope.colI.stones[3].Xcoord, $scope.colI.stones[3].Ycoord);

	cxt.moveTo ($scope.colA.stones[0].Xcoord, $scope.colA.stones[0].Ycoord);
	cxt.lineTo ($scope.colD.stones[3].Xcoord, $scope.colD.stones[3].Ycoord);
	cxt.moveTo ($scope.colF.stones[4].Xcoord, $scope.colF.stones[4].Ycoord);
	cxt.lineTo ($scope.colI.stones[4].Xcoord, $scope.colI.stones[4].Ycoord);

	cxt.moveTo ($scope.colA.stones[1].Xcoord, $scope.colA.stones[1].Ycoord);
	cxt.lineTo ($scope.colH.stones[5].Xcoord, $scope.colH.stones[5].Ycoord);
	cxt.moveTo ($scope.colA.stones[2].Xcoord, $scope.colA.stones[2].Ycoord);
	cxt.lineTo ($scope.colG.stones[6].Xcoord, $scope.colG.stones[6].Ycoord);
	cxt.moveTo ($scope.colA.stones[3].Xcoord, $scope.colA.stones[3].Ycoord);
	cxt.lineTo ($scope.colF.stones[7].Xcoord, $scope.colF.stones[7].Ycoord);
	cxt.moveTo ($scope.colA.stones[4].Xcoord, $scope.colA.stones[4].Ycoord);
	cxt.lineTo ($scope.colE.stones[8].Xcoord, $scope.colE.stones[8].Ycoord);

	//NE_SW lines
	cxt.moveTo ($scope.colE.stones[0].Xcoord, $scope.colE.stones[0].Ycoord);
	cxt.lineTo ($scope.colA.stones[0].Xcoord, $scope.colA.stones[0].Ycoord);
	cxt.moveTo ($scope.colF.stones[0].Xcoord, $scope.colF.stones[0].Ycoord);
	cxt.lineTo ($scope.colA.stones[1].Xcoord, $scope.colA.stones[1].Ycoord);
	cxt.moveTo ($scope.colG.stones[0].Xcoord, $scope.colG.stones[0].Ycoord);
	cxt.lineTo ($scope.colA.stones[2].Xcoord, $scope.colA.stones[2].Ycoord);
	cxt.moveTo ($scope.colH.stones[0].Xcoord, $scope.colH.stones[0].Ycoord);
	cxt.lineTo ($scope.colA.stones[3].Xcoord, $scope.colA.stones[3].Ycoord);

	cxt.moveTo ($scope.colI.stones[0].Xcoord, $scope.colI.stones[0].Ycoord);
	cxt.lineTo ($scope.colF.stones[3].Xcoord, $scope.colF.stones[3].Ycoord);
	cxt.moveTo ($scope.colD.stones[4].Xcoord, $scope.colD.stones[4].Ycoord);
	cxt.lineTo ($scope.colA.stones[4].Xcoord, $scope.colA.stones[4].Ycoord);

	cxt.moveTo ($scope.colI.stones[1].Xcoord, $scope.colI.stones[1].Ycoord);
	cxt.lineTo ($scope.colB.stones[5].Xcoord, $scope.colB.stones[5].Ycoord);
	cxt.moveTo ($scope.colI.stones[2].Xcoord, $scope.colI.stones[2].Ycoord);
	cxt.lineTo ($scope.colC.stones[6].Xcoord, $scope.colC.stones[6].Ycoord);
	cxt.moveTo ($scope.colI.stones[3].Xcoord, $scope.colI.stones[3].Ycoord);
	cxt.lineTo ($scope.colD.stones[7].Xcoord, $scope.colD.stones[7].Ycoord);
	cxt.moveTo ($scope.colI.stones[4].Xcoord, $scope.colI.stones[4].Ycoord);
	cxt.lineTo ($scope.colE.stones[8].Xcoord, $scope.colE.stones[8].Ycoord);
	cxt.stroke();


	//draw all stones
	cxt.strokeStyle = '#D0D0D0';
	cxt.lineWidth = 3;
	var radius = 20;
	angular.forEach($scope.cols, function(column, key) {
		angular.forEach(column.stones, function(stone, key) {
			if ( ! ($scope.currentmove != null && $scope.currentmove.startstone == stone) ) {
				$scope.drawStone(stone.Xcoord, stone.Ycoord, stone.color, stone.type, stone.height, cxt);
			}
		});
	});


	if ($scope.printlabels) {
		//label all points, with text to the right of the point
		cxt.font = "bold 14px sans-serif";
		cxt.fillStyle = '#C00000';

		angular.forEach($scope.cols, function(column, key) {
			angular.forEach(column.stones, function(stone, key) {
				if ( stone.name != "E5" ) {
					cxt.fillText(stone.name, stone.Xcoord+7, stone.Ycoord+7);
				}
			});
		});
	}
};

$scope.drawStone = function(X, Y, color, type, height, cxt) {
	var stripeHeight = height % 7; //14 is maximum legal height I believe //TODO checkthat
	var bandHeight = (height - stripeHeight) / 7;
	var stripeColor = '#D0D0D0';
	if ( bandHeight >= 1) {
		stripeHeight++;
		stripeColor = '#FF6601'; //Sioux orange
		console.log("Orange!");
	}
	if ( bandHeight >= 2) {
		stripeColor = '#FFD700'; //Gold
		console.log("Gold!");
	}
	cxt.strokeStyle = stripeColor;
	cxt.lineWidth = 3;
	var radius = 20;	
	var mainColor = null;
	if ( color == "white" ) {
		//draw white stone
		mainColor = '#FFFFFF';
	} else if ( color == "black" ) {
		//draw black stone
		mainColor = '#000000';
	} else {
		return;
	}
	//draw shadow first
	cxt.globalAlpha = 0.5;
	cxt.fillStyle = '#000000';
	for (var i = 1; i <= height; i++) {
		cxt.beginPath();
		cxt.arc(X+(3*i), Y+(3*i), radius, 0, 2 * Math.PI, false);
		cxt.fill();
	}
	cxt.globalAlpha = 1.0;
	//draw stone
	cxt.fillStyle = mainColor;
	cxt.beginPath();
	cxt.arc(X, Y, radius, 0, 2 * Math.PI, false);
	cxt.fill(); cxt.stroke();
	switch ( type ) {
		case "TZAAR" :
			//console.log("TZAAR stone type at " + stone.name );
			//draw outer circle
			cxt.fillStyle = stripeColor;
			cxt.beginPath();
			cxt.arc(X, Y, radius*0.7, 0, 2 * Math.PI, false);
			cxt.fill();
			cxt.fillStyle = mainColor;
			cxt.beginPath();
			cxt.arc(X, Y, radius*0.5, 0, 2 * Math.PI, false);
			cxt.fill();
			//draw inner circle
			cxt.beginPath();
			cxt.fillStyle = stripeColor;
			cxt.arc(X, Y, radius*0.3, 0, 2 * Math.PI, false);
			cxt.fill();
		break;
		case "TZARRAS" :
			//console.log("TZARRAS stone type at " + stone.name );
			//draw inner circle
			cxt.beginPath();
			cxt.fillStyle = stripeColor;
			cxt.arc(X, Y, radius*0.4, 0, 2 * Math.PI, false);
			cxt.fill();
		break;
		case "TOTTS" :
			//console.log("TOTTS stone type at " + stone.name );
		break;
		default:
			console.log("Unknown stone type at " + stone.name );
		break;
	}
	if (stripeHeight > 1) {
		cxt.fillStyle = stripeColor;
		for (var i = 0; i < stripeHeight; i++) {
			//console.log(color + " stone @ X: " + X + ", Y:" + Y + ", height: " + height);
			var angle = (Math.PI*1.5) + i *( (Math.PI*2) / (stripeHeight) );
			var offset = Math.PI/15;
			cxt.beginPath();
			cxt.moveTo(X,Y);
			cxt.arc(X,Y, radius*0.9, angle - offset, angle + offset, false);
			cxt.fill()
		}
	}
}

$scope.loadGame = function() {
	console.log("Selected game state:" + $scope.game.startstate);
	if ($scope.game.startstate.length == 61) {
		var stoneIndex = 0;
		angular.forEach($scope.cols, function(column, key) {
			angular.forEach(column.stones, function(stone, key) {
				if ( stone.name != "E5" ) {
					stone.height = 1;
					var stoneState = $scope.game.startstate[stoneIndex];
					switch ( stoneState ) {
						case "t" :
							stone.color = "white";
							stone.type = "TZAAR";
						break;
						case "z" :
							stone.color = "white";
							stone.type = "TZARRAS";
						break;
						case "o" :
							stone.color = "white";
							stone.type = "TOTTS";
						break;
						case "T" :
							stone.color = "black";
							stone.type = "TZAAR";
						break;
						case "Z" :
							stone.color = "black";
							stone.type = "TZARRAS";
						break;
						case "O" :
							stone.color = "black";
							stone.type = "TOTTS";
						break;
						default :
							stone.type = null;
							stone.color = null; 
						break;
					}
				}
				stoneIndex++;
			});
		});
	} else {
		console.log("invalid game state length, should be 61 chars long: " + $scope.game.startstate.length);
	}
};

$scope.getMoveString = function(moveIndex) {
	var move = $scope.game.moves.substring(moveIndex*4, moveIndex*4 + 4)
	console.log("getMove: " + move);
	if (move != "PASS") {
		var startcol = $scope.cols[move.charCodeAt(0) - "A".charCodeAt(0) ];
		//console.log("found startcol:" + startcol.name + "startcol:Xcoord" + startcol.Xcoord);
		var start = startcol.stones[move.charCodeAt(1) - "1".charCodeAt(0)];
		//console.log("found startstone: " + start.name + " X:" + start.Xcoord + " from move: " + move);

		var endcol = $scope.cols[move.charCodeAt(2) - "A".charCodeAt(0)];
		var end = endcol.stones[move.charCodeAt(3) - "1".charCodeAt(0)];
		//console.log("found endstone: " + end.name + " from move: " + move);

		return { index: moveIndex+1, moveString: move, startstone: start, endstone: end };
	} else {
		return { index: moveIndex+1, moveString: move};
	}
}

$scope.startPlaying = function() {
	console.log("called startPlaying");
	$scope.isPlaying = true;
	$scope.increaseMoveAndUpdate();
}

$scope.pausePlaying = function() {
	console.log("called pausePlaying");
	$scope.isPlaying = false;
}

$scope.increaseMoveAndUpdate = function() {


	//update next move
        if ( $scope.currentmoveIndex < ($scope.game.moves.length/4)) {
		$scope.currentmoveIndex++;
		$scope.currentmove = $scope.game.moveStrings[$scope.currentmoveIndex];
		var startTime = (new Date()).getTime();
		$scope.isAnimating = true;
		$scope.drawGame();
		$scope.animate( startTime);
	}

};

$scope.decreaseMoveAndUpdate = function() {


	//update next move
    if ( $scope.currentmoveIndex > 0) {
		$scope.currentmoveIndex--;
		$scope.currentmove = $scope.game.moveStrings[$scope.currentmoveIndex];
		console.log("calling processMoves from decreaseMoveAndUPdate");
		$scope.processMoves($scope.currentmoveIndex);
		$scope.updateActiveColor(false);
		$scope.drawGame();
		//$scope.drawCompletedMove();
	}

};

$scope.loadMoves = function() {
	$scope.game.moveStrings = [{ index: 0, moveString: "PASS", move_img: ""}];
	for ( var moveIndex = 0; moveIndex < ($scope.game.moves.length/4); moveIndex++) {
		var move = $scope.getMoveString(moveIndex);
		if (moveIndex == ($scope.game.moves.length/4)-1) {
			//last move
			move.move_img = "./img/w.png"
		} else if (move.moveString != "PASS" ) {
			if (move.endstone.color == move.startstone.color) {
				move.move_img = "./img/shield_PNG1268.png";
				move.endstone.height = move.startstone.height + 1;
			} else {
				move.move_img = "./img/Crossed_gladii.png";
				move.endstone.height = move.startstone.height;
				move.endstone.color = move.startstone.color;
			}
			move.endstone.type = move.startstone.type;
			move.startstone.color = null;
			move.startstone.type = null;
		} else {
			move.move_img = "./img/clock-147257_640.png";
		}
		console.log("adding move " + move.index);
		$scope.game.moveStrings.push(move);
	}
	//reset game
	$scope.loadGame();
};

$scope.processMoves = function(moveCount) {
	$scope.loadGame();
	for ( var moveIndex = 0; moveIndex <= moveCount; moveIndex++) {
		var move = $scope.game.moveStrings[moveIndex];
		//console.log("processing move: " +  move.moveString);
		if (move.moveString != "PASS" ) {
			if (move.endstone.color == move.startstone.color) {
				move.endstone.height = move.endstone.height + move.startstone.height;
			} else {
				move.endstone.height = move.startstone.height;
				move.endstone.color = move.startstone.color;
			}
			move.endstone.type = move.startstone.type;
			move.startstone.color = null;
			move.startstone.type = null;
		}
	}
	$scope.updateStoneCount ();
	$scope.checkVictory();
};



$scope.checkVictory = function() {
	if ( $scope.game.whiteTzaarCount == 0 || 
		$scope.game.whiteTzarrasCount == 0 ||
		$scope.game.whiteTottsCount == 0 ) {
		$scope.winner = $scope.game.bot2;
	} else if ($scope.game.blackTzaarCount == 0 ||
		$scope.game.blackTzarrasCount == 0 ||
		$scope.game.blackTottsCount == 0 ) {
		$scope.winner = $scope.game.bot1;
	} else if ( false ) {//no more legal moves
	}
}


$scope.updateStoneCount = function() {
	$scope.game.whiteTzaarCount = 0;
	$scope.game.whiteTzarrasCount = 0;
	$scope.game.whiteTottsCount = 0;
	$scope.game.blackTzaarCount = 0;
	$scope.game.blackTzarrasCount = 0;
	$scope.game.blackTottsCount = 0;
	angular.forEach($scope.cols, function(column, key) {
		angular.forEach(column.stones, function(stone, key) {
			if ( stone.color == "white" ) {
				switch ( stone.type ) {
					case "TZAAR" :
						$scope.game.whiteTzaarCount++;
					break;
					case "TZARRAS" :
						$scope.game.whiteTzarrasCount++;
					break;
					case "TOTTS" :
						$scope.game.whiteTottsCount++;
					break;
				}
			} else if ( stone.color == "black") {
				switch ( stone.type ) {
					case "TZAAR" :
						$scope.game.blackTzaarCount++;
					break;
					case "TZARRAS" :
						$scope.game.blackTzarrasCount++;
					break;
					case "TOTTS" :
						$scope.game.blackTottsCount++;
					break;
				}
			}
		});
	});
};

$scope.updateActiveColor = function(applyFlag) {
	var color = "";
	if ( $scope.currentmoveIndex < $scope.game.moves.length/4 ) {
	//console.log("currentmoveIndex = " + $scope.currentmoveIndex + " calc: " + (Math.floor(($scope.currentmoveIndex)/2) % 2) + "floor: " + Math.floor(($scope.currentmoveIndex)/2));
		if ( (Math.floor(($scope.currentmoveIndex+1)/2) % 2) == 0) {
				color = "white";
		} else {
				color = "black";
		}
	} else {
		color = "Game Over";
	}
	if ( applyFlag) {
		$scope.$apply(function() {
			$scope.activeColor = color;
		});
	} else {
		$scope.activeColor = color;
	}
	//console.log("activeColor: " + $scope.activeColor);
};



$scope.drawCompletedMove = function() {
	if($scope.currentmoveIndex > 0 && $scope.currentmove == null) {
		/*var cxt = document.getElementById('c').getContext('2d');
		if ( $scope.drawingloaded == true) {
			cxt.drawImage($scope.drawing,$scope.colD.stones[3].Xcoord+12,$scope.colD.stones[3].Ycoord + 12,2*$scope.NW_SE_X - 24,2*$scope.NW_SE_Y - 24);
		}*/
		
		/*
		var move = $scope.getMoveString($scope.currentmoveIndex-1);
		if (move.moveString != "PASS" ) {
			console.log("Drawing Completed move for moveIndex: " + move.index + " = " + move.moveString);
			if ( move.endstone.color == "white" ) {
				//draw white stone
				cxt.fillStyle = '#FFFFFF';
			} else {
				//draw black stone
				cxt.fillStyle = '#000000';
			}		
			cxt.strokeStyle = '#000000';
			cxt.lineWidth = 3;
			cxt.beginPath();

			var headlen = 20;   // length of head in pixels
			var bodydwidth = 20;
			var angle = Math.atan2(move.endstone.Ycoord-move.startstone.Ycoord,move.endstone.Xcoord-move.startstone.Xcoord);

			var B1 = { x:move.startstone.Xcoord-bodydwidth*Math.cos(angle-Math.PI/2), y:move.startstone.Ycoord-bodydwidth*Math.sin(angle-Math.PI/2)};
			var B2 = { x:move.startstone.Xcoord+bodydwidth*Math.cos(angle-Math.PI/2), y:move.startstone.Ycoord+bodydwidth*Math.sin(angle-Math.PI/2)};
			var HC = { x:move.endstone.Xcoord-headlen*Math.cos(angle), y:move.endstone.Ycoord-headlen*Math.sin(angle)};

			cxt.moveTo(B1.x, B1.y);
			cxt.lineTo(B2.x, B2.y);
			//cxt.lineTo(endstone.Xcoord, endstone.Ycoord);
			cxt.lineTo(HC.x, HC.y);
			//cxt.lineTo(startstone.Xcoord-bodydwidth*Math.cos(angle+Math.PI/6),startstone.Ycoord-bodydwidth*Math.sin(angle+Math.PI/6));

			cxt.lineTo(B1.x, B1.y);
			cxt.fill();
			cxt.stroke();

		}*/
	}
}

    $scope.drawGame();

    $scope.$watch('printlabels', function(value) {
      console.log(value);
      $scope.drawGame();
    });
    $scope.$watch('cols', function(value) {
      console.log("update to cols");
      //$scope.drawGame();
      //$scope.drawCompletedMove();
    }, true);


//////////////////////////////////////////ANIMATION /////////////////////////////////////////
      window.requestAnimFrame = (function(callback) {
        return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame ||
        function(callback) {
          window.setTimeout(callback, 1000 / 60);
        };
      })();

$scope.animate = function( startTime) {
		
	var cxt = document.getElementById('cmove').getContext('2d');
	cxt.clearRect(0,0,cxt.canvas.width,cxt.canvas.height);
	if ($scope.currentmove == null || $scope.currentmove.moveString == "PASS") {
		console.log("done animating, currentmoveIndex:" + $scope.currentmoveIndex);
		/*$scope.isAnimating = false;
		console.log("Calling processMoves from animate function start");
		$scope.processMoves($scope.currentmoveIndex);
		$scope.updateActiveColor(false);
		$scope.drawGame();
		return;*/
	} else {

        // update
        var time = (new Date()).getTime() - startTime;
		var period = 1000;

		var startX = $scope.currentmove.startstone.Xcoord;
		var startY = $scope.currentmove.startstone.Ycoord;
		var endX = $scope.currentmove.endstone.Xcoord;
		var endY = $scope.currentmove.endstone.Ycoord;
		//console.log("animate:  Xcoord: " + $scope.currentmove.startstone.Xcoord );
		//console.log("endX: " + endX + " startX: " + startX + " time: " + time + " time/period " + time/period);
		var currentX = startX - ((startX - endX) * (time/period)); 
		var currentY = startY - ((startY - endY) * (time/period));

		//$scope.drawGame();
		
		//console.log("animate, currentmove=" + $scope.currentmove.index + "X:" + currentX + " Y:" + currentY + " color:" + $scope.currentmove.startstone.color);
		$scope.drawStone(currentX, currentY, $scope.currentmove.startstone.color, $scope.currentmove.startstone.type, $scope.currentmove.startstone.height, cxt);
	}
        // request new frame
	if ( time < period ) {
		//console.log("do another animate");
		requestAnimFrame(function() {
		  $scope.animate( startTime);
		});
	} else {
		console.log("done animating, currentmoveIndex:" + $scope.currentmoveIndex);
		cxt.clearRect(0,0,cxt.canvas.width,cxt.canvas.height);
		$scope.isAnimating = false;
		console.log("Calling processMoves from animate function end");
		$scope.processMoves($scope.currentmoveIndex);
		$scope.updateActiveColor(true);
		$scope.drawGame();
		if($scope.isPlaying) {
			$scope.increaseMoveAndUpdate();
		}
	}
      }
//////////////////////////////////////////ANIMATION /////////////////////////////////////////
}]);

//CONTROLLER FOR mod_insert.html
meanControllers.controller('InsertCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {
	$scope.newStartstate = "oOOOOozZZZooztTTzooztoOtzoOZTO otzoOZToOTZOOZttTZOOzzzZOooooO"
	$scope.insertSubmit = function() {
		var data = {
			'name' : $scope.newName, 
			'bot1' : $scope.newBot1, 
			'bot2' : $scope.newBot2, 
			'startstate' : $scope.newStartstate, 
			'moves' : $scope.newMoves, 
		};
		$http.post('/api/game/create/', data).success(function(){
			$location.path("/");
		});
	}

}]);
