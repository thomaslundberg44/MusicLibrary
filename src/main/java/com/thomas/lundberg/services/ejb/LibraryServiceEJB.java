package com.thomas.lundberg.services.ejb;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.thomas.lundberg.dao.LibraryDAO;
import com.thomas.lundberg.entities.Library;
import com.thomas.lundberg.services.LibraryService;

@Stateless
@Local
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LibraryServiceEJB implements LibraryService {

	@Inject private LibraryDAO libraryDAO;
	
	public boolean addLibrary(Library library) {
		System.out.println("******** In Library Service EJB. Adding library **********");
		return libraryDAO.addLibrary(library);
	}

	public Collection<Library> getAllLibraries() {
		return libraryDAO.getAllLibraries();
	}

	public Collection<Library> getLibrariesForUser(int userId) {
		return libraryDAO.getLibrariesForUser(userId);
	}

	public Response deleteLibrary(String libPersistentId) {
		return libraryDAO.deleteLibrary(libPersistentId);
	}

}
