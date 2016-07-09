import java.awt.Color;
import java.awt.Dimension;

import javax.media.Format;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
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
    this.setSize(Global.FRAME_SIZE.width, Global.FRAME_SIZE.height);
    this.setResizable(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // this.addMenuBar();
    
    JPanel basePanel = new JPanel();
    basePanel.setPreferredSize(Global.FRAME_SIZE);
    this.add(basePanel);
    
    BoxLayout boxLayout = new BoxLayout(basePanel, BoxLayout.Y_AXIS);
    basePanel.setLayout(boxLayout);
    
    JPanel mixedPanel = new JPanel();
    mixedPanel.setPreferredSize(Global.VERTICAL_MIXED_PANEL_SIZE);
    JPanel horizontalborderPanel = new JPanel();
    horizontalborderPanel.setPreferredSize(Global.HORIZONTAL_BORDER_SIZE);
    horizontalborderPanel.setBackground(Global.DEEP_BLUE);
    mixedPanel.add(this.playlistPanel);
    mixedPanel.add(horizontalborderPanel);
    mixedPanel.add(this.lyricPanel);
    
    JPanel VerticalborderPanel = new JPanel();
    VerticalborderPanel.setPreferredSize(Global.VERTICAL_BORDER_SIZE);
    VerticalborderPanel.setBackground(Global.DEEP_BLUE);
    
    basePanel.add(mixedPanel);
    basePanel.add(VerticalborderPanel);
    basePanel.add(controllerPanel);
    
    this.setVisible(true);
    this.player.setAudioPath("./music/ne.mp3");
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