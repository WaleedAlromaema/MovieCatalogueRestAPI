package com.alromaema.projects.moviecatalogsystem.service;

import java.util.Collection;
import org.springframework.dao.DataAccessException;

import com.alromaema.projects.moviecatalogsystem.domain.Director;
/**
 *  Director Service Interface  
 *
 * @author Waleed Alromaema
 */
public interface DirectorService {
	
	    Collection<Director> getDirectors() throws DataAccessException;
	    
		Director getDirectorById(Integer id) throws DataAccessException;
		
		
		void saveDirector(Director actor) throws DataAccessException;
		
		void deleteDirector(Director actor) throws DataAccessException;
		

}
