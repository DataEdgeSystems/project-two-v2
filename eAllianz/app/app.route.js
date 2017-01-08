/**
 * Loading all the routes here
 */
CollaborationApp.config(['$routeProvider', '$locationProvider', '$httpProvider',function($routeProvider,$locationProvider,$httpProvider){

    // allows the cookie with session id to be send back
    $httpProvider.defaults.withCredentials = true;

    $routeProvider
    .when('/home',{
        templateUrl: 'app/components/page/home.html',
        controller: 'navigation'
    })
    .when('/login',{
        templateUrl: 'app/components/authentication/login.html',
        controller: 'navigation'
    })
    .otherwise('/');

}]);