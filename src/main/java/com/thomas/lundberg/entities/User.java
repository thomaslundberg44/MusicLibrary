package com.thomas.lundberg.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Users")
public class User implements Serializable {
	
	// auto-generated serial id
	private static final long serialVersionUID = -2638163469020347999L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idUser") private int id;
	
	@Column(name="username") private String username;
	@Column(name="name") private String name;
	@Column(name="password") private String password;
	
	// relationship to library table through library persistent id
	@OneToMany(fetch=FetchType.LAZY, mappedBy="user", cascade=CascadeType.ALL)
	private Set<Library> libraries= new HashSet<Library>();
	
	public User() {}
	
	public User(String username, String name, String password, Set<Library> libraries) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.libraries = libraries;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonIgnore
	public Set<Library> getLibraries() {
		return libraries;
	}

	public void setLibraries(Set<Library> libraries) {
		this.libraries = libraries;
	}
}

