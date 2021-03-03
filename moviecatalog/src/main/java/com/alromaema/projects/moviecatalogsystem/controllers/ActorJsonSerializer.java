package com.alromaema.projects.moviecatalogsystem.controllers;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

import com.alromaema.projects.moviecatalogsystem.domain.Actor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 *  Serializer Actor class to Jackson Object  
 *
 * @author Waleed Alromaema
 */

public class ActorJsonSerializer extends StdSerializer<Actor> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActorJsonSerializer() {
		this(null);
	}

	public ActorJsonSerializer(Class<Actor> t) {
		super(t);
	}

	@Override
	public void serialize(Actor actor, JsonGenerator json_generator, SerializerProvider provider) throws IOException {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		json_generator.writeStartObject();// Start of Actor to Jason writer 
		if (actor.getId() == null) {
			   json_generator.writeNullField("id");
		} else {
			   json_generator.writeNumberField("id", actor.getId());
		       }
		json_generator.writeStringField("firstName", actor.getFirstName());
		json_generator.writeStringField("lastName", actor.getLastName());
		json_generator.writeStringField("nationality", actor.getNationality());
		json_generator.writeStringField("gander", actor.getGander());
		json_generator.writeNumberField("numMovies", actor.getNumber_movies());
		json_generator.writeStringField("startYear", formatter.format(actor.getStartYear()));
		json_generator.writeStringField("birthDate", formatter.format(actor.getBirthDate()));
		
		json_generator.writeEndObject(); // Start of Actor to Jason writer 
	}

}
