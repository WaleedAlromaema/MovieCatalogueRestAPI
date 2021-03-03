package com.alromaema.projects.moviecatalogsystem.controllers;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

import com.alromaema.projects.moviecatalogsystem.domain.Director;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
/**
 *  Serializer Object from Director class to Jackson Object  
 *
 * @author Waleed Alromaema
 */

public class DirectorJsonSerializer extends StdSerializer<Director> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DirectorJsonSerializer() {
		this(null);
	}

	public DirectorJsonSerializer(Class<Director> t) {
		super(t);
	}

	@Override
	public void serialize(Director director, JsonGenerator json_generator, SerializerProvider provider) throws IOException {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		json_generator.writeStartObject();// start write from Director object to Json node of director
		
		if (director.getId() == null) 
		   {
			  json_generator.writeNullField("id");
		   } else {
			  json_generator.writeNumberField("id", director.getId());
		          }
		json_generator.writeStringField("firstName", director.getFirstName());
		json_generator.writeStringField("lastName", director.getLastName());
		json_generator.writeStringField("nationality", director.getNationality());
		json_generator.writeStringField("gander", director.getGander());
		json_generator.writeNumberField("number_directed_movies", director.getNumber_directed_movies());
		json_generator.writeStringField("startYear", formatter.format(director.getStartYear()));
		json_generator.writeStringField("birthDate", formatter.format(director.getBirthDate()));
		
		json_generator.writeEndObject(); // End of Director Object field in Json file 
	}

}
