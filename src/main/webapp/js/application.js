var app = angular.module('angularBoot', [])
app.controller('statsCtrl', function($scope, $rootScope, $http) {
    $http.get('/fields').then(function(response) {
        $scope.fields = response.data
    })
    $scope.selectField = function(field) {
        $rootScope.selectedField = field
        $http({
            method: 'GET',
            url: '/stats',
            params: { column: field }
        }).then(function(response) {
           $rootScope.stats = response.data
        })
    }
})
