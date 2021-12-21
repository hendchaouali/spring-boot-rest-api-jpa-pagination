package com.rest.playlist.service;

import com.rest.playlist.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISongService {

    Page<Song> getAllSongs(int page,int size);

    Page<Song> getSongsByCategory(String category,int page,int size);

    Page<Song> getSongsByArtistName(String artistName,int page,int size);

    Song getSongById(Long id);

    Song createSong(Song song);

    Song updateSong(Song song);

    void deleteSongById(Long id);
}
