'use strict';


var LiveStatusController = function($scope, $http) {
    $scope.fetchLiveStatusList = function() {
        $http.get('liveStatus/liveStatusList.json').success(function(liveStatuslist){
        	$scope.liveServices = [];
            $scope.liveServices = liveStatuslist;
        });
    };

    $scope.fetchLiveStatusList();
};