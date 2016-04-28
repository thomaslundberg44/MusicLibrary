package com.thomas.lundberg.services;

import java.util.Collection;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import com.thomas.lundberg.entities.Playlist;

@Local
public interface PlaylistService {
	public void addPlayList(Playlist playlist);
	public void addSetPlaylists(Collection<Playlist> playlists);
	public Playlist getPlaylist(int playlistId);
	public Collection<Playlist> getAllPlaylists();
	public Collection<Playlist> getPlaylistsForLib(String libPersistentId);
	public Collection<Playlist> getTracksForPlaylist(String playlistName);
	public Response deletePlaylist(int playlistId);
	public Response deleteTrackFromPlaylist(String playlistName, int trackId);
}
