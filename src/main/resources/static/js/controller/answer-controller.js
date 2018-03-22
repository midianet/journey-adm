app.controller('AnswerController', ['$scope','$http','$location','$routeParams',
    function ($scope,$http,$location,$routeParams) {
        $scope.answer = {};

        $scope.save = function (){
            var method =  $scope.answer.id ? 'PUT' : 'POST';
            var url    =  $scope.answer.id ? 'api/bedrooms/'  + $scope.answer.id : 'api/bedrooms/';
            var msg    =  $scope.answer.id ? 'Alterado com sucesso' : 'Criado com sucesso';
            $http({method: method, url: url  ,data:$scope.answer})
                .then(function (){
                    $location.path('resposta');
                    $.notify({message : msg},
                        {type : 'success',
                            offset: {x: 10, y: 59}});
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $.notify({message : response.data.erro},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

        $scope.deleteObject = function(id){
            $http({method: 'DELETE', url:'api/bedrooms/' + id})
                .then(function (response){
                    $('#dt-list').DataTable().ajax.reload();
                    $.notify({message : "Removido com sucesso."},
                        {type : 'success',
                            offset: {x: 10, y: 59}});
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('resposta');
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

        if($routeParams.id){
            $http({method: 'GET', url: 'api/bedrooms/types/'})
                .then(function (response){
                    $scope.types =  response.data;
                    $("#type-search").each(function() {
                        $(this).select2({allowClear: true,
                                         placeholder: "Tipo"},
                                        {data: $(this).data()});
                    });
                    $http({method: 'GET', url:'api/bedrooms/' + $routeParams.id})
                        .then(function (response){
                            $scope.answer = response.data;
                            setTimeout(function(){
                                $scope.propagate = false;
                                $("#type-search").val($scope.answer.type.id).trigger("change");
                                $scope.propagate = true;
                            },0);
                        } , function (response){
                            console.log(response.data);
                            console.log(response.status);
                            $location.path('resposta');
                            $.notify({message : response.data.message},
                                {type : 'danger',
                                    offset: {x: 10, y: 59}});
                        });

                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('resposta');
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });
        }else{
            $http({method: 'GET', url: 'api/bedrooms/types/'})
                .then(function (response){
                    $scope.types =  response.data;
                    $("#type-search").each(function() {
                        $(this).select2({allowClear: true,
                                placeholder: "Tipo"},
                            {data: $(this).data()});
                    });
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });
        }
    }
]);