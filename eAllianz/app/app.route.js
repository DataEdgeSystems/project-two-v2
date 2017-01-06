/**
 * Loading all the routes here
 */
CollaborationApp.config(['$routeProvider', '$locationProvider',function($routeProvider,$locationProvider){

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