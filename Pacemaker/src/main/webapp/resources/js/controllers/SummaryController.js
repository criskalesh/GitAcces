'use strict';


var SummaryController = function($scope, $http,$location) {
    $scope.fetchSummaryList = function() {
    	alert('hlee');
        $http.get('summary/summarylist.json').success(function(summarylist){
        	$scope.services = [];
            $scope.services = summarylist;
        });
    };
    
    /*$scope.fetchLiveStatus = function() {
		$http.get(url).success(function(member){
			$scope.member = member;
		});
	};*/
  /*  $scope.fetchSummaryList();*/
	
};