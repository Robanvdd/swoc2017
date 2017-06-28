var meanApp = angular.module('meanApp', ['ngRoute', 'ngUpload', 'ngAnimate', 'meanControllers']);

meanApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider
		.when('/', {
			controller:'HomeCtrl',
			templateUrl:'mod_home.html'
		})
		.when('/game_log/:index', {
			controller:'ListCtrl',
			templateUrl:'mod_list.html'
		})
		.when('/upload/', {
			controller:'UploadCtrl',
			templateUrl:'mod_upload.html'
		})
		.when('/login/', {
			controller:'LogInCtrl',
			templateUrl:'mod_login.html'
		})
		.when('/rules/', {
			controller:'RulesCtrl',
			templateUrl:'mod_rules.html'
		})
		.when('/leaderboard/', {
			controller:'LeaderboardCtrl',
			templateUrl:'mod_leaderboard.html'
		})
		.when('/createuser/', {
			controller:'CreateUserCtrl',
			templateUrl:'mod_createuser.html'
		})
		.otherwise({
			redirectTo: '/'
		});
		
}]);