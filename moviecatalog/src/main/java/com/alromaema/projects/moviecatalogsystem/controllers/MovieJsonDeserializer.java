package com.alromaema.projects.moviecatalogsystem.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.text.ParseException;

import com.alromaema.projects.moviecatalogsystem.domain.Actor;
import com.alromaema.projects.moviecatalogsystem.domain.Director;
import com.alromaema.projects.moviecatalogsystem.domain.Movie;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
/**
 *  Deserializer Object from Jackson Object to Movie class
 *
 * @author Waleed Alromaema
 */

public class MovieJsonDeserializer extends StdDeserializer<Movie> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MovieJsonDeserializer(){
		this(null);
	}

	public MovieJsonDeserializer(Class<Movie> t) {
		super(t);
	}

	@Override
	public Movie deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		
		Movie movie = new Movie();
		Set<Actor> actors= new HashSet<>();
		Set<Director> directors= new HashSet<>();
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		JsonNode actor_node = node.get("Actors");
		JsonNode director_node = node.get("Directors");
		System.out.println("Actors node==: "+actor_node);
		System.out.println("Directors node==: "+director_node);
		
		if(actor_node!=null)
		{ System.out.println("actor_node.size() ="+actor_node.size());
		for(int i=0;i<actor_node.size();i++)
		{
		        Actor a =new Actor();
		        
		        if( (actor_node.get(i).get("id")!=null))
		        {
		        a.setId(actor_node.get(i).get("id").asInt()); 
		        System.out.println("actor_node.get(i).get(\"id\").asInt()="+actor_node.get(i).get("id").asInt());
		        }
		        if((actor_node.get(i).get("FIRST_NAME")!=null))
		        {
		        	 a.setFirstName(actor_node.get(i).get("FIRST_NAME").asText());
		        }
		        if( (actor_node.get(i).get("LAST_NAME")!=null))
		        {
		        	a.setLastName(actor_node.get(i).get("LAST_NAME").asText());
		        }
		        if((actor_node.get(i).get("NUMBER_MOVIES")!=null))
		        {
		        	a.setNumber_movies(actor_node.get(i).get("NUMBER_MOVIES").asInt());
		        }
		        if((actor_node.get(i).get("NATIONALITY")!=null))
		        {
		        	a.setNationality(actor_node.get(i).get("NATIONALITY").asText());	   
		        }
		        if((actor_node.get(i).get("GANDER")!=null))
		        {
		        	a.setGander(actor_node.get(i).get("GANDER").asText());
		        }
		        if((actor_node.get(i).get("START_YEAR")!=null))
		        {
		        	String sy=actor_node.get(i).get("START_YEAR").asText();
				try {
					 a.setStartYear(formatter.parse(sy));
				} catch (ParseException e) {
					e.printStackTrace();
					throw new IOException(e);
				}
		        }
		        if((actor_node.get(i).get("BIRTH_DATE")!=null))
		        {
		        	String bd=actor_node.get(i).get("BIRTH_DATE").asText();
				try {
					 a.setBirthDate(formatter.parse(bd));
				} catch (ParseException e) {
					e.printStackTrace();
					throw new IOException(e);
				}
		        }
				
				actors.add(a);
				 System.out.println("Actor["+i+"] is="+a);
			     	
		     		}
		} else {System.out.println("the actor node is null");}
		if(director_node!=null)
		{
		for(int i=0;i<director_node.size();i++)
		{
		        Director a =new Director();
		        if((director_node.get(i).get("id")!=null))
		        {
		        a.setId(director_node.get(i).get("id").asInt()); 
		        }
		        if( (director_node.get(i).get("FIRST_NAME")!=null))
		        {
		        	 a.setFirstName(director_node.get(i).get("FIRST_NAME").asText());
		        }
		        if((director_node.get(i).get("LAST_NAME")!=null))
		        {
		        	a.setLastName(director_node.get(i).get("LAST_NAME").asText());
		        }
		        if( (director_node.get(i).get("NUMBER_DIRECTED_MOVIES")!=null))
		        {
		        	a.setNumber_directed_movies(director_node.get(i).get("NUMBER_DIRECTED_MOVIES").asInt());
		        }
		        if( (director_node.get(i).get("NATIONALITY")!=null))
		        {
		        	a.setNationality(director_node.get(i).get("NATIONALITY").asText());	   
		        }
		        if( (director_node.get(i).get("GANDER")!=null))
		        {
		        	a.setGander(director_node.get(i).get("GANDER").asText());
		        }
		        if( (director_node.get(i).get("START_YEAR")!=null))
		        {
		        	String sy=director_node.get(i).get("START_YEAR").asText();
				try {
					 a.setStartYear(formatter.parse(sy));
				} catch (ParseException e) {
					e.printStackTrace();
					throw new IOException(e);
				}
		        }
		        if((director_node.get(i).get("BIRTH_DATE")!=null))
		        {
		        	String bd=director_node.get(i).get("BIRTH_DATE").asText();
				try {
					 a.setBirthDate(formatter.parse(bd));
				} catch (ParseException e) {
					e.printStackTrace();
					throw new IOException(e);
				}
		        }
		        
				directors.add(a);
		        System.out.println("Director["+i+"] is="+a);
		     		}
		
		}else {System.out.println("the director node is null");}
		String TITEL = node.get("TITEL").asText(null);
		Date MOVIE_YEAR=null;
		System.out.println("TITEL= "+TITEL);
		String startYearStr=node.get("MOVIE_YEAR").asText(null);
		try {
			MOVIE_YEAR = formatter.parse(startYearStr);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		System.out.println("MOVIE_YEAR"+MOVIE_YEAR);
		
		String GENRE = node.get("GENRE").asText(null);
		System.out.println("GENRE= "+GENRE);
		
		Integer RATING = node.get("RATING").asInt();
		System.out.println("RATING"+RATING);
		
		if (node.hasNonNull("id")) {
			//System.out.println("ID="+node.get("ID").asInt(null));
			movie.setId(node.get("id").asInt());
		}
		
		
		
		//System.out.println("the actor node is null");
        movie.setTitel(TITEL);
        movie.setGenre(GENRE);
        movie.setRating(RATING);
        movie.setMovie_year(MOVIE_YEAR);
        movie.setActors(actors);
        movie.setDirectors(directors);
        System.out.println("the movie returned from deserializing= "+movie);  
      return movie;
	}
	
}