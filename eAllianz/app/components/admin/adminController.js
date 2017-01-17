AdminModule.controller('AdminController',['AdminService','$rootScope','$timeout', function(AdminService,$rootScope,$timeout) {
    

    // call the jQuery dynamically        
    $timeout(function(){
        load();
    },100);
    
    
}]);

