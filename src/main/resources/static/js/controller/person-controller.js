app.controller('PersonController', ['$scope','$http','$location','$routeParams','$filter',
    function ($scope,$http,$location,$routeParams,$filter) {
        $scope.person = {};

        $scope.save = function (){
            var method =  $scope.person.id ? 'PUT' : 'POST';
            var url    =  $scope.person.id ? 'api/persons/'  + $scope.person.id : 'api/persons/';
            var msg    =  $scope.person.id ? 'Alterado com sucesso' : 'Criado com sucesso';
            $http({method: method, url: url  ,data:$scope.person})
                .then(function (){
                    $location.path('pessoas');
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
        $scope.enableSave = function(){
            return $scope.frm.$invalid;
        };

        $scope.deleteObject = function(id){
            $http({method: 'DELETE', url:'api/persons/' + id})
                .then(function (response){
                    $('#dt-list').DataTable().ajax.reload();
                    $.notify({message : "Removido com sucesso."},
                        {type : 'success',
                            offset: {x: 10, y: 59}});
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    //$location.path('pessoas');
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

        if($routeParams.id){
            $http({method: 'GET', url:'api/persons/types'})
                .then(function (response){
                    $scope.types = response.data;
                    $http({method: 'GET', url:'api/persons/' + $routeParams.id})
                        .then(function (response){
                            $scope.person = response.data;
                            //$scope.person.type = $scope.person.type.toUpperCase();
                        } , function (response){
                            console.log(response.data);
                            console.log(response.status);
                            $location.path('pergunta');
                            $.notify({message : response.data.message},
                                {type : 'danger', offset: {x: 10, y: 59}});
                        });
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('pergunta');
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });

            $http({method: 'GET', url:'api/bedrooms'})
                .then(function (response){
                    $scope.answers = response.data;
                    $("#answer").each(function() {
                        $(this).select2({allowClear: true,
                                placeholder: "Resposta"},
                            {data: $(this).data()});
                    });
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('pergunta');
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });

        }else{
            // $http({method: 'GET', url:'api/persons/types'})
            //     .then(function (response){
            //         $scope.types = response.data;
            //     } , function (response){
            //         console.log(response.data);
            //         console.log(response.status);
            //         $location.path('pergunta');
            //         $.notify({message : response.data.message},
            //             {type : 'danger',
            //                 offset: {x: 10, y: 59}});
            //     });

            // $http({method: 'GET', url:'api/bedrooms'})
            //     .then(function (response){
            //         $scope.answers = response.data;
            //         $("#answer").each(function() {
            //             $(this).select2({allowClear: true,
            //                     placeholder: "Resposta"},
            //                 {data: $(this).data()});
            //         });
            //     } , function (response){
            //         console.log(response.data);
            //         console.log(response.status);
            //         $location.path('pergunta');
            //         $.notify({message : response.data.message},
            //             {type : 'danger',
            //                 offset: {x: 10, y: 59}});
            //     });

        }
    }
]);