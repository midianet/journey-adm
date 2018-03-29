app.controller('PersonController', ['$scope','$http','$location','$routeParams',
               function ($scope,$http,$location,$routeParams) {
        $scope.person   = {};
        $scope.persons  = [];
        $scope.bedroons = []  ;

        $scope.save = function (){
            var method =  $scope.person.id ? 'PUT' : 'POST';
            var url    =  $scope.person.id ? '../api/persons/'  + $scope.person.id : '../api/persons/';
            var msg    =  $scope.person.id ? 'Alterado com sucesso' : 'Criado com sucesso';
            $http({method: method, url: url  ,data:$scope.person})
                .then(function (){
                    $location.path('pessoas');
                    $.notify({message : msg},
                        {person : 'success',
                         offset: {x: 10, y: 59}});
                    } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $.notify({message : response.data.message},
                        {person : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

        $scope.deleteObject = function(id){
            $http({method: 'DELETE', url:'../api/persons/' + id})
                .then(function (response){
                    $('#dt-list').DataTable().ajax.reload();
                    $.notify({message : "Removido com sucesso."},
                        {person : 'success',
                            offset: {x: 10, y: 59}});
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('pessoas');
                    $.notify({message : response.data.message},
                        {person : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

       $scope.listBedroons = function (){
           $http({method: 'GET', url: '../api/bedroons/'})
               .then(function (response){
                   $scope.bedroons =  response.data;
                   $("#bedroom").each(function() {
                       $(this).select2({allowClear: true,
                               placeholder: "Quarto"},
                           {data: $(this).data()});
                   });
               } , function (response){
                   console.log(response.data);
                   console.log(response.status);
                   $.notify({message : response.data.message},
                       {type  : 'danger',
                           offset: {x: 10, y: 59}});
               });
       };

        if($routeParams.id && $routeParams.id != 'new') {
            $http({method: 'GET', url: '../api/bedroons/'})
                .then(function (response){
                    $scope.bedroons =  response.data;
                    $("#bedroom").each(function() {
                        $(this).select2({allowClear: true,
                                         placeholder: "Quarto"},
                                         {data: $(this).data()});
                    });
                    $http({method: 'GET', url:'../api/persons/' + $routeParams.id})
                        .then(function (response){
                            $scope.person = response.data;
                            if($scope.person.bedroom) {
                                setTimeout(function () {
                                    $scope.propagate = false;
                                    $("#bedroom").val($scope.person.bedroom.id).trigger("change");
                                    $scope.propagate = true;
                                }, 0);
                            }
                        } , function (response){
                            console.log(response.data);
                            console.log(response.status);
                            $location.path('pessoas');
                            $.notify({message : response.data.message},
                                {type : 'danger',
                                    offset: {x: 10, y: 59}});
                        });

                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('pessoas');
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });
        }else{
           $scope.listBedroons();
        }
    }
]);