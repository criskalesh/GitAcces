'use strict';
var PacemakerApp = angular.module('PacemakerApp', ['components','ngRoute','ui.bootstrap']);

PacemakerApp.config(['$routeProvider', function ($routeProvider) {	
	$routeProvider.when('/summary', {
		templateUrl: 'summary/layout'       
	}).when('/liveStatusSingle/:serviceName/:env', {
		templateUrl: 'liveStatus/singleLayout',
		controller: 'SingleLiveStatusController'
	}).when('/construction', {
		templateUrl: 'summary/progress'       
	}).when('/serviceSummary/:env', {
		templateUrl: 'summary/serviceSummary',
	}).when('/serviceTroubleShoot', {
		templateUrl: 'summary/serviceTroubleShoot',
		controller: 'ServicesSummaryController'
	}).when('/utility', {
		templateUrl: 'summary/utility'
	}).when('/dbinfo', {
		templateUrl: 'summary/dbinfo'
	}).when('/redirect/:menuItem', {
		templateUrl: 'summary/layout',
		controller:'MenuRedirectController'
	}).when('/updateSpecification', {
		templateUrl: 'summary/editSpec',
		controller:'ManageSpecControl'
	}).when('/createSpecification', {
		templateUrl: 'summary/addSpec',
		controller:'CreateSpecCtrl'
	}).otherwise({
		redirectTo: '/summary'
	});
}]);

PacemakerApp.factory('specService', function(){
    var specifications = [
        {id:1, title:'Spec 1', content:'My very first spec'},
        {id:2, title:'Spec 2', content:'The second spec that I created.'}
    ];//{ id: 1, title: '', content:'' }
    return {
    	getAllSpecs: function(){
            return specifications;
        },
        addSpecification: function(specTitle, specContent, $http, $scope){        	
            var specId = specifications.length + 1;
            specifications.push({id: specId,title:specTitle, content:specContent});
            console.log('New spec with title: '+specTitle+' added!');
            $http.post('SVC0001.json', $scope.specs).then(function(data) {
	              $scope.msg = 'Data saved';
	              console.log(msg);
	        });	        
        },
        getSpec:function(specId){
            if(specId<= specifications.length){
                return specifications[specId-1];
            }
        }
    }          
  });


PacemakerApp.controller('ManageSpecControl',["$scope", "$location","specService", function($scope, $location, specService){
	 var allSpecs = specService.getAllSpecs();
	 $scope.specs = allSpecs;
}]);

PacemakerApp.controller('CreateSpecCtrl',["$scope", "$location","$http", "specService", function($scope, $location, $http, specService){
	$scope.addSpecification = function(){
		alert($scope.selectedServiceSpecName.name);
	    var specId = specService.addSpecification($scope.specTitle, $scope.specContent, $http, $scope);  
	    };
}]);

PacemakerApp.controller('TestSelectControl', function($scope, $http, $location){
	$scope.selectAction = function() {	
		if(null != $scope.member){		
			console.log("--> Menu Change " + $scope.selectedOperationName.testDescription);
			$scope.initialMessageToTest = $scope.selectedOperationName.testDescription;			
		}	
	};	

	$scope.resetTestServiceForm = function() {
		$scope.resetError();
		$scope.ts = {};
	};

	$scope.resetError = function() {
		$scope.error = false;
		$scope.errorMessage = '';
	};

	$scope.setError = function(message) {
		$scope.error = true;
		$scope.errorMessage = message;
	};

	$scope.testSoapMethod = function(ts) {
		$scope.resetError();
		if(null != ts && null != ts.name && null != ts.testDescription){
			console.log("--> Test " + $scope.member.name + " for " + ts.name);
			console.log("--> Test Data" + ts.testDescription);
			if(ts.testEndPoint == ''){
				ts.testEndPoint = $scope.member.endpoint;
			}
			console.log("--> Submitting form");
			$http.post('testservice/soaptest', ts).success(function(ts) {
				console.log("--> Submitting success" + ts.testOutPut);
				$scope.ts = ts;
				$scope.selectedOperationName.testOutPut = ts.testOutPut;
			}).error(function() {
				$scope.setError('Error. Could not perform testing!');
			});	
		}else{
			alert('Select Operation: and Select Request: data are mandatory!');
			return;
		}	
	};	

	$scope.resetSoapMethod = function(ts) {
		console.log("--> Resetting" + ts.testDescription);
		if(null != $scope.initialMessageToTest){	
			console.log("--> With" + $scope.initialMessageToTest);
			ts.testDescription = $scope.initialMessageToTest;
			ts.testOutPut ='';
		}		
	};	
});

