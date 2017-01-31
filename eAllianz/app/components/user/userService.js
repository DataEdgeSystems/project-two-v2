var UserModule = angular.module('UserModule', []);

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

UserModule.service('UserService',['$http','$rootScope','REST_URI', function ($http, $rootScope,REST_URI) {

    this.uploadFile = function (file) {
        var fd = new FormData();
        fd.append('file', file);
        fd.append('id', $rootScope.user.id);
        $http.post(REST_URI + 'upload/profile-picture', fd, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        })
        .then(
            function (response) {
                console.log(response.message);
            },
            function () {}
        );
    }

}]);