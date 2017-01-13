// list the routes here for user to navigate through the website.
window.routes =
{
    "/home": {
        templateUrl: 'app/components/user/home.html', 
        controller: 'UserController', 
        controllerAs: 'userCtrl',
        requireLogin: true,
        role: 'USER'
    },

    "/login": {
        templateUrl: 'app/components/authentication/login.html', 
        controller: 'AuthenticationController', 
        controllerAs: 'authCtrl',
        requireLogin: false,
        role: 'GUEST'
    },
    "/register": {
        templateUrl: 'app/components/authentication/register.html', 
        controller: 'AuthenticationController', 
        controllerAs: 'authCtrl',
        requireLogin: false,
        role: 'GUEST'
    },
    "/error/403": {
        templateUrl: 'app/components/authentication/unauthorized.html', 
        controller: 'AuthenticationController', 
        controllerAs: 'authCtrl',
        requireLogin: false,
        role: 'GUEST'
    }    

};

/**
 * Loading all the routes here
 */
CollaborationApp.config(['$routeProvider', '$locationProvider', '$httpProvider',function($routeProvider,$locationProvider,$httpProvider){

    // allows the cookie with session id to be send back
    $httpProvider.defaults.withCredentials = true;

    // fill up the path in the $routeProvider the objects created before
    for(var path in window.routes) {
        $routeProvider.when(path,window.routes[path]);
    }

    $routeProvider.otherwise({redirectTo: '/login'});

}]);

// The REST endpoint to get the data from the server
CollaborationApp.constant('REST_URI', 'http://localhost:8080/collaboration-backend/');

// When the app runs check whether the user navigating through the website is
// authenticated and authorized to view the exisiting page
CollaborationApp.run(function($rootScope,AuthenticationService) {
    $rootScope.$on('$locationChangeStart', function(event, next, current) {
        // iterate through all the routes
        for(var i in window.routes) {
            // if routes is present make sure 
            // the user is authenticated before login in
            // using the session service
            if(next.indexOf(i)!=-1) {
                if(window.routes[i].requireLogin && !AuthenticationService.getUserIsAuthenticated()) {
                    $location.path("/login");
                    event.preventDefault();
                }
                else if(AuthenticationService.getUserIsAuthenticated() && AuthenticationService.getRole() !== window.routes[i].role ) {
                    $location.path("/error/403");
                    event.preventDefault(); 
                }
            }
        }
    });
});
 


