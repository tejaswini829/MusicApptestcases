package com.stackroute.MuzixApp.repository;

import com.stackroute.MuzixApp.domain.Track;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest

public class TrackRepositoryTest {

    @Autowired
    private MusicRepository musicRepository;
    private Track track;

    @Before
    public void setUp() throws Exception {

        track = new Track(1,"Worth It","Listen" );
    }

    @Test
    public void testRegisterUserSuccess() {

        musicRepository.save(track);
        Track object = musicRepository.findById(track.getTrackId()).get();
        assertEquals(track,object);
    }


}