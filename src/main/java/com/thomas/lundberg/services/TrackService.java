package com.thomas.lundberg.services;

import java.util.Collection;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import com.thomas.lundberg.entities.Track;

@Local
public interface TrackService {
	public void addTrack(Track track);
	public void addCollectionTracks(Collection<Track> tracks);
	public Track getTrack(int trackId);
	public Collection<Track> getAllTracks();
	public Response deleteTrack(int trackId, String playlistName);
	public Response moveTrack(int trackId, String oldPlaylist, String newPlaylist);
}
