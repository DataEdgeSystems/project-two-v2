var AuthenticationModule = angular.module('AuthenticationModule',[]);
AuthenticationModule.service('AuthenticationService',function() {

    var userIsAuthenticated = false;
    var role = 'guest';

    this.setUserIsAuthenticated = function(value) {
        userIsAuthenticated = value;
    }

    this.getUserIsAuthenticated = function() {
        return userIsAuthenticated;
    }

    this.setRole = function(value) {
        role = value;
    }

    this.getRole = function() {
        return role;
    }

});