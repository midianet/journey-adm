app.controller('PaymentController', ['$scope','$http','$location','$routeParams',
               function ($scope,$http,$location,$routeParams) {
        $scope.payment = {};

        $scope.save = function (){
            var method =  $scope.payment.id ? 'PUT' : 'POST';
            var url    =  $scope.payment.id ? '../api/payments/'  + $scope.payment.id : '../api/payments/';
            var msg    =  $scope.payment.id ? 'Alterado com sucesso' : 'Criado com sucesso';
            $http({method: method, url: url  ,data:$scope.payment})
                .then(function (){
                    $location.path('payments');
                    $.notify({message : msg},
                        {payment : 'success',
                         offset: {x: 10, y: 59}});
                    } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $.notify({message : response.data.message},
                        {payment : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

        $scope.deleteObject = function(id){
            $http({method: 'DELETE', url:'../api/payments/' + id})
                .then(function (response){
                    $('#dt-list').DataTable().ajax.reload();
                    $.notify({message : "Removido com sucesso."},
                        {payment : 'success',
                            offset: {x: 10, y: 59}});
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('payments');
                    $.notify({message : response.data.message},
                        {payment : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

        if($routeParams.id) {
            if ($routeParams.id != 'new') {
                $http({method: 'GET', url: '../api/payments/' + $routeParams.id})
                    .then(function (response) {
                        $scope.payment = response.data;
                    }, function (response) {
                        console.log(response.data);
                        console.log(response.status);
                        $location.path('payments');
                        $.notify({message: response.data.message},
                            {
                                payment: 'danger',
                                offset: {x: 10, y: 59}
                            });
                    });
            }
        }
    }
]);