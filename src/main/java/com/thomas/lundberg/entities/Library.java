package com.thomas.lundberg.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Libraries")
public class Library implements Serializable {
	
	// auto-generated serial id
	private static final long serialVersionUID = 7371754399923348579L;

	@Id 
	@Column(name="libraryPersistentId")
	private String libPersistentId;

	@Column(name="musicFolder")
	private String musicFolder;
	
	@ManyToOne
	@JoinColumn(name="userId", referencedColumnName="idUser")
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy="library", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Collection<Playlist> playlists = new ArrayList<Playlist>();

	public Library() {}
	
	public Library(String libPersistentId, String musicFolder, 
			Collection<Playlist> playlists) {
		this.libPersistentId = libPersistentId;
		this.musicFolder = musicFolder;
		this.playlists = playlists;
	}

	public String getLibPersistentId() {
		return libPersistentId;
	}

	public void setLibPersistentId(String libPersistentId) {
		this.libPersistentId = libPersistentId;
	}

	public String getMusicFolder() {
		return musicFolder;
	}

	public void setMusicFolder(String musicFolder) {
		this.musicFolder = musicFolder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonIgnore
	public Collection<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(Collection<Playlist> playlists) {
		this.playlists = playlists;
	}
}
