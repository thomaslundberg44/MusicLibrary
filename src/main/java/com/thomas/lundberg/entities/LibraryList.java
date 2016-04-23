package com.thomas.lundberg.entities;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LibraryList {
	private Collection<Library> libraries;
	
	public Collection<Library> getLibraryCollection() {
		return this.libraries;
	}
	
	public void setLibraryCollection(Collection<Library> libraries) {
		this.libraries = libraries;
	}
}
