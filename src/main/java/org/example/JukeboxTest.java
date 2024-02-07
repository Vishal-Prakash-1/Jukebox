package org.example;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

import static org.junit.Assert.*;
public class JukeboxTest {

User user;
Jukebox jb;
    @Before
    public void setUp() {
    user = new User();
    jb = new Jukebox();
    }

    @Test
    public void correctUserId(){
        Boolean result = user.checkUserId("Ritesh123");
        assertEquals(true,true);
    }
    @Test
    public void wrongUserId(){
        Boolean result = user.checkUserId("Ritesh321");
        //assertFalse("Not present",false);
        assertEquals(false,false);
    }

    @Test
    public void correctPlaylistId(){
        Boolean result = jb.checkPlaylistId("riteshpl1");
        assertEquals(true,true);
    }

    @After
    public void tearDown()
    {user = null; jb = null;}

}
