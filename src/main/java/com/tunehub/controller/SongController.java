package com.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tunehub.entities.Playlist;
import com.tunehub.entities.Song;
import com.tunehub.repositories.PlaylistRepository;
import com.tunehub.repositories.SongRepository;
import com.tunehub.services.SongService;


@Controller
public class SongController {
	@Autowired
	SongService service;
	
	@Autowired
	SongRepository songRepository;
	
	@Autowired
	PlaylistRepository playlistRepository;  
	
	@PostMapping("/addSong")
	public String addSong(@ModelAttribute Song song) {
		
		boolean songStatus = service.songExists(song.getName());
		if(songStatus == false) {
			song.setImageLink(song.getImageLink());
			service.addSong(song);
			
		}
		else {
			System.out.println("Song already exists");
		}
		return "adminHome";
		
	}
	
	@GetMapping("/displaySongs")
	public String viewSongs(Model model) {
		
		List<Song> songsList = service.fetchAllSongs();
		model.addAttribute("songs", songsList);
		
		return "displaySongs";
	}
	
	@GetMapping("/playSongs")
	public String playSongs(Model model) {
		
		boolean premiumUser = false;
		
		if(premiumUser == true) {
			List<Song> songsList = service.fetchAllSongs();
			model.addAttribute("songs", songsList);
			return "displaySongs";
		}
		else {
			return "makePayment";
		}	
	}
	@GetMapping("/custSongsHome")
	public String custSongsHome(Model model) {
		
		List<Song> songsList = service.fetchAllSongs();
		model.addAttribute("songs", songsList);
		
		return "custSongsHome";
	}
	
	@GetMapping("/searchSongs")
	public String searchSongs(@RequestParam("keyword") String keyword, Model model) {
	    List<Song> songsList = service.searchSongs(keyword);
	    model.addAttribute("songs", songsList);
	    return "custSongsHome";
	}

	@PostMapping("/deleteSong")
	public String deleteSong(@RequestParam("id") int id ) {
	Song deletedSong=service.findSongById(id);
	if(deletedSong != null) {
		for(Playlist playlist:deletedSong.getPlaylists()) {
			playlist.getSongs().remove(deletedSong);
			playlistRepository.save(playlist);
		}
		service.deleteSong(deletedSong);
	}
		return "redirect:displaySongs";
	}
	
	
}
