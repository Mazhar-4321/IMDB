package controllers;

import exceptions.UserDefinedException;
import models.ApiResponse;
import models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import services.AuthorService;
import services.MovieService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private AuthorService authorService;

    @GetMapping("{genre}")
    public ResponseEntity<ApiResponse> getMoviesByGenre(@PathVariable("genre") String genre) {

        List<Movie> movieList = movieService.getMoviesByGenre(genre);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(movieList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping(params = {"genre", "year"})
    public ResponseEntity<ApiResponse> getMoviesByGenreAndYear(@RequestParam("genre") String genre, @RequestParam("year") String year) {
        List<Movie> movieList = movieService.getMoviesByGenreAndYear(genre, year);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(movieList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("{rating}")
    public ResponseEntity<ApiResponse> getMoviesByRating(@PathVariable("rating") String rating) {
        float floatRating = Float.parseFloat(rating);
        List<Movie> movieList = movieService.getMoviesByRating(floatRating);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(movieList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getMoviesById(@PathVariable("id") String id) {
        int integerId = Integer.parseInt(id);
        List<Movie> movieList = movieService.getMoviesByActorId(integerId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(movieList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("{actorName}")
    public ResponseEntity<ApiResponse> getMoviesByActorName(@PathVariable("actorName") String actorName) {
        List<Movie> movieList = movieService.getMoviesByActorName(actorName);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(movieList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("{actorId}")
    public ResponseEntity<ApiResponse> getRatingsByMovieId(@PathVariable("actorId") String actorId) {
        int actId = Integer.parseInt(actorId);
        float ratings = movieService.getRating(actId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(ratings);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("{actorId1}")
    public ResponseEntity<ApiResponse> getAllMovies(@PathVariable("actorId1") int actorId)
    {
      List<Movie> movieList=  movieService.getMoviesByActorId(actorId);
      ApiResponse apiResponse= new ApiResponse();
      apiResponse.setData(movieList);
      return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ApiResponse> addMovie(@RequestBody Movie movie, HttpServletRequest req) {
        try {
            String token = req.getHeader("X-Auth-Token");
            ApiResponse apiResponse = new ApiResponse();
            boolean flag = StringUtils.hasText(token);
            if (!flag) {
                throw new UserDefinedException("Authentication Token Missing");
            }
            String movieName=movie.getName();
            flag=StringUtils.hasText(movieName);
            if(!flag)
            {
                throw new UserDefinedException("Movie Name Missing");
            }
            Movie movie1=movieService.addMovie(movie);
            apiResponse.setData(movie1);
            return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
        } catch (Exception e) {

        }
        return null;
    }
    @PatchMapping("{id}")
    public ResponseEntity<ApiResponse> updateMovieActors(@PathVariable("id") int id,@RequestBody Movie movie,HttpServletRequest req) throws UserDefinedException {
        String request=req.getHeader("X-Auth-Token");
        ApiResponse apiResponse= new ApiResponse();
        boolean flag=StringUtils.hasText(request);
        if(!flag)
        {
            throw new UserDefinedException("Auth Token Missing");
        }
        Integer size=movie.getActorList().size();
        Float rating=movie.getRating();
        if((size!=null&&rating!=null) ||(rating!=null)||(size!=null))
        {
            if(size!=null&&rating!=null)
            {
               Movie m= movieService.updateMovieActors(id,movie.getActorList());
               apiResponse.setData(m);
               return new ResponseEntity<>(apiResponse,HttpStatus.OK);

            }
            if(rating!=null)
            {
                Movie m= movieService.updateMovieRating(id,movie.getRating());
                apiResponse.setData(m);
                return new ResponseEntity<>(apiResponse,HttpStatus.OK);
            }
            else
            {
                Movie m= movieService.updateMovieActors(id,movie.getActorList());
                apiResponse.setData(m);
                return new ResponseEntity<>(apiResponse,HttpStatus.OK);
            }
        }
        else
        {
            apiResponse.setCode("404");
            apiResponse.setMessage("Operations Unsuccessful");
            return new ResponseEntity<>(apiResponse,HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateMovie(@PathVariable("id") int id,@RequestBody Movie movie)
    {
        Movie mov= movieService.updateMovie(movie,id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(mov);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<Boolean> deleteMovie(@RequestParam("id") int id)
    {
        boolean flag = movieService.deleteMovie(id);
        if(flag)
        {
            return new ResponseEntity<>(flag,HttpStatus.OK);
        }
        return new ResponseEntity<>(flag,HttpStatus.NOT_ACCEPTABLE);
    }

}
