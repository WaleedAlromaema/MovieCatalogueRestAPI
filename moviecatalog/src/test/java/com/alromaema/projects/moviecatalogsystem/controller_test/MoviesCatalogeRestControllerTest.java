package com.alromaema.projects.moviecatalogsystem.controller_test;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.alromaema.projects.moviecatalogsystem.controllers.ExceptionControllerAdvice;
import com.alromaema.projects.moviecatalogsystem.controllers.MoviesCatalogeRestController;
import com.alromaema.projects.moviecatalogsystem.domain.Actor;
import com.alromaema.projects.moviecatalogsystem.domain.Director;
import com.alromaema.projects.moviecatalogsystem.domain.Movie;
import com.alromaema.projects.moviecatalogsystem.service.MoviesCatalogeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ApplicationTestConfig.class)
@WebAppConfiguration
public class MoviesCatalogeRestControllerTest {

    @Autowired
    private MoviesCatalogeRestController movieRestController;

    @MockBean
    private MoviesCatalogeService moviesCatalogeService;

    private MockMvc mockMvc;

    private List<Movie> movies;

    @Before
    public void initMovies(){
    	this.mockMvc = MockMvcBuilders.standaloneSetup(movieRestController)
    			.setControllerAdvice(new ExceptionControllerAdvice())
    			.build();
    	movies = new ArrayList<Movie>();
    	for(int i=0;i<3;i++)
    		movies.add(getMovies(i));

    	

            }
    Movie getMovies(int i)
    {    
        Actor actor=new Actor();
        Director director=new Director();
        Movie movie = new Movie();
        Set<Actor> actors=new HashSet<Actor>();
        Set<Director> directors=new HashSet<Director>();
        
        Date d=new Date();
        
        actor.setBirthDate(d);        
        actor.setFirstName("Waleed "+i);
        actor.setLastName("Alromaema "+i);
        actor.setGander("Male");
        actor.setId(i);
        actor.setNationality("Yemeni");
        actor.setNumber_movies(100+i);
        actor.setStartYear(d);
        
        actors.add(actor);
        
        actor.setBirthDate(d);        
        actor.setFirstName("Waleed "+i);
        actor.setLastName("Alromaema "+i);
        actor.setGander("Male");
        actor.setId(i);
        actor.setNationality("Yemeni");
        actor.setNumber_movies(100+i);
        actor.setStartYear(d);
        
        actors.add(actor);
        /////
        director.setBirthDate(d);        
        director.setFirstName("Waleed "+i);
        director.setLastName("Alromaema "+i);
        director.setGander("Male");
        director.setId(i);
        director.setNationality("Yemeni");
        director.setNumber_directed_movies(100+i);
        director.setStartYear(d);
        
        directors.add(director);
        
        director.setBirthDate(d);        
        director.setFirstName("Waleed "+i);
        director.setLastName("Alromaema "+i);
        director.setGander("Male");
        director.setId(i);
        director.setNationality("Yemeni");
        director.setNumber_directed_movies(100+i);
        director.setStartYear(d);
        
        directors.add(director);
        
        /////
        
        movie.setId(i);
        movie.setTitel("Star Ware "+i);
    	movie.setMovie_year(d);
    	
    	movie.setRating(5+i);
    	movie.setGenre("Advanture");
    	
    	movie.setActors(actors);
    	movie.setDirectors(directors);
    	
    	return movie;
    }

   
    @Test
    public void testGetMovieSuccess() throws Exception {
    	given(this.moviesCatalogeService.getMovieById(0)).willReturn(movies.get(0));
        this.mockMvc.perform(get("/api/movies/0")
        	.accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(0))
            .andExpect(jsonPath("$.titel").value("Star War 0"));
    }

