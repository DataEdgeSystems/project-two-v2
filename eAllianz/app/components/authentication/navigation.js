var AuthenticationModule = angular.module('AuthenticationModule',[]);
AuthenticationModule.controller('navigation',['$scope','$rootScope','$http','$location',function($scope,$rootScope,$http,$location){


    var REST_URI = 'http://localhost:8080/collaboration-backend/'

    var authenticate = function(callback) {

    $http.get(REST_URI+'user').then(function(response) {           
      debugger; 
      if (response.data.name) {
        $rootScope.authenticated = true;
      } else {
        $rootScope.authenticated = false;
      }
      callback && callback();
    }).catch(function() {
      $rootScope.authenticated = false;
      callback && callback();
    });
  }
  authenticate();
  $scope.credentials = {};
  $scope.login = function() {
      
    $http.post(REST_URI+'login', $.param($scope.credentials), {        
      headers : {
        "content-type" : "application/x-www-form-urlencoded"
      }
    }).then(function(data) {


      authenticate(function() {        
        if ($rootScope.authenticated) {
          $location.path("/");
          $scope.error = false;
        } else {
          $location.path("/login");
          $scope.error = true;
        }
      });
    }).catch(function(data) {      
      $location.path("/login");
      $scope.error = true;
      $rootScope.authenticated = false;
    })
  };

}]);