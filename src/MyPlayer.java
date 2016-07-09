import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.Time;
import javax.media.format.AudioFormat;
import javax.sound.sampled.AudioFileFormat;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

public class MyPlayer {
  private Player player;
  private String audioPath = null;
  private boolean newCreatedAudio;
  
  public MyAudioProperty audioProperty = null;

  public MyPlayer() {
    audioProperty = new MyAudioProperty();
    newCreatedAudio = true;
  }
  
  public static void main(String[] args) throws Exception {
    MyPlayer.loadMp3Plugin();
    File f = new File("./music/ne.mp3");
    MediaLocator locator = new MediaLocator(f.toURI().toURL());  
    Player player = Manager.createRealizedPlayer(locator);
    System.out.println(player.getMediaTime().getNanoseconds());
    System.out.println(player.getStopTime().getNanoseconds());
    player.prefetch();
    player.start();
    //MyPlayer player = new MyPlayer();
    //player.setAudioPath("./music/hotaru.mp3");
    //player.createPlayer();
  }
  
  public static void loadMp3Plugin() {
    Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
    Format input2 = new AudioFormat(AudioFormat.MPEG);
    Format output = new AudioFormat(AudioFormat.LINEAR);
    PlugInManager.addPlugIn(
      "com.sun.media.codec.audio.mp3.JavaDecoder",
      new Format[]{input1, input2},
      new Format[]{output},
      PlugInManager.CODEC
    );
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