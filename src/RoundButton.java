import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

public class RoundButton extends JButton {
  private static final long serialVersionUID = 1L;
  
  private Dimension size;
  private ButtonType type;
  private Polygon poly;
  private int radius;
  
  public RoundButton(String label) {
    super(label);

    Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width, 
      size.height);
    setPreferredSize(size);
    setContentAreaFilled(false);
  }
  
  public RoundButton(String label, int width, int height, int radius, ButtonType type) {
    super(label);    
    this.type = type;
    this.radius = radius;
    this.size = new Dimension();
    this.size.width = width;
    this.size.height = height;
    setPreferredSize(size);
    setContentAreaFilled(false);
    
    switch(this.type) {
      case PLAY:
        poly = buildPlayTriangle(); 
        break;
      case PREV:
        poly = buildPrevTriangle();
        break;
      case NEXT:
        poly = buildNextTriangle();
        break;
    }
  }
  
  private Polygon buildPlayTriangle() {
    int[] xs = new int[3];
    int[] ys = new int[3];
    int length = (int)((int) 8 * 1.732 / 15 * radius);
    int width = this.getPreferredSize().width;
    int height = this.getPreferredSize().height;
    xs[0] = xs[1] = width / 2 - radius * 2 / 5;
    ys[0] = height / 2 - length / 2;
    ys[1] = height / 2 + length / 2;
    xs[2] = width / 2 + radius * 2 / 5;
    ys[2] = height / 2;
    
    int offset = 2 * radius / 15;
    for (int i = 0; i < 3; ++i) {
      xs[i] += offset;
    }
    Polygon poly = new Polygon(xs, ys, 3);
    
    return poly;
  }
  
  private Polygon buildPrevTriangle() {
    int[] xs = new int[3];
    int[] ys = new int[3];
    int length = (int)((int) 4 * 1.732 / 9 * radius);
    int width = this.getPreferredSize().width;
    int height = this.getPreferredSize().height;
    xs[0] = width / 2 - radius / 3;
    ys[0] = height / 2;
    xs[1] = xs[2] = width / 2 + radius / 3;
    ys[1] = height / 2 - length / 2;
    ys[2] = height / 2 + length / 2;
    Polygon poly = new Polygon(xs, ys, 3);
    
    return poly;
  }
  
  private Polygon buildNextTriangle() {
    int[] xs = new int[3];
    int[] ys = new int[3];
    int length = (int)((int) 4 * 1.732 / 9 * radius);
    int width = this.getPreferredSize().width;
    int height = this.getPreferredSize().height;
    xs[0] = xs[1] = width / 2 - radius / 3;
    ys[0] = height / 2 - length / 2;
    ys[1] = height / 2 + length / 2;
    xs[2] = width / 2 + radius / 3;
    ys[2] = height / 2;
    Polygon poly = new Polygon(xs, ys, 3);
    
    return poly;
  }

// Paint the round background and label.
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    
    if (getModel().isArmed()) {
// You might want to make the highlight color 
   // a property of the RoundButton class.
      // g.setColor(Color.lightGray);
      g2.setColor(Global.SUPER_DEEP_BLUE);
    } else {
      g2.setColor(getBackground());
    }
    //g.fillOval(1, 1, getSize().width-3, 
    //  getSize().height-3);
    int width = this.getSize().width;
    int height = this.getSize().height;
    int left = (width - radius * 2) / 2;
    int up = (height - radius * 2) / 2;
    g2.fillOval(left, up, radius * 2, 
      radius * 2);
    
    g2.setColor(Global.DARK_WHITE);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        (RenderingHints.VALUE_ANTIALIAS_ON));
    g2.setStroke(new BasicStroke(2));
    if (poly != null) {
      int xpos = poly.xpoints[0];
      int ymin = poly.ypoints[0];
      int ymax = poly.ypoints[0];
      for (int i = 0; i < 3; ++i) {
        if (ymin > poly.ypoints[i]) {
          ymin = poly.ypoints[i];
        }
        if (ymax < poly.ypoints[i]) {
          ymax = poly.ypoints[i];
        }
      }
      if (this.type == ButtonType.NEXT) {
        for (int i = 0; i < 3; ++i) {
          if (xpos < poly.xpoints[i]) {
            xpos = poly.xpoints[i];
          }
        }
        g2.fillPolygon(poly);
        g2.drawLine(xpos, ymax, xpos, ymin);
          
      } else if(this.type == ButtonType.PREV) {
        for (int i = 0; i < 3; ++i) {
          if (xpos > poly.xpoints[i]) {
            xpos = poly.xpoints[i];
          }
        }
        g2.fillPolygon(poly);
        g2.drawLine(xpos, ymax, xpos, ymin);
      } else {
        g2.setStroke(new BasicStroke(3));
        if (!ControllerPanel.paused) {
          int xmin = width / 2 - radius / 4;
          int xmax = width / 2 + radius / 4;
          int bar = (ymax - ymin) * 2 / 5;
          g2.drawLine(xmin, height / 2 - bar, xmin, height / 2 + bar);
          g2.drawLine(xmax, height / 2 - bar, xmax, height / 2 + bar);
        } else {
          g2.fillPolygon(poly);
        }
      }
    }

// This call will paint the label and the 
   // focus rectangle.
    super.paintComponent(g);
  }

// Paint the border of the button using a simple stroke.
  protected void paintBorder(Graphics g) {
    //g.setColor(getForeground());
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Global.DEEP_BLUE);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        (RenderingHints.VALUE_ANTIALIAS_ON));
    g2.setStroke(new BasicStroke(1));
    int width = this.getSize().width;
    int height = this.getSize().height;
    int left = (width - radius * 2) / 2;
    int up = (height - radius * 2) / 2;
    g2.drawOval(left, up, radius * 2, 
      radius * 2);
  }

  Shape shape;
  public boolean contains(int x, int y) {
// If the button has changed size, 
   // make a new shape object.
    if (shape == null || 
      !shape.getBounds().equals(getBounds())) {
      shape = new Ellipse2D.Float(0, 0, 
        getWidth(), getHeight());
    }
    return shape.contains(x, y);
  }

// Test routine.
//  public static void main(String[] args) {
//// Create a button with the label "Jackpot".
//    JButton button = new RoundButton("Jackpot");
//    button.setBackground(Color.green);
//
//// Create a frame in which to show the button.
//    JFrame frame = new JFrame();
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.getContentPane().setBackground(Color.yellow);
//    frame.getContentPane().add(button);
//    frame.getContentPane().setLayout(new FlowLayout());
//    frame.setSize(150, 150);
//    frame.setVisible(true);
//  }
}
