package com.thomas.lundberg.entities;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TrackList {

	private Collection<Track> tracks;
	
	public Collection<Track> getTrackCollection() {
		return this.tracks;
	}
	
	public void setTrackCollection(Collection<Track> tracks) {
		this.tracks = tracks;
	}
}
