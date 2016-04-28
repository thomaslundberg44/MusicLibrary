package com.thomas.lundberg.jax_rs;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.thomas.lundberg.services.TrackService;

@Path("/tracks")
public class TrackCRUDService {
	
	@Inject private TrackService trackService;
	
	@DELETE
	@Path("/deleteTrack")
	@Consumes(MediaType.APPLICATION_JSON) 
	public Response deleteTrack(String json) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(json);
			JSONObject jsonObject = (JSONObject)obj;
			String playlistName = (String) jsonObject.get("playlistName");
			int trackId = Integer.parseInt((String) jsonObject.get("trackId"));
			trackService.deleteTrack(trackId, playlistName);
			return Response.ok().build();
		} catch (ParseException e) {
			System.out.println("Parse Exception: Error parsing user JSON object");
			return Response.status(404).build();
		}
	}

}
