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
import com.alromaema.projects.moviecatalogsystem.domain.Director;
import com.alromaema.projects.moviecatalogsystem.service.DirectorService;
/**
 *  Director Rest Controller That handle HTTP Requests related to actor path [api/directors]
 *
 * @author Waleed Alromaema
 */

@RestController
@RequestMapping("api/directors")
@CrossOrigin(exposedHeaders = "errors, content-type")
public class DirectorRestController {

	@Autowired
	private DirectorService directorService;
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<Director>> getAllDirectors(){
		Collection<Director> directors = new ArrayList<Director>();
		directors.addAll(this.directorService.getDirectors());
		if (directors.isEmpty()){
			return new ResponseEntity<Collection<Director>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Director>>(directors, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/{Id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Director> getDirector(@PathVariable("Id") int Id){
		Director director = this.directorService.getDirectorById(Id);
		if(director == null){
			return new ResponseEntity<Director>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Director>(director, HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST,consumes=MediaType.ALL_VALUE, produces = "application/json")
	public ResponseEntity<Director> addDirector(@RequestBody Director director){
		System.out.println("Hiiiiiiiii IM Here");
		this.directorService.saveDirector(director);
		return new ResponseEntity<Director>(director, HttpStatus.CREATED);
	}
	/**
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "", method = RequestMethod.POST, consumes=MediaType.ALL_VALUE,produces = "application/json")
	public ResponseEntity<Director> addDirector(@RequestBody Director director, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		BindValidator errors = new BindValidator();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (director == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Director>(headers, HttpStatus.BAD_REQUEST);
		}
		this.directorService.saveDirector(director);
		headers.setLocation(ucBuilder.path("/api/directors/{id}").buildAndExpand(director.getId()).toUri());
		return new ResponseEntity<Director>(director, headers, HttpStatus.CREATED);
	}
	*/
	
	@RequestMapping(value = "/{Id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Director> updateDirector(@PathVariable("Id") int Id, @RequestBody Director director, BindingResult bindingResult){
		BindValidator errors = new BindValidator();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (director == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Director>(headers, HttpStatus.BAD_REQUEST);
		}
		Director currentDirector = this.directorService.getDirectorById(Id);
		if(currentDirector == null){
			return new ResponseEntity<Director>(HttpStatus.NOT_FOUND);
		}
		currentDirector.setBirthDate(director.getBirthDate());
		currentDirector.setFirstName(director.getFirstName());
		currentDirector.setLastName(director.getLastName());
		currentDirector.setId(director.getId());
		currentDirector.setGander(director.getGander());
		currentDirector.setNationality(director.getNationality());
		currentDirector.setStartYear(director.getStartYear());
		
		
		this.directorService.saveDirector(currentDirector);
		return new ResponseEntity<Director>(currentDirector, HttpStatus.NO_CONTENT);
	}
	@RequestMapping(value = "/{Id}", method = RequestMethod.DELETE, produces = "application/json")
	@Transactional
	public ResponseEntity<Void> deleteDirector(@PathVariable("Id") int Id){
		Director director = this.directorService.getDirectorById(Id);
		if(director == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.directorService.deleteDirector(director);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
