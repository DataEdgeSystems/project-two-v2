

AuthenticationModule.controller('AuthenticationController',['AuthenticationService','$scope','$rootScope','$location','$timeout',function(AuthenticationService,$scope,$rootScope,$location,$timeout){
    
    var me = this;

    // Credentials required inside the login.html
    me.credentials = {};
    me.error = false;
    me.user = {
      birthDate: new Date().toISOString().slice(0,10)      
    };
    me.loginExist = false;


    // once the controller loads call the jQuery
    $timeout(function(){
        load();
    },100);


    me.login = function(){
      AuthenticationService.login(me.credentials)
        .then(
          function(user){                  
            AuthenticationService.saveUser(user);
            switch(user.role) {
              case 'ADMIN':
              case 'USER':
                $location.path('/home');
                break;
              default:
                $location.path('/error');
            }
          },
          function(error) {            
            AuthenticationService.setUserIsAuthenticated(false);
            $rootScope.authenticated = false;
            if($rootScope.message)  $rootScope.message = false;
            me.error = true;
          }
        )
    };


    me.checkLogin = function() {
      if(me.user.login !== undefined && me.user.login.length > 0) {
        AuthenticationService.checkLogin(me.user.login)
        .then(
          function(response){
            console.log(response);
            if(response.statusText === 'Found') {
              me.loginExist = true;
              $scope.register.login.$setValidity("login", false)
            }
            else {
              me.loginExist = false;
              $scope.register.login.$setValidity("login", true)
            }
          },
          function(error){
            me.loginExist = false;
          }
        );
      }
      
    }

    me.register = function() {
      console.log(me.user);
    }

}]);
