package com.thomas.lundberg.jax_rs;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	
	@POST
	@Path("/getPlaylistsForLib")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PlaylistList getPlaylistsForLib(String library) {
		PlaylistList playlists = new PlaylistList();
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(library);
			JSONObject jsonObject = (JSONObject)obj;
			String libPersistentId = (String) jsonObject.get("libPersistentId");
			playlists.setPlaylistCollection(playlistService.getPlaylistsForLib(libPersistentId));
		} catch (ParseException e) {
			System.out.println("Parse Exception: Error parsing user JSON object");
		}
		return playlists;
	}
	
	@POST
	@Path("/getTracksForPlaylist")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PlaylistList getTracksForPlaylist(String playlistName) {
		PlaylistList playlists = new PlaylistList();
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(playlistName);
			JSONObject jsonObject = (JSONObject)obj;
			String playlist = (String) jsonObject.get("playlistName");
			playlists.setPlaylistCollection(playlistService.getTracksForPlaylist(playlist));
		} catch (ParseException e) {
			System.out.println("Parse Exception: Error parsing user JSON object");
		}
		return playlists;
	}
	
	@DELETE
	@Path("/deletePlaylist")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response deletePlaylist(String playlistIdStr) {
		int playlistId = Integer.parseInt(playlistIdStr);
		System.out.println("*** In Playlist CRUD delete method. Got playlist ID: "+playlistId);
		return playlistService.deletePlaylist(playlistId);
	}
	
}
