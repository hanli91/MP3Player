package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.media.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Test {
  
  public static void main(String[] args) throws Exception {
    AudioInputStream audioInputStream =AudioSystem.getAudioInputStream(
        new FileInputStream("./music/ne.mp3"));
    Clip clip = AudioSystem.getClip();
    clip.open(audioInputStream);
    clip.start( );
  }
}