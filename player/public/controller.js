var meanControllers = angular.module('meanControllers', ['ngAnimate', 'highcharts-ng']);

//CONTROLLER FOR mod_list.html
meanControllers.controller('ListCtrl', ['$scope', '$http', '$location', '$routeParams', function ($scope, $http, $location, $routeParams) {

	$scope.user = '';
	$scope.selectedUser = {username: 'All users'};
	$scope.activeUsers = [$scope.selectedUser];
	$scope.index = $routeParams.index;

	$scope.filter = function() {
		var url = '/api/match/retrievelatest/' + $scope.index;
		if ($scope.selectedUser && $scope.selectedUser.username != 'All users') {
			url += '/' + $scope.selectedUser.username;
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
	
	$scope.filter();

	$http.get('/user').success(function(user){
		$scope.user = user;
		// $scope.oldBots = [];
	});

	$http.get('/users/active').success(function(users){
		//Start with the current user
		for (var i = 0; i < users.length; i++) {
			if (users.username === $scope.user) {
				$scope.activeUsers.push(users[i]);
				break;
			}
		}

		//Append other users
		for (var i = 0; i < users.length; i++) {
			if (users.username !== $scope.user) {
				$scope.activeUsers.push(users[i]);
			}
		}
	});

	$scope.olderMatches = function() {
		$scope.index = Number($scope.index) + 20;
		$scope.filter();
	}

	$scope.newerMatches = function() {
		$scope.index = Math.max($scope.index - 20, 0);
		$scope.filter();
	}
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


