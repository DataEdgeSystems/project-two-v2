// User module is dependend on the AuthenticationModule 
// which allows to update the cookie value
var UserModule = angular.module('UserModule', ['AuthenticationModule']);

//ng-model doesn't work with input[type='file'] that;s why we have to create a custom directive
// the name of the directive is fileModel so we have to use file-model in the HTML
UserModule.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            console.log(model);
            var modelSetter = model.assign;
            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);                    
                });
            });
        }
    };
}]);

// Service that contains the file upload function
UserModule.service('UserService',['$q','$http','$rootScope','REST_URI', function ($q,$http, $rootScope,REST_URI) {

    // uploadFile function to upload the image on the server
    this.uploadFile = function (file) {

        var deferred = $q.defer();

        // NOTE: the 'Content-Type' is undefined to add a boundary between the multipart content
        // and other data content which is added automatically thats why here we don't use 
                
        var fd = new FormData();
        fd.append('file', file);
        // send the user id which can be used to update the usera
        // and to set the file name
        fd.append('id', $rootScope.user.id);
        $http.post(REST_URI + 'upload/profile-picture', fd, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        })
        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (error) {
                console.log(error);
                deferred.reject(error);
            }
        );
        return deferred.promise;
    }

}]);