package org.TranscribeMe.java;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TranscribeMe extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TranscribeMe () {
		
    	buildGUI();
    }	
	
	public void buildGUI() {
		
        
        final CaptureRecording recording = new CaptureRecording();
        final PlaybackRecording playback = new PlaybackRecording();
        final SaveRecording savedRecording = new SaveRecording();
		final Container panel = getContentPane();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        JButton startButton = new JButton("Start Recording");
        JButton stopButton = new JButton("Stop Recording");
        JButton saveButton = new JButton("Save");
        JButton playButton = new JButton("Playback");
                               
        JPanel staves = new JPanel();
        staves.setPreferredSize(new Dimension(120, 120));
        staves.setBorder(LineBorder.createGrayLineBorder());
        staves.setBackground(Color.white);
        
        JPanel waveform = new JPanel();
        waveform.setPreferredSize(new Dimension(120, 120));
        waveform.setBorder(LineBorder.createGrayLineBorder());
        waveform.setBackground(Color.blue);
    
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(layout
        	    .createParallelGroup(GroupLayout.Alignment.LEADING)
        	    .addGroup(layout.createSequentialGroup()
        	        .addComponent(startButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        	        .addComponent(stopButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        	        .addComponent(saveButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        	        .addComponent(playButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        	    .addComponent(staves, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        		.addComponent(waveform));
        
        layout.setVerticalGroup(layout.createSequentialGroup()
        	    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        	        .addComponent(startButton).addComponent(stopButton).addComponent(saveButton).addComponent(playButton))
        	    .addComponent(staves)
        	    .addComponent(waveform));
        
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
        		String message = recording.startRecord();
        		JOptionPane.showMessageDialog(panel, message);
        		
            }
           
        });   
        
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	String message = recording.stopRecord();
        		JOptionPane.showMessageDialog(panel, message);
            	}
        });      
        
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	String message = playback.playback();
        		JOptionPane.showMessageDialog(panel, message);
            }
        });   
        
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	String message = savedRecording.save();
        		JOptionPane.showMessageDialog(panel, message);
            	}
        });   
        
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("File");
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Save");  
        menu.add(menuItem);

        menuItem = new JMenuItem("Exit");
        menu.add(menuItem);
               
        setTitle("Final Year Project: test");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);    
    }
    
    public static void main(String[] args) {
        
       EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TranscribeMe session = new TranscribeMe();
                session.setVisible(true);
            }
        });
    }
}