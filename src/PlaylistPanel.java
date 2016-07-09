import java.awt.Color;

import javax.swing.JPanel;

public class PlaylistPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  
  public PlaylistPanel() {
    this.setBackground(Color.gray);
    this.setPreferredSize(Global.PLAYLIST_PANEL_SIZE);
  }
}