import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;

public class PlaylistPanel extends JPanel implements ListSelectionListener {

  private static final long serialVersionUID = 1L;

  private JList playlistBox;
  private MediaPlayer player;
  private JScrollPane listScroller;

  private DefaultListModel listModel;
  

  PlaylistPanel(MediaPlayer player) {
	  this.player = player; 
	  this.setSize(216, 400);
	  this.setBackground(Global.DARK_WHITE);
	  

	  // Create the list with items
	  listModel = new DefaultListModel();
	  listModel.addElement("First song");
	  listModel.addElement("Second song");
	  listModel.addElement("Third song");
	  listModel.addElement("Fourth song");
	  listModel.addElement("Last song");

	  // Create the list and put it in a scroll pane
	  this.playlistBox = new JList(listModel);
	  this.playlistBox.setSelectedIndex(0);
	  this.playlistBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	  this.playlistBox.setLayoutOrientation(JList.VERTICAL);
	  this.playlistBox.addListSelectionListener(this);
	  this.playlistBox.setFont(Global.Helvetica);
//	  this.playlistBox.setVisibleRowCount(5);
	  this.listScroller = new JScrollPane(playlistBox);

	  this.listScroller.setPreferredSize(new Dimension(405,400));
	  
	  this.add(listScroller);

  }

@Override
public void valueChanged(ListSelectionEvent arg0) {
	// TODO Auto-generated method stub
	
}
  
  
}