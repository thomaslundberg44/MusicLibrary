package com.thomas.lundberg.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.thomas.lundberg.entities.Library;
import com.thomas.lundberg.entities.Playlist;
import com.thomas.lundberg.entities.Track;
import com.thomas.lundberg.entities.User;
import com.thomas.lundberg.jaxb_generated_objects.Array;
import com.thomas.lundberg.jaxb_generated_objects.Dict;
import com.thomas.lundberg.jaxb_generated_objects.Key;
import com.thomas.lundberg.jaxb_generated_objects.ObjectFactory;
import com.thomas.lundberg.jaxb_generated_objects.Plist;
import com.thomas.lundberg.services.LibraryService;
import com.thomas.lundberg.services.PlaylistService;
import com.thomas.lundberg.services.TrackService;

@Stateless
public class ProcessXMLPlistJaxB {
	
	public static String FILE_PATH = "/home/tommy/Documents/xml_itunes_files/iTunes Music Library3.xml";
	
	@Inject private LibraryService libService;
	@Inject private PlaylistService playService;
	@Inject private TrackService trackService;
	
	@PersistenceContext private EntityManager em;
	
	private int userId;
	
	private Library library;
	private Collection<Playlist> playlists;
	private Collection<Track> tracks;
	
	private Dict libDict, trackDict;
	private Array playlistArray;
	
	public ProcessXMLPlistJaxB() {
		library = new Library();
		playlists = new ArrayList<>();
		tracks = new ArrayList<>();
	}
	
	public void processXMLFile(File file, int userId) {
		this.userId = userId;
		long start = System.currentTimeMillis();
		process(file);
		long end = System.currentTimeMillis();
		System.out.println("File processed. Took "+(end-start)/1000.0+" seconds");
		
//		printTestOutput();
	}
	
	private void printTestOutput() {
		
		System.out.println("*----- Printing library: -----*\n");
		System.out.println("\tLibrary Persistent ID: "+library.getLibPersistentId());
		System.out.println("\tMusic Folder: "+library.getMusicFolder());
		
		System.out.println("\n");
		System.out.println("*----- Printing Tracks -----*\n");
		for(Track track : tracks) {
			System.out.println("Got track with ID: "+track.getId());
			System.out.println("\tName: "+track.getTitle());
			System.out.println("\tArtist: "+track.getArtist());
			System.out.println("\tAlbum: "+track.getAlbum());
			System.out.println("\tGenre: "+track.getGenre());
			System.out.println("\tPersistent ID: "+track.getPersistentId());
			System.out.println("\tPlaylists containing this track:");
//			for(Playlist playlist : track.getPlaylists()) {
//				System.out.println("\t\tPlaylist ID: "+playlist.getPlayListId());
//				System.out.println("\t\tPlaylist Name: "+playlist.getName());
//			}
		}
		
		System.out.println("\n");
		System.out.println("*----- Printing Playlists -----*\n");
		for(Playlist playlist : playlists) {
			System.out.println("Got Playlist with ID: "+playlist.getPlayListId());
			System.out.println("\tName: "+playlist.getName());
			System.out.println("\tPersistent ID: "+playlist.getPersistentId());
			System.out.println("\tLibrary Persistent ID: "+playlist.getLibrary().getLibPersistentId());
			System.out.println("\tTracks for playlist:");
			for(Track track : playlist.getTracks()) {
				System.out.println("\t\tTrack ID: "+track.getId());
				System.out.println("\t\tTrack Name: "+track.getTitle());
				System.out.println("\t\tTrack Artist: "+track.getArtist());
			}
		}
	}
	
	private void process(File file) {
		System.out.println("**** In Process XML class. *****");
		System.out.println("Processing File at path: "+file.getAbsolutePath());
		try {
//			File file = new File(filePath);
			JAXBContext jaxBContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jaxBContext.createUnmarshaller();
			
			Plist plist = (Plist) unmarshaller.unmarshal(file);
			
			List<Object> root = plist.getArrayOrDataOrDateOrDictOrRealOrIntegerOrStringOrTrueOrFalse();
			
			libDict = (Dict)root.get(0);
			
			setLibraries();		
			setTracks();
			setPlaylists();

			libService.addLibrary(library);
			trackService.addCollectionTracks(tracks);
			playService.addSetPlaylists(playlists);
		}catch(JAXBException e) {
			e.printStackTrace();
		}
	}
	
	private void setLibraries() {
		List<Object> libChildren = 
				libDict.getKeyOrArrayOrDataOrDateOrDictOrRealOrIntegerOrStringOrTrueOrFalse();
		for(int i = 0; i < libChildren.size(); i++) {
			if(libChildren.get(i).getClass().getName().contains("Key")) {
				if(((Key) libChildren.get(i)).getvalue().equals("Library Persistent ID")){
					library.setLibPersistentId(((com.thomas.lundberg.jaxb_generated_objects.String) libChildren.get(i+1)).getvalue());
				}else if(((Key) libChildren.get(i)).getvalue().equals("Music Folder")){
					library.setMusicFolder(((com.thomas.lundberg.jaxb_generated_objects.String) libChildren.get(i+1)).getvalue());
				}else if(((Key) libChildren.get(i)).getvalue().equals("Tracks")) {
					trackDict = (Dict) libChildren.get(i+1);
				}else if(((Key) libChildren.get(i)).getvalue().equals("Playlists")) {
					playlistArray = (Array) libChildren.get(i+1);
				}
			}
		}
		library.setUser(em.find(User.class, userId));
	}
	
