AdminModule.controller('AdminController',['AdminService','$rootScope','$timeout', function(AdminService,$rootScope,$timeout) {

    var me = this;

    me.users = [];

    // get all the users for activation
    me.getUsersForActivation = function() {
        AdminService.getUsersForActivation()
        .then(
            function(users) {
                console.log(users);
                me.users = users;
            },
            function(error) {
                console.log(error);
            }
        );
    }

    // call the method to get the users for activation
    me.getUsersForActivation();

    me.approveUser = function(userId) {
        //console.log(userId);
        AdminService.approveUser(userId)
        .then(
            function(response) {                
                $rootScope.message = response.message;
            },
            function(error) {
                $rootScope.message = error.message;
            }
        );
    }    

}]);

