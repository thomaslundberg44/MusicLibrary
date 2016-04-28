package com.thomas.lundberg.dao.jpa;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Response;

import com.thomas.lundberg.dao.PlaylistDAO;
import com.thomas.lundberg.entities.Playlist;
import com.thomas.lundberg.entities.Track;

@Local
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JPAPlayListDAO implements PlaylistDAO {

	@PersistenceContext
	private EntityManager em;

	public void addPlayList(Playlist playlist) {
		Query query = em.createQuery("From Playlist");
		@SuppressWarnings("unchecked")
		List<Playlist> playlists = 	query.getResultList();
		if(!playlists.contains(playlist))
			em.merge(playlist);
	}

	public Playlist getPlaylist(int playlistId) {
		Query query = em.createQuery("From Playlist p where p.playlistId := playlistId");
		query.setParameter("playlistId", playlistId);
		return (Playlist) query.getSingleResult();
	}

	public Collection<Playlist> getAllPlaylists() {
		Query query = em.createQuery("From Playlist");
		@SuppressWarnings("unchecked")
		List<Playlist> playlists = query.getResultList();
		return playlists;
	}
	
	public void addSetPlaylists(Collection<Playlist> playlists) {
		System.out.println("*** In JPA Playlist DAO. Got playlist list size: "+playlists.size());
		for(Playlist playlist : playlists) {
			em.merge(playlist);
		}
	}

	public Collection<Playlist> getPlaylistsForLib(String libPersistentId) {
		Query query = em.createQuery("Select p.playListId, p.name, p.persistentId, p.tracks.size"
				+ " From Playlist p Where p.library.libPersistentId =:libPersistentId");
		query.setParameter("libPersistentId", libPersistentId);
		@SuppressWarnings("unchecked")
		List<Playlist> playlists = query.getResultList();
		return playlists;
	}

	@SuppressWarnings("unchecked")
	public Collection<Playlist> getTracksForPlaylist(String playlistName) {
		System.out.println("*** playlist name: "+playlistName);
		Query query = em.createQuery("Select p.tracks from Playlist p Where p.name =:playlistName");
		query.setParameter("playlistName", playlistName);
		List<Playlist> playlists= query.getResultList();
		System.out.println("*** Got "+playlists.size()+" tracks");
		return playlists;
	}

	public Response deletePlaylist(int playlistId) {
		Playlist playlist = em.find(Playlist.class, playlistId);
		if(playlist != null) {
			System.out.println("*** In playlist JPA. Found playlist: name: "+playlist.getName());
			deleteTracksForPlaylist(playlist);
			em.remove(playlist);
			return Response.ok().build();
		} else {
			System.out.println("Playlist is null");
			return Response.status(404).build();
		}
	}
	
	// delete any tracks that will become orphaned after deleting playlist
	private void deleteTracksForPlaylist(Playlist playlist) {
		Set<Track> playlistTracks = playlist.getTracks();
		for(Track track : playlistTracks) {
			System.out.println("Track is on "+track.getPlaylists().size()+" playlists");
			track.getPlaylists().remove(playlist);
			
			if(track.getPlaylists().size() == 0) {
				System.out.println("Removing track: "+track.getId()+" to prevent orphaning");
				em.remove(track);
			} else {
				System.out.println("Track "+track.getId()+" is still on "
						+track.getPlaylists().size()+" playlists");
			}
		}
	}
	
	public Response deleteTrackFromPlaylist(String playlistId, int trackId) {
		Track track = em.find(Track.class, trackId);
//		Collection<Playlist> trackPlaylists = track.getPlaylists();
//		trackPlaylists.remove(track);
//		em.merge(trackPlaylists);
//		if(trackPlaylists.size() == 0) {
//			System.out.println("Removing track: ID: "+track.getId());
//			em.remove(track);
//		}else {
//			System.out.println("Not removing. size: "+trackPlaylists.size());
//		}
		return Response.ok().build();
	}

}
