var AuthenticationModule = angular.module('AuthenticationModule',['ngCookies']);
AuthenticationModule.service('AuthenticationService',['$http','$q','$cookies','REST_URI',function($http,$q,$cookies,REST_URI) {    
    
    var userIsAuthenticated = false;
    var role = 'GUEST';    
    var user = false;
        

    /** 
     * Setters and Getters for user
    */
    this.setUser = function(value) {
        user = value;
    }

    this.getUser = function() {
        return user;
    }

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

    this.login = function(credentials) {
        console.log(credentials);
        // get the deferred object
        var deferred = $q.defer();
        // $http will use the POST method and will call the angular js security
        // since we have to pass it to the spring security
        // we have to transform it in to the form value rather than JSON
        // so we are using the transformRequest to convert the credentials into the format
        // that is acceptable by spring security
        $http({
            method: 'POST',
            url: REST_URI + 'login',
            headers: {
                'Content-type': 'application/x-www-form-urlencoded'
            },
            transformRequest: function(obj) {
                var t = [];
                for(var p in obj) {
                    t.push(encodeURIComponent(p) + '=' + encodeURIComponent(obj[p]));
                }
                return t.join('&');
            },
            data: credentials
        }).then(
            // success callback
            function(response){
                deferred.resolve(response.data)
            },
            // error callback
            function(error){
                deferred.reject(error)
            }
        );

        return deferred.promise;
    }

    this.logout = function(){
        // get the deferred object
        var deferred = $q.defer();
        $http({
            method: 'GET',
            url: REST_URI + 'logout',        
        }).then(function(response){
            //console.log(response);
            $cookies.putObject('user', undefined);
            userIsAuthenticated  = false;
            role = 'GUEST';
            deferred.resolve(response.data);
        });

        return deferred.promise;
    }


    this.loadUserFromCookie = function() {
        user = $cookies.getObject('user');
        if(user){
            userIsAuthenticated = true;
            role = user.role;
        }
        else {
            userIsAuthenticated = false;
            role = 'GUEST';
        }        
        return user;
    }



    /**
     * Save the user into the cookie and 
     * fill the other details for the fields inside AuthenticationService
     * 
    */

    this.saveUser = function(user) {
        // save the user inside the cookie
        $cookies.putObject('user',user);
        role = user.role;
        userIsAuthenticated = true;        

    }

    this.checkLogin = function(login) {
        var deferred = $q.defer();
        $http.post(REST_URI + 'guest/checkLogin',login, {
             headers: {
                 'Content-Type': undefined
            }
        })
        .then(
            function(response){                
                deferred.resolve(response);
            },
            function(error){                
                deferred.resolve(error);
            }
        );

        return deferred.promise;
    }


}]);