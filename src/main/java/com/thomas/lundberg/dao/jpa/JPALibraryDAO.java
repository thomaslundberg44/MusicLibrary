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
			em.persist(library);
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

}
