

AuthenticationModule.controller('AuthenticationController',['AuthenticationService','$scope','$rootScope','$location','$timeout',function(AuthenticationService,$scope,$rootScope,$location,$timeout){
    
    // self reference using the variable as me
    var me = this;

    // Credentials required inside the login.html
    me.credentials = {};
    // to display the error to the user related to login
    me.error = false;

    // object that will be used in registration
    me.user = {            
      email: 'abc@xyz.com',
      birthDate: new Date().toISOString().slice(0,10)      
    };
    // flag to display the error if username already exists
    me.usernameExist = false;


    // once the controller loads call the jQuery
    $timeout(function(){
        load();
    },100);


    // login function
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


    // check if the username already exists
    me.checkUsername = function() {
      if(me.user.username !== undefined && me.user.username.length > 0) {
        AuthenticationService.checkUsername(me.user.username)
        .then(
          function(response){
            console.log(response);
            if(response.statusText === 'Found') {
              me.usernameExist = true;
              // set the validity as false if the username alrady exists
              $scope.register.username.$setValidity("username", false)
            }
            else {
              // if the username does not alrady exists
              me.usernameExist = false;
              $scope.register.username.$setValidity("username", true)
            }
          },
          function(error){
            me.usernameExist = false;
            $scope.register.username.$setValidity("username", false)
          }
        );
      }
      
    }

    // register the user here
    me.register = function() {
      AuthenticationService.register(me.user)
      .then(
        function(status) {
          console.log(status);
          if(status == 200) {
            $rootScope.message = "You will receive an email once super admin approves your details!";
            $location.path("/login");
          }
        }
      )
    }

}]);
