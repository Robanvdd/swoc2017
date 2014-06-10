function MeanDemo($scope, $http) {
	
	$scope.users = [];
	
	$scope.loadData = function() {
		$http({method: 'GET', url: '/api/user/retrieveall/'}).
			success(function(data, status, headers, config) {
				console.table(data);
				$scope.users = data;
			}).
			error(function(data, status, headers, config) {
				console.log(data);
			});
	};
	
 	
}