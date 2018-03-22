app.controller('TaskController', ['$scope','$http','$location','$routeParams','$filter',
    function ($scope,$http,$location,$routeParams,$filter) {
        $scope.task   = {};
        $scope.tmp    = {}
        $scope.task.answers =[];
        $scope.times  = [];
        $scope.days   = [];
        $scope.weeks   = [ {id:'1',value:"Domingo"},{id:'2',value:"Segunda"},{id:'3',value:"Terça"},{id:'4',value:"Quarta"},{id:'5',value:"Quinta"},{id:'6',value:"Sexta"},{id:'7',value:"Sábado"},
           ];
        $scope.months = [{id:'01',value:"Janeiro"},{id:'02',value:"Fevereiro"},{id:'03',value:"Março"},{id:'04',value:"Abril"},{id:'05',value:"Maio"},{id:'06',value:"Junho"},
            {id:'07',value:"Julho"},{id:'08',value:"Agosto"},{id:'09',value:"Setembro"},{id:'10',value:"Outubro"},{id:'11',value:"Novembro"},{id:'12',value:"Dezembro"}];
        for (i = 0; i < 24; i++) {
            var hour = (i < 10  ?  '0' + i : i)  + ':00';
            $scope.times.push({'id': hour});
        }
        for (i = 1; i < 31; i++) {
            var day = i < 10  ?  ('0' + i) : (i + '');
            $scope.days.push({'id': day});
        }

        $('#date input').datepicker({
            clearBtn: true,
            startDate: "0d",
            format: 'dd-mm-yyyy',
            language: "pt-BR",
            toggleActive: true,
            todayHighlight: true
        });

        $scope.showDatePicker = function(){
            return $scope.task.type == 'SINGLE';
        };

        $scope.showWeek = function(){
            return $scope.task.type == 'WEEKLY';
        };

        $scope.showDay = function(){
            return ($scope.task.type == 'MONTHLY' || $scope.task.type == 'YEARLY');
        };

        $scope.showMonth = function(){
            return $scope.task.type == 'YEARLY';
        };

        $scope.setValue = function(){
            if($scope.task.type){
                var type = $scope.task.type;
                if(type == 'DAILY'){
                    $scope.task.value = '0';
                }else if(type =='SINGLE'){
                    $scope.task.value = $scope.tmp.date;
                }else if(type == 'WEEKLY'){
                    $scope.task.value = $scope.tmp.week;
                }else if( type == 'MONTHLY'){
                    $scope.task.value = $scope.tmp.day;
                }else if(type == 'YEARLY'){
                    $scope.task.value = $scope.tmp.month + "-" + $scope.tmp.day;
                }else{
                    $scope.task.value = '0';
                }
            }
        }

        $scope.getValue = function(){
            $scope.tmp = {}
            if($scope.task.type){
                var type = $scope.task.type;
                if(type =='SINGLE'){
                    $scope.tmp.date = $scope.task.value;
                }else if(type == 'WEEKLY'){
                    $scope.tmp.week = $scope.task.value;
                }else if( type == 'MONTHLY'){
                    $scope.tmp.day = $scope.task.value;
                }else if(type == 'YEARLY'){
                    $scope.tmp.month = $scope.task.value.split('-')[0];
                    $scope.tmp.day   = $scope.task.value.split('-')[1];
                }
            }
        }

        $scope.save = function (){
            var method =  $scope.task.id ? 'PUT' : 'POST';
            var url    =  $scope.task.id ? 'api/tasks/'  + $scope.task.id : 'api/tasks/';
            var msg    =  $scope.task.id ? 'Alterado com sucesso' : 'Criado com sucesso';
            $scope.setValue();
            $http({method: method, url: url  ,data:$scope.task})
                .then(function (){
                    $scope.task = {};
                    $scope.tmp  = {}
                    $location.path('agendamento');
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
            return $scope.frm.$invalid || $scope.task.answers == 0;
        };

        $scope.addAnswer = function(answer){
            var a = JSON.parse(answer)
            var t = $filter("filter")($scope.task.answers, {id:a.id});
            if(t.length == 0 ){
                $scope.task.answers.push(a);
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
            $scope.task.answers.splice(index,1);
        };

        $scope.deleteObject = function(id){
            $http({method: 'DELETE', url:'api/tasks/' + id})
                .then(function (response){
                    $('#dt-list').DataTable().ajax.reload();
                    $.notify({message : "Removido com sucesso."},
                        {type : 'success',
                            offset: {x: 10, y: 59}});
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('agendamento');
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });
        };

        if($routeParams.id){
            $http({method: 'GET', url:'api/tasks/types'})
                .then(function (response){
                    $scope.types = response.data;
                    $http({method: 'GET', url:'api/tasks/' + $routeParams.id})
                        .then(function (response){
                            $scope.task = response.data;
                            $scope.getValue();

                        } , function (response){
                            console.log(response.data);
                            console.log(response.status);
                            $location.path('agendamento');
                            $.notify({message : response.data.message},
                                {type : 'danger', offset: {x: 10, y: 59}});
                        });
                } , function (response){
                    console.log(response.data);
                    console.log(response.status);
                    $location.path('agendamento');
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
                    $location.path('agendamento');
                    $.notify({message : response.data.message},
                        {type : 'danger',
                            offset: {x: 10, y: 59}});
                });

        }else{
            $http({method: 'GET', url:'api/tasks/types'})
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