package se.mulander.cosmos.common.model.movies.movie;

import se.mulander.cosmos.common.database.jpa.Database;

import java.util.*;

/**
 * The implementation of movie data access object
 */

public class MovieDaoImpl implements se.mulander.cosmos.common.model.movies.movie.MovieDao {

    @Override
    public List<Movie> getAllMovies() throws Exception {
        List<Movie> result;
        try {
            result = Database.getObjects("from Movie");
        } catch (Exception e) {
            throw e;
        }

        return result;
    }

    @Override
    public List<Movie> getMoviesByStatus(String status) throws Exception {
        List<Movie> result;
        Map<String, Object> param = new HashMap<>();
        param.put("status", status);
        try {
            result = Database.getObjects("from Movie WHERE extendedMovie.status = :status", param);
        } catch (Exception e){
            throw e;
        }

        return result;
    }

    @Override
    public Movie getMovieById(String id) throws Exception {
        List<Movie> result;
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        try {
            result = Database.getObjects("from Movie WHERE internalID = :id", param);
            return result.get(0);
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Movie getMovieByTitle(String title) throws Exception {
        List<Movie> result;
        Map<String, Object> param = new HashMap<>();
        param.put("title", title);
        try {
            result = Database.getObjects("from Movie WHERE title = :title", param);
            return result.get(0);
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public void updateMovie(Movie movie) { Database.updateObject(movie); }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
        try {
            Database.deleteObject(movie);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void saveMovie(Movie movie) {
        Database.saveObject(movie);
    }
}