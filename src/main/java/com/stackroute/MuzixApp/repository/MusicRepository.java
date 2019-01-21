package com.stackroute.MuzixApp.repository;

import com.stackroute.MuzixApp.domain.Track;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface MusicRepository extends CrudRepository<Track,Integer> {

    @Query(value = "select * from track where track_name = ?1", nativeQuery = true)
    public List<Track> getTrackBYName(String trackName);
}
