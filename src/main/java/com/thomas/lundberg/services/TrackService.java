package com.thomas.lundberg.services;

import java.util.Collection;

import javax.ejb.Local;

import com.thomas.lundberg.entities.Track;

@Local
public interface TrackService {
	public void addTrack(Track track);
	public void addCollectionTracks(Collection<Track> tracks);
	public Track getTrack(int trackId);
	public Collection<Track> getAllTracks();
}
