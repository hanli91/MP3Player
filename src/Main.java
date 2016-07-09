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

  public void init() {
    this.setSize(1080, 300);
    this.setResizable(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // this.addMenuBar();
    this.add(controllerPanel);
    this.setVisible(true);
    String mp3Path = "/Users/haiyun/music/我觉得好听的/王菲 - 匆匆那年.mp3";
    this.player.setAudioPath(mp3Path);
    this.player.createPlayer();
    this.controllerPanel.updateSliderAndTotalTimeLabel();
  }
  
  public void addMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu openMenu = new JMenu("Open");
    openMenu.setFont(Global.Helvetica);
    menuBar.add(openMenu);
    this.setJMenuBar(menuBar);
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    this.init();
  }
}