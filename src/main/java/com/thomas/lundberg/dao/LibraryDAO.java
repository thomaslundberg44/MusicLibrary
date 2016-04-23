package com.thomas.lundberg.dao;

import java.util.Collection;

import javax.ejb.Local;

import com.thomas.lundberg.entities.Library;

@Local
public interface LibraryDAO {
	public boolean addLibrary(Library library);
	public Collection<Library> getAllLibraries();
}
