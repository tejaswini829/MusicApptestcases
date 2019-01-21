package com.stackroute.MuzixApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.MuzixApp.domain.Track;
import com.stackroute.MuzixApp.exception.TrackAlreadyExistsException;
import com.stackroute.MuzixApp.exception.TrackDoesnotExistsException;
import com.stackroute.MuzixApp.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class MusicServiceImpl implements MusicService {

    private MusicRepository musicRepository;

    @Autowired
    public MusicServiceImpl(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }


    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistsException {


        if (musicRepository.existsById(track.getTrackId())) {
            throw new TrackAlreadyExistsException("Track Already Exists");
        }
        Track addTrack = musicRepository.save(track);


        return addTrack;
    }

    @Override
    public List<Track> displayAllTracks() {
        List<Track> allTracks = (List<Track>) musicRepository.findAll();
        return allTracks;
    }

    @Override
    public Track updateComment(String comment, int id) throws TrackDoesnotExistsException {
        Optional optional;
        optional=musicRepository.findById(id);
        if (optional.isPresent()) {
            Track track = musicRepository.findById(id).get();
            track.setTrackComments(comment);
            return track;
        }
        else {
            throw new TrackDoesnotExistsException("Track Not Found");
        }
    }


    @Override
    public Track deleteTrack(int id) throws TrackDoesnotExistsException {
        Track deleteTrack = null;
        Optional optional = musicRepository.findById(id);
        if(optional.isPresent()) {
            deleteTrack = musicRepository.findById(id).get();
            musicRepository.deleteById(id);
        }
        else
            throw new TrackDoesnotExistsException("Track Does Not Exists");
        return deleteTrack;

    }

    @Override
    public List<Track> getTrackName(String name) throws TrackDoesnotExistsException {
        List<Track> listOfTracks = null;

        listOfTracks = musicRepository.getTrackBYName(name);
        if (listOfTracks.equals(null)) {
            throw new TrackDoesnotExistsException("Track does not Exist");
        }

        return listOfTracks;
    }
}


