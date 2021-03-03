package com.alromaema.projects.moviecatalogsystem.controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alromaema.projects.moviecatalogsystem.domain.Actor;
import com.alromaema.projects.moviecatalogsystem.service.ActorService;

/**
 *  Actor Rest Controller That handle HTTP Requests related to actor path [api/actors]
 *
 * @author Waleed Alromaema
 */

@RestController
@RequestMapping("api/actors")
@CrossOrigin(exposedHeaders = "errors, content-type")
public class ActorRestController {

	@Autowired
	private ActorService actorService;
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<Actor>> getAllActors(){
		Collection<Actor> actors = new ArrayList<Actor>();
		actors.addAll(this.actorService.getActors());
		if (actors.isEmpty()){
			return new ResponseEntity<Collection<Actor>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Actor>>(actors, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/{Id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Actor> getActor(@PathVariable("Id") int Id){
		Actor actor = this.actorService.getActorById(Id);
		if(actor == null){
			return new ResponseEntity<Actor>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Actor>(actor, HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST,consumes=MediaType.ALL_VALUE, produces = "application/json")
	public ResponseEntity<Actor> addActor(@RequestBody Actor actor){
		System.out.println("Hiiiiiiiii IM Here");
		this.actorService.saveActor(actor);
		return new ResponseEntity<Actor>(actor, HttpStatus.CREATED);
	}
	/**
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "", method = RequestMethod.POST, consumes=MediaType.ALL_VALUE,produces = "application/json")
	public ResponseEntity<Actor> addActor(@RequestBody Actor actor, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (actor == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Actor>(headers, HttpStatus.BAD_REQUEST);
		}
		this.actorService.saveActor(actor);
		headers.setLocation(ucBuilder.path("/api/actors/{id}").buildAndExpand(actor.getId()).toUri());
		return new ResponseEntity<Actor>(actor, headers, HttpStatus.CREATED);
	}
	*/
	
	@RequestMapping(value = "/{Id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Actor> updateActor(@PathVariable("Id") int Id, @RequestBody Actor actor, BindingResult bindingResult){
		BindValidator errors = new BindValidator();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (actor == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Actor>(headers, HttpStatus.BAD_REQUEST);
		}
		Actor currentActor = this.actorService.getActorById(Id);
		if(currentActor == null){
			return new ResponseEntity<Actor>(HttpStatus.NOT_FOUND);
		}
		currentActor.setBirthDate(actor.getBirthDate());
		currentActor.setFirstName(actor.getFirstName());
		currentActor.setLastName(actor.getLastName());
		currentActor.setId(actor.getId());
		currentActor.setGander(actor.getGander());
		currentActor.setNationality(actor.getNationality());
		currentActor.setStartYear(actor.getStartYear());
		
		
		this.actorService.saveActor(currentActor);
		return new ResponseEntity<Actor>(currentActor, HttpStatus.NO_CONTENT);
	}
	@RequestMapping(value = "/{Id}", method = RequestMethod.DELETE, produces = "application/json")
	@Transactional
	public ResponseEntity<Void> deleteActor(@PathVariable("Id") int Id){
		Actor actor = this.actorService.getActorById(Id);
		if(actor == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.actorService.deleteActor(actor);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
