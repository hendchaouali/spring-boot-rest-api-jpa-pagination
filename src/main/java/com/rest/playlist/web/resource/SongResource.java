package com.rest.playlist.web.resource;

import com.rest.playlist.model.Song;
import com.rest.playlist.service.SongServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/songs")
public class SongResource {

    final private com.rest.playlist.service.ISongService ISongService;
    private static final Logger log = LoggerFactory.getLogger(SongServiceImpl.class);

    public SongResource(com.rest.playlist.service.ISongService ISongService) {
        this.ISongService = ISongService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllSongs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        Map<String, Object> response = setResponsePage(ISongService.getAllSongs(page, size));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<Map<String, Object>> getSongsByCategory(@PathVariable String category,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "3") int size) {
        Map<String, Object> response = setResponsePage(ISongService.getSongsByCategory(category, page, size));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/artist/{artistName}")
    public ResponseEntity<Map<String, Object>> getSongsByArtist(@PathVariable String artistName,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "3") int size) {
        Map<String, Object> response = setResponsePage(ISongService.getSongsByArtistName(artistName, page, size));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        Song song = ISongService.getSongById(id);
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Song> createSong(@Valid @RequestBody Song song) {
        Song addedSong = ISongService.createSong(song);
        return new ResponseEntity<>(addedSong, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity updateSong(@Valid @RequestBody Song song) {
        Song updatedSong = ISongService.updateSong(song);
        return new ResponseEntity<>(updatedSong, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSongById(@PathVariable Long id) {
        ISongService.deleteSongById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Map<String, Object> setResponsePage(Page<Song> songPages) {
        if (!songPages.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("songs", songPages.getContent());
            response.put("currentPage", songPages.getNumber());
            response.put("totalItems", songPages.getTotalElements());
            response.put("totalPages", songPages.getTotalPages());

            return response;
        }
        return null;
    }
}

