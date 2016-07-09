public class MyAudioProperty {
  private String title;
  private String artist;
  private byte[] image;
  private long duration;
  private int frameNum;
  private float frameRate;
  
  public void init() {
    this.title = null;
    this.artist = null;
    this.image = null;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setArtist(String artist) {
    this.artist = artist;
  }
  
  public String getArtist() {
    return this.artist;
  }
  
  public void setImage(byte[] image) {
    this.image = image;
  }
  
  public void setDuration(long duration) {
    this.duration = duration;
  }
  
  public long getDuration() {
      return this.duration;
  }
  
  public void setFrameNum(int frameNum) {
    this.frameNum = frameNum;
  }
  
  public int getFrameNum() {
    return this.frameNum;
  }
  
  public void setFrameRate(float frameRate) {
    this.frameRate = frameRate;
  }
  
  public float getFrameRate() {
    return this.frameRate;
  }
}
