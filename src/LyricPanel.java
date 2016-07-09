
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javazoom.jl.decoder.JavaLayerException;



public class LyricPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private JLabel lyricBox;
  private MediaPlayer player;
  
  public LyricPanel(MediaPlayer player) {
	  this.player = player; 
	  this.setSize(864, 100);
	  this.setBackground(Global.DARK_WHITE);
	  
	  this.lyricBox = new JLabel("Lyric Box");
	  
	  this.lyricBox.setFont(Global.Helvetica);
	  lyricBox.setText("Hahaha");
	  this.add(lyricBox);

	  
  }

  public void updateSliderAndTotalTimeLabel() {
	    int duration = (int) player.audioProperty.getDuration() / 1000000;
	    int duraMin = (int) duration / 60;
	    int duraSec = (int) duration % 60;
	    lyricBox.setText(String.format("%02d:%02d", duraMin, duraSec));
//	    lyricBox.setText("Hahahaha");

	  }

  void repaint(int value){
		lyricBox.setText(Integer.toString(value));
	}

  
}

	

class LyricThread extends Thread {
  MediaPlayer player;
  JLabel lyricBox;
  
  public LyricThread(MediaPlayer player, JSlider slider, JLabel playedTimeLabel) {
    this.player = player;
   
  }
  
  @Override
  public void run() {
    while (!ControllerPanel.paused && !ControllerPanel.toggled) {
      int curFrame = player.getCurrentFrame();
      int totalFrame = player.getTotalFrames();
      if (totalFrame >= curFrame && curFrame >= 0) {
        int value = (int) (curFrame * 1.0 / totalFrame );
        this.lyricBox.setText(String.format("%02d:%02d", value / 60, value % 60));
        
//        this.playedTimeLabel.setText(String.format("%02d:%02d", value / 60, value % 60));
       
        
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
  }
}


