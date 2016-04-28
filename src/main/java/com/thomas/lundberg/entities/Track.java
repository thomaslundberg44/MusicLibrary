package com.thomas.lundberg.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Tracks")
public class Track implements Serializable {

	// auto-generated serial id
	private static final long serialVersionUID = -4739056178010135031L;

	@Id
	@Column(name="trackId") private int id;
	
	@Column(name="title") private String title;
	@Column(name="artist") private String artist;
	@Column(name="album") private String album;
	@Column(name="genre") private String genre;
	@Column(name="trackPersistentId") private String persistentId; 

	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, 
			fetch = FetchType.LAZY, mappedBy="tracks")
	private Set<Playlist> playlists = new HashSet<Playlist>();
	
	public Track() {}
	
	public Track(int id, String title, String artist, String album, 
			String genre, String persistentId, Set<Playlist> playlists) {
		this.id = id;
		this.title = title;
		this.album = album;
		this.artist = artist;
		this.genre = genre;
		this.persistentId = persistentId;
		this.playlists = playlists;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getPersistentId() {
		return persistentId;
	}

	public void setPersistentId(String persistentId) {
		this.persistentId = persistentId;
	}

	@JsonIgnore
	public Collection<Playlist> getPlaylists() {
		return playlists;
	}
	
	public void setPlaylist(Set<Playlist> playlists) {
		this.playlists = playlists;
	}
	
	public void addPlaylist(Playlist playlist) {
		playlists.add(playlist);
	}
}
