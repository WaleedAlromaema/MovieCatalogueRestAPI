package com.alromaema.projects.moviecatalogsystem.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alromaema.projects.moviecatalogsystem.domain.Actor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 *  Deserializer Object from Jackson Object to Actor class
 *
 * @author Waleed Alromaema
 */
public class ActorJsonDeserializer extends StdDeserializer<Actor> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActorJsonDeserializer(){
		this(null);
	}

	public ActorJsonDeserializer(Class<Actor> a) {
		super(a);
	}

	@Override
	public Actor deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		Actor actor = new Actor();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Date birthDate = null;
		Date startYear=null;
		String firstName = node.get("firstName").asText(null);
		String lastName = node.get("lastName").asText(null);
		String nationality = node.get("nationality").asText(null);
		String gander = node.get("gander").asText(null);
		Integer numMovies = node.get("numMovies").asInt();
		String startYearStr=node.get("startYear").asText(null);
		try {
			 startYear = formatter.parse(startYearStr);
		    } catch (ParseException e)
		                    {
			         e.printStackTrace();
			         throw new IOException(e);
		                    }
		
		String bd = node.get("birthDate").asText(null);
		try {
			 birthDate = formatter.parse(bd);
		    } catch (ParseException e) {
			              e.printStackTrace();
			              throw new IOException(e);
		                               }
		if (node.hasNonNull("id")) 
		{
			actor.setId(node.get("id").asInt());
		}
        actor.setFirstName(firstName);
        actor.setLastName(lastName);
        actor.setNationality(nationality);
        actor.setGander(gander);
        actor.setNumber_movies(numMovies);
        actor.setStartYear(startYear);
        actor.setBirthDate(birthDate);
		return actor;
	}

}
