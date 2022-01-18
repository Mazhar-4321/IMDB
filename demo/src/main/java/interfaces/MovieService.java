package interfaces;

import models.Actor;
import models.Movie;

import java.util.List;

public interface MovieService {
    // All get calls
    List<Movie> getMoviesByGenre(String name);
    List<Movie> getMoviesByGenreAndYear(String name,String year);
    List<Movie> getMoviesByRating(float rating);
    List<Movie> getMoviesByActorName(String name);
    List<Movie> getMoviesByActorId(int actorId);
    float getRating(int movieId);
    // All add calls
    Movie addMovie(Movie movie);
    // All Update Calls
    Movie updateMovie(Movie movie,int id);
    Movie updateMovieActors(int movieId,List<Actor> actorList);
    Movie updateMovieRating(int id,float rating);
    Movie updateMovieActorsAndRatings(int movieId,float rating,List<Actor> actorList);
    // All Delete Calls
      boolean deleteMovie(int id);

}
