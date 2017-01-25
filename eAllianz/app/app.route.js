// list the routes here for user to navigate through the website.
window.routes =
{
    "/home": {
        templateUrl: 'app/components/page/home.html', 
        controller: 'AuthenticationController', 
        controllerAs: 'authCtrl',
        requireLogin: true,
        roles: ['USER','ADMIN']
    },
    "/admin/user/activate": {
        templateUrl: 'app/components/admin/useractivation.html', 
        controller: 'AdminController', 
        controllerAs: 'adminCtrl',
        requireLogin: true,
        roles: ['ADMIN']
    },
    "/login": {
        templateUrl: 'app/components/authentication/login.html', 
        controller: 'AuthenticationController', 
        controllerAs: 'authCtrl',
        requireLogin: false,
        roles: ['GUEST']
    },
    "/register": {
        templateUrl: 'app/components/authentication/register.html', 
        controller: 'AuthenticationController', 
        controllerAs: 'authCtrl',
        requireLogin: false,
        roles: ['GUEST']
    },
    "/error": {
        templateUrl: 'app/components/authentication/error.html', 
        controller: 'AuthenticationController', 
        controllerAs: 'authCtrl',
        requireLogin: false,
        roles: ['GUEST']
    },
    "/blogs": {
        templateUrl: 'app/components/blog/listblog.html', 
        controller: 'BlogController', 
        controllerAs: 'blogCtrl',
        requireLogin: true,
        roles: ['USER','ADMIN']
    },
    "/forums": {
        templateUrl: 'app/components/forums/listforum.html', 
        controller: 'ForumController', 
        controllerAs: 'forumCtrl',
        requireLogin: true,
        roles: ['USER','ADMIN']
    },
    
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

    $routeProvider.otherwise({redirectTo: '/home'});

}]);

// The REST endpoint to get the data from the server
CollaborationApp.constant('REST_URI', 'http://localhost:8080/collaboration-backend/');

// When the app runs check whether the user navigating through the website is
// authenticated and authorized to view the exisiting page
CollaborationApp.run(function($rootScope,$location,AuthenticationService) {


    $rootScope.$on('$locationChangeStart', function(event, next, current) {    
        // iterate through all the routes
        for(var i in window.routes) {
            // if routes is present make sure the user is authenticated 
            // before login using the authentication service            
            if(next.indexOf(i)!=-1) {                
                // if trying to access page which requires login and is not logged in                                                 
                $rootScope.user = AuthenticationService.loadUserFromCookie();
                $rootScope.authenticated = AuthenticationService.getUserIsAuthenticated();
                
                if(window.routes[i].requireLogin && !AuthenticationService.getUserIsAuthenticated()) {                                   
                    $location.path('/login');
                }
                else if((AuthenticationService.getUserIsAuthenticated()) 
                        &&
                        (window.routes[i].roles.indexOf(AuthenticationService.getRole())==-1)) {
                        $location.path('/error');
                }                
            }
        }        
    });


    $rootScope.logout = function() {
        // call the logout  function created in AuthenticationService
        AuthenticationService.logout()
        .then(
            // function callback
            function(message) {
                $rootScope.message = message;
                $location.path('/login');
            }
        );

    };

});
 


