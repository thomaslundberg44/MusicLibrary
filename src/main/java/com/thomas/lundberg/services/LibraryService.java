package com.thomas.lundberg.services;

import java.util.Collection;

import javax.ejb.Local;

import com.thomas.lundberg.entities.Library;

@Local
public interface LibraryService {
	public boolean addLibrary(Library library);
	public Collection<Library> getAllLibraries();
}
