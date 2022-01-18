package services;

import models.Actor;
import models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieService implements interfaces.MovieService {
    private List<Movie> movieList= new ArrayList<>();

    @Override
    public List<Movie> getMoviesByGenre(String name) {
        List<Movie> movieListTemp= new ArrayList<>();
        for (Movie movie:movieList) {
            if(movie.getGenre().equals(name))
            {
                movieListTemp.add(movie);
            }
        }
        return movieListTemp;
    }

    @Override
    public List<Movie> getMoviesByGenreAndYear(String name, String year) {
        List<Movie> movieListTemp= new ArrayList<>();
        for (Movie movie:movieList) {
            if(movie.getGenre().equals(name)&&movie.getYear().equals(year))
            {
                movieListTemp.add(movie);
            }
        }
        return movieListTemp;
    }

    @Override
    public List<Movie> getMoviesByRating(float rating) {
        List<Movie> movieListTemp= new ArrayList<>();
        for (Movie movie:movieList) {
            if(movie.getRating()==rating)
            {
                movieListTemp.add(movie);
            }
        }
        return movieListTemp;
    }

    @Override
    public List<Movie> getMoviesByActorName(String name) {
        List<Movie> movieListTemp= new ArrayList<>();
        for (Movie movie:movieList) {
            List<Actor> actorList = movie.getActorList();
            for (Actor actor:actorList) {
                if(actor.getName().equals(name))
                {
                    movieListTemp.add(movie);
                }
            }
        }
        return movieListTemp;
    }

    @Override
    public List<Movie> getMoviesByActorId(int actorId) {
        List<Movie> movieListTemp= new ArrayList<>();
        for (Movie movie:movieList) {
           List<Actor> actorList= movie.getActorList();
            for (Actor actor:actorList
                 ) {
                if(actor.getId()==actorId)
                {
                    movieListTemp.add(movie);
                }
            }
        }
        return movieListTemp;
    }

    @Override
    public float getRating(int movieId) {
        float rating =0.0f;
        for (Movie movie:movieList) {
           if(movie.getId()==movieId)
           {
               rating=movie.getRating();
               break;
           }
        }
        return rating;
    }

    @Override
    public Movie addMovie(Movie movie) {
        movieList.add(movie);
        return movie;
    }

    @Override
    public Movie updateMovie(Movie movie, int id) {

        int i=0;
        for (Movie movieTemp:movieList) {
            if(movieTemp.getId()==id)
            {
                movieList.set(i,movie);
            }
            i++;
        }
        return movie;
    }

    @Override
    public Movie updateMovieActors(int movieId, List<Actor> actorList) {
        int i=0;
        Movie movieTemp = new Movie();
        for (Movie movie:movieList) {
            if(movie.getId()==movieId)
            {
                movie.setActorList(actorList);
                movieTemp=movie;
                movieList.set(i,movie);
            }
            i++;
        }
        return movieTemp;
    }

    @Override
    public Movie updateMovieRating(int id, float rating) {
        int i=0;
        for (Movie movieTemp:movieList) {
            if(movieTemp.getId()==id)
            {
                movieTemp.setRating(rating);
                movieList.set(i,movieTemp);
                return movieTemp;
            }
            i++;
        }
        return null;
    }

    @Override
    public Movie updateMovieActorsAndRatings(int movieId, float rating, List<Actor> actorList) {
        int i=0;
        for (Movie movieTemp:movieList) {
            if(movieTemp.getId()==movieId)
            {
                movieTemp.setRating(rating);
                movieTemp.setActorList(actorList);
                movieList.set(i,movieTemp);
                return movieTemp;
            }
            i++;
        }
        return null;
    }

    @Override
    public boolean deleteMovie(int id) {
        int i=0;
        for (Movie movieTemp:movieList) {
            if(movieTemp.getId()==id)
            {

                movieList.remove(i);
                return true;
            }
            i++;
        }
        return false;
    }
}
