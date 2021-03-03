package com.alromaema.projects.moviecatalogsystem.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alromaema.projects.moviecatalogsystem.domain.Director;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
/**
 *  Deserializer Object from Jackson Object to Director class
 *
 * @author Waleed Alromaema
 */

public class DirectorJsonDeserializer extends StdDeserializer<Director>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DirectorJsonDeserializer(){
		this(null);
	}

	public DirectorJsonDeserializer(Class<Director> t) {
		super(t);
	}

	@Override
	public Director deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);// node that read data of Json file as a tree of node and fields
		Director director = new Director();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Date birthDate = null;
		Date startYear=null;
		String firstName = node.get("firstName").asText(null);
		String lastName = node.get("lastName").asText(null);
		String nationality = node.get("nationality").asText(null);
		String gander = node.get("gander").asText(null);
		Integer numMovies = node.get("number_directed_movies").asInt();
		String startYearStr=node.get("startYear").asText(null);
		try {
			startYear = formatter.parse(startYearStr);
		    } catch (ParseException e) {
			    e.printStackTrace();
			    throw new IOException(e);
		                               }
		
		String birthDateStr = node.get("birthDate").asText(null);
		try {
			  birthDate = formatter.parse(birthDateStr);
		    } catch (ParseException e) {
			    e.printStackTrace();
			    throw new IOException(e);
		                               }
		if (node.hasNonNull("id")) 
		   {
			 director.setId(node.get("id").asInt());
		   }
        director.setFirstName(firstName);
        director.setLastName(lastName);
        director.setNationality(nationality);
        director.setGander(gander);
        director.setNumber_directed_movies(numMovies);
        director.setStartYear(startYear);
        director.setBirthDate(birthDate);
        
		return director;
	}

}
