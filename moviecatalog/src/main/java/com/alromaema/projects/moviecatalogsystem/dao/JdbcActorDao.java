package com.alromaema.projects.moviecatalogsystem.dao;

import java.util.Collection;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import com.alromaema.projects.moviecatalogsystem.domain.Actor;

/**
 *  Actor Data Access Object Interface to JDBC  
 *
 * @author Waleed Alromaema
 */

public interface JdbcActorDao {
	
    Collection<Actor> findByLastName(String lastName) throws DataAccessException;

    Actor findById(int id) throws DataAccessException;

    void save(Actor actor) throws DataAccessException;
    void save(Set<Actor> actors) throws DataAccessException;
    
    Collection<Actor> findAll() throws DataAccessException;
	
    void delete(Actor actor) throws DataAccessException;


}

                              
