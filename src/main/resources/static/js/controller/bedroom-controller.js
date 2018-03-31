app.controller('BedroomController', ['$scope','$http','$location','$routeParams',
               function ($scope,$http,$location,$routeParams) {
        $scope.bedroom = {};

        $scope.save = function (){
            var method =  $scope.bedroom.id ? 'PUT' : 'POST';
            var url    =  $scope.bedroom.id ? 'api/bedroons/'  + $scope.bedroom.id : 'api/bedroons/';
            var msg    =  $scope.bedroom.id ? 'Alterado com sucesso' : 'Criado com sucesso';
            $http({method: method, url: url  ,data:$scope.bedroom})
                .then(function (){
                    $location.path('quartos');
                    $.notify({message : msg},
                        {bedroom : 'success',
                         offset: {x: 10, y: 59}});
                    } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $.notify({message : response.data.message},
                        {bedroom : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

        $scope.deleteObject = function(id){
            $http({method: 'DELETE', url:'api/bedroons/' + id})
                .then(function (response){
                    $('#dt-list').DataTable().ajax.reload();
                    $.notify({message : "Removido com sucesso."},
                        {bedroom : 'success',
                            offset: {x: 10, y: 59}});
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('quartos');
                    $.notify({message : response.data.message},
                        {bedroom : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

        if($routeParams.id && $routeParams.id != 'new') {
            $http({method: 'GET', url: 'api/bedroons/' + $routeParams.id})
                .then(function (response) {
                    $scope.bedroom = response.data;
                }, function (response) {
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('quartos');
                    $.notify({message: response.data.message},
                        {
                            bedroom: 'danger',
                            offset: {x: 10, y: 59}
                        });
                });
        }
    }
]);