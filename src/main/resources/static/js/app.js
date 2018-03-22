var app = angular.module('App', ['ngRoute','goDataTable']);

app.config(function($routeProvider, $locationProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'view/main.html',
            controller: 'MainController'
        })
        .when('/pergunta', {
            templateUrl: 'view/question-list.html',
            controller: 'QuestionController'
        })
        .when('/pergunta-form', {
            templateUrl: 'view/question-form.html',
            controller: 'QuestionController'
        })
        .when('/pergunta-form/:id', {
            templateUrl: 'view/question-form.html',
            controller: 'QuestionController'
        })
        .when('/resposta', {
            templateUrl: 'view/answer-list.html',
            controller: 'AnswerController'
        })
        .when('/resposta-form', {
            templateUrl: 'view/answer-form.html',
            controller: 'AnswerController'
        })
        .when('/resposta-form/:id', {
            templateUrl: 'view/answer-form.html',
            controller: 'AnswerController'
        })
        .when('/resposta-tipo', {
            templateUrl: 'view/answer-type-list.html',
            controller: 'AnswerTypeController'
        })
        .when('/resposta-tipo-form', {
            templateUrl: 'view/answer-type-form.html',
            controller: 'AnswerTypeController'
        })
        .when('/resposta-tipo-form/:id', {
            templateUrl: 'view/answer-type-form.html',
            controller: 'AnswerTypeController'
        })
        .when('/parametro', {
            templateUrl: 'view/parameter-list.html',
            controller: 'ParameterController'
        })
        .when('/parametro-form', {
            templateUrl: 'view/parameter-form.html',
            controller: 'ParameterController'
        })
        .when('/parametro-form/:id', {
            templateUrl: 'view/parameter-form.html',
            controller: 'ParameterController'
        })
        .when('/agendamento', {
            templateUrl: 'view/task-list.html',
            controller: 'TaskController'
        })
        .when('/agendamento-form', {
            templateUrl: 'view/task-form.html',
            controller: 'TaskController'
        })
        .when('/agendamento-form/:id', {
            templateUrl: 'view/task-form.html',
            controller: 'TaskController'
        })
        .when('/membro', {
            templateUrl: 'view/member-list.html',
            controller: 'MemberController'
        })
        .when('/membro-form', {
            templateUrl: 'view/member-form.html',
            controller: 'MemberController'
        })
        .when('/membro-form/:id', {
            templateUrl: 'view/member-form.html',
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