PacemakerApp.controller('TestRestSelectControl', function($scope, $http, $location){	
	$scope.testRestMethod = function(messageDetails) {	
		if(null != messageDetails && null != messageDetails.inputType && null != messageDetails.testEndPoint){
			console.log("--> Test " + $scope.member.name + " for " + messageDetails.testEndPoint);
			if(null != messageDetails.testDescription){
					console.log("--> Test Data" + messageDetails.testDescription);
			}
			console.log("--> Submitting form with messageDetails.inputType" + messageDetails.inputType  + " and messageDetails.testEndPoint " + messageDetails.testEndPoint);
			$http.post('testservice/resttest', messageDetails).success(function(messageDetails) {
				console.log("--> Submitting success" + messageDetails.testOutPut);
				$scope.messageDetails = messageDetails;
			}).error(function() {
				$scope.setError('Error. Could not perform testing!');
			});	
		}else{
			alert('Method: and URL: data are mandatory!');
			return;
		}		
	};	

	$scope.resetRestMethod = function(messageDetails) {
		console.log("--> Resetting" + $scope.operationRestInputURL);
		messageDetails.testEndPoint='';
		messageDetails.testDescription='';
		messageDetails.testOutPut='';
	};	

});

PacemakerApp.controller('SummaryController', function($scope, $http,$location) {
	$http.get('summary/summarylist.json').success(function(summarylist){
		$scope.services = [];
		$scope.liveservices = [];
		if(null != $scope.selectedSvcEnvironment){
			console.log("--> scope.selectedSvcEnvironment is not null");
		}else{
			$scope.selectedSvcEnvironment = "QA_ENV";
		}		
		$scope.services = summarylist;
	});

	$scope.$watch('selectedServiceName', function(selectedServiceName) {
		if (!selectedServiceName) {
			return;
		}
		$location.path('/liveStatusSingle/' + $scope.selectedServiceName.name + "/"+ $scope.selectedSvcEnvironment);
	});
	
	$scope.envClicked = function(selectedEnvironment) {
		console.log("--> Setting" + selectedEnvironment);
		$scope.selectedSvcEnvironment = selectedEnvironment;	
		
		if(selectedEnvironment == 'PROD_ENV'){
			$http.get('summary/summaryProdlist.json').success(function(summarylist){
				$scope.services = [];
				$scope.liveservices = [];					
				$scope.services = summarylist;
			}).error(function() {
				$scope.setError('Error. Could not get data!');
			});	
		}else{
			$http.get('summary/summarylist.json').success(function(summarylist){
				$scope.services = [];
				$scope.liveservices = [];					
				$scope.services = summarylist;
			}).error(function() {
				$scope.setError('Error. Could not get data!');
			});	
		}		
		$location.path('/summary');
	};
	
	$scope.helpNSupport = function() {
		alert('Please email to kbalakr@searshc.com or cjose0@searshc.com for any issues');
	}
});

PacemakerApp.controller('SingleLiveStatusController', function($scope, $http, $routeParams) {
	var url = 'liveStatus/member.json/' + $routeParams.serviceName + '/' + $routeParams.env;
	$http.get(url).success(function(member) {
		$scope.member = member;
	});
});

PacemakerApp.controller('MenuRedirectController',['$routeParams','$window',
                                                  function($routeParams,$window) {
	if($routeParams.menuItem == 'graphite'){
		$window.open('http://graphite.prod.ch4.s.com/');
	}else if($routeParams.menuItem == 'esbmon'){
		$window.open('http://esbmon.intra.sears.com');
	}
}]);

PacemakerApp.controller('ServicesSummaryController', function($scope, $http,$location, $routeParams) {
	$http.get('summary/summarylivelist.json/' + $routeParams.env).success(function(liveServicesList){
		$scope.liveservices = [];
		$scope.liveservices = liveServicesList;
	});  
});