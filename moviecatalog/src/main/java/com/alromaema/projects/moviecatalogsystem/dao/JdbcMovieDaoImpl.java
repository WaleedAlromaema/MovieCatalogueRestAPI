package com.alromaema.projects.moviecatalogsystem.dao;

import java.util.Collection;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alromaema.projects.moviecatalogsystem.domain.Actor;
import com.alromaema.projects.moviecatalogsystem.domain.Director;
import com.alromaema.projects.moviecatalogsystem.domain.Movie;
import com.alromaema.projects.moviecatalogsystem.service.ActorService;
import com.alromaema.projects.moviecatalogsystem.service.DirectorService;
/**
 *  Movie Data Access Object implementation to JDBC  
 *
 * @author Waleed Alromaema
 */
@Repository
@Profile("jdbc")
public class JdbcMovieDaoImpl implements JdbcMovieDao{
	
	    private JdbcTemplate jdbcTemplate;
		private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
		private SimpleJdbcInsert insertMovie;
		private JdbcActorDao jdbcActorDao;
		private JdbcDirectorDao jdbcDirectorDao;
		private JdbcDirectorMovieDao jdbcDirectorMovieDao;
		private JdbcActorMovieDao jdbcActorMovieDao;
		
		@Autowired
	    public JdbcMovieDaoImpl(DataSource dataSource, JdbcTemplate jdbcTemplate) {
			
	        this.jdbcTemplate = jdbcTemplate;
			this.insertMovie = new SimpleJdbcInsert(dataSource).withTableName("Movie").usingGeneratedKeyColumns("id");
			this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			this.jdbcActorDao=new JdbcActorDaoImpl(dataSource);
			this.jdbcDirectorDao=new JdbcDirectorDaoImpl(dataSource);
			this.jdbcDirectorMovieDao=new JdbcDirectorMovieDaoImpl(dataSource,this.jdbcDirectorDao);
			this.jdbcActorMovieDao=new JdbcActorMovieDaoImpl(dataSource,this.jdbcActorDao);
			
	    }

	    
		public Collection<Movie> findAll1() throws DataAccessException {
			List<Movie> movies = this.namedParameterJdbcTemplate.query(
		            "SELECT * FROM Movie",
		            new HashMap<String, Object>(),
		            BeanPropertyRowMapper.newInstance(Movie.class));
			System.out.println("Movies from database is>>>");
	        System.out.println(movies);
			return movies;
		}
	    @Override
	    public Collection<Movie> findAll() throws DataAccessException {
			System.out.println("I am In FIND ALL METHOD");
	        List<Movie> movies = new ArrayList<>();
	        
	        movies.addAll(this.jdbcTemplate.query(
	                                              "SELECT * FROM Movie ",
	                                                BeanPropertyRowMapper.newInstance(Movie.class)
	                                             )
	        		      );
	        System.out.println("Movies from database is>>>");
	        System.out.println(movies);
	        
	        

	        final List<Actor> actors = this.jdbcTemplate.query(
	                                                          "SELECT * FROM Actor",
	                                                          BeanPropertyRowMapper.newInstance(Actor.class)
	                                                          );

	        // Add list of Actors for each Movie in the Movies List.
	        for (Movie movie : movies) 
	        {
	            final List<Integer> movieActorIds = this.jdbcTemplate.query(
	                  "SELECT ACTOR_ID FROM Actor_Participate_Movie WHERE MOVIE_ID=?",
	                   new BeanPropertyRowMapper<Integer>() 
	                       {
	                        @Override
	                        public Integer mapRow(ResultSet rs, int row) throws SQLException 
	                           {
	                             return rs.getInt(1);
	                           }
	                       },
	                    movie.getId()
	                                                                       );
	            for (int actorId : movieActorIds) 
	            {
	            	
	            	for (Actor act : actors) 
	            	{
	                    if (act.getId() == actorId ) 
	                    {
	                       movie.addActor(act);
	                    }
	                
	               }
	            }
	        }
	     // Get All Actors.
	        final List<Director> directors = this.jdbcTemplate.query(
	                                               "SELECT * FROM Director",
	                                               BeanPropertyRowMapper.newInstance(Director.class)
	                                                                );
	       
	        
	        for (Movie movie : movies) 
	        {
	            final List<Integer> movieDirectorIds = this.jdbcTemplate.query
	            		(
	                               "SELECT DIRECTOR_ID FROM Director_Participate_Movie  WHERE MOVIE_ID=?"
	            		          , new BeanPropertyRowMapper<Integer>() 
	                                   {
	                                     @Override
	                                     public Integer mapRow(ResultSet rs, int row) throws SQLException 
	                                                 {
	                                                  return rs.getInt(1);
	                                                 }
	                                   }
	            		          , movie.getId()
	            		);
	            for (int directorId : movieDirectorIds) 
	            {
	            	
	            	for (Director direct : directors) 
	            	{
	                    if (direct.getId() == directorId ) 
	                    {
	                       movie.addDirector(direct);
	                    }
	                
	               }
	            }
	        }
	        System.out.println("Movies returned is<<<<");
	        
	        System.out.println(movies);
	        return movies;
	    }
	    
