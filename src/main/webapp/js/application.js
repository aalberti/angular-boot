var app = angular.module('angularBoot', [])
app.controller('statsCtrl', function($scope, $rootScope, $http) {
    $http.get('/rest/fields').then(function(response) {
        $scope.fields = response.data
    })
    $rootScope.selectedField = "<- Select a field"
    $rootScope.returnedCount = '?'
    $rootScope.totalCount = '?'
    $scope.selectField = function(field) {
        $rootScope.selectedField = field
        $http({
            method: 'GET',
            url: '/rest/stats',
            params: { column: field }
        }).then(function(response) {
           $rootScope.stats = response.data.rows
           $rootScope.totalCount = response.data.totalCount
           $rootScope.returnedCount = response.data.returnedCount
        })
    }
})
