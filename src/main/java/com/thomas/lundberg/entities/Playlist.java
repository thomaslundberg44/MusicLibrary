package com.thomas.lundberg.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Playlists")
public class Playlist implements Serializable {
	
	// auto-generated serial id
	private static final long serialVersionUID = 8548911370977382108L;

	@Id
	@Column (name="playlistId") private int playListId;
	
	@Column (name="name") private String name;
	@Column (name="playlistPersistentId") private String persistentId;
	
	// map to library class with library persistent id
	@ManyToOne
	@JoinColumn(name="libraryPersistentId", referencedColumnName="libraryPersistentId", nullable=false)
	private Library library;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="Playlists_Tracks",
			joinColumns = {
				@JoinColumn(name = "playlistId", nullable=false, updatable = false)
			},
			inverseJoinColumns = {
				@JoinColumn(name = "trackId", nullable = false, updatable = false)
			}
	)
	private Set<Track> tracks = new HashSet<Track>();
	
	
	public Playlist() { }
	
	public Playlist(int playlistId, String name, String persistentId, 
			Library library, Set<Track> tracks) {
		this.playListId = playlistId;
		this.name = name;
		this.persistentId = persistentId;
		this.library = library;
		this.tracks = tracks;
	}

	public int getPlayListId() {
		return playListId;
	}

	public void setPlayListId(int playListId) {
		this.playListId = playListId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersistentId() {
		return persistentId;
	}

	public void setPersistentId(String persistentId) {
		this.persistentId = persistentId;
	}
	
	@JsonIgnore
	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public Set<Track> getTracks() {
		return tracks;
	}

	public void setTracks(Set<Track> tracks) {
		this.tracks = tracks;
	}

}
