package com.alromaema.projects.moviecatalogsystem.domain;

import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinColumn;
import org.springframework.format.annotation.DateTimeFormat;
import com.alromaema.projects.moviecatalogsystem.controllers.ActorJsonSerializer;
import com.alromaema.projects.moviecatalogsystem.controllers.ActorJsonDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 *  Actor Business Domain Object  
 *
 * @author Waleed Alromaema
 */
@Entity
@Table(name = "Actor")
@JsonSerialize(using = ActorJsonSerializer.class)
@JsonDeserialize(using =ActorJsonDeserializer.class)
public class Actor {
	@Id
	@GeneratedValue
    @Column(name = "ID")
	private Integer id;
	
	@Column(name = "FIRST_NAME")
    //@NotEmpty
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "NUMBER_MOVIES")
	private int number_movies;
	
	public int getNumber_movies() {
		return number_movies;
	}
	public void setNumber_movies(int number_movies) {
		this.number_movies = number_movies;
	}
	@Column(name = "START_YEAR")
	@Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startYear;
	
	@Column(name = "BIRTH_DATE")
	@Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")   
	private Date birthDate;
	
	@Column(name = "NATIONALITY")
	private String nationality;
	
	@Column(name = "GANDER")
	private String gander;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "Actor_Participate_Movie",joinColumns = @JoinColumn(name = "ACTOR_ID",referencedColumnName="ID"),
	inverseJoinColumns = @JoinColumn(name = "MOVIE_ID",referencedColumnName="ID"))
	private Set<Movie> movies;
	
	
	@Override
	public String toString() {
		return "Actor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", numMovies=" + number_movies
				+ ", startYear=" + startYear + ", birthDate=" + birthDate + ", nationality=" + nationality + ", gander="
				+ gander + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Date getStartYear() {
		return startYear;
	}
	public void setStartYear(Date startYear) {
		this.startYear = startYear;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getGander() {
		return gander;
	}
	public void setGander(String gander) {
		this.gander = gander;
	}
	public Set<Movie> getMovies() {
		return movies;
	}
	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}
	@JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }

}
