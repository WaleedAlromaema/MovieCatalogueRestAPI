package com.alromaema.projects.moviecatalogsystem.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.alromaema.projects.moviecatalogsystem.domain.Actor;
import com.alromaema.projects.moviecatalogsystem.domain.Director;
import com.alromaema.projects.moviecatalogsystem.domain.Movie;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 *  Serializer Object from Movie class to Jackson Object 
 *
 * @author Waleed Alromaema
 */
public class MovieJsonSerializer extends StdSerializer<Movie> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MovieJsonSerializer() {
		this(null);
	}

	public MovieJsonSerializer(Class<Movie> t) {
		super(t);
	}

	@Override
	public void serialize(Movie movie, JsonGenerator json_generator, SerializerProvider provider) throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 
	    json_generator.writeStartObject("Movies");//Start object of Movies
		//ID
		if (movie.getId() == null) 
		  {
			json_generator.writeNullField("id");
			
		  } else {
			   json_generator.writeNumberField("id", movie.getId());
		      }
		
		//TITEL
		json_generator.writeStringField("TITEL", movie.getTitel());
		//MOVIE YEAR
		json_generator.writeStringField("MOVIE_YEAR",formatter.format( movie.getMovie_year()));
		//GENRE
		json_generator.writeStringField("GENRE", movie.getGenre());
		//RATING
		json_generator.writeNumberField("RATING", movie.getRating());
		if(movie.getActors()!=null)
		{
		// write actors array
		json_generator.writeArrayFieldStart("Actors");
		for (Actor actor : movie.getActors())
		{   if(actor==null)
			  break;
			json_generator.writeStartObject(); // actor
			if (actor.getId() == null) {
				json_generator.writeNullField("id");
			} else {
				json_generator.writeNumberField("id", actor.getId());
			}
			json_generator.writeStringField("FIRST_NAME", actor.getFirstName());
			json_generator.writeStringField("LAST_NAME", actor.getLastName());
			json_generator.writeNumberField("NUMBER_MOVIES", actor.getNumber_movies());
			json_generator.writeStringField("NATIONALITY", actor.getNationality());
			json_generator.writeStringField("START_YEAR", formatter.format(actor.getBirthDate()));
			json_generator.writeStringField("BIRTH_DATE", formatter.format(actor.getStartYear()));
			
			json_generator.writeEndObject();//end of Actor json object
		}
	    json_generator.writeEndArray(); // actors end of array
		}
		//if(movie.getDirectors()!=null)
		//{
	      // write directors array
			json_generator.writeArrayFieldStart("Directors");
			for (Director director : movie.getDirectors())
			{ 
				if(director==null)
				break;
				
				json_generator.writeStartObject(); // actor
				if (director.getId() == null) {
					json_generator.writeNullField("id");
				} else {
					json_generator.writeNumberField("id", director.getId());
				}
				json_generator.writeStringField("FIRST_NAME", director.getFirstName());
				json_generator.writeStringField("LAST_NAME", director.getLastName());
				json_generator.writeNumberField("NUMBER_DIRECTED_MOVIES", director.getNumber_directed_movies());
				json_generator.writeStringField("NATIONALITY", director.getNationality());
				json_generator.writeStringField("START_YEAR", formatter.format(director.getBirthDate()));
				json_generator.writeStringField("BIRTH_DATE", formatter.format(director.getStartYear()));
				
				json_generator.writeEndObject();//end of Director Array
			}
		   json_generator.writeEndArray(); // end of directors object 
		//}
	   json_generator.writeEndObject(); // End of movies json field
	   
	}

}
