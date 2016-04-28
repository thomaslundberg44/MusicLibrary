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

import com.thomas.lundberg.entities.LibraryList;
import com.thomas.lundberg.services.LibraryService;

@Path("/libraries")
public class LibraryCRUDService {
	
	@Inject LibraryService libService;

	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public LibraryList getLibraries() {
		LibraryList libraries = new LibraryList();
		libraries.setLibraryCollection(libService.getAllLibraries());
		return libraries;
	}
	
	@POST
	@Path("/getLibsForUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LibraryList getLibrariesForUser(String userIdStr) {
		int userId = Integer.parseInt(userIdStr);
		LibraryList libraries = new LibraryList();
		libraries.setLibraryCollection(libService.getLibrariesForUser(userId));
		return libraries;
	}
	
	@DELETE
	@Path("/deleteLib")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response deleteLibrary(String libPersistentId) {
		System.out.println("In library JaxRs delete method. Persistent ID: "+libPersistentId);
		return libService.deleteLibrary(libPersistentId);
	}
}
