import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.media.Manager;
import javax.media.Player;
import javax.media.Time;
import javax.sound.sampled.AudioFileFormat;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

public class MyPlayer {
  private Player player;
  private String audioPath = null;
  private boolean newCreatedAudio;
  
  public AudioProperty audioProperty = null;

  public MyPlayer() {
    audioProperty = new AudioProperty();
    newCreatedAudio = true;
  }
  
  public static void main(String[] args) throws Exception {
    Main.loadMp3Plugin();
    File f = new File("./music/test1.wav");
    Player player = Manager.createRealizedPlayer(f.toURI().toURL());
    player.start();
    //MyPlayer player = new MyPlayer();
    //player.setAudioPath("./music/hotaru.mp3");
    //player.createPlayer();
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
  
  public Player getPlayer() {
    return this.player;
  }
  
  public void printProperty() {
    System.out.println(this.audioProperty.getTitle());
    System.out.println(this.audioProperty.getArtist());
    System.out.println(this.audioProperty.getDuration());
    System.out.println(this.audioProperty.getFrameRate());   
  }
  
  public void createPlayer() {
    try {
      if (this.newCreatedAudio) {
        File f = new File(this.audioPath);
        this.player = Manager.createRealizedPlayer(f.toURI().toURL());  
        this.audioProperty.init();
        this.getAudioInfo(this.audioPath);
        this.setNewCreatedAudioFlag(false);
      } else {
        File f = new File(this.audioPath);
        this.player = Manager.createRealizedPlayer(f.toURI().toURL());
      }
      this.player.start();
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