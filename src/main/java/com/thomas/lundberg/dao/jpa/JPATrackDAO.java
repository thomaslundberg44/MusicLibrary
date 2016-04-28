package com.thomas.lundberg.dao.jpa;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Response;

import com.thomas.lundberg.dao.TrackDAO;
import com.thomas.lundberg.entities.Playlist;
import com.thomas.lundberg.entities.Track;

@Stateless
@Local
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JPATrackDAO implements TrackDAO {

	@PersistenceContext
	private EntityManager em;
	
	public void addTrack(Track track) {
		Query query = em.createQuery("From Track");
		@SuppressWarnings("unchecked")
		List<Track> tracks = query.getResultList();
		if(!tracks.contains(track))
			em.persist(track);
	}

	public void addCollectionTracks(Collection<Track> tracks) {
		System.out.println("In JPA Track DAO. Got collection size: "+tracks.size());
		for(Track track : tracks) {
			em.merge(track);
		}
	}

	public Track getTrack(int trackId) {
		Query query = em.createQuery("From Track t where t.trackId := trackId");
		query.setParameter("trackId", trackId);
		return (Track) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<Track> getAllTracks() {
		Query query = em.createQuery("From Track");
		return query.getResultList();
	}

	public Response deleteTrack(int trackId, String playlistName) {
		Track track = em.find(Track.class, trackId);
		if(track != null) {
			System.out.println("Track is on "+track.getPlaylists().size()+" playlists");
			Playlist playlist = null;
			Collection<Playlist> playlists = track.getPlaylists();
			for(Playlist playlistItem : playlists) {
				if(playlistItem.getName().equals(playlistName))
					playlist = playlistItem;
			}
			if(playlist != null) {
				System.out.println("Removing association of playlist id: "+playlist.getPlayListId());
				track.getPlaylists().remove(playlist);
				playlist.getTracks().remove(track);
				em.merge(track);
			}
			
			if(track.getPlaylists().size() == 0) {
				System.out.println("Removing track: "+track.getId()+" to prevent orphaning");
				em.remove(track);
			} else {
				System.out.println("Track "+track.getId()+" is still on "
						+track.getPlaylists().size()+" playlists");
			}
			return Response.ok().build();
		} else {
			System.out.println("Track is null");
			return Response.status(404).build();
		}
	}

	public Response moveTrack(int trackId, String oldPlaylistStr, String newPlaylistStr) {
		System.out.println("In Move Track method, trackId: "
				+trackId+", old playlist: "+oldPlaylistStr+", new Playlist: "+newPlaylistStr);
		Track track = em.find(Track.class, trackId);
		if(track != null) {
			System.out.println("Track is on "+track.getPlaylists().size()+" playlists");
			Playlist oldPlaylist = null, newPlaylist = null;
			Query query = em.createQuery("From Playlist");
			@SuppressWarnings("unchecked")
			List<Playlist> playlists = query.getResultList();
			for(Playlist playlistItem : playlists) {
				if(playlistItem.getName().equals(oldPlaylistStr))
					oldPlaylist = playlistItem;
				else if(playlistItem.getName().equals(newPlaylistStr))
					newPlaylist = playlistItem;
			}
			if(oldPlaylist != null && newPlaylist != null) {
				System.out.println("Removing association of playlist id: "+oldPlaylist.getPlayListId());
				track.getPlaylists().remove(oldPlaylist);
				oldPlaylist.getTracks().remove(track);
				
				if(!newPlaylist.getTracks().contains(track)) {
					track.getPlaylists().add(newPlaylist);
					newPlaylist.getTracks().add(track);
				}
				
				em.merge(track);
			}else 
				System.out.println("oldPlaylist is null: "+(oldPlaylist == null)+", newPlaylist is null: "+(newPlaylist == null));
			return Response.ok().build();
		}
		else
			return Response.status(404).build();
	}

}
