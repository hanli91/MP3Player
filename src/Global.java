import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class Global {
  public static final Font Helvetica = new Font("Helvetica", Font.PLAIN, 12);
  
  public static final Color DEEP_BLUE = new Color(88, 149, 246);
  public static final Color SUPER_DEEP_BLUE = new Color(59, 129, 249);
  public static final Color DARK_WHITE = new Color(250, 250, 250);
  
  public static final Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
    new Color(45, 92, 162),
    new Color(43, 66, 97),
    new Color(45, 92, 162),
    new Color(84, 123, 200));
  
  static final int HEIGHT = 500;
  public static final Dimension FRAME_SIZE = new Dimension(1000, HEIGHT+62);
  public static final Dimension VERTICAL_MIXED_PANEL_SIZE = new Dimension(1000, HEIGHT);
  public static final Dimension PLAYLIST_PANEL_SIZE = new Dimension(300, HEIGHT);
  public static final Dimension HORIZONTAL_BORDER_SIZE = new Dimension(2, HEIGHT);
  public static final Dimension LYRIC_PANEL_SIZE = new Dimension(698, HEIGHT);
  public static final Dimension VERTICAL_BORDER_SIZE = new Dimension(1000, 2);
  public static final Dimension CONTROLLER_PANEL_SIZE = new Dimension(1000, 60);
}

enum ButtonType {
    PLAY,
    PREV,
    NEXT;
}