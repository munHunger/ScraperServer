import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";
import { map, catchError } from "rxjs/operators";
import { HttpClient } from "@angular/common/http";
import { Movie } from "../model/movie.model";

@Injectable()
export class MovieService {
  constructor(private http: HttpClient) {}

  public popular(): Observable<Movie[]> {
    return this.http.get<Movie[]>("/api/popular").pipe(
      map((data: any) => data.movie),
      catchError(error => {
        return this.handleError(error);
      })
    );
  }
  public library(): Observable<Movie[]> {
    return this.http.get<Movie[]>("/api/library").pipe(
      map((data: any) => data.movie),
      catchError(error => {
        return this.handleError(error);
      })
    );
  }

  public getSingle(id: number): Observable<Movie> {
    return this.http.get<Movie>("/api/movie?id=" + id).pipe(
      map((data: any) => data.movie[0]),
      catchError(error => this.handleError(error))
    );
  }

  public addToWishlist(id: number): Observable<any> {
    return this.http
      .post<any>("/api/wish", { id })
      .pipe(catchError(error => this.handleError(error)));
  }

  public search(query: string): Observable<Movie[]> {
    return this.http.get<Movie[]>("/api/search?query=" + encodeURI(query)).pipe(
      map((data: any) => data.search),
      catchError(error => this.handleError(error))
    );
  }

  private handleError(error: Response | any) {
    let errMsg: string;
    errMsg = `${error.status} - ${error.statusText || ""}`;
    console.error(errMsg);
    return throwError(errMsg);
  }
}
