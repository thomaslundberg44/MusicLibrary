package com.thomas.lundberg.jax_rs;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
}