	private void setTracks() {
		List<Object> trackChildren = 
				trackDict.getKeyOrArrayOrDataOrDateOrDictOrRealOrIntegerOrStringOrTrueOrFalse();
		for(int i = 0; i < trackChildren.size(); i++) {
			if(trackChildren.get(i).getClass().getName().contains("Dict"))
				tracks.add(getTrack((Dict)trackChildren.get(i)));
		}
	}
	
	private Track getTrack(Dict trackNode) {
		List<Object> trackChildKeys = 
				trackNode.getKeyOrArrayOrDataOrDateOrDictOrRealOrIntegerOrStringOrTrueOrFalse();
		Track track = new Track();
		for(int i = 0; i < trackChildKeys.size(); i++) {
			if(trackChildKeys.get(i).getClass().getName().contains("Key")) {
				if(((Key) trackChildKeys.get(i)).getvalue().equals("Track ID")){
					track.setId(Integer.parseInt((((com.thomas.lundberg.jaxb_generated_objects.Integer) trackChildKeys.get(i+1)).getvalue())));
				}else if(((Key) trackChildKeys.get(i)).getvalue().equals("Name")) {
					track.setTitle((((com.thomas.lundberg.jaxb_generated_objects.String) trackChildKeys.get(i+1)).getvalue()));
				}else if(((Key) trackChildKeys.get(i)).getvalue().equals("Artist")) {
					track.setArtist((((com.thomas.lundberg.jaxb_generated_objects.String) trackChildKeys.get(i+1)).getvalue()));
				}else if(((Key) trackChildKeys.get(i)).getvalue().equals("Album")) {
					track.setAlbum((((com.thomas.lundberg.jaxb_generated_objects.String) trackChildKeys.get(i+1)).getvalue()));
				}else if(((Key) trackChildKeys.get(i)).getvalue().equals("Genre")) {
					track.setGenre((((com.thomas.lundberg.jaxb_generated_objects.String) trackChildKeys.get(i+1)).getvalue()));
				}else if(((Key) trackChildKeys.get(i)).getvalue().equals("Persistent ID")) {
					track.setPersistentId((((com.thomas.lundberg.jaxb_generated_objects.String) trackChildKeys.get(i+1)).getvalue()));
				}
			}
		}
		return track;
	}
	
	private void setPlaylists() {
		List<Object> playlistChildren = 
				playlistArray.getArrayOrDataOrDateOrDictOrRealOrIntegerOrStringOrTrueOrFalse();
		for(int i = 0; i < playlistChildren.size(); i++) {
			if(playlistChildren.get(i).getClass().getName().contains("Dict"))
				playlists.add(getPlaylist((Dict)playlistChildren.get(i)));
		}
	}
	
	private Playlist getPlaylist(Dict playlistNode) {
		List<Object> playlistChildKeys = 
				playlistNode.getKeyOrArrayOrDataOrDateOrDictOrRealOrIntegerOrStringOrTrueOrFalse();
		Playlist playlist = new Playlist();
		for(int i = 0; i < playlistChildKeys.size(); i++) {
			if(playlistChildKeys.get(i).getClass().getName().contains("Key")) {
				if(((Key) playlistChildKeys.get(i)).getvalue().equals("Playlist ID")){
					playlist.setPlayListId(Integer.parseInt((((com.thomas.lundberg.jaxb_generated_objects.Integer) playlistChildKeys.get(i+1)).getvalue())));
				}else if(((Key) playlistChildKeys.get(i)).getvalue().equals("Name")) {
					playlist.setName((((com.thomas.lundberg.jaxb_generated_objects.String) playlistChildKeys.get(i+1)).getvalue()));
				}else if(((Key) playlistChildKeys.get(i)).getvalue().equals("Playlist Persistent ID")) {
					playlist.setPersistentId((((com.thomas.lundberg.jaxb_generated_objects.String) playlistChildKeys.get(i+1)).getvalue()));
				}else if(((Key) playlistChildKeys.get(i)).getvalue().equals("Playlist Items")) {
					Array playlistTracks = (Array)playlistChildKeys.get(i+1);
					playlist.setTracks(getTracksForPlaylist(playlistTracks, playlist));
				}
			}
		}
		playlist.setLibrary(library);
		return playlist;
	}
	
	private Set<Track> getTracksForPlaylist(Array playlistTracks, Playlist playlist) {
		Set<Track> tracks = new HashSet<>();
		List<Object> nodes = playlistTracks.getArrayOrDataOrDateOrDictOrRealOrIntegerOrStringOrTrueOrFalse();
		for(int i = 0; i < nodes.size(); i++) {
			Dict dict = (Dict) nodes.get(i);
			List<Object> trackNodes = dict.getKeyOrArrayOrDataOrDateOrDictOrRealOrIntegerOrStringOrTrueOrFalse();
			for(int j = 0; j < trackNodes.size(); j++) {
				if(trackNodes.get(j).getClass().getSimpleName().equals("Integer")) {
					int trackId = Integer.parseInt(
							(((com.thomas.lundberg.jaxb_generated_objects.Integer)
									trackNodes.get(j)).getvalue()));
					Track track = getTrack(trackId);
					tracks.add(track);
//					track.addPlaylist(playlist);
				}
			}
		}
		return tracks;
	}
	
	private Track getTrack(int trackId) {
		for(Track track : tracks) {
			if(track.getId() == trackId)
				return track;
		}
		return null;
	}

//	public static void main(String[] args) {
//		new ProcessXMLPlistJaxB();
//	}
}
