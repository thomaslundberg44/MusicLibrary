package com.thomas.lundberg.services;

import java.util.Collection;

import javax.ejb.Local;

import com.thomas.lundberg.entities.Playlist;

@Local
public interface PlaylistService {
	public void addPlayList(Playlist playlist);
	public void addSetPlaylists(Collection<Playlist> playlists);
	public Playlist getPlaylist(int playlistId);
	public Collection<Playlist> getAllPlaylists();
}
