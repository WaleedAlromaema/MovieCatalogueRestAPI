package com.alromaema.projects.moviecatalogsystem.controllers;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.alromaema.projects.moviecatalogsystem.service.MoviesCatalogeService;
import com.alromaema.projects.moviecatalogsystem.domain.Actor;
import com.alromaema.projects.moviecatalogsystem.domain.Director;
import com.alromaema.projects.moviecatalogsystem.domain.Movie;
/**
 *  Movie Catalogue Rest Controller That handle HTTP Requests related to movies path [api/movies]
 *
 * @author Waleed Alromaema
 */

@RestController
@RequestMapping("api/movies")
@CrossOrigin(exposedHeaders = "errors, content-type")

public class MoviesCatalogeRestController {
	
	@Autowired
	private MoviesCatalogeService moviesCatalogeService;
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<Movie>> getAllMovies(){
		Collection<Movie> movies = new ArrayList<Movie>();
		System.out.println("I am Before ADD ALL MOVIES");
		movies.addAll(this.moviesCatalogeService.getMoviesCataloge());
		System.out.println("I am AFTER ADD ALL MOVIES");
		System.out.println(movies);
		
		
		if (movies.isEmpty()){
			System.out.println("I am INSIDE EXCEPTION  MOVIES are EMPTY");
			
			return new ResponseEntity<Collection<Movie>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Movie>>(movies, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/{Id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Movie> getMovie(@PathVariable("Id") int Id){
		Movie movie = this.moviesCatalogeService.getMovieById(Id);
		if(movie == null){
			return new ResponseEntity<Movie>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Movie>(movie, HttpStatus.OK);
	}
	//find movies by actor id
	@RequestMapping(value = "/actor/{Id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<Movie>> getMovieByActorId(@PathVariable("Id") int Id){
		Actor actor=new Actor();
		actor.setId(Id);
		Collection<Movie> movies = new ArrayList<Movie>();
		movies.addAll(this.moviesCatalogeService.getAllMoviesByActor(actor));
		
		
		if (movies.isEmpty()){
			
			return new ResponseEntity<Collection<Movie>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Movie>>(movies, HttpStatus.OK);

	}
	
	//find movies by Director id
		@RequestMapping(value = "/director/{Id}", method = RequestMethod.GET, produces = "application/json")
		public ResponseEntity<Collection<Movie>> getMovieByDirectorId(@PathVariable("Id") int Id){
			Director director=new Director();
			director.setId(Id);
			Collection<Movie> movies = new ArrayList<Movie>();
			movies.addAll(this.moviesCatalogeService.getAllMoviesByDirector(director));
			
			
			if (movies.isEmpty()){
				
				return new ResponseEntity<Collection<Movie>>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Collection<Movie>>(movies, HttpStatus.OK);

		}
		
		//find movies by Director id
				@RequestMapping(value = "/director_byname/{firstname}", method = RequestMethod.GET, produces = "application/json")
				public ResponseEntity<Collection<Movie>> getMovieByDirectorName(@PathVariable("firstname") String firstname){//,@PathVariable("lastname") String lastname){
					System.out.println("First name =="+firstname);//+"  Last Name=="+lastname);
					Director director=new Director();
					director.setFirstName(firstname);
					//director.setLastName(firstname[1]);
					
					Collection<Movie> movies = new ArrayList<Movie>();
					movies.addAll(this.moviesCatalogeService.getAllMoviesByDirector(director));
					
					
					if (movies.isEmpty()){
						
						return new ResponseEntity<Collection<Movie>>(HttpStatus.NOT_FOUND);
					}
					return new ResponseEntity<Collection<Movie>>(movies, HttpStatus.OK);

				}

				@RequestMapping(value = "/actor_by_name/{firstname}", method = RequestMethod.GET, produces = "application/json")
				public ResponseEntity<Collection<Movie>> getMovieByActorName(@PathVariable("firstname") String firstname){//,@PathVariable("lastname") String lastname){
					System.out.println("First name =="+firstname);//+"  Last Name=="+lastname);
					Actor actor=new Actor();
					actor.setFirstName(firstname);
					
					Collection<Movie> movies = new ArrayList<Movie>();
					movies.addAll(this.moviesCatalogeService.getAllMoviesByActor(actor));
					
					
					if (movies.isEmpty()){
						
						return new ResponseEntity<Collection<Movie>>(HttpStatus.NOT_FOUND);
					}
					return new ResponseEntity<Collection<Movie>>(movies, HttpStatus.OK);

				}

		
		//find movies by genre
				@RequestMapping(value = "/genre/{genre}", method = RequestMethod.GET, produces = "application/json")
				public ResponseEntity<Collection<Movie>> getMovieByGenreName(@PathVariable("genre") String genre){
					Collection<Movie> movies = new ArrayList<Movie>();
					movies.addAll(this.moviesCatalogeService.getAllMoviesByGenre(genre));
					
					
					if (movies.isEmpty()){
						
						return new ResponseEntity<Collection<Movie>>(HttpStatus.NOT_FOUND);
					}
					return new ResponseEntity<Collection<Movie>>(movies, HttpStatus.OK);

				}
		
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
		System.out.println("Hiiiiiiiii IM Here");
		this.moviesCatalogeService.saveMovie(movie);
		return new ResponseEntity<Movie>(movie, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/all/", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<List<Movie>> addMovies(@RequestBody List<Movie> movies, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		HttpHeaders headers = new HttpHeaders();
		   
		for(Movie movie: movies) 
		{
		   BindValidator errors = new BindValidator();
		   if(bindingResult.hasErrors() || (movie == null))
		   {
			   errors.addAllErrors(bindingResult);
			   headers.add("errors", errors.toJSON());
			   return new ResponseEntity<List<Movie>>(headers, HttpStatus.BAD_REQUEST);
		    }
		  this.moviesCatalogeService.saveMovie(movie);
		  headers.setLocation(ucBuilder.path("/api/movies/{id}").buildAndExpand(movie.getId()).toUri());
		  
		}
		System.out.println(new ResponseEntity<List<Movie>>(movies, headers, HttpStatus.CREATED));
		return new ResponseEntity<List<Movie>>(movies, headers, HttpStatus.CREATED);
	}
	@RequestMapping(value = "/{Id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Movie> updateMovie(@PathVariable("Id") int Id, @RequestBody Movie movie, BindingResult bindingResult){
		BindValidator errors = new BindValidator();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (movie == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Movie>(headers, HttpStatus.BAD_REQUEST);
		}
		Movie currentMovie = this.moviesCatalogeService.getMovieById(Id);
		if(currentMovie == null){
			return new ResponseEntity<Movie>(HttpStatus.NOT_FOUND);
		}
		currentMovie.setActors(movie.getActors());
		currentMovie.setDirectors(movie.getDirectors());
		currentMovie.setGenre(movie.getGenre());
		currentMovie.setId(movie.getId());
		currentMovie.setTitel(movie.getTitel());
		currentMovie.setRating(movie.getRating());
		currentMovie.setMovie_year(movie.getMovie_year());
		
		
		this.moviesCatalogeService.saveMovie(currentMovie);
		return new ResponseEntity<Movie>(currentMovie, HttpStatus.NO_CONTENT);
	}
	@RequestMapping(value = "/{Id}", method = RequestMethod.DELETE, produces = "application/json")
	@Transactional
	public ResponseEntity<Void> deleteMovie(@PathVariable("Id") int Id){
		Movie movie = this.moviesCatalogeService.getMovieById(Id);
		if(movie == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.moviesCatalogeService.deleteMovie(movie);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
