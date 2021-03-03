package com.alromaema.projects.moviecatalogsystem.domain;

import java.util.Date;
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
import com.alromaema.projects.moviecatalogsystem.controllers.DirectorJsonSerializer;
import com.alromaema.projects.moviecatalogsystem.controllers.DirectorJsonDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *  Director Business Domain Object  
 *
 * @author Waleed Alromaema
 */
@Entity
@Table(name = "Director")
@JsonSerialize(using = DirectorJsonSerializer.class)
@JsonDeserialize(using =DirectorJsonDeserializer.class)
public class Director {
	@Id
	@GeneratedValue
    @Column(name = "ID")
	private Integer id;
	
	@Column(name = "FIRST_NAME")
    //@NotEmpty
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	
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
	
	@Column(name = "NUMBER_DIRECTED_MOVIES")
	private int number_directed_movies;
	
	public int getNumber_directed_movies() {
		return number_directed_movies;
	}
	public void setNumber_directed_movies(int number_directed_movies) {
		this.number_directed_movies = number_directed_movies;
	}

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "Director_Participate_Movie",joinColumns = @JoinColumn(name = "DIRECTOR_ID",referencedColumnName="ID"),
	inverseJoinColumns = @JoinColumn(name = "MOVIE_ID",referencedColumnName="ID"))	
	private Set<Movie> movies;
	
	@Override
	public String toString() {
		return "Director [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", numDirectedMovies=" + number_directed_movies
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
	@JsonIgnore
	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}

	@JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }


}
