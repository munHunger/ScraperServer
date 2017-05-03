"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require("@angular/core");
var movie_service_1 = require("../movie-service/movie.service");
var MovieList = (function () {
    function MovieList(movieService) {
        this.movieService = movieService;
    }
    MovieList.prototype.ngOnInit = function () {
        this.getMovies();
    };
    MovieList.prototype.getMovies = function () {
        this.movielist = this.movieService.getShortMovieInfoList();
    };
    return MovieList;
}());
MovieList = __decorate([
    core_1.Component({
        selector: 'movie-list',
        templateUrl: './movie-list.component.html',
        styleUrls: ['./movie-list.component.css'],
        providers: [movie_service_1.MovieService]
    }),
    __metadata("design:paramtypes", [movie_service_1.MovieService])
], MovieList);
exports.MovieList = MovieList;
//# sourceMappingURL=movie-list.component.js.map