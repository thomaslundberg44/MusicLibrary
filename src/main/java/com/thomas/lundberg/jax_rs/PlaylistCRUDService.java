package com.thomas.lundberg.jax_rs;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.thomas.lundberg.entities.PlaylistList;
import com.thomas.lundberg.services.PlaylistService;

@Path("/playlists")
public class PlaylistCRUDService {
	
	@Inject PlaylistService playlistService;

	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public PlaylistList getAllPlaylists() {
		PlaylistList playlists = new PlaylistList();
		playlists.setPlaylistCollection(playlistService.getAllPlaylists());
		return playlists;
	}
}
