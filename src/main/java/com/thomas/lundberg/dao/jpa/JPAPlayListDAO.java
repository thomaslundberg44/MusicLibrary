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

import com.thomas.lundberg.dao.PlaylistDAO;
import com.thomas.lundberg.entities.Playlist;

@Local
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JPAPlayListDAO implements PlaylistDAO {

	@PersistenceContext
	private EntityManager em;

	public void addPlayList(Playlist playlist) {
		Query query = em.createQuery("From Playlist");
		@SuppressWarnings("unchecked")
		List<Playlist> playlists = 	query.getResultList();
		if(!playlists.contains(playlist))
			em.persist(playlist);
	}

	public Playlist getPlaylist(int playlistId) {
		Query query = em.createQuery("From Playlist p where p.playlistId := playlistId");
		query.setParameter("playlistId", playlistId);
		return (Playlist) query.getSingleResult();
	}

	public Collection<Playlist> getAllPlaylists() {
		Query query = em.createQuery("From Playlist");
		@SuppressWarnings("unchecked")
		List<Playlist> playlists = query.getResultList();
		return playlists;
	}
	
	public void addSetPlaylists(Collection<Playlist> playlists) {
		System.out.println("*** In JPA Playlist DAO. Got playlist list size: "+playlists.size());
		for(Playlist playlist : playlists) {
			em.merge(playlist);
		}
	}

}