		@Override
		public Movie findById(Integer id) throws DataAccessException {
			Movie movie;
			try {
				Map<String, Object> movie_params = new HashMap<>();
				movie_params.put("id", id);
				movie = this.namedParameterJdbcTemplate.queryForObject(
						"SELECT * FROM Movie WHERE ID= :id",
						movie_params,
						BeanPropertyRowMapper.newInstance(Movie.class));

				final List<Actor> actors = this.namedParameterJdbcTemplate.query(
						"SELECT * FROM Actor", movie_params, BeanPropertyRowMapper.newInstance(Actor.class));

				final List<Integer> movieActorsIds = this.namedParameterJdbcTemplate.query(
						"SELECT ACTOR_ID FROM Actor_Participate_Movie WHERE MOVIE_ID=:id",
		                movie_params,
						new BeanPropertyRowMapper<Integer>() {
							@Override
							public Integer mapRow(ResultSet rs, int row) throws SQLException {
								return rs.getInt(1);
							}
						});
				for (int actorId : movieActorsIds) 
	            {
	            	
	            	for (Actor act : actors) 
	            	{
	                    if (act.getId() == actorId ) 
	                    {
	                       movie.addActor(act);
	                    }
	                
	               }
	            }
				
				final List<Director> directors = this.namedParameterJdbcTemplate.query(
						"SELECT * FROM Director", movie_params, BeanPropertyRowMapper.newInstance(Director.class));

				final List<Integer> movieDirectorsIds = this.namedParameterJdbcTemplate.query(
						"SELECT DIRECTOR_ID FROM Director_Participate_Movie WHERE MOVIE_ID=:id",
		                movie_params,
						new BeanPropertyRowMapper<Integer>() {
							@Override
							public Integer mapRow(ResultSet rs, int row) throws SQLException {
								return rs.getInt(1);
							}
						});
				for (int directorId : movieDirectorsIds) 
	            {
	            	
	            	for (Director direct : directors) 
	            	{
	                    if (direct.getId() == directorId ) 
	                    {
	                       movie.addDirector(direct);
	                    }
	                
	               }
	            }
				
			   
		    
			}catch (EmptyResultDataAccessException ex) {
					throw new ObjectRetrievalFailureException(Movie.class, id);
				}
				return movie;
		}
		@Override
	    public void save(Movie movie) throws DataAccessException {
			this.save1(movie);
			this.jdbcActorDao.save(movie.getActors());
			this.jdbcDirectorDao.save(movie.getDirectors());
			this.jdbcActorMovieDao.save(movie);
			this.jdbcDirectorMovieDao.save(movie);
	                 
	    }
		//@Override
		@Transactional
		public void save1(Movie movie) throws DataAccessException {
			BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(movie);
			
			if (movie.isNew()) {
				Number newKey = this.insertMovie.executeAndReturnKey(parameterSource);
				movie.setId(newKey.intValue());
			} else {
				    this.namedParameterJdbcTemplate.update("UPDATE Movie SET TITEL=:titel, MOVIE_YEAR=:movie_year ,GENRE=:genre,RATING=:rating WHERE ID=:id", parameterSource);
			       }
			
		}
		@Override
		public void save(Set<Movie> movies) throws DataAccessException {
			for(Movie movie : movies)
			{   
				BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(movie);
				System.out.println("save  movie --");
				if (movie.isNew()) {
					System.out.println("is new movie---");
					Number newKey = this.insertMovie.executeAndReturnKey(parameterSource);
					System.out.println("is new movie with key="+newKey);
					movie.setId(newKey.intValue());
					// updateMovieActors(movie);
				     //updateMovieDirectors(movie);
				} else {
					System.out.println("is not new movie ---");
					    this.namedParameterJdbcTemplate.update("UPDATE Movie SET TITEL=:titel, MOVIE_YEAR=:year ,GENRE=:genre,RATING=:rating WHERE ID=:id", parameterSource);
			           // updateMovieActors(movie);
				        //updateMovieDirectors(movie);
			           }
				}
			}

