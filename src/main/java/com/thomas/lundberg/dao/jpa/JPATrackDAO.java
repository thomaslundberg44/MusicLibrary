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

import com.thomas.lundberg.dao.TrackDAO;
import com.thomas.lundberg.entities.Track;

@Stateless
@Local
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JPATrackDAO implements TrackDAO {

	@PersistenceContext
	private EntityManager em;
	
	public void addTrack(Track track) {
		Query query = em.createQuery("From Track");
		@SuppressWarnings("unchecked")
		List<Track> tracks = query.getResultList();
		if(!tracks.contains(track))
			em.persist(track);
	}

	public void addCollectionTracks(Collection<Track> tracks) {
		System.out.println("In JPA Track DAO. Got collection size: "+tracks.size());
		for(Track track : tracks) {
			em.merge(track);
		}
	}

	public Track getTrack(int trackId) {
		Query query = em.createQuery("From Track t where t.trackId := trackId");
		query.setParameter("trackId", trackId);
		return (Track) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<Track> getAllTracks() {
		Query query = em.createQuery("From Track");
		return query.getResultList();
	}

}
