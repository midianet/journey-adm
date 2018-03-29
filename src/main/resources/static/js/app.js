var app = angular.module('App', ['ngRoute','goDataTable','ui.utils.masks']);

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
        .when('/pagamentos', {
        templateUrl: 'payments.html',
        controller:  'PaymentController'
    })
        .when('/pagamentos/:id', {
            templateUrl: 'payment.html',
            controller: 'PaymentController'
        })
        .when('/quartos', {
            templateUrl: 'bedroons.html',
            controller : 'BedroomController'
        })
        .when('/quartos/:id', {
            templateUrl: 'bedroom.html',
            controller : 'BedroomController'
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

