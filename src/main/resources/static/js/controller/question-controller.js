app.controller('QuestionController', ['$scope','$http','$location','$routeParams','$filter',
    function ($scope,$http,$location,$routeParams,$filter) {
        $scope.question = {};
        $scope.question.answers =[];

        $scope.save = function (){
            var method =  $scope.question.id ? 'PUT' : 'POST';
            var url    =  $scope.question.id ? 'api/questions/'  + $scope.question.id : 'api/questions/';
            var msg    =  $scope.question.id ? 'Alterado com sucesso' : 'Criado com sucesso';
            $http({method: method, url: url  ,data:$scope.question})
                .then(function (){
                    $location.path('pergunta');
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

        $scope.enableAddAnswer = function(){
            return $scope.answer;
        };

        $scope.enableSave = function(){
            return $scope.frm.$invalid || $scope.question.answers == 0;
        };



        $scope.addAnswer = function(answer){
            var a = JSON.parse(answer)
            var t = $filter("filter")($scope.question.answers, {id:a.id});
            if(t.length == 0 ){
                $scope.question.answers.push(a);
                $scope.propagate = false;
                $('#answer').val('').trigger('change.select2');
                $scope.propagate = true;
                $scope.answer = null;
            }else{
                $.notify({message : 'Resposta já adicionada.'},
                    {type : 'warning',
                        offset: {x: 10, y: 59}});
            }
        };

        $scope.removeAnswer = function(index){
            $scope.question.answers.splice(index,1);
        };

        $scope.deleteObject = function(id){
            $http({method: 'DELETE', url:'api/questions/' + id})
                .then(function (response){
                    $('#dt-list').DataTable().ajax.reload();
                    $.notify({message : "Removido com sucesso."},
                        {type : 'success',
                            offset: {x: 10, y: 59}});
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('pergunta');
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

        if($routeParams.id){
            $http({method: 'GET', url:'api/questions/types'})
                .then(function (response){
                    $scope.types = response.data;
                    $http({method: 'GET', url:'api/questions/' + $routeParams.id})
                        .then(function (response){
                            $scope.question = response.data;
                            //$scope.question.type = $scope.question.type.toUpperCase();
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
            $http({method: 'GET', url:'api/questions/types'})
                .then(function (response){
                    $scope.types = response.data;
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

        }
    }
]);