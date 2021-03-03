package com.alromaema.projects.moviecatalogsystem.service;

import java.util.Collection;
import org.springframework.dao.DataAccessException;

import com.alromaema.projects.moviecatalogsystem.domain.Actor;
/**
 *  Actor Service Interface  
 *
 * @author Waleed Alromaema
 */
public interface ActorService {
	
	    Collection<Actor> getActors() throws DataAccessException;
	    
		Actor getActorById(Integer id) throws DataAccessException;
		
		
		void saveActor(Actor actor) throws DataAccessException;
		
		void deleteActor(Actor actor) throws DataAccessException;
		

}
