package com.alromaema.projects.moviecatalogsystem.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import com.alromaema.projects.moviecatalogsystem.controllers.MovieJsonDeserializer;
import com.alromaema.projects.moviecatalogsystem.controllers.MovieJsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 *  Movie Business Domain Object  
 *
 * @author Waleed Alromaema
 */
@Entity
@Table(name = "Movie")
@JsonSerialize(using = MovieJsonSerializer.class)
@JsonDeserialize(using =MovieJsonDeserializer.class)

public class Movie {
	@Id
	@GeneratedValue
    @Column(name = "ID")
	private Integer id;
	
	@Column(name = "TITEL")
	private String titel;
	
	@Column(name = "MOVIE_YEAR")
	@Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date movie_year;
	
	@Column(name = "GENRE")
	private String genre;
	
	@Column(name = "RATING")
	private Integer rating;
	
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "Director_Participate_Movie",joinColumns = @JoinColumn(name = "MOVIE_ID",referencedColumnName="ID"),
	inverseJoinColumns = @JoinColumn(name = "DIRECTOR_ID",referencedColumnName="ID"))	
	private Set<Director> directors;
	
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "Actor_Participate_Movie",joinColumns = @JoinColumn(name = "MOVIE_ID",referencedColumnName="ID"),
	inverseJoinColumns = @JoinColumn(name = "ACTOR_ID",referencedColumnName="ID"))
	private Set<Actor> actors;
	
	
	public  Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	@Override
	public String toString() {
		return "Movie [titel=" + titel + ", directors=" + directors + ", actors=" + actors + ", movie_year=" + movie_year
				+ ", genre=" + genre + ", rating=" + rating + ", id=" + id + "]";
	}
	
	
	public Set<Director> getDirectors() {
		if (this.directors == null) {
            this.directors = new HashSet<>();
        }
        return this.directors;
		
	}
	public void setDirectors(Set<Director> directors) {
		this.directors = directors;
	}
	
	public Set<Actor> getActors() {
		if (this.actors == null) {
            this.actors = new HashSet<>();
        }
        return this.actors;
		
	}
	public void setActors(Set<Actor> actors) {
		this.actors = actors;
	}
	 public void addActor(Actor actor) {
		 getActors().add(actor);
	    }
	 public void addDirector(Director director) {
		 getDirectors().add(director);
	    }
	 
	public String getGenre() {
		return genre;
	}
	public Date getMovie_year() {
		return movie_year;
	}
	public void setMovie_year(Date movie_year) {
		this.movie_year = movie_year;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
	@JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }

}
