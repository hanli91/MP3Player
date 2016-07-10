import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
  private boolean useDefaultLyrics = false;
  private boolean lrcLoaded = false;
		  
  /** Time stored in millisecond */
  private int[] timeLinesArray;
  /** Lyric corresponds to timelinesArray index*/
  private String[] lrcLinesArray;

  
  public LyricPanel(MediaPlayer player) {
	  
	  
	  this.player = player; 
	  // this.setSize(864, 100);
	  this.setPreferredSize(Global.LYRIC_PANEL_SIZE);
	  this.setBackground(Global.DARK_WHITE);
	  this.setLayout(null);
	  
	  this.lyricBox = new JLabel("Lyric Box");
	  this.lyricBox.setFont(Global.Helvetica);
	  this.lyricBox.setFont(new Font("Helvetica", Font.PLAIN, 20));

	  
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

  
  void loadLrcFile(){

	  // Get the lrc file, currently only support lrc with same name
	  String songName = player.getAudioPath();
//	  String songName = "./music/王菲-匆匆那年.mp3";
	  System.out.println("songname"+songName);
	  String lrcName = songName.substring(0, songName.length()-3)+"lrc";
	  ArrayList<Integer> timeLines = new ArrayList<Integer>();
	  ArrayList<String> lrcLines = new ArrayList<String>();
	  try {
		BufferedReader reader = new BufferedReader(new FileReader(lrcName));
		String line;
//		 example
//		 [03:16.43]就像那年匆促
		while ((line = reader.readLine()) != null) {
			// remove any leading and trailing white space
			line = line.trim();
			if ( line.substring(0, 1).equals("[")){
				String timeStr = line.substring(0,10).substring(1, 9);
				int minute = Integer.parseInt(timeStr.substring(0, 2));
				int second = Integer.parseInt(timeStr.substring(3, 5));
				int millisec = Integer.parseInt(timeStr.substring(6,8));
				int time = (minute * 60 + second ) * 1000 + millisec;
				timeLines.add(time);
				lrcLines.add(line.substring(10));
			}
		}
		reader.close();
		// Copy lyrics to array for quicker access
		lrcLinesArray = new String[lrcLines.size()];
		timeLinesArray = new int[lrcLines.size()];
		for ( int i = lrcLinesArray.length-1 ; i > -1 ; i-- ){
			lrcLinesArray[i]  = lrcLines.remove(lrcLines.size()-1);
			timeLinesArray[i] = timeLines.remove(timeLines.size()-1);
		}
	  } catch (FileNotFoundException e) {
		// Use default lrc
		useDefaultLyrics = true;		  
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	  lrcLoaded = true;
	  
  }
  void repaint(int currentSecond){
	  int curFrame = this.player.getCurrentFrame();
	  int totalFrame = this.player.getTotalFrames();
//	  if (totalFrame >= curFrame && curFrame >= 0) {		  
//	  }
	  if ( ! lrcLoaded )
		  loadLrcFile();
	  
	  if (!useDefaultLyrics ){
		  int currentMillisec = currentSecond*1000;
		  // find the largest number in timeLineArray that is smaller than currentMillisec
		  int currentline = 0;
		  for (int i = 0 ; i < timeLinesArray.length; i++ ){
			  if (timeLinesArray[i] > currentMillisec ) {
				  break;
			  }
			  currentline = i;
		  }
		  
//		  System.out.println("curFrame: " + curFrame);
//		  System.out.println("curFrame: " + curFrame);
//		  System.out.println("bounds:"+this.getHeight()*curFrame/totalFrame);
		  
		  this.lyricBox.setBounds(100,this.getHeight()*curFrame/totalFrame ,this.getWidth(),100);
		  this.lyricBox.setText(lrcLinesArray[currentline]);
//		  lyricBox.setBounds(0, 0, 216, 400);
		  
	  }
  }
}
