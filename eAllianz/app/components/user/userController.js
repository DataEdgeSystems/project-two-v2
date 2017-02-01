
UserModule.controller('UserController', ['UserService','AuthenticationService','$rootScope', '$scope' , '$timeout', function (UserService, AuthenticationService, $rootScope, $scope ,$timeout) {

    var me = this;
    // load the user stored inside the cookie
    me.user = AuthenticationService.loadUserFromCookie();    
    var date = new Date(me.user.birthDate.year, me.user.birthDate.monthValue - 1, me.user.birthDate.dayOfMonth + 1).toISOString().slice(0, 10);
    me.user.birthDate = date;    
    me.picture = undefined;

    // the decached technique is used to see the updated image immediately with out page refresh
    me.user.pictureId = me.user.pictureId + '?decached=' + Math.random(); 

    // once the controller loads call the jQuery
    $timeout(function () {
        load();
    }, 100);

    // to upload the file    
    me.uploadFile = function () {
        
        if(me.picture == undefined) {
            return;
        }

        UserService.uploadFile(me.picture)
        .then(
            function(response){
                $rootScope.message = 'Profile picture updated successfully!';
                //message contains the pictureId updated in the backend too
                me.user.pictureId = response.message + '?decached=' + Math.random();
                // update the controller user too
                $rootScope.user.pictureId = response.message + '?decached=' + Math.random();
                // need to update the cookie value too
                AuthenticationService.saveUser($rootScope.user);

                // hide the card panel by setting the rootScope.message as undefined
                $timeout(function() {                    
                    $rootScope.message = undefined;
                },2000);

            },
            function(error){
                console.log(error);
            } 
        )
    };
}]);

