import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;


public class MediaPlayer {
  private AdvancedPlayer player = null;
  private String audioPath = null;
  private boolean newCreatedAudio;
  
  private FileInputStream fin = null;
  private BufferedInputStream bin = null;
  
  public int remainedTime;
  public int totalTime;
  public AudioProperty audioProperty = null;

  
  public MediaPlayer() {
    audioProperty = new AudioProperty();
    remainedTime = 0;
    newCreatedAudio = true;
  }
  
  public static void main(String[] args) throws Exception {
    MediaPlayer player = new MediaPlayer();
  }
  
  public boolean isNullPlayer() {
    if (this.player == null) {
      return true;
    }
    return false;
  }
  
  public void setAudioPath(String audioPath) {
    this.audioPath = audioPath;
  }
  
  public String getAudioPath() {
    return this.audioPath;
  }
  
  public void setNewCreatedAudioFlag(boolean newCreatedAudio) {
    this.newCreatedAudio = newCreatedAudio;
  }
  
  public AdvancedPlayer getPlayer() {
    return this.player;
  }
  
  public void printProperty() {
    System.out.println(this.audioProperty.getTitle());
    System.out.println(this.audioProperty.getArtist());
    System.out.println(this.audioProperty.getDuration());
    System.out.println(this.audioProperty.getFrameRate());   
  }
  
  public void play() {
    System.out.println("In play()");
    this.createPlayer();
    System.out.println("totaltime: " + this.totalTime);
    System.out.println("remainedtime: " + this.remainedTime);
    int start = totalTime - remainedTime;
    System.out.println("start: " + start);
    try {
      this.fin.skip(start);
    } catch (IOException e) {
      e.printStackTrace();
    }
    new PlayerThread(this.player).start();
  }
  
  public void pause() {
    System.out.println("In stop()");
    if (this.player != null) {
      try {
        this.remainedTime = fin.available();
        System.out.println("remainedtime:" + this.remainedTime);
      } catch (IOException e) {
        e.printStackTrace();
      }
      this.player.close();
    }
  }
  
  public void createPlayer() {
    try {
      if (this.newCreatedAudio) {
        fin = new FileInputStream(this.audioPath);
        bin = new BufferedInputStream(fin);
        this.totalTime = fin.available();
        this.remainedTime = fin.available();
        this.player = new AdvancedPlayer(bin);
        this.audioProperty.init();
        this.getAudioInfo(this.audioPath);
        this.setNewCreatedAudioFlag(false);
      } else {
        fin = new FileInputStream(this.audioPath);
        bin = new BufferedInputStream(fin);
        this.player = new AdvancedPlayer(bin);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void getAudioInfo(String filepath) throws Exception {
    // Get frame and duration info.
    File file = new File(filepath);
    AudioFileFormat format = new MpegAudioFileReader().getAudioFileFormat(file);
    int frameNum = (Integer) format.properties().get("mp3.length.frames");
    this.audioProperty.setFrameNum(frameNum);
    long duration = (Long) format.properties().get("duration");
    this.audioProperty.setDuration(duration);
    float frameRate = (Float) format.properties().get("mp3.framerate.fps");
    this.audioProperty.setFrameRate(frameRate);
    
    // Get id3tag info.
    Mp3File f = new Mp3File(filepath);
    if (f.hasId3v2Tag()) {
      ID3v2 tag = f.getId3v2Tag();
      this.audioProperty.setArtist(tag.getArtist());
      this.audioProperty.setTitle(tag.getTitle());
      this.audioProperty.setImage(tag.getAlbumImage());
    }
  }
}

class PlayerThread extends Thread {
  private AdvancedPlayer player;
  
  public PlayerThread(AdvancedPlayer player) {
    this.player = player;
  }
  
  @Override
  public void run() {
    try {
      this.player.play();
    } catch (JavaLayerException e) {
      e.printStackTrace();
    }
  }
}