		@Override
		public void delete(Movie movie) throws DataAccessException {
			Map<String, Object> params = new HashMap<>();
			params.put("id", movie.getId());
			this.namedParameterJdbcTemplate.update("DELETE FROM Director_Participate_Movie WHERE MOVIE_ID=:id", params);
			this.namedParameterJdbcTemplate.update("DELETE FROM Actor_Participate_Movie WHERE MOVIE_ID=:id", params);			
			this.namedParameterJdbcTemplate.update("DELETE FROM Movie WHERE ID=:id", params);
		}
		@Override
		public void delete(Set<Movie> movies) throws DataAccessException 
		{
			for(Movie movie: movies) 
			{
			Map<String, Object> params = new HashMap<>();
			params.put("id", movie.getId());
			this.namedParameterJdbcTemplate.update("DELETE FROM Director_Participate_Movie WHERE MOVIE_ID=:id", params);
			this.namedParameterJdbcTemplate.update("DELETE FROM Actor_Participate_Movie WHERE MOVIE_ID=:id", params);			
			this.namedParameterJdbcTemplate.update("DELETE FROM Movie WHERE ID=:id", params);
		   }
		}
		/**
		private void updateMovieActors(Movie movie) throws DataAccessException {
			Map<String, Object> params = new HashMap<>();
			Map<String, Object> params_actor = new HashMap<>();
			
			if(movie.getActors()!=null)
			{
			params.put("id", movie.getId());
			this.namedParameterJdbcTemplate.update("DELETE FROM Actor_Participate_Movie WHERE MOVIE_ID=:id", params);
			for (Actor act : movie.getActors()) 
			{
				if(act!=null)
				{
				params_actor.put("id", act.getId());
				
				final List<Integer> ActorsIds = this.namedParameterJdbcTemplate.query(
						"SELECT ID FROM Actor WHERE ID=:id",
						params_actor,
						new BeanPropertyRowMapper<Integer>() {
							@Override
							public Integer mapRow(ResultSet rs, int row) throws SQLException {
								return rs.getInt(1);
							}
						});
				if(ActorsIds==null)
				{ 
					actorService.saveActor(act);
					
				}
					
				params.put("actor_id", act.getId());
				if(!(act.getId() == null)) {
					this.namedParameterJdbcTemplate.update("INSERT INTO Actor_Participate_Movie VALUES (:actor_id, :id )", params);
				                           }
			    }
			
			}
			}
		}
		private void updateMovieDirectors(Movie movie) throws DataAccessException {
			Map<String, Object> params = new HashMap<>();
			Map<String, Object> params_dir = new HashMap<>();
			
			if(movie.getDirectors()!=null)
			{
			params.put("id", movie.getId());
			this.namedParameterJdbcTemplate.update("DELETE FROM Director_Participate_Movie WHERE MOVIE_ID=:id", params);
			for (Director direct : movie.getDirectors()) {
				if(direct!=null)
				{
					params_dir.put("id", direct.getId());
					
					if(directorService.getDirectorById(direct.getId())==null)
					{
						directorService.saveDirector(direct);
					}
				params.put("director_id", direct.getId());
				if(!(direct.getId() == null)) {
					this.namedParameterJdbcTemplate.update("INSERT INTO Director_Participate_Movie VALUES (:director_id, :id)", params);
				}
				}
			}
			}
		}
		*/
		@Override
		public Collection<Movie> findAllByActor(Actor actor) throws DataAccessException
		{
		List<Movie> movies=new ArrayList<>();
		Map<String, Object> params = new HashMap<>();
		params.put("id", actor.getId());
		params.put("firstname", actor.getFirstName());
		params.put("lastname", actor.getLastName());
		if(actor.getId()==null)
		{
			final List<Integer> actId = this.namedParameterJdbcTemplate.query(
					"SELECT ID FROM Actor WHERE FIRST_NAME=:firstname" ,
	                params,
					new BeanPropertyRowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int row) throws SQLException {
							return rs.getInt(1);
						}
					});
			actor.setId(actId.get(0));
			params.put("id", actor.getId());
			
		}
		
		final List<Integer> movieActorsIds = this.namedParameterJdbcTemplate.query(
				"SELECT MOVIE_ID FROM Actor_Participate_Movie WHERE ACTOR_ID=:id",
                params,
				new BeanPropertyRowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int row) throws SQLException {
						return rs.getInt(1);
					}
				});
		 List<Movie> movs = new ArrayList<>();
	        // Retrieve the list of all Movies.
	        movs.addAll(this.jdbcTemplate.query(
	            "SELECT * FROM Movie ",
	            BeanPropertyRowMapper.newInstance(Movie.class)));
	        
		for (int movieId : movieActorsIds) 
        {
        	
        	for (Movie m : movs) 
        	{
                if (m.getId() == movieId ) 
                {
                   movies.add(this.findById(m.getId()));
                }
            
           }
        }
		
		return movies;
		
		}
		@Override	
		public Collection<Movie> findAllByDirector(Director director) throws DataAccessException
		{
		List<Movie> movies=new ArrayList<>();
		Map<String, Object> params = new HashMap<>();
		params.put("id", director.getId());
		params.put("firstname", director.getFirstName());
		params.put("lastname", director.getLastName());
		if(director.getId()==null)
		{
			final List<Integer> dirId = this.namedParameterJdbcTemplate.query(
					"SELECT ID FROM Director WHERE FIRST_NAME=:firstname" ,
	                params,
					new BeanPropertyRowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int row) throws SQLException {
							return rs.getInt(1);
						}
					});
			director.setId(dirId.get(0));
			params.put("id", director.getId());
			
		}
		
		final List<Integer> movieDirectorsIds = this.namedParameterJdbcTemplate.query(
				"SELECT MOVIE_ID FROM Director_Participate_Movie WHERE DIRECTOR_ID=:id",
                params,
				new BeanPropertyRowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int row) throws SQLException {
						return rs.getInt(1);
					}
				});
		 List<Movie> movs = new ArrayList<>();
	        // Retrieve the list of all Movies.
	        movs.addAll(this.jdbcTemplate.query(
	            "SELECT * FROM Movie ",
	            BeanPropertyRowMapper.newInstance(Movie.class)));
	        
		for (int movieId : movieDirectorsIds) 
        {
        	
        	for (Movie m : movs) 
        	{
                if (m.getId() == movieId ) 
                {
                   movies.add(this.findById(m.getId()));
                }
            
           }
        }
		return movies;
		
		}
		@Override
		public Collection<Movie> findAllByGenre(String genre) throws DataAccessException
		{
			List<Movie> movs=new ArrayList<>();
			List<Movie> movies=new ArrayList<>();
			
			Map<String, Object> movie_params = new HashMap<>();
			movie_params.put("genre", genre);
			movs = this.namedParameterJdbcTemplate.query(
					"SELECT * FROM Movie WHERE GENRE= :genre",
					movie_params,
					BeanPropertyRowMapper.newInstance(Movie.class));
			for(Movie m :movs) 
			{
				movies.add(this.findById(m.getId()));
			}
			
			return movies;
		}
		   
}
