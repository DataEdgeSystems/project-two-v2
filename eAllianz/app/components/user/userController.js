
UserModule.controller('UserController', ['UserService', '$rootScope', '$timeout', function (UserService, $rootScope, $timeout) {

    var me = this;
    me.user = $rootScope.user;
    var date = new Date(me.user.birthDate.year, me.user.birthDate.monthValue - 1, me.user.birthDate.dayOfMonth + 1).toISOString().slice(0, 10);
    me.user.birthDate = date;

    // once the controller loads call the jQuery
    $timeout(function () {
        load();
    }, 100);

    
    me.uploadFile = function () {
        var file = me.picture;
        UserService.uploadFile(file);
    };


}]);

