package com.stackroute.MuzixApp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.MuzixApp.domain.Track;

import com.stackroute.MuzixApp.service.MusicService;
import com.stackroute.MuzixApp.service.MusicServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.swing.*;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(MusicController.class)
public class MusicControllerTest {
    @Autowired
    private MockMvc mockMVC;
    @MockBean
    public MusicServiceImpl musicService;

    private Track track;

    private List<Track> allTracks;
    Optional optional;
    @InjectMocks
    public MusicController musicController;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMVC = MockMvcBuilders.standaloneSetup(musicController).build();
        track = new Track(10,"trackone","trackone is added");
        track.setTrackId(10);
        track.setTrackName("Thunder Clouds");
        track.setTrackComments("Nice song");
    }

    private static String jsonToString(final Object obj) throws JsonProcessingException {
        String result;
        try {

            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            result = jsonContent;
        } catch (JsonProcessingException e) {
            result = "Json processing error";
        }
        return result;

    }
    @Test
    public void saveTrack() throws  Exception {
       when(musicService.saveTrack(track)).thenReturn(track);
        System.out.println("track***"+ track);
        mockMVC.perform(post("/api/v1/track")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonToString(track)))
                .andExpect(status().isCreated());

        verify(musicService, times(1)).saveTrack(Mockito.any(Track.class));
        verifyNoMoreInteractions(musicService);
    }


    @Test
    public void getAllTracks() throws Exception {
        List<Track>  music = Arrays.asList(
                new Track(1, "wrecking ball","miley cyrus"),
                new Track(2, "boulevard of broken dreams","Greenday"));
        when(musicService.displayAllTracks()).thenReturn(music);
        mockMVC.perform(get("/api/v1/track/display"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect((ResultMatcher) jsonPath("$[0].trackId", is(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].trackName", is("wrecking ball")))
                .andExpect((ResultMatcher) jsonPath("$[0].trackComments", is("miley cyrus")))
                .andExpect((ResultMatcher) jsonPath("$[1].trackId", is(2)))
                .andExpect((ResultMatcher) jsonPath("$[1].trackName", is("boulevard of broken dreams")))
                .andExpect((ResultMatcher) jsonPath("$[1].trackComments", is("Greenday")));
        verify(musicService, times(1)).displayAllTracks();
        verifyNoMoreInteractions(musicService);
    }


   @Test
  public void updateComment() throws Exception {

         when(musicService.updateComment(track.getTrackComments(), track.getTrackId())).thenReturn(track);
       mockMVC.perform(post("/api/v1/track/{comments}/{id}")
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON)
               .content(jsonToString(track)))
               .andExpect(status().isOk());

       verify(musicService, times(1)).updateComment(track.getTrackComments(),track.getTrackId());
       verifyNoMoreInteractions(musicService);
    }

    @Test
    public void removeTrack() throws Exception {

       doNothing().when(musicService).deleteTrack(track.getTrackId());
        mockMVC.perform(
                delete("/api/v1/track/{id}", track.getTrackId()))
                .andExpect(status().isOk());
        verify(musicService, times(1)).deleteTrack(track.getTrackId());
        verifyNoMoreInteractions(musicService);
    }


}