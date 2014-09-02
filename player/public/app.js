var meanApp = angular.module('meanApp', ['ngRoute', 'ngUpload', 'ngAnimate', 'meanControllers']);

meanApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider
		.when('/', {
			controller:'HomeCtrl',
			templateUrl:'mod_home.html'
		})
		.when('/game_log/', {
			controller:'ListCtrl',
			templateUrl:'mod_list.html'
		})
		.when('/upload/', {
			controller:'UploadCtrl',
			templateUrl:'mod_upload.html'
		})
		.when('/indextest.html/', {
			controller:'ListCtrl',
			templateUrl:'indextest.html'
		})
		.when('/detail/:gameId', {
			controller:'DetailCtrl',
			templateUrl:'mod_detail.html'
		})
		.when('/create/', {
			controller:'InsertCtrl',
			templateUrl:'mod_insert.html'
		})
		.when('/delete/:gameId', {
			controller:'DeleteCtrl',
			templateUrl:'mod_delete.html'
		})
		.when('/play/', {
			controller:'PlayCtrl',
			templateUrl:'mod_play.html'
		})
		.when('/playvcpu/', {
			controller:'PlayvCPUCtrl',
			templateUrl:'mod_play_v_cpu.html'
		})
		.otherwise({
			redirectTo: '/'
		});
		
}]);