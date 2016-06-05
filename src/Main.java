import javax.media.Format;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel;

public class Main extends JFrame implements Runnable {
  
  private static final long serialVersionUID = 1L;
  private MediaPlayer player;
  private ControllerPanel controllerPanel;
  private PlaylistPanel playlistPanel;
  private LyricPanel lyricPanel;
  
  public static void main(String[] args) {
    Main.setLookAndFeel();
    Main.loadMp3Plugin();
    SwingUtilities.invokeLater(new Main());
  }
  
  public Main() {
    this.player = new MediaPlayer();
    this.controllerPanel = new ControllerPanel(this.player);
    this.playlistPanel = new PlaylistPanel();
    this.lyricPanel = new LyricPanel();    
  }
  
  public static void setLookAndFeel() {
    try {
      UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
    } catch (Exception e) {
      e.printStackTrace();
    }
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
  
  public void init() {
    this.setSize(1080, 720);
    this.setResizable(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.addMenuBar();
    this.add(controllerPanel);
    this.setVisible(true);
    this.player.setAudioPath("./music/hotaru.mp3");
  }
  
  public void addMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu openMenu = new JMenu("Open");
    menuBar.add(openMenu);
    this.setJMenuBar(menuBar);
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    this.init();
  }
}