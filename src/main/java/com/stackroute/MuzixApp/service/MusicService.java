package com.stackroute.MuzixApp.service;

import com.stackroute.MuzixApp.domain.Track;
import com.stackroute.MuzixApp.exception.TrackAlreadyExistsException;
import com.stackroute.MuzixApp.exception.TrackDoesnotExistsException;

import java.util.List;

public interface MusicService {
    public Track saveTrack(Track track) throws TrackAlreadyExistsException;

    public List<Track> displayAllTracks();

    public Track updateComment(String comment, int id) throws TrackDoesnotExistsException;

    public Track deleteTrack(int id) throws TrackDoesnotExistsException;

    public List<Track> getTrackName(String name) throws TrackDoesnotExistsException;

}
