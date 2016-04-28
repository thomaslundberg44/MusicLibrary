package com.thomas.lundberg.services.ejb;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.thomas.lundberg.dao.PlaylistDAO;
import com.thomas.lundberg.entities.Playlist;
import com.thomas.lundberg.services.PlaylistService;

@Stateless
@Local
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PlaylistServiceEJB implements PlaylistService {

	@Inject private PlaylistDAO playlistDAO;
	
	public void addPlayList(Playlist playlist) {
		playlistDAO.addPlayList(playlist);
	}

	public Playlist getPlaylist(int playlistId) {
		return playlistDAO.getPlaylist(playlistId);
	}

	public Collection<Playlist> getAllPlaylists() {
		return playlistDAO.getAllPlaylists();
	}

	public void addSetPlaylists(Collection<Playlist> playlists) {
		System.out.println("*********** In Playlist Service EJB. Adding collection Playlists ***");
		playlistDAO.addSetPlaylists(playlists);
	}

	public Collection<Playlist> getPlaylistsForLib(String libPersistentId) {
		return playlistDAO.getPlaylistsForLib(libPersistentId);
	}

	public Collection<Playlist> getTracksForPlaylist(String playlistName) {
		return playlistDAO.getTracksForPlaylist(playlistName);
	}

	public Response deletePlaylist(int playlistId) {
		return playlistDAO.deletePlaylist(playlistId);
	}

	public Response deleteTrackFromPlaylist(String playlistName, int trackId) {
		return playlistDAO.deleteTrackFromPlaylist(playlistName, trackId);
	}

}
