app.controller('PhotoController', ['$scope','$http','$location','$routeParams',
               function ($scope,$http,$location,$routeParams) {
        $scope.photo   = {};
        $scope.photos  = [];
        $scope.persons = []  ;

        $scope.save = function (){
            var method =  $scope.photo.id ? 'PUT' : 'POST';
            var url    =  $scope.photo.id ? 'api/photos/'  + $scope.photo.id : 'api/photos/';
            var msg    =  $scope.photo.id ? 'Alterado com sucesso' : 'Criado com sucesso';
            $http({method: method, url: url  ,data:$scope.photo})
                .then(function (){
                    $location.path('fotos');
                    $.notify({message : msg},
                        {photo : 'success',
                         offset: {x: 10, y: 59}});
                    } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $.notify({message : response.data.message},
                        {photo : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

        $scope.deleteObject = function(id){
            $http({method: 'DELETE', url:'api/photos/' + id})
                .then(function (response){
                    $('#dt-list').DataTable().ajax.reload();
                    $.notify({message : "Removido com sucesso."},
                        {photo : 'success',
                            offset: {x: 10, y: 59}});
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('fotos');
                    $.notify({message : response.data.message},
                        {photo : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

        if($routeParams.id) {
            $http({method: 'GET', url:'api/photos/' + $routeParams.id})
                .then(function (response){
                    $scope.photo = response.data;
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('fotos');
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });
        }else{
            $http({method: 'GET', url: 'api/persons/'})
                .then(function (response){
                    $scope.persons =  response.data;
                    $("#person-search").each(function() {
                        $(this).select2({allowClear: true,
                                placeholder: "Pessoa"},
                            {data: $(this).data()});
                    });
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $.notify({message : response.data.message},
                        {type  : 'danger',
                            offset: {x: 10, y: 59}});
                });
        }
    }
]);