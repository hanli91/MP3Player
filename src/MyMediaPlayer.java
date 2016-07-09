import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;


public class MyMediaPlayer {
  private AdvancedPlayer player = null;
  private String audioPath = null;
  private boolean newCreatedAudio;
  
  private FileInputStream fin = null;
  private BufferedInputStream bin = null;
  
  public int remainedTime;
  public int totalTime;
  public int skipFrame;
  public int playedTimeMilliSec = 0;
  public long startTime, endTime;
  public MyAudioProperty audioProperty = null;
  
  public MyMediaPlayer() {
    audioProperty = new MyAudioProperty();
    remainedTime = 0;
    newCreatedAudio = true;
  }
  
  public static void main(String[] args) throws Exception {
    MyMediaPlayer player = new MyMediaPlayer();
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
    
    this.skipFrame += (Math.round(this.playedTimeMilliSec * this.audioProperty.getFrameRate() / 1000));
    System.out.println(this.skipFrame);
    new MyPlayerThread(this.player, this.skipFrame).start();
  }
  
  public void pause() {
    System.out.println("In stop()");
    if (this.player != null) {
      try {
        this.remainedTime = fin.available();
        //System.out.println("remainedtime:" + this.remainedTime);
      } catch (IOException e) {
        e.printStackTrace();
      }
      this.player.stop();
    }
  }
  
  public void createPlayer() {
    try {
      if (this.newCreatedAudio) {
        this.fin = new FileInputStream(this.audioPath);
        this.bin = new BufferedInputStream(fin);
        this.skipFrame = 0;
        this.totalTime = fin.available();
        this.remainedTime = fin.available();
        this.player = new AdvancedPlayer(bin);
        this.audioProperty.init();
        this.getAudioInfo(this.audioPath);
        this.setNewCreatedAudioFlag(false);
      } else {
        this.fin = new FileInputStream(this.audioPath);
        this.bin = new BufferedInputStream(fin);
        this.player = new AdvancedPlayer(bin);
      }
      this.player.setPlayBackListener(new PlaybackListener() {
        @Override
        public void playbackFinished(PlaybackEvent event) {
            endTime = System.currentTimeMillis();
            playedTimeMilliSec = event.getFrame();
            System.out.println("totalFrame:" + audioProperty.getFrameNum());
            System.out.println("frameRate:" + audioProperty.getFrameRate());
            System.out.println("jlayer played time: " + playedTimeMilliSec);
            System.out.println("system played time: " + (endTime - startTime));
        }
        
        @Override
        public void playbackStarted(PlaybackEvent event) {
          startTime = System.currentTimeMillis();
      }
    });
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
    System.out.println(format.properties());
        
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

class MyPlayerThread extends Thread {
  private AdvancedPlayer player;
  private int startFrame;
  
  public MyPlayerThread(AdvancedPlayer player, int startFrame) {
    this.player = player;
    this.startFrame = startFrame;
  }
  
  @Override
  public void run() {
    try {
      System.out.println(this.player);
      this.player.play(this.startFrame, Integer.MAX_VALUE);
    } catch (JavaLayerException e) {
      e.printStackTrace();
    }
  }
}