import java.awt.Color;

import javax.swing.JPanel;

public class LyricPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  
  public LyricPanel() {
    this.setBackground(Color.cyan);
    this.setPreferredSize(Global.LYRIC_PANEL_SIZE);
  }
}