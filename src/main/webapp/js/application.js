var app = angular.module('angularBoot', [])
app.controller('statsCtrl', function($scope) {
    $scope.name = 'anybody'
    $scope.rows = [
          {field: 'toto', count: 12},
          {field: 'Chuck Norris what did you expect', count: 42}
    ]
})

app.controller('fieldCtrl', function($scope, $rootScope, $http) {
    $http.get('/fields').then(function(response) {
        $scope.fields = response.data
    })
    $scope.selectField = function(field) {
        $rootScope.selectedField = field
    }
})
