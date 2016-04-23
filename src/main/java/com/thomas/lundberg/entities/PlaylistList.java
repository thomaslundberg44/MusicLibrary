package com.thomas.lundberg.entities;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlaylistList {
	
	private Collection<Playlist> playlists;
	
	public Collection<Playlist> getPlaylistCollection() {
		return playlists;
	}
	
	public void setPlaylistCollection(Collection<Playlist> playlists) {
		this.playlists = playlists;
	}

}