    @Test
    public void testGetMovieNotFound() throws Exception {
    	given(this.moviesCatalogeService.getMovieById(-1)).willReturn(null);
        this.mockMvc.perform(get("/api/movies/-1")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testGetMoviesListSuccess() throws Exception {
    	given(this.moviesCatalogeService.getAllMoviesByGenre("Advanture")).willReturn(movies);
        this.mockMvc.perform(get("/api/movies/genre/Advanture")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.[0].id").value(0))
            .andExpect(jsonPath("$.[0].titel").value("Star War 0"))
            .andExpect(jsonPath("$.[1].id").value(1))
            .andExpect(jsonPath("$.[1].titel").value("Star War 1"));
    }

    @Test
    public void testGetMoviesListNotFound() throws Exception {
    	movies.clear();
    	Actor a=new Actor();
    	a.setId(10);
    	given(this.moviesCatalogeService.getAllMoviesByActor(a)).willReturn(movies);
        this.mockMvc.perform(get("/api/movies/actor/10")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllMoviesSuccess() throws Exception {
    	movies.remove(0);
    	movies.remove(1);
    	given(this.moviesCatalogeService.getMoviesCataloge()).willReturn(movies);
        this.mockMvc.perform(get("/api/movies")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.[0].id").value(2))
            .andExpect(jsonPath("$.[0].titel").value("Star War 2"));
           
    }

    @Test
    public void testGetAllMoviesNotFound() throws Exception {
    	movies.clear();
    	given(this.moviesCatalogeService.getMoviesCataloge()).willReturn(movies);
        this.mockMvc.perform(get("/api/movies")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateMovieSuccess() throws Exception {
    	Movie newMovie = movies.get(0);
    	newMovie.setId(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newMovieAsJSON = mapper.writeValueAsString(newMovie);
    	this.mockMvc.perform(post("/api/movies")
    		.content(newMovieAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
    		.andExpect(status().isCreated());
    }

    @Test
    public void testCreateMovieErrorIdSpecified() throws Exception {
        Movie newMovie = movies.get(0);
        newMovie.setId(999);
        ObjectMapper mapper = new ObjectMapper();
        String newMovieAsJSON = mapper.writeValueAsString(newMovie);
        this.mockMvc.perform(post("/api/movies")
            .content(newMovieAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andExpect(header().string("errors",
                "[{\"objectName\":\"body\",\"fieldName\":\"id\",\"fieldValue\":\"999\",\"errorMessage\":\"must not be specified\"}]"));
    }

    @Test
    public void testCreateMovieError() throws Exception {
    	Movie newMovie = movies.get(0);
    	newMovie.setId(null);
    	newMovie.setTitel(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newMovieAsJSON = mapper.writeValueAsString(newMovie);
    	this.mockMvc.perform(post("/api/movies")
        		.content(newMovieAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        		.andExpect(status().isBadRequest());
     }

    @Test
    public void testUpdateMovieSuccess() throws Exception {
        given(this.moviesCatalogeService.getMovieById(0)).willReturn(movies.get(0));
        int movieId = movies.get(0).getId();
        Movie updatedMovie = new Movie();
        
        updatedMovie.setId(movieId);
        updatedMovie.setTitel("Titanic");
        updatedMovie.setGenre("Romantic");
        updatedMovie.setRating(8);
        updatedMovie.setMovie_year(new Date());
        ObjectMapper mapper = new ObjectMapper();
        String newMovieAsJSON = mapper.writeValueAsString(updatedMovie);
        this.mockMvc.perform(put("/api/movies/" + movieId)
            .content(newMovieAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().contentType("application/json"))
            .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/api/movies/" + movieId)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(movieId))
            .andExpect(jsonPath("$.titel").value("Titanic"));

    }
    @Test
    public void testUpdateMovieSuccessNoBodyId() throws Exception {
    	given(this.moviesCatalogeService.getMovieById(0)).willReturn(movies.get(0));
        int movieId = movies.get(0).getId();
        Movie updatedMovie = new Movie();
        updatedMovie.setId(movieId);
        updatedMovie.setTitel("Titanic");
        updatedMovie.setGenre("Romantic");
        updatedMovie.setRating(8);
        updatedMovie.setMovie_year(new Date());
       ObjectMapper mapper = new ObjectMapper();
    	String newMovieAsJSON = mapper.writeValueAsString(updatedMovie);
    	this.mockMvc.perform(put("/api/movies/" + movieId)
    		.content(newMovieAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(content().contentType("application/json"))
        	.andExpect(status().isNoContent());

    	this.mockMvc.perform(get("/api/movies/" + movieId)
           	.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(movieId))
            .andExpect(jsonPath("$.titel").value("Titanic"));

    }

    @Test
    public void testUpdateMovieErrorBodyIdMismatchWithPathId() throws Exception {
        int movieId = movies.get(0).getId();
        Movie updatedMovie = new Movie();
        // body.id != ownerId
        updatedMovie.setId(-1);
        updatedMovie.setId(movieId);
        updatedMovie.setTitel("Titanic");
        updatedMovie.setGenre("Romantic");
        updatedMovie.setRating(8);
        updatedMovie.setMovie_year(new Date());
       ObjectMapper mapper = new ObjectMapper();
        String newMovieAsJSON = mapper.writeValueAsString(updatedMovie);
        this.mockMvc.perform(put("/api/movies/" + movieId)
            .content(newMovieAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andExpect(header().string("errors",
                "[{\"objectName\":\"body\",\"fieldName\":\"id\",\"fieldValue\":\"-1\",\"errorMessage\":\"does not match pathId: 1\"}]"));
    }

    @Test
    public void testUpdateMovieError() throws Exception {
    	Movie newMovie = movies.get(0);
    	newMovie.setTitel("");
    	ObjectMapper mapper = new ObjectMapper();
    	String newMovieAsJSON = mapper.writeValueAsString(newMovie);
    	this.mockMvc.perform(put("/api/movies/0")
    		.content(newMovieAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isBadRequest());
     }

    @Test
    public void testDeleteMovieSuccess() throws Exception {
    	Movie newMovie = movies.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newMovieAsJSON = mapper.writeValueAsString(newMovie);
    	given(this.moviesCatalogeService.getMovieById(0)).willReturn(movies.get(0));
    	this.mockMvc.perform(delete("/api/movies/0")
    		.content(newMovieAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteMovieError() throws Exception {
    	Movie newMovie = movies.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newMovieAsJSON = mapper.writeValueAsString(newMovie);
    	given(this.moviesCatalogeService.getMovieById(-1)).willReturn(null);
    	this.mockMvc.perform(delete("/api/movies/-1")
    		.content(newMovieAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isNotFound());
    }

}
