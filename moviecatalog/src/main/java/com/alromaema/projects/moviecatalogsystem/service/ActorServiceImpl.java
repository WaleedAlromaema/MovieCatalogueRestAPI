package com.alromaema.projects.moviecatalogsystem.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alromaema.projects.moviecatalogsystem.dao.JdbcActorDao;
import com.alromaema.projects.moviecatalogsystem.domain.Actor;
/**
 *  Actor Service Implementation  
 *
 * @author Waleed Alromaema
 */
@Service
public class ActorServiceImpl implements ActorService{
private JdbcActorDao jdbcActorDaoInterface;
	
	@Autowired
	public ActorServiceImpl(JdbcActorDao jdbcActorDaoInterface)
	{
		this.jdbcActorDaoInterface=jdbcActorDaoInterface;
	}
	@Override
	@Transactional(readOnly = true)
	public Collection<Actor> getActors() throws DataAccessException
	  {
		  return jdbcActorDaoInterface.findAll();	
	  }
	@Override
	@Transactional(readOnly = true)
	public Actor getActorById(Integer id) throws DataAccessException
	  {
		return jdbcActorDaoInterface.findById(id);
	  }
	
	
	@Override
	@Transactional
	public void saveActor(Actor actor) throws DataAccessException
	{
		jdbcActorDaoInterface.save(actor);
	}
	
	
	@Override
	@Transactional
	public void deleteActor(Actor actor) throws DataAccessException
	{
		
			jdbcActorDaoInterface.delete(actor);
		
	}
	
	}
