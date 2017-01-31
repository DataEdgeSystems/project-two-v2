var AdminModule = angular.module('AdminModule', []);
AdminModule.service('AdminService', ['REST_URI','$http','$q',function(REST_URI,$http,$q) {

    // return the user for activation
    this.getUsersForActivation = function() {

        var deferred = $q.defer();

        $http.get(REST_URI + 'admin/user-activation')
        .then(
            function(response) {
                deferred.resolve(response.data);
            },
            function(error){
                deferred.reject(error);
            }
        );
        return deferred.promise;
    }


    // return the text after approving the user
    this.approveUser = function(id) {
        var deferred = $q.defer();
        $http.put(REST_URI + 'admin/approve-user', id)
        .then(
            function(response) {
                deferred.resolve(response.data);
            },
            function(error){
                deferred.reject(error);
            }
        );
        return deferred.promise;
    }

}]);