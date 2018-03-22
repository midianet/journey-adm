app.controller('MemberController', ['$scope','$http','$location','$routeParams',
               function ($scope,$http,$location,$routeParams) {
        $scope.member = {};

        $scope.save = function (){
            var method =  $scope.member.id ? 'PUT' : 'POST';
            var url    =  $scope.member.id ? 'api/members/'  + $scope.member.id : 'api/members/';
            var msg    =  $scope.member.id ? 'Alterado com sucesso' : 'Criado com sucesso';
            $http({method: method, url: url  ,data:$scope.member})
                .then(function (){
                    $location.path('membro');
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
            $http({method: 'DELETE', url:'api/members/' + id})
                .then(function (response){
                    $('#dt-list').DataTable().ajax.reload();
                    $.notify({message : "Removido com sucesso."},
                        {type : 'success',
                            offset: {x: 10, y: 59}});
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('membro');
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

        if($routeParams.id){
            $http({method: 'GET', url:'api/members/' + $routeParams.id})
                .then(function (response){
                    $scope.member = response.data;
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('membro');
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });
        }

    }
]);