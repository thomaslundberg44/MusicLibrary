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

import com.thomas.lundberg.entities.User;
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
	@Path("/getUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(String userIdStr) {
		int userId = Integer.parseInt(userIdStr);
		User user = userService.getUser(userId);
		return user;
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
			return userService.verifyUser(uname, pwd);
		} catch (ParseException e) {
			System.out.println("Parse Exception: Error parsing user JSON object");
			return "Error verifying user";
		}
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String create(String userJson) {
		User user = getUserFromJson(userJson, true);
		return userService.addUser(user);
	}
	
	@POST
	@Path("/update")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String update(String userJson) {
		User user = getUserFromJson(userJson, false);
		return userService.updateUser(user);
	}
	
	private User getUserFromJson(String userJson, boolean autoId) {
		User user = new User();
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(userJson);
			JSONObject jsonObject = (JSONObject)obj;
			if(autoId)
				user.setId(0);
			else {
				String userIdStr = (String)jsonObject.get("userId");
				System.out.println("Got id: "+userIdStr);
				user.setId(Integer.parseInt(userIdStr));
			}
			
			user.setUsername((String) jsonObject.get("username"));
			user.setPassword((String) jsonObject.get("password"));
			user.setName((String)jsonObject.get("name"));
		} catch (ParseException e) {
			System.out.println("Parse Exception: Error parsing user JSON object");
		}
		return user;
	}
}
