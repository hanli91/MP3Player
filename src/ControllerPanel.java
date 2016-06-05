import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControllerPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  
  private JButton prevButton;
  private JButton playButton;
  private JButton nextButton;
  private MediaPlayer player;
  private boolean paused;
  
  public ControllerPanel(MediaPlayer player) {
    this.player = player;
    this.paused = true;
    this.setSize(1080, 100);
    GridLayout layout = new GridLayout(1, 6);
    playButton = new JButton("Play");
    prevButton = new JButton("Prev");
    nextButton = new JButton("Next");
    this.setLayout(layout);
    this.add(prevButton);
    this.add(playButton);
    this.add(nextButton);
    
    this.playButtonAddAction();
  }
  
  public void playButtonAddAction() {
    this.playButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (player.getAudioPath() != null) {
          if (paused) {
            paused = false;
            playButton.setText("Pause");
            player.play();
          } else {
            paused = true;
            playButton.setText("Play");
            player.pause();
          }
        }
      }
    });
  }
}