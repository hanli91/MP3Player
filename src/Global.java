import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class Global {
  public static final Font Helvetica = new Font("Helvetica", Font.PLAIN, 12);
  
  public static final Color DEEP_BLUE = new Color(88, 149, 246);
  public static final Color DARK_WHITE = new Color(250, 250, 250);
  
  public static final Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
    new Color(45, 92, 162),
    new Color(43, 66, 97),
    new Color(45, 92, 162),
    new Color(84, 123, 200));
}

enum ButtonType {
    PLAY,
    PREV,
    NEXT;
}