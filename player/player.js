
var SWOCController = function($scope) {

    $scope.printlabels = false;     
	
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
	$scope.games = [ { name : "game1", bot1 : "JaWSnl1", bot2 : "Roban2", startState : "oOOOO" + "ozZZZo" + "oztTTzo" + "oztoOtzo" + "OZTO otzo" + "OZToOTZO" + "OZttTZO" + "OzzzZO" + "ooooO", moves : ["WA1A2", "BC4C3", "BC5D6", "WA2A3", "WPASS", "BC3D3", "BD6E7", "WA3A4", "WI1H1", "BF3G3", "BG5G4"] },
			 { name : "game2", bot1 : "Roban1", bot2 : "Ralph3", startState : "oO  O" + "o  ZZo" + "oztT  o" + "ozt  tzo" + "O    otzo" + "     TZO" + "O      " + "   zZO" + "oo  O"},
			 { name : "game3", bot1 : "Duncan2", bot2 : "JaWSnl2", startState : "oOOOO" + "ozZZZo" + "oztTTzo" + "oztoOtzo" + "OZTO otzo" + "OZToOTZO" + "OZttTZO" + "OzzzZO" + "ooooO"}];
	//initial values
	$scope.selectedgame = $scope.games[0];
        $scope.selectedmove = -1;
        $scope.activeColor = "White";
	$scope.selectedcol = $scope.cols[0];
	$scope.selectedstone = $scope.selectedcol.stones[0];



$scope.drawGame = function() {

	  
	var cxt = document.getElementById('c').getContext('2d');
	cxt.clearRect(0,0,cxt.canvas.width,cxt.canvas.height);
	//draw grey hexagon
	console.log("draw grey hexagon");
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
	cxt.strokeStyle = '#D0D0D0';
	cxt.lineWidth = 3;
	var radius = 20;
	angular.forEach($scope.cols, function(column, key) {
		angular.forEach(column.stones, function(stone, key) {
			var mainColor = null;
			if ( stone.color == "white" ) {
				//draw white stone
				mainColor = '#FFFFFF';
			} else if ( stone.color == "black" ) {
				//draw black stone
				mainColor = '#000000';
			} else {
				return;
			}
			cxt.fillStyle = mainColor;
			cxt.beginPath();
			cxt.arc(stone.Xcoord, stone.Ycoord, radius, 0, 2 * Math.PI, false);
			cxt.fill(); cxt.stroke();
			switch ( stone.type ) {
				case "TZAAR" :
					console.log("TZAAR stone type at " + stone.name );
					//draw outer circle
					cxt.fillStyle = '#D0D0D0';
					cxt.beginPath();
					cxt.arc(stone.Xcoord, stone.Ycoord, radius*0.7, 0, 2 * Math.PI, false);
					cxt.fill();
					cxt.fillStyle = mainColor;
					cxt.beginPath();
					cxt.arc(stone.Xcoord, stone.Ycoord, radius*0.5, 0, 2 * Math.PI, false);
					cxt.fill();
					//draw inner circle
					cxt.beginPath();
					cxt.fillStyle = '#D0D0D0';
					cxt.arc(stone.Xcoord, stone.Ycoord, radius*0.3, 0, 2 * Math.PI, false);
					cxt.fill();
				break;
				case "TZARRAS" :
					console.log("TZARRAS stone type at " + stone.name );
					//draw inner circle
					cxt.beginPath();
					cxt.fillStyle = '#D0D0D0';
					cxt.arc(stone.Xcoord, stone.Ycoord, radius*0.4, 0, 2 * Math.PI, false);
					cxt.fill();
				break;
				case "TOTTS" :
					//nothing needs to be done
				break;
				default:
					console.log("Unkown stone type at " + stone.name );
				break;
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

$scope.loadGame = function() {
	console.log("Selected game state:" + $scope.selectedgame.startState);
	if ($scope.selectedgame.startState.length == 61) {
		var stoneIndex = 0;
		angular.forEach($scope.cols, function(column, key) {
			angular.forEach(column.stones, function(stone, key) {
				if ( stone.name != "E5" ) {
					var stoneState = $scope.selectedgame.startState[stoneIndex];
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
		console.log("invalid game state length, should be 61 chars long: " + $scope.selectedgame.startState.length);
	}
};

$scope.increaseMoveAndUpdate = function() {


	//update next move
        if ( $scope.selectedmove < $scope.selectedgame.moves.length - 1) {
		$scope.selectedmove++;
	}

	$scope.processMoves();
	$scope.updateActiveColor();
};

$scope.decreaseMoveAndUpdate = function() {


	//update next move
        if ( $scope.selectedmove >= 0) {
		$scope.selectedmove--;
	}

	$scope.processMoves();
	$scope.updateActiveColor();
};

$scope.processMoves = function() {
	$scope.loadGame();
	for ( var moveIndex = 0; moveIndex <= $scope.selectedmove && moveIndex < $scope.selectedgame.moves.length; moveIndex++) {
		var move = $scope.selectedgame.moves[moveIndex];
		if (move[1] != "P" ) { //P for PASS
			//update board with move
			console.log("move: " + move);
			var colIndex = "A".charCodeAt(0) - move.charCodeAt(1);
			var startcol = $scope.cols[move.charCodeAt(1) - "A".charCodeAt(0) ];
			var startstone = startcol.stones[move.charCodeAt(2) - "1".charCodeAt(0)];
			console.log("found startstone: " + startstone.name + " from move: " + move);

			var endcol = $scope.cols[move.charCodeAt(3) - "A".charCodeAt(0)];
			var endstone = endcol.stones[move.charCodeAt(4) - "1".charCodeAt(0)];
			console.log("found endstone: " + endstone.name + " from move: " + move);

			endstone.color = startstone.color;
			endstone.type = startstone.type;
			//TODO height of stone
			startstone.color = null;
			startstone.type = null;
		}
	}
};

$scope.updateActiveColor = function() {

	if ( $scope.selectedmove+1 < $scope.selectedgame.moves.length ) {
		switch ( $scope.selectedgame.moves[$scope.selectedmove+1][0] ) {
			case "W" :
				$scope.activeColor = "White";
			break;
			case "B" :
				$scope.activeColor = "Black";
			break;
			default : 
				console.log( "Invalid active color:" + $scope.selectedgame.moves[$scope.selectedmove]);
				$scope.activeColor = "Invalid";
			break;
		}
	} else {
		$scope.activeColor = "Game Over";
	}
};

    $scope.loadGame();
    $scope.drawGame();

    $scope.$watch('printlabels', function(value) {
      console.log(value);
      $scope.drawGame();
    });
    $scope.$watch('cols', function(value) {
      console.log(value);
      $scope.drawGame();
    }, true);
    $scope.$watch('selectedgame', function(value) {
      $scope.loadGame();
      $scope.drawGame();
      $scope.selectedmove = -1;
      $scope.activeColor = "White";
    });


};
