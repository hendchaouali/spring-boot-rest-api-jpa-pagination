package com.rest.playlist.repository;

import com.rest.playlist.enums.SongCategory;
import com.rest.playlist.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@EnableJpaAuditing
public interface SongRepository extends JpaRepository<Song, Long> {
    Page<Song> findAll(Pageable pageable);
    Page<Song> findSongsByCategory(SongCategory category, Pageable pageable);
    Page<Song> findSongsByArtistName(String artistName, Pageable pageable);
}
