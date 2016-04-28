package com.thomas.lundberg.dao;

import java.util.Collection;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import com.thomas.lundberg.entities.Track;

@Local
public interface TrackDAO {
	public void addTrack(Track track);
	public void addCollectionTracks(Collection<Track> tracks);
	public Track getTrack(int trackId);
	public Collection<Track> getAllTracks();
	public Response deleteTrack(int trackId, String playlistName);
}
