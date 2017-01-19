

AuthenticationModule.controller('AuthenticationController',['AuthenticationService','$rootScope','$location','$cookies',function(AuthenticationService,$rootScope,$location,$cookies){
    
    var me = this;

    // Credentials required inside the login.html
    me.credentials = {};
    me.error = false;
    me.user = {};    

    me.login = function(){
      AuthenticationService.login(me.credentials)
        .then(
          function(user){                  
            AuthenticationService.setUserIsAuthenticated(true);            
            AuthenticationService.setRole(user.role);
            AuthenticationService.setUser(user);
            $cookies.put('user',user);
            $rootScope.authenticated = true;
            $rootScope.message = 'Welcome ' + user.firstName + ' ' + user.familyName;            
            console.log(user);
            switch(user.role) {
              case 'ADMIN':
                $location.path('/admin/home');
                break;
              case 'USER':
                $location.path('/user/home');
                break;
              default:
                $location.path('/error/403');
            }

          },
          function(error) {
            console.log(error);
            AuthenticationService.setUserIsAuthenticated(false);
            $rootScope.authenticated = false;
            me.error = true;
          }
        )
    };

    me.fillUser = function(user) {
      
    }
      
}]);
