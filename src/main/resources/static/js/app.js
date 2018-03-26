var app = angular.module('App', ['ngRoute','goDataTable']);

app.config(function($routeProvider, $locationProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'main.html',
            controller: 'MainController'
        })
        .when('/pessoas', {
            templateUrl: 'persons.html',
            controller:  'PersonController'
        })
        .when('/pessoas/:id', {
            templateUrl: 'person.html',
            controller:  'PersonController'
        })
        .when('/pergunta-form/:id', {
            templateUrl: 'question-form.html',
            controller: 'QuestionController'
        })
        .when('/resposta', {
            templateUrl: 'answer-list.html',
            controller: 'AnswerController'
        })
        .when('/resposta-form', {
            templateUrl: 'answer-form.html',
            controller: 'AnswerController'
        })
        .when('/resposta-form/:id', {
            templateUrl: 'answer-form.html',
            controller: 'AnswerController'
        })
        .when('/resposta-tipo', {
            templateUrl: 'answer-type-list.html',
            controller: 'AnswerTypeController'
        })
        .when('/resposta-tipo-form', {
            templateUrl: 'answer-type-form.html',
            controller: 'AnswerTypeController'
        })
        .when('resposta-tipo-form/:id', {
            templateUrl: 'answer-type-form.html',
            controller: 'AnswerTypeController'
        })
        .when('/parametro', {
            templateUrl: 'parameter-list.html',
            controller: 'ParameterController'
        })
        .when('/parametro-form', {
            templateUrl: 'parameter-form.html',
            controller: 'ParameterController'
        })
        .when('/parametro-form/:id', {
            templateUrl: 'parameter-form.html',
            controller: 'ParameterController'
        })
        .when('/agendamento', {
            templateUrl: 'task-list.html',
            controller: 'TaskController'
        })
        .when('/agendamento-form', {
            templateUrl: 'task-form.html',
            controller: 'TaskController'
        })
        .when('/agendamento-form/:id', {
            templateUrl: 'task-form.html',
            controller: 'TaskController'
        })
        .when('/membro', {
            templateUrl: 'member-list.html',
            controller: 'MemberController'
        })
        .when('/membro-form', {
            templateUrl: 'member-form.html',
            controller: 'MemberController'
        })
        .when('/membro-form/:id', {
            templateUrl: 'member-form.html',
            controller: 'MemberController'
        })
        .otherwise({
             rediretTo : '/'
        });
   // $locationProvider.html5Mode(true);
});

app.directive('restrictTo', function() {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var re = RegExp(attrs.restrictTo);
            var exclude = /Backspace|Enter|Tab|Delete|Del|ArrowUp|Up|ArrowDown|Down|ArrowLeft|Left|ArrowRight|Right/;

            element[0].addEventListener('keydown', function(event) {
                if (!exclude.test(event.key) && !re.test(event.key)) {
                    event.preventDefault();
                }
            });
        }
    }
});

