package com.alromaema.projects.moviecatalogsystem.dao;

import java.util.Collection;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.alromaema.projects.moviecatalogsystem.domain.Director;
/**
 *  Director Data Access Object Interface to JDBC  
 *
 * @author Waleed Alromaema
 */
public interface JdbcDirectorDao {
	
	 
    Collection<Director> findByLastName(String lastName) throws DataAccessException;

    Director findById(int id) throws DataAccessException;

    void save(Director Director) throws DataAccessException;
    void save(Set<Director> directors) throws DataAccessException;
    
	Collection<Director> findAll() throws DataAccessException;
	
	void delete(Director Director) throws DataAccessException;
}
