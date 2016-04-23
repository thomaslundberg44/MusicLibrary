package com.thomas.lundberg.jax_rs;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.thomas.lundberg.entities.UserList;
import com.thomas.lundberg.services.UserService;

@Path("/users")
public class UserCRUDService {

	@Inject private UserService userService;
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public UserList getAllUsers() {
		UserList userList = new UserList();
		userList.setUserCollection(userService.getAllUsers());
		return userList;
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String login(String userJson) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(userJson);
			JSONObject jsonObject = (JSONObject)obj;
			String uname = (String) jsonObject.get("username");
			String pwd = (String) jsonObject.get("password");
			System.out.println("Attempting login. username: "+uname+", pwd: "+pwd);
			return userService.verifyUser(uname, pwd);
		} catch (ParseException e) {
			System.out.println("Parse Exception: Error parsing user JSON object");
			return "Error verifying user";
		}
	}
}
