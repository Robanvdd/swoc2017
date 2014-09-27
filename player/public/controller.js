var meanControllers = angular.module('meanControllers', ['ngAnimate', 'highcharts-ng']);

meanControllers.factory("GameLibrary", function() {
	var gameLibrary = {};

	gameLibrary.initGame = function(game) {



		game.currentmoveIndex = 0;

		game.numberOfSides = 6;
		game.size = 240;
		game.Xcenter = 275;
		game.Ycenter = 275;
		game.stoneRadius = 20;
		
		game.unit_distance = (game.size / 4);
		game.activeColor = "white";
		game.passStatus = "First_move";
		// Vertical |
		// NW_SE \
		// NE_SW /
		// ((starting at top))
		game.vertical_X = 0;
		game.vertical_Y = game.unit_distance;
		
		game.NW_SE_X = game.unit_distance * Math.cos(0.5* 2 * Math.PI / game.numberOfSides);
		game.NW_SE_Y = game.unit_distance * Math.sin(0.5* 2 * Math.PI / game.numberOfSides);

		game.NE_SW_X = game.unit_distance * Math.cos(5.5* 2 * Math.PI / game.numberOfSides);
		game.NE_SW_Y = game.unit_distance * Math.sin(5.5* 2 * Math.PI / game.numberOfSides);

		game.colA = {
			name : "A",
			size : 5,
			Xcoord : game.Xcenter +  (game.size) * Math.cos((3.5)* 2 * Math.PI / game.numberOfSides),
			Ycoord : game.Ycenter +  (game.size) * Math.sin((3.5)* 2 * Math.PI / game.numberOfSides),
			stones: []
		};

		game.colB = {
			name : "B",
			size : 6,
			Xcoord : game.colA.Xcoord + game.NE_SW_X,
			Ycoord : game.colA.Ycoord + game.NE_SW_Y,
			stones: []
		};

		game.colC = {
			name : "C",
			size : 7,
			Xcoord : game.colB.Xcoord + game.NE_SW_X,
			Ycoord : game.colB.Ycoord + game.NE_SW_Y,
			stones: []
		};

		game.colD = {
			name : "D",
			size : 8,
			Xcoord : game.colC.Xcoord + game.NE_SW_X,
			Ycoord : game.colC.Ycoord + game.NE_SW_Y,
			stones: []
		};

		game.colE = {
			name : "E",
			size : 9,
			Xcoord : game.colD.Xcoord + game.NE_SW_X,
			Ycoord : game.colD.Ycoord + game.NE_SW_Y,
			stones: []
		};

		game.colF = {
			name : "F",
			size : 8,
			Xcoord : game.colE.Xcoord + game.NW_SE_X,
			Ycoord : game.colE.Ycoord + game.NW_SE_Y,
			stones: []
		};

		game.colG = {
			name : "G",
			size : 7,
			Xcoord : game.colF.Xcoord + game.NW_SE_X,
			Ycoord : game.colF.Ycoord + game.NW_SE_Y,
			stones: []
		};

		game.colH = {
			name : "H",
			size : 6,
			Xcoord : game.colG.Xcoord + game.NW_SE_X,
			Ycoord : game.colG.Ycoord + game.NW_SE_Y,
			stones: []
		};

		game.colI = {
			name : "I",
			size : 5,
			Xcoord : game.colH.Xcoord + game.NW_SE_X,
			Ycoord : game.colH.Ycoord + game.NW_SE_Y,
			stones: []
		};


		game.cols = [game.colA, game.colB, game.colC, game.colD, game.colE, game.colF, game.colG, game.colH, game.colI];
		angular.forEach(game.cols, function(column, key) {
			for (var i=1; i<= column.size; i++) {
				column.stones.push({
					name : column.name + i,
					Xcoord : column.Xcoord,
					Ycoord : column.Ycoord + column.stones.length * game.vertical_Y,
					color : null,
					height : null,
					type : null
				});
				console.log("added a stone to column " + column.name + ": " + column.stones[i-1].name 
				+ " at X:" + column.stones[i-1].Xcoord + " Y:" + column.stones[i-1].Ycoord);
			}
		});

		//NE_SW cols
		game.NE_SWcolE1 = {name: "E1", size: 5, stones: [game.colE.stones[0], game.colD.stones[0], game.colC.stones[0], game.colB.stones[0], game.colA.stones[0]]};
		game.NE_SWcolF1 = {name: "F1", size: 6, stones: [game.colF.stones[0], game.colE.stones[1], game.colD.stones[1], game.colC.stones[1], game.colB.stones[1], game.colA.stones[1]]};
		game.NE_SWcolG1 = {name: "G1", size: 7, stones: [game.colG.stones[0], game.colF.stones[1], game.colE.stones[2], game.colD.stones[2], game.colC.stones[2], game.colB.stones[2], game.colA.stones[2]]};
		game.NE_SWcolH1 = {name: "H1", size: 8, stones: [game.colH.stones[0], game.colG.stones[1], game.colF.stones[2], game.colE.stones[3], game.colD.stones[3], game.colC.stones[3], game.colB.stones[3], game.colA.stones[3]]};
		game.NE_SWcolI1 = {name: "I1", size: 9, stones: [game.colI.stones[0], game.colH.stones[1], game.colG.stones[2], game.colF.stones[3], game.colE.stones[4], game.colD.stones[4], game.colC.stones[4], game.colB.stones[4], game.colA.stones[4]]};
		game.NE_SWcolI2 = {name: "I2", size: 8, stones: [game.colI.stones[1], game.colH.stones[2], game.colG.stones[3], game.colF.stones[4], game.colE.stones[5], game.colD.stones[5], game.colC.stones[5], game.colB.stones[5]]};
		game.NE_SWcolI3 = {name: "I3", size: 7, stones: [game.colI.stones[2], game.colH.stones[3], game.colG.stones[4], game.colF.stones[5], game.colE.stones[6], game.colD.stones[6], game.colC.stones[6]]};
		game.NE_SWcolI4 = {name: "I4", size: 6, stones: [game.colI.stones[3], game.colH.stones[4], game.colG.stones[5], game.colF.stones[6], game.colE.stones[7], game.colD.stones[7]]};
		game.NE_SWcolI5 = {name: "I5", size: 5, stones: [game.colI.stones[4], game.colH.stones[5], game.colG.stones[6], game.colF.stones[7], game.colE.stones[8]]};
		game.NE_SWcols = [game.NE_SWcolE1, game.NE_SWcolF1, game.NE_SWcolG1, game.NE_SWcolH1, game.NE_SWcolI1, game.NE_SWcolI2, game.NE_SWcolI3, game.NE_SWcolI4, game.NE_SWcolI5];
		//NW_SE cols
		game.NW_SEcolE1 = {name: "E1", size: 5, stones: [game.colE.stones[0], game.colF.stones[0], game.colG.stones[0], game.colH.stones[0], game.colI.stones[0]]};
		game.NW_SEcolD1 = {name: "D1", size: 6, stones: [game.colD.stones[0], game.colE.stones[1], game.colF.stones[1], game.colG.stones[1], game.colH.stones[1], game.colI.stones[1]]};
		game.NW_SEcolC1 = {name: "C1", size: 7, stones: [game.colC.stones[0], game.colD.stones[1], game.colE.stones[2], game.colF.stones[2], game.colG.stones[2], game.colH.stones[2], game.colI.stones[2]]};
		game.NW_SEcolB1 = {name: "B1", size: 8, stones: [game.colB.stones[0], game.colC.stones[1], game.colD.stones[2], game.colE.stones[3], game.colF.stones[3], game.colG.stones[3], game.colH.stones[3], game.colI.stones[3]]};
		game.NW_SEcolA1 = {name: "A1", size: 9, stones: [game.colA.stones[0], game.colB.stones[1], game.colC.stones[2], game.colD.stones[3], game.colE.stones[4], game.colF.stones[4], game.colG.stones[4], game.colH.stones[4], game.colI.stones[4]]};
		game.NW_SEcolA2 = {name: "A2", size: 8, stones: [game.colA.stones[1], game.colB.stones[2], game.colC.stones[3], game.colD.stones[4], game.colE.stones[5], game.colF.stones[5], game.colG.stones[5], game.colH.stones[5]]};
		game.NW_SEcolA3 = {name: "A3", size: 7, stones: [game.colA.stones[2], game.colB.stones[3], game.colC.stones[4], game.colD.stones[5], game.colE.stones[6], game.colF.stones[6], game.colG.stones[6]]};
		game.NW_SEcolA4 = {name: "A4", size: 6, stones: [game.colA.stones[3], game.colB.stones[4], game.colC.stones[5], game.colD.stones[6], game.colE.stones[7], game.colF.stones[7]]};
		game.NW_SEcolA5 = {name: "A5", size: 5, stones: [game.colA.stones[4], game.colB.stones[5], game.colC.stones[6], game.colD.stones[7], game.colE.stones[8]]};
		game.NW_SEcols = [game.NW_SEcolE1, game.NW_SEcolD1, game.NW_SEcolC1, game.NW_SEcolB1, game.NW_SEcolA1, game.NW_SEcolA2, game.NW_SEcolA3, game.NW_SEcolA4, game.NW_SEcolA5];
	}

	gameLibrary.loadGame = function(game) {
		console.log("Selected game state:" + game.startstate);
		if (game.startstate.length == 61) {
			var stoneIndex = 0;
			angular.forEach(game.cols, function(column, key) {
				angular.forEach(column.stones, function(stone, key) {
					if ( stone.name != "E5" ) {
						stone.height = 1;
						var stoneState = game.startstate[stoneIndex];
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
			console.log("invalid game state length, should be 61 chars long: " + game.startstate.length);
		}
	}

	gameLibrary.loadMoves = function(game) {
		game.moveStrings = [{ index: 0, moveString: "PASS", move_img: ""}];
		for ( var moveIndex = 0; moveIndex < (game.moves.length/4); moveIndex++) {
			var move = gameLibrary.getMoveString(game, moveIndex);
			if (move.moveString != "PASS" ) {
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
			//console.log("adding move " + move.index);
			game.moveStrings.push(move);
		}
		//reset game
		gameLibrary.loadGame(game);
	};

	gameLibrary.getMoveString = function(game, moveIndex) {
		var move = game.moves.substring(moveIndex*4, moveIndex*4 + 4)
		console.log("getMove: " + move);
		if (move != "PASS") {
			var startcol = game.cols[move.charCodeAt(0) - "A".charCodeAt(0) ];
			//console.log("found startcol:" + startcol.name + "startcol:Xcoord" + startcol.Xcoord);
			var start = startcol.stones[move.charCodeAt(1) - "1".charCodeAt(0)];
			//console.log("found startstone: " + start.name + " X:" + start.Xcoord + " from move: " + move);

			var endcol = game.cols[move.charCodeAt(2) - "A".charCodeAt(0)];
			var end = endcol.stones[move.charCodeAt(3) - "1".charCodeAt(0)];
			//console.log("found endstone: " + end.name + " from move: " + move);

			return { index: moveIndex+1, moveString: move, startstone: start, endstone: end };
		} else {
			return { index: moveIndex+1, moveString: move};
		}
	}

	gameLibrary.drawGame = function(game) {

		console.log("draw game");
		var cxt = document.getElementById('c').getContext('2d');
		cxt.clearRect(0,0,cxt.canvas.width,cxt.canvas.height);
		  
		//draw grey hexagon
		cxt.beginPath();
		cxt.moveTo (game.Xcenter +  game.size * Math.cos(0.5), game.Ycenter +  game.size *  Math.sin(0.5));          

		for (var i = 1; i <= game.numberOfSides;i += 1) {
		    cxt.lineTo (game.Xcenter + game.size * Math.cos((i+0.5) * 2 * Math.PI / game.numberOfSides), game.Ycenter + game.size * Math.sin((i+0.5) * 2 * Math.PI / game.numberOfSides));
		}
		cxt.fillStyle = '#F0F0F0';
		cxt.strokeStyle = "#000000";
		cxt.lineWidth = 1;
		cxt.fill();
		//clear center piece
		cxt.fillStyle = '#FFFFFF';
		cxt.beginPath();
		cxt.moveTo (game.Xcenter +  (game.size/4) * Math.cos(0.5), game.Ycenter +  (game.size/4) *  Math.sin(0.5));          

		for (var i = 1; i <= game.numberOfSides;i += 1) {
		    cxt.lineTo (game.Xcenter + (game.size/4) * Math.cos((i+0.5) * 2 * Math.PI / game.numberOfSides), game.Ycenter + (game.size/4) * Math.sin((i+0.5) * 2 * Math.PI / game.numberOfSides));
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
		cxt.moveTo (game.colA.stones[0].Xcoord, game.colA.stones[0].Ycoord);
		cxt.lineTo (game.colA.stones[4].Xcoord, game.colA.stones[4].Ycoord);
		cxt.moveTo (game.colB.stones[0].Xcoord, game.colB.stones[0].Ycoord);
		cxt.lineTo (game.colB.stones[5].Xcoord, game.colB.stones[5].Ycoord);
		cxt.moveTo (game.colC.stones[0].Xcoord, game.colC.stones[0].Ycoord);
		cxt.lineTo (game.colC.stones[6].Xcoord, game.colC.stones[6].Ycoord);
		cxt.moveTo (game.colD.stones[0].Xcoord, game.colD.stones[0].Ycoord);
		cxt.lineTo (game.colD.stones[7].Xcoord, game.colD.stones[7].Ycoord);

		cxt.moveTo (game.colE.stones[0].Xcoord, game.colE.stones[0].Ycoord);
		cxt.lineTo (game.colE.stones[3].Xcoord, game.colE.stones[3].Ycoord);
		cxt.moveTo (game.colE.stones[5].Xcoord, game.colE.stones[5].Ycoord);
		cxt.lineTo (game.colE.stones[8].Xcoord, game.colE.stones[8].Ycoord);

		cxt.moveTo (game.colF.stones[0].Xcoord, game.colF.stones[0].Ycoord);
		cxt.lineTo (game.colF.stones[7].Xcoord, game.colF.stones[7].Ycoord);
		cxt.moveTo (game.colG.stones[0].Xcoord, game.colG.stones[0].Ycoord);
		cxt.lineTo (game.colG.stones[6].Xcoord, game.colG.stones[6].Ycoord);
		cxt.moveTo (game.colH.stones[0].Xcoord, game.colH.stones[0].Ycoord);
		cxt.lineTo (game.colH.stones[5].Xcoord, game.colH.stones[5].Ycoord);
		cxt.moveTo (game.colI.stones[0].Xcoord, game.colI.stones[0].Ycoord);
		cxt.lineTo (game.colI.stones[4].Xcoord, game.colI.stones[4].Ycoord);

		//NW_SE lines
		cxt.moveTo (game.colE.stones[0].Xcoord, game.colE.stones[0].Ycoord);
		cxt.lineTo (game.colI.stones[0].Xcoord, game.colI.stones[0].Ycoord);
		cxt.moveTo (game.colD.stones[0].Xcoord, game.colD.stones[0].Ycoord);
		cxt.lineTo (game.colI.stones[1].Xcoord, game.colI.stones[1].Ycoord);
		cxt.moveTo (game.colC.stones[0].Xcoord, game.colC.stones[0].Ycoord);
		cxt.lineTo (game.colI.stones[2].Xcoord, game.colI.stones[2].Ycoord);
		cxt.moveTo (game.colB.stones[0].Xcoord, game.colB.stones[0].Ycoord);
		cxt.lineTo (game.colI.stones[3].Xcoord, game.colI.stones[3].Ycoord);

		cxt.moveTo (game.colA.stones[0].Xcoord, game.colA.stones[0].Ycoord);
		cxt.lineTo (game.colD.stones[3].Xcoord, game.colD.stones[3].Ycoord);
		cxt.moveTo (game.colF.stones[4].Xcoord, game.colF.stones[4].Ycoord);
		cxt.lineTo (game.colI.stones[4].Xcoord, game.colI.stones[4].Ycoord);

		cxt.moveTo (game.colA.stones[1].Xcoord, game.colA.stones[1].Ycoord);
		cxt.lineTo (game.colH.stones[5].Xcoord, game.colH.stones[5].Ycoord);
		cxt.moveTo (game.colA.stones[2].Xcoord, game.colA.stones[2].Ycoord);
		cxt.lineTo (game.colG.stones[6].Xcoord, game.colG.stones[6].Ycoord);
		cxt.moveTo (game.colA.stones[3].Xcoord, game.colA.stones[3].Ycoord);
		cxt.lineTo (game.colF.stones[7].Xcoord, game.colF.stones[7].Ycoord);
		cxt.moveTo (game.colA.stones[4].Xcoord, game.colA.stones[4].Ycoord);
		cxt.lineTo (game.colE.stones[8].Xcoord, game.colE.stones[8].Ycoord);

		//NE_SW lines
		cxt.moveTo (game.colE.stones[0].Xcoord, game.colE.stones[0].Ycoord);
		cxt.lineTo (game.colA.stones[0].Xcoord, game.colA.stones[0].Ycoord);
		cxt.moveTo (game.colF.stones[0].Xcoord, game.colF.stones[0].Ycoord);
		cxt.lineTo (game.colA.stones[1].Xcoord, game.colA.stones[1].Ycoord);
		cxt.moveTo (game.colG.stones[0].Xcoord, game.colG.stones[0].Ycoord);
		cxt.lineTo (game.colA.stones[2].Xcoord, game.colA.stones[2].Ycoord);
		cxt.moveTo (game.colH.stones[0].Xcoord, game.colH.stones[0].Ycoord);
		cxt.lineTo (game.colA.stones[3].Xcoord, game.colA.stones[3].Ycoord);

		cxt.moveTo (game.colI.stones[0].Xcoord, game.colI.stones[0].Ycoord);
		cxt.lineTo (game.colF.stones[3].Xcoord, game.colF.stones[3].Ycoord);
		cxt.moveTo (game.colD.stones[4].Xcoord, game.colD.stones[4].Ycoord);
		cxt.lineTo (game.colA.stones[4].Xcoord, game.colA.stones[4].Ycoord);

		cxt.moveTo (game.colI.stones[1].Xcoord, game.colI.stones[1].Ycoord);
		cxt.lineTo (game.colB.stones[5].Xcoord, game.colB.stones[5].Ycoord);
		cxt.moveTo (game.colI.stones[2].Xcoord, game.colI.stones[2].Ycoord);
		cxt.lineTo (game.colC.stones[6].Xcoord, game.colC.stones[6].Ycoord);
		cxt.moveTo (game.colI.stones[3].Xcoord, game.colI.stones[3].Ycoord);
		cxt.lineTo (game.colD.stones[7].Xcoord, game.colD.stones[7].Ycoord);
		cxt.moveTo (game.colI.stones[4].Xcoord, game.colI.stones[4].Ycoord);
		cxt.lineTo (game.colE.stones[8].Xcoord, game.colE.stones[8].Ycoord);
		cxt.stroke();


		//draw all stones
		angular.forEach(game.cols, function(column, key) {
			angular.forEach(column.stones, function(stone, key) {
				if ( ! (game.currentmove != null && game.currentmove.startstone == stone) ) {
					gameLibrary.drawStone(game, stone.Xcoord, stone.Ycoord, stone.color, stone.type, stone.height, cxt);
				}
			});
		});


		if (game.printlabels) {
			//label all points, with text to the right of the point
			cxt.font = "bold 14px sans-serif";
			cxt.fillStyle = '#C00000';

			angular.forEach(game.cols, function(column, key) {
				angular.forEach(column.stones, function(stone, key) {
					if ( stone.name != "E5" ) {
						cxt.fillText(stone.name, stone.Xcoord+7, stone.Ycoord+7);
					}
				});
			});
		}
	};

	gameLibrary.drawStone = function(game, X, Y, color, type, height, cxt) {
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
		if ( game.selectedStone != null &&  X == game.selectedStone.Xcoord && Y == game.selectedStone.Ycoord ) {
			stripeColor = '#0000FF'; //BLUE
		} else if ( gameLibrary.isValidTargetStone(game, X,Y) == "true" ) {
			stripeColor = '#00FF00'; //GREEN
		}
		cxt.strokeStyle = stripeColor;
		cxt.lineWidth = 3;
		var radius = game.stoneRadius;	
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

	gameLibrary.isValidTargetStone =  function(game, X, Y) {
		var result = "false";
		angular.forEach(game.validTargetStones, function(stone, key) {
			if ( stone.Xcoord == X && stone.Ycoord == Y ) {
				result = "true";
			}
		});
		return result;
	}

	gameLibrary.processMoves = function(game, moveCount) {
		gameLibrary.loadGame(game);
		for ( var moveIndex = 0; moveIndex <= moveCount; moveIndex++) {
			var move = game.moveStrings[moveIndex];
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
		gameLibrary.updateActiveColor(game, false);
		gameLibrary.updateStoneCount(game);
		gameLibrary.checkVictory(game);
	};

	gameLibrary.checkVictory = function(game) {
		var victory = false;
		var mayPause = ((game.currentmoveIndex+1) % 2 == 1) && (game.currentmoveIndex != 0);
		if ( game.whiteTzaarCount == 0 || 
			game.whiteTzarrasCount == 0 ||
			game.whiteTottsCount == 0 ) {
			game.winner = game.bot2;
			victory = true;
		} else if (game.blackTzaarCount == 0 ||
			game.blackTzarrasCount == 0 ||
			game.blackTottsCount == 0 ) {
			game.winner = game.bot1;
			victory = true;
		} else  if ( mayPause == false ) {
			validmove = false;
			angular.forEach(game.cols, function(column, key) {
				angular.forEach(column.stones, function(stone, key) {
					if ( stone.color == game.activeColor) {
						var validTargetStones = gameLibrary.getValidTargets(game, stone);
						if ( validTargetStones.length > 0) {
							validmove = true;
						}
					}
				});
			});
			if ( validmove == false) {
				victory = true;
				if (game.activeColor == "white") {
					game.winner = game.bot2;
				} else {
					game.winner = game.bot1;
				}
			}
		}
		if ( victory ) {
			console.log("we have a winner: " + game.winner);
			game.moveStrings.push({ index: game.moveStrings.length, moveString: "PASS", move_img: "./img/w.png"});
			game.currentmoveIndex++;
		}
	}

	gameLibrary.updateStoneCount = function(game) {
		game.whiteTzaarCount = 0;
		game.whiteTzarrasCount = 0;
		game.whiteTottsCount = 0;
		game.blackTzaarCount = 0;
		game.blackTzarrasCount = 0;
		game.blackTottsCount = 0;
		angular.forEach(game.cols, function(column, key) {
			angular.forEach(column.stones, function(stone, key) {
				if ( stone.color == "white" ) {
					switch ( stone.type ) {
						case "TZAAR" :
							game.whiteTzaarCount++;
						break;
						case "TZARRAS" :
							game.whiteTzarrasCount++;
						break;
						case "TOTTS" :
							game.whiteTottsCount++;
						break;
					}
				} else if ( stone.color == "black") {
					switch ( stone.type ) {
						case "TZAAR" :
							game.blackTzaarCount++;
						break;
						case "TZARRAS" :
							game.blackTzarrasCount++;
						break;
						case "TOTTS" :
							game.blackTottsCount++;
						break;
					}
				}
			});
		});
	};


	gameLibrary.updateActiveColor = function(game, applyFlag) {
		var color = "";
		if ( game.winner != null ) {
			color = "Game Over";
		} else 	if ( (Math.floor((game.currentmoveIndex+1)/2) % 2) == 0) {
				color = "white";
		} else {
				color = "black";
		}
		game.activeColor = color;
		console.log("game.currentmoveIndex : " + game.currentmoveIndex + " activeColor: " + game.activeColor);
	};

	gameLibrary.pauseClick = function(game) {
		var move = {};
		move.move_img = "./img/clock-147257_640.png";
		move.index = game.moveStrings.length;
		move.moveString = "PASS"
		game.moves = game.moves + move.moveString;
		game.moveStrings.push(move);
		game.selectedStone = null;
		game.validTargetStones = [];
		
		game.currentmoveIndex++;
		game.currentmove = game.moveStrings[game.currentmoveIndex];
		gameLibrary.drawGame(game);
		var startTime = (new Date()).getTime();
		game.isAnimating = true;
		gameLibrary.animate(game, startTime);
	}

	gameLibrary.clickGame = function(game, event) {
		console.log("clickGame, event: " + event + " pageX: " + event.pageX + " pageY: " + event.pageY);
		var canvasX = event.pageX - event.target.offsetLeft;
		var canvasY = event.pageY - event.target.offsetTop;
		console.log("clickGame, event: " + event + " canvasX : " + canvasX  + " canvasY : " + canvasY );
		var performMove = false;
		var validTargetStone = null;
		//did we click on a valid target?
		angular.forEach(game.validTargetStones, function(stone, key) {
			if ( Math.abs(stone.Xcoord - canvasX) < game.stoneRadius && Math.abs(stone.Ycoord - canvasY) < game.stoneRadius) {
				console.log("clicked valid target stone: " + stone.name);
				validTargetStone = stone;
				performMove = true;
			}
		});
		if (performMove == true) {
			var move = {};
			move.endstone = validTargetStone;
			move.startstone = game.selectedStone;
			if (move.endstone.color != move.startstone.color) {
				move.move_img = "./img/Crossed_gladii.png";
			} else {
				move.move_img = "./img/shield_PNG1268.png";
			}
			move.index = game.moveStrings.length;
			move.moveString = move.startstone.name + move.endstone.name;
			game.moves = game.moves + move.moveString;
			game.moveStrings.push(move);
			game.selectedStone = null;
			game.validTargetStones = [];
			
			game.currentmoveIndex++;
			game.currentmove = game.moveStrings[game.currentmoveIndex];
			gameLibrary.drawGame(game);
			var startTime = (new Date()).getTime();
			game.isAnimating = true;
			gameLibrary.animate(game, startTime);
		} else {
			game.selectedStone = null;
			game.validTargetStones = [];
			var selectedVerticalColumn = null;
			angular.forEach(game.cols, function(column, key) {
				angular.forEach(column.stones, function(stone, key) {
					if ( Math.abs(stone.Xcoord - canvasX) < game.stoneRadius && Math.abs(stone.Ycoord - canvasY) < game.stoneRadius) {
						console.log("clicked stone: " + stone.name);
						if ( stone.color == game.activeColor) {
							game.selectedStone = stone;
							selectedVerticalColumn = column;
						}
					}
				});
			});
			if (game.selectedStone != null) {
				game.validTargetStones = gameLibrary.getValidTargets(game, game.selectedStone);
			}
			gameLibrary.drawGame(game);
		}
	}

	gameLibrary.getValidTargets = function(game, sourceStone) {
		var targetX = sourceStone.Xcoord;
		var targetY = sourceStone.Ycoord;
		var mayDefend = ((game.currentmoveIndex+1) % 2 == 1) && (game.currentmoveIndex != 0);
		//console.log("moveIndex: " + game.currentmoveIndex + " mayDefend: " + mayDefend);
		var validTargetStones = [];

		var allCols = [game.cols, game.NE_SWcols, game.NW_SEcols];
		angular.forEach(allCols, function(cols, key){
			var index = 0;
			var selectedColumn = null;
			angular.forEach(cols, function(column, key) {
				for (var stoneIndex = 0; stoneIndex < column.stones.length; stoneIndex++) {
					var stone = column.stones[stoneIndex];
					if ( stone == sourceStone ) {
						selectedColumn = column;
						index = stoneIndex;
					}
				}
			});

			for (var i = index -1; i >= 0; i--) {
				var stone = selectedColumn.stones[i];
				if ( stone.name == "E5" ) break;
				if ( stone.color != null ) {
					if ((mayDefend && stone.color == sourceStone.color) || (stone.height <= sourceStone.height && stone.color != sourceStone.color)) {
						//console.log("valid target stone: " + stone.name );
						validTargetStones.push(stone);
					}
					break;
				}
			}
			for (var i = index +1; i < selectedColumn.size; i++) {
				var stone = selectedColumn.stones[i];
				if ( stone.name == "E5" ) break;
				if ( stone.color != null ) {
					if ((mayDefend && stone.color == sourceStone.color) || (stone.height <= sourceStone.height && stone.color != sourceStone.color)) {
						//console.log("valid target stone: " + stone.name );
						validTargetStones.push(stone);
					}
					break;
				}
			}
		});
		return validTargetStones;
	}

	gameLibrary.increaseMoveAndUpdate = function(game) {
		//update next move
        if ( game.currentmoveIndex < (game.moves.length/4)) {
			game.currentmoveIndex++;
			game.currentmove = game.moveStrings[game.currentmoveIndex];
			var startTime = (new Date()).getTime();
			game.isAnimating = true;
			gameLibrary.drawGame(game);
			gameLibrary.animate(game, startTime);
		}
	};

	window.requestAnimFrame = (function(callback) {
        return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame ||
        function(callback) {
          window.setTimeout(callback, 1000 / 60);
        };
      })();

	gameLibrary.animate = function(game, startTime) {
		
		var cxt = document.getElementById('cmove').getContext('2d');
		cxt.clearRect(0,0,cxt.canvas.width,cxt.canvas.height);
        var time = (new Date()).getTime() - startTime;
		var period = 1000;
		if (game.currentmove == null || game.currentmove.moveString == "PASS") {
			console.log("done animating, currentmoveIndex:" + game.currentmoveIndex);
			/*game.isAnimating = false;
			console.log("Calling processMoves from animate function start");
			gameLibrary.processMoves(game.currentmoveIndex);
			gameLibrary.drawGame(game);
			return;*/
		} else {

	        // update

			var startX = game.currentmove.startstone.Xcoord;
			var startY = game.currentmove.startstone.Ycoord;
			var endX = game.currentmove.endstone.Xcoord;
			var endY = game.currentmove.endstone.Ycoord;
			//console.log("animate:  Xcoord: " + game.currentmove.startstone.Xcoord );
			//console.log("endX: " + endX + " startX: " + startX + " time: " + time + " time/period " + time/period);
			var currentX = startX - ((startX - endX) * (time/period)); 
			var currentY = startY - ((startY - endY) * (time/period));

			//game.drawGame();
			
			//console.log("animate, currentmove=" + gameLibrary.currentmove.index + "X:" + currentX + " Y:" + currentY + " color:" + game.currentmove.startstone.color);
			gameLibrary.drawStone(game, currentX, currentY, game.currentmove.startstone.color, game.currentmove.startstone.type, game.currentmove.startstone.height, cxt);
		}
	        // request new frame
		if ( time < period ) {
			//console.log("do another animate");
			requestAnimFrame(function() {
			  gameLibrary.animate(game, startTime);
			});
		} else {
			console.log("done animating, currentmoveIndex:" + game.currentmoveIndex);
			cxt.clearRect(0,0,cxt.canvas.width,cxt.canvas.height);
			game.isAnimating = false;
			console.log("Calling processMoves from animate function end");
			gameLibrary.processMoves(game, game.currentmoveIndex);
			gameLibrary.drawGame(game);
			game.animationDone();
			if(game.isPlaying) {
				gameLibrary.increaseMoveAndUpdate(game);
			}
		}
	};

	return gameLibrary;
});

meanControllers.controller('PlayvCPUCtrl', ['$scope', '$http', '$location', 'GameLibrary', function ($scope, $http, $location, GameLibrary) {
	$scope.getCPUMove = function(columns, cpu, level) {
		$http.post('/api/game/botmove/', {cols: columns, CPUcolor: cpu, AILevel: level })
			.success(function(data){
				console.log("Cpu move(s):" + data);
				$scope.game.moves = $scope.game.moves + data;
				GameLibrary.loadMoves($scope.game);				
				GameLibrary.processMoves($scope.game, $scope.game.currentmoveIndex);
				GameLibrary.increaseMoveAndUpdate($scope.game);
			})
			.error(function(data) {
				console.log(data);
			});
	}
	$scope.game = {};
	GameLibrary.initGame($scope.game);
	$scope.game.startstate = "oOOOOozZZZooztTTzooztoOtzoOZTO otzoOZToOTZOOZttTZOOzzzZOooooO";	
	$scope.game.moveStrings = [{ index: 0, moveString: "PASS", move_img: ""}];
	$scope.game.moves = "";
	GameLibrary.loadGame($scope.game);
	GameLibrary.drawGame($scope.game);
	GameLibrary.updateStoneCount($scope.game);
	GameLibrary.updateActiveColor($scope.game);

	$scope.selectCPUColor = function(color) {
		console.log("selectCPUColor");
		$scope.CPUcolor = color;
	}

	$scope.selectCPU = function() {
		console.log("selectCPU");
		var CPUname;
		switch ($scope.AILevel) {
			case "1" : CPUname = "AIALPHABETA"; break;
			case "2" : CPUname = "AIALPHABETA_ID"; break;
			case "3" : CPUname = "AIALPHABETA_ID_PV"; break;
			case "4" : CPUname = "AIALPHABETA_ID_PV_MO"; break;
			case "5" : CPUname = "AIALPHABETA_ID_MO"; break;
			case "6" : CPUname = "AIALPHABETA_RANDOM"; break;
			case "7" : CPUname = "AIALPHABETA_ID_PV_MO_SCOUT"; break;
			case "8" : CPUname = "AIALPHABETA_ID_PV_MO_HISTORY"; break;
			case "9" : CPUname = "AIALPHABETA_MAX"; break;
			case "20" : CPUname = "DFPNS"; break;
			case "21" : CPUname = "DFPNS_EPS_TRICK"; break;
			case "22" : CPUname = "WEAK_PNS"; break;
			case "23" : CPUname = "DFPNS_EVAL_BASED"; break;
			case "24" : CPUname = "DFPNS_WEAK_EPS_EVAL"; break;
			case "25" : CPUname = "DFPNS_DYNAMIC_WIDENING_EPS_EVAL"; break;
			case "40" : CPUname = "BEGINNER"; break;
			case "41" : CPUname = "INTERMEDIATE"; break;
			case "42" : CPUname = "GODLIKE"; break;
		}
		if ( $scope.CPUcolor == 1) {
			$scope.game.bot1 = CPUname;
			$scope.game.bot2 = "Player";
			$scope.getCPUMove($scope.game.cols, $scope.CPUcolor, $scope.AILevel);
		} else {
			$scope.game.bot1 = "Player";
			$scope.game.bot2 = CPUname;
		}
		$scope.CPUSelected = true;
	}


	$scope.clickGame = function(event) {
		console.log("clickgame");
		GameLibrary.clickGame($scope.game, event);
	};

	$scope.pauseClick = function() {
		console.log("pauseClick");
		GameLibrary.pauseClick($scope.game);	
	}
	$scope.game.animationDone = function() {
		console.log("animationDone, currentmoveIndex:" + $scope.game.currentmoveIndex);
		var mayPause = (($scope.game.currentmoveIndex+1) % 2 == 1) && ($scope.game.currentmoveIndex != 0);
		if ( mayPause == true ) {
			$scope.game.passStatus = "Pass";
		} else {
			$scope.game.passStatus = "First_move";
		}
			$scope.$apply(function() {		});
		if ( $scope.game.isCpuMove() == 1) {
			$scope.getCPUMove($scope.game.cols, $scope.CPUcolor, $scope.AILevel);
		} else if ( $scope.game.isCpuMove() == 2) {
			GameLibrary.increaseMoveAndUpdate($scope.game);
		}
	};

	$scope.game.isCpuMove = function() {
		var cpuMove = 0;
		console.log("currentmoveIndex: " + $scope.game.currentmoveIndex + ", ($scope.game.currentmoveIndex+1) % 4)=" + (($scope.game.currentmoveIndex+1) % 4));
		if ( $scope.CPUcolor == 1 ) { //white
			if ( (($scope.game.currentmoveIndex+1) % 4) == 0) { // It is CPU's first turn
				cpuMove = 1;
			} else if ( (($scope.game.currentmoveIndex+1) % 4) == 1) { // It is CPU's second turn
				cpuMove = 2;
			}
		} else { // black
			if ( (($scope.game.currentmoveIndex+1) % 4) == 2) { // It is CPU's first turn
				cpuMove = 1;
			} else if ( (($scope.game.currentmoveIndex+1) % 4) == 3) { // It is CPU's second turn
				cpuMove = 2;
			}
		} 
		return cpuMove;
	};	

    $scope.$watch('game.printlabels', function(value) {
      console.log(value);
      GameLibrary.drawGame($scope.game);
    });
}]);

//CONTROLLER FOR mod_list.html
meanControllers.controller('ListCtrl', ['$scope', '$http', '$location', '$routeParams', function ($scope, $http, $location, $routeParams) {

	$scope.user = '';
	$http.get('/user').success(function(user){
		$scope.mineChecked = true;
		$scope.user = user;
		$scope.oldBots = [];
		$scope.index = $routeParams.index;
		$scope.filter();
	});

	$scope.olderMatches = function() {
		$scope.index = Number($scope.index) + 20;
		$scope.filter();
	}

	$scope.newerMatches = function() {
		$scope.index = Math.max($scope.index - 20, 0);
		$scope.filter();
	}

	$scope.filter = function() {
		var url = '/api/match/retrievelatest/' + $scope.index;
		if ($scope.mineChecked) {
			url += '/' + $scope.user.user;
		}
		$scope.matches = [];
		$http.get(url)
			.success(function(data){
				$scope.matches = data;
			})
			.error(function(data) {
				console.log(data);
			});
	}

	$scope.toggleOwnMatches = function() {
		$scope.mineChecked = ($scope.mineChecked) ? false : true;
		$scope.filter();
	}
}]);

//CONTROLLER FOR mod_play.html
meanControllers.controller('PlayCtrl', ['$scope', '$http', '$location', 'GameLibrary', function ($scope, $http, $location, GameLibrary) {
    console.log("PlayCtrl");
	$scope.game = {startstate: "oOOOOozZZZooztTTzooztoOtzoOZTO otzoOZToOTZOOZttTZOOzzzZOooooO"};
	$scope.game.moveStrings = [{ index: 0, moveString: "PASS", move_img: ""}];
	$scope.game.moves = "";
	$scope.game.bot1 = "White";
	$scope.game.bot2 = "Black";
	$scope.game.name = "My Game";
	GameLibrary.initGame($scope.game);
    GameLibrary.loadGame($scope.game);
    GameLibrary.drawGame($scope.game);
	GameLibrary.updateStoneCount($scope.game);
	GameLibrary.updateActiveColor($scope.game);


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


	$scope.clickGame = function(event) {
		console.log("clickgame");
		GameLibrary.clickGame($scope.game, event);
	};

	$scope.pauseClick = function() {
		console.log("pauseClick");
		GameLibrary.pauseClick($scope.game);	
	}

	$scope.game.animationDone = function() {
		console.log("animationDone, currentmoveIndex:" + $scope.game.currentmoveIndex);
		var mayPause = (($scope.game.currentmoveIndex+1) % 2 == 1) && ($scope.game.currentmoveIndex != 0);
		if ( mayPause == true ) {
			$scope.game.passStatus = "Pass";
		} else {
			$scope.game.passStatus = "First_move";
		}
		$scope.$apply(function() {		});	
	};

    $scope.$watch('game.printlabels', function(value) {
      console.log(value);
      GameLibrary.drawGame($scope.game);
    });
}]);
 
//CONTROLLER FOR mod_detail.html
meanControllers.controller('DetailCtrl', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
	$http.get('/api/match/retrieveid/'+$routeParams.gameId).success(function(data){
		$scope.game = data;		
		$scope.game.startstate = "oOOOOozZZZooztTTzooztoOtzoOZTO otzoOZToOTZOOZttTZOOzzzZOooooO";
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

//CONTROLLER FOR mod_home.html
meanControllers.controller('HomeCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {
}]);

//CONTROLLER FOR mod_rules.html
meanControllers.controller('RulesCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {
}]);

//CONTROLLER FOR mod_leaderboard.html
meanControllers.controller('LeaderboardCtrl', ['$scope', '$http', '$location', '$interval', function($scope, $http, $location, $interval) {

	$scope.getAll = function() {
		$http.get('/api/bot/getactivebots/')
			.success(function(bots){
				$scope.bots = bots;
			})
			.error(function(data) {
				console.log(data);
			});
	}
	$scope.bots = [];
	$scope.getAll();
    var intervalPromise = $interval($scope.getAll, 5000);
	$scope.$on('$destroy', function () { $interval.cancel(intervalPromise); });
}]);

//CONTROLLER FOR mod_login.html
meanControllers.controller('LogInCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {
	$scope.user = '';
	$http.get('/user').success(function(user){
		$scope.user = user;
	});
}]);

//CONTROLLER FOR mod_upload.html
meanControllers.controller('UploadCtrl', ['$scope', '$http', function($scope, $http) {
	$scope.startUploading = function() {
		console.log('uploading....');
		$scope.hasResult = false;
		$scope.uploadResponse2 = "[Status: Uploading] ";
	};

	$scope.uploadFile = function (content, completed) {
		console.log('uploading');
		$scope.result = content.result;
		$scope.stderr = content.stderr;
		$scope.stdout = content.stdout;
	};

	$scope.getOldBots = function() {
		$http.get('/api/bot/getoldbots/').success(function(oldBots){
			$scope.oldBots = oldBots;
		})
		.error(function(data) {
			console.log(data);
		});
	}

	$scope.oldBots = [];
	$scope.getOldBots();
}]);

//CONTROLLER FOR mod_createuser.html
meanControllers.controller('CreateUserCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {
	$scope.createUser = function() {
		console.log('Creating user');
		$http.post('/createuser', $scope.credentials).success(function(response) {
			$scope.error = null;
			$scope.success = response.message;
		}).error(function(response) {
			$scope.success = null;
			$scope.error = response.message;
		});
	};
}]);

//CONTROLLER FOR mod_statisticshtml
meanControllers.controller('StatisticsCtrl', ['$scope', '$http', function($scope, $http) {
    $scope.chartConfig = {
        options: {
            chart: {
                type: 'line',
                zoomType: 'x'
            }
        },
        title: {
            text: 'Bot ranking over time'
        },
        subtitle: {
        	text: 'Select area to zoom, enable/disable users below'
        },
        xAxis: {
            type: 'datetime',
            dateTimeLabelFormats: {
                month: '%e. %b',
                year: '%b'
            },
            title: {
                text: 'Date'
            }
        },
        yAxis: {
            title: {
                text: 'MMR'
            },
            min: 0,
            max: 2000
            min: 0
        },
        series: [],
        loading: false
    }

	$http.get('/api/statistics/rank-history').success(function(rows){
		var curName = rows[0].name;
		var curSerie = {
			name: curName,
			data: []
		};
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			if (curName != row.name) {
				curName = row.name;
				$scope.chartConfig.series.push(curSerie);
				curSerie = {
					name: curName,
					data: []
				};
			}
			curSerie.data.push([
				Date.UTC(row.year, row.month - 1, row.day, row.hour),
				row.ranking
			]);
		}
		$scope.chartConfig.series.push(curSerie);

		// Determine top three users for visibility
		$http.get('/api/statistics/top-three-users').success(function(rows){
			for (var i = 0; i < $scope.chartConfig.series.length; i++) {
				var serie = $scope.chartConfig.series[i];
				if (rows.indexOf(serie.name) === -1) {
					$scope.chartConfig.series[i].visible = false;
				}
			}
		})
	});

}]);

