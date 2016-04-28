package com.thomas.lundberg.dao.jpa;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Response;

import com.thomas.lundberg.dao.LibraryDAO;
import com.thomas.lundberg.entities.Library;

@Stateless
@Local
@TransactionAttribute (TransactionAttributeType.REQUIRED)
public class JPALibraryDAO implements LibraryDAO {

	@PersistenceContext
	private EntityManager em;
	
	public boolean addLibrary(Library library) {
		Query query = em.createQuery("From Library");
		@SuppressWarnings("unchecked")
		List<Library> libraries = query.getResultList();
		if(!libraries.contains(library)) {
			em.merge(library);
			return true;
		} else
			return false;
	}

	public Collection<Library> getAllLibraries() {
		Query query = em.createQuery("From Library");
		@SuppressWarnings("unchecked")
		List<Library> libraries = query.getResultList();
		return libraries;
	}

	public Collection<Library> getLibrariesForUser(int userId) {
		Query query = em.createQuery(
				"Select l.libPersistentId, l.musicFolder"
				+ " From Library l where l.user.id =:userId");
		query.setParameter("userId", userId);
		@SuppressWarnings("unchecked")
		List<Library> libraries = query.getResultList();
		return libraries;
	}

	public Response deleteLibrary(String libPersistentId) {
		Library library = em.find(Library.class, libPersistentId);
		if(library != null) {
			System.out.println("*** In Library JPA. Deleting library. Persistent ID: "+library.getLibPersistentId());
			System.out.println("\t*** User ID: "+library.getUser().getId());
			em.remove(library);
			return Response.ok().build();
		}else {
			System.out.println("*** Library is null!");
			return Response.status(404).build();
		}
	}
}
