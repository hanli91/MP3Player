import java.awt.Insets;

import javax.media.Format;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel;

public class Main extends JFrame implements Runnable {
  
  private static final long serialVersionUID = 1L;
  private MediaPlayer player;
  public JPanel basePanel;
  private ControllerPanel controllerPanel;
  private PlaylistPanel playlistPanel;
  private LyricPanel lyricPanel;
  
  public static void main(String[] args) {
    Main.setLookAndFeel();
    SwingUtilities.invokeLater(new Main());
  }
  
  public Main() {
    this.player = new MediaPlayer();
    
//    this.basePanel = new JPanel(new GridLayout(5,1));
    /**  base panel set up */
    this.basePanel = new JPanel();
    this.basePanel.setLayout(null);
    this.basePanel.setSize(1080,500);
    
    this.lyricPanel = new LyricPanel(this.player);
    this.controllerPanel = new ControllerPanel(this.player,this.lyricPanel);

    this.playlistPanel = new PlaylistPanel(this.player);
  }
  
  public static void setLookAndFeel() {
    try {
      UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void init() {
    this.setSize(1080, 500);
    this.setResizable(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//     this.addMenuBar();


//    this.add(lyricPanel);
//    this.add(controllerPanel);
    
    this.add(basePanel);
    Insets insets = basePanel.getInsets();

    // Add lyric panel
    this.basePanel.add(playlistPanel);
    playlistPanel.setBounds(0, 0, 216, 400);
    
    // Add lyric panel
    this.basePanel.add(lyricPanel);
    lyricPanel.setBounds(405, 0, 864, 400);
    
    // Add controllerPanel
    this.basePanel.add(controllerPanel);
    controllerPanel.setBounds(0, 400, 1080, 100);

    
    this.setVisible(true);
    String mp3Path = "/Users/haiyun/music/我觉得好听的/王菲 - 匆匆那年.mp3";
    this.player.setAudioPath(mp3Path);
    this.player.createPlayer();
//    this.lyricPanel.updateSliderAndTotalTimeLabel();
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