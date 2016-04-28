package com.thomas.lundberg.services.ejb;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.thomas.lundberg.dao.TrackDAO;
import com.thomas.lundberg.entities.Track;
import com.thomas.lundberg.services.TrackService;

@Stateless
@Local
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TrackServiceEJB implements TrackService {

	@Inject private TrackDAO trackDAO;
	
	public void addTrack(Track track) {
		trackDAO.addTrack(track);
	}

	public void addCollectionTracks(Collection<Track> tracks) {
		trackDAO.addCollectionTracks(tracks);
	}

	public Track getTrack(int trackId) {
		return trackDAO.getTrack(trackId);
	}

	public Collection<Track> getAllTracks() {
		System.out.println("********* In Track Service EJB. Adding Collection Track *********");
		return trackDAO.getAllTracks();
	}

	public Response deleteTrack(int trackId, String playlistName) {
		return trackDAO.deleteTrack(trackId, playlistName);
	}

}
