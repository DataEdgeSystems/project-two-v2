

AuthenticationModule.controller('AuthenticationController',['AuthenticationService','$rootScope','$location',function(AuthenticationService,$rootScope,$location){
    
    var me = this;

    // Credentials required inside the login.html
    me.credentials = {};
    me.error = false;

    me.login = function(){
      AuthenticationService.login(me.credentials)
        .then(
          function(user){
            AuthenticationService.setUserIsAuthenticated(true);
            $rootScope.authenticated = true;
            $rootScope.message = 'Welcome ' + user.firstName + ' ' + user.lastName;            
            $location.path("/home");
          },
          function(error) {
            console.log(error);
            AuthenticationService.setUserIsAuthenticated(false);
            $rootScope.authenticated = false;
            me.error = true;
          }
        )
    }
  
}]);