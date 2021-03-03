package com.alromaema.projects.moviecatalogsystem.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alromaema.projects.moviecatalogsystem.dao.JdbcDirectorDao;
import com.alromaema.projects.moviecatalogsystem.domain.Director;
/**
 *  Director Service Implementation  
 *
 * @author Waleed Alromaema
 */
@Service
public class DirectorServiceImpl implements DirectorService{
private JdbcDirectorDao jdbcDirectorDaoInterface;
	
	@Autowired
	public DirectorServiceImpl(JdbcDirectorDao jdbcDirectorDaoInterface)
	{
		this.jdbcDirectorDaoInterface=jdbcDirectorDaoInterface;
	}
	@Override
	@Transactional(readOnly = true)
	public Collection<Director> getDirectors() throws DataAccessException
	  {
		  return jdbcDirectorDaoInterface.findAll();	
	  }
	@Override
	@Transactional(readOnly = true)
	public Director getDirectorById(Integer id) throws DataAccessException
	  {
		return jdbcDirectorDaoInterface.findById(id);
	  }
	
	
	@Override
	@Transactional
	public void saveDirector(Director director) throws DataAccessException
	{
		jdbcDirectorDaoInterface.save(director);
	}
	
	
	@Override
	@Transactional
	public void deleteDirector(Director director) throws DataAccessException
	{
		
			jdbcDirectorDaoInterface.delete(director);
		
	}
	
	}
