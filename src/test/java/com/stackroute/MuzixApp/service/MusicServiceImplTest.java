package com.stackroute.MuzixApp.service;

import com.stackroute.MuzixApp.domain.Track;
import com.stackroute.MuzixApp.exception.TrackAlreadyExistsException;
import com.stackroute.MuzixApp.exception.TrackDoesnotExistsException;
import com.stackroute.MuzixApp.repository.MusicRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class MusicServiceImplTest {
@Mock
   private MusicRepository musicRepository;

@InjectMocks
private  MusicServiceImpl musicService;

@Before

public  void setup(){
    MockitoAnnotations.initMocks(this);
    track1 =new Track(10,"trackone","trackone is added");
    track3=new Track(10,"trackone","tracktwo is changed");
}


private Track  track1;
private Track track3;
Optional optional;
    private List<Track> allTrack;
    @Test
    public void saveTrack() throws TrackAlreadyExistsException {
       Mockito.when(musicRepository.save(track1)).thenReturn(track1);
        Track tracksaved=musicService.saveTrack(track1);
        assertEquals(tracksaved,track1);
        verify(musicRepository,times(1)).save(track1);
    }
;
    private List<Track> allTrack1;
    @Test
    public void displayAllTracks() {
        allTrack=(List<Track>) musicRepository.findAll();
        Mockito.when(allTrack).thenReturn(allTrack);
        List allTrack1=musicService.displayAllTracks();
        assertEquals(allTrack,allTrack1);
        verify(musicRepository,times(1)).findAll();
    }



    @Test(expected = NullPointerException.class)
    public void updateComment() throws TrackDoesnotExistsException{
        when(musicRepository.findById(track1.getTrackId())).thenReturn(optional);
        Track expectedTrack = musicService.updateComment(track3.getTrackComments(),track3.getTrackId());
        assertEquals(track3,expectedTrack);
        verify(musicRepository,times(2)).findById(track1.getTrackId());


    }

    @Test(expected = NullPointerException.class)
    public void deleteTrack() throws TrackDoesnotExistsException {
        when(musicRepository.findById(track1.getTrackId())).thenReturn(optional);
        Track expected = musicService.deleteTrack(track1.getTrackId());
        verify(musicRepository, times(1)).deleteById(track1.getTrackId());
    }


    private List<Track> allTrack2;
    @Test
    public void getByTrackName() throws TrackDoesnotExistsException{
        when(musicRepository.save(track1)).thenReturn(track1);
        allTrack= musicRepository.getTrackBYName("trackone");
        when(musicRepository.getTrackBYName(track1.getTrackName())).thenReturn(allTrack);
        List allTrack2=musicService.getTrackName(track1.getTrackName());
        assertEquals(allTrack,allTrack2);
    }
}
