package com.rest.playlist.service;

import com.rest.playlist.enums.SongCategory;
import com.rest.playlist.model.Song;
import com.rest.playlist.repository.SongRepository;
import com.rest.playlist.web.exception.ResourceNotFoundException;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SongServiceImpl implements ISongService {
    private static final Logger log = LoggerFactory.getLogger(SongServiceImpl.class);

    private final SongRepository songRepository;

    private Pageable paging = PageRequest.of(0, 3, Sort.by(new Sort.Order(Sort.Direction.DESC, "id")));

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Song> getAllSongs(int page,int size) {
        return songRepository.findAll(paging);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Song> getSongsByCategory(String category,int page,int size) {

        SongCategory searchedCategory = EnumUtils.getEnumIgnoreCase(SongCategory.class, category);
        if (searchedCategory == null) {
            throw new ResourceNotFoundException("Not found Category with value = " + category);
        }

        return songRepository.findSongsByCategory(searchedCategory, paging);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Song> getSongsByArtistName(String artistName,int page,int size) {
        return songRepository.findSongsByArtistName(artistName, paging);
    }

    @Override
    @Transactional(readOnly = true)
    public Song getSongById(Long id) {

        return songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found song with id = " + id));
    }

    @Override
    public Song createSong(Song song) {
        return songRepository.save(song);
    }

    @Override
    public Song updateSong(Song song) {

        Song searchedSong = songRepository.findById(song.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found song with id = " + song.getId()));

        searchedSong.setTitle(song.getTitle());
        searchedSong.setDescription(song.getDescription());
        searchedSong.setArtistName(song.getArtistName());
        searchedSong.setCategory(song.getCategory());
        searchedSong.setDuration(song.getDuration());

        return songRepository.saveAndFlush(song);
    }

    @Override
    public void deleteSongById(Long id) {
        songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found song with id = " + id));

        songRepository.deleteById(id);
    }
}
