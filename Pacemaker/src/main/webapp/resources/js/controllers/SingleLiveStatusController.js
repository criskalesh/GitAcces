'use strict';


var SingleLiveStatusController = function($scope, $http, $routeParams) {
	var url = 'liveStatus/member.json/' + $routeParams.serviceName;
    $scope.fetchLiveStatus = function() {
    	alert("SingleLiveStatusController");
        $http.get(url).success(function(member){
            $scope.member = member;
        });
    };

    $scope.fetchLiveStatus();
};