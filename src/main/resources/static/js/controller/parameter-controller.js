app.controller('ParameterController', ['$scope','$http','$location','$routeParams',
               function ($scope,$http,$location,$routeParams) {
        $scope.parameter = {};

        $scope.save = function (){
            var method =  $scope.parameter.id ? 'PUT' : 'POST';
            var url    =  $scope.parameter.id ? 'api/parameters/'  + $scope.parameter.id : 'api/parameters/';
            var msg    =  $scope.parameter.id ? 'Alterado com sucesso' : 'Criado com sucesso';
            $http({method: method, url: url  ,data:$scope.parameter})
                .then(function (){
                    $location.path('parametro');
                    $.notify({message : msg},
                        {type : 'success',
                         offset: {x: 10, y: 59}});
                    } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

        $scope.deleteObject = function(id){
            $http({method: 'DELETE', url:'api/parameters/' + id})
                .then(function (response){
                    $('#dt-list').DataTable().ajax.reload();
                    $.notify({message : "Removido com sucesso."},
                        {type : 'success',
                            offset: {x: 10, y: 59}});
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('parametro');
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

        if($routeParams.id){
            $http({method: 'GET', url:'api/parameters/' + $routeParams.id})
                .then(function (response){
                    $scope.parameter = response.data;
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('parametro');
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });
        }

    }
]);