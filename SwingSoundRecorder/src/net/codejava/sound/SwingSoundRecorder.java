package net.codejava.sound;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;



/**
 * A Sound Recorder program in Java Swing.
 * @author www.codejava.net
 *
 */
public class SwingSoundRecorder extends JFrame implements ActionListener {


	private static final long serialVersionUID = 1L;
	
	private JLabel labelInputChoice = new JLabel("1. Input can be recorded and then saved, or chosen as a preset note recording:");
	private JButton buttonRecord = new JButton("Record file");
	private JButton buttonA0 = new JButton("A0");
	private JButton buttonA1 = new JButton("A1");
	private JButton buttonA2 = new JButton("A2");
	private JButton buttonA3 = new JButton("A3");
	private JButton buttonA4 = new JButton("A4");
	private JButton buttonA5 = new JButton("A5");
	private JButton buttonA6 = new JButton("A6");
	private JButton buttonA7 = new JButton("A7");
	private JLabel labelAlgorithmChoice = new JLabel("2. Frequency estimation:");
	private JButton buttonFFT = new JButton("Harmonic Product Spectrum");
	private JButton buttonZeroCrossing = new JButton("Zero Crossing");
	private JButton buttonYIN = new JButton("YIN");
	private JButton buttonAutocorrelation = new JButton("Autocorrelation via FFT");
	private JLabel labelResult = new JLabel("3. The resulting pitch values and confidence measures:");
	private JLabel resultFFT = new JLabel("  HPS: ");
	private JLabel resultCepstrum = new JLabel("  Zero Crossing:");
	private JLabel resultYIN = new JLabel("  YIN:");
	private JLabel resultAutocorrelation = new JLabel("  Autocorrelation via FFT:");
	private JLabel lineBreak = new JLabel("");


	private SoundRecordingUtil recorder = new SoundRecordingUtil();
	private boolean isRecording = false;
	private String saveFilePath;
	private double groundTruth;


	public SwingSoundRecorder() {
		super("Pitch Estimation: Testing Program");
		super.setSize(780,300);
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			    	.addComponent(labelInputChoice)
				    .addGroup(layout.createSequentialGroup()

			    		.addComponent(buttonRecord)
			    		 .addComponent(buttonA0)
			    		 .addComponent(buttonA1)
			    		 .addComponent(buttonA2)
			    		 .addComponent(buttonA3)			    		
			    		 .addComponent(buttonA4)
			    		 .addComponent(buttonA5)
			    		 .addComponent(buttonA6)
			    		 .addComponent(buttonA7))
 			    		.addComponent(labelAlgorithmChoice)
				    .addGroup(layout.createSequentialGroup()				    
			    		.addComponent(buttonFFT)
		 			      .addComponent(buttonZeroCrossing)
		 			      .addComponent(buttonYIN)
		 			      .addComponent(buttonAutocorrelation))
		 			.addComponent(labelResult)
		 			.addComponent(lineBreak)
		 			.addComponent(resultFFT)
		 			.addComponent(resultCepstrum)
		 			.addComponent(resultYIN)
		 			.addComponent(resultAutocorrelation))
			);
			         
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createSequentialGroup()
				         .addComponent(labelInputChoice)
				         .addGroup(layout.createParallelGroup()
				                  .addComponent(buttonRecord)
				                  .addComponent(buttonA0)
				                  .addComponent(buttonA1)
				                  .addComponent(buttonA2)
				                  .addComponent(buttonA3)
				                  .addComponent(buttonA4)
				                  .addComponent(buttonA5)
				                  .addComponent(buttonA6)
				                  .addComponent(buttonA7)))
				    .addComponent(labelAlgorithmChoice)
				    .addGroup(layout.createSequentialGroup()
				    	 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				 			      .addComponent(buttonFFT)
				 			      .addComponent(buttonZeroCrossing)
				 			      .addComponent(buttonYIN)
				 			      .addComponent(buttonAutocorrelation))
				 	.addComponent(labelResult)
				 	.addComponent(lineBreak)
		 			.addComponent(resultFFT)
		 			.addComponent(resultCepstrum)
		 			.addComponent(resultYIN)
				 	.addComponent(resultAutocorrelation))
				 	
				);
		
		buttonRecord.addActionListener(this);
		buttonFFT.addActionListener(this);
		buttonZeroCrossing.addActionListener(this);
		buttonYIN.addActionListener(this);
		buttonAutocorrelation.addActionListener(this);
		
		buttonA0.addActionListener(this);
		buttonA1.addActionListener(this);
		buttonA2.addActionListener(this);
		buttonA3.addActionListener(this);
		buttonA4.addActionListener(this);
		buttonA5.addActionListener(this);
		buttonA6.addActionListener(this);
		buttonA7.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	/**
	 * Handle click events on the buttons.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		JButton button = (JButton) event.getSource();
		if (button == buttonRecord) {
			if (!isRecording) {
				startRecording();
			} else {
				stopRecording();
			}

		}
		else if (button == buttonFFT) {
			
			try {
				double FFTResult = getFFreq.HPS(saveFilePath);
				double FFTConfidence = 1-((Math.abs(groundTruth - FFTResult))/groundTruth);
				resultFFT.setText("  HPS: "+FFTResult+ "Hz with a confidence of "+FFTConfidence);

			} catch (IOException e) {
				e.printStackTrace();
			}

			}
		else if (button == buttonZeroCrossing) {
			
			try {
				double cepstrumResult = getFFreq.zeroCrossing(saveFilePath);
				double cepstrumConfidence = 1-((Math.abs(groundTruth - cepstrumResult))/groundTruth);
				resultCepstrum.setText("  Zero Crossing: "+cepstrumResult+ "Hz with a confidence of "+cepstrumConfidence);

			} catch (IOException e) {
				e.printStackTrace();
			}

			}
		else if (button == buttonYIN)
		{}
		
				
			
		else if (button == buttonAutocorrelation) {
		
			try {
				double autoResult = getFFreq.autocorrelation(saveFilePath);
				double autoConfidence = 1-((Math.abs(groundTruth - autoResult))/groundTruth);
				resultAutocorrelation.setText("  Autocorrelation via FFT: "+autoResult+ "Hz with a confidence of "+autoConfidence);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			}
		else if (button == buttonA0) {
			
			groundTruth = 27.5;
			saveFilePath = "/users/jackmattison/Documents/notes/A0.wav";
			
			}
		else if (button == buttonA1) {
			
			groundTruth = 55;
			saveFilePath = "/users/jackmattison/Documents/notes/A1.wav";
			
			}
		else if (button == buttonA2) {
			
			groundTruth = 110;
			saveFilePath = "/users/jackmattison/Documents/notes/A2.wav";
			
			}
		else if (button == buttonA3) {
			
			groundTruth = 220;
			saveFilePath = "/users/jackmattison/Documents/notes/A3.wav";
			
			}
		else if (button == buttonA4) {
			
			groundTruth = 440;
			saveFilePath = "/users/jackmattison/Documents/notes/A4tensecs.wav";
			
			}
		else if (button == buttonA5) {
			
			groundTruth = 880;
			saveFilePath = "/users/jackmattison/Documents/notes/A5.wav";
			
			}
		else if (button == buttonA6) {
			
			groundTruth = 1760;
			saveFilePath = "/users/jackmattison/Documents/notes/A6.wav";
			
			}
		else if (button == buttonA7) {
			
			groundTruth = 3520;
			saveFilePath = "/users/jackmattison/Documents/notes/A7.wav";
			
			}
		}
	
	/**
	 * Start recording sound, the time will count up.
	 */
	private void startRecording() {
		Thread recordThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					isRecording = true;
					buttonRecord.setText("Stop & Save Recording");
					recorder.start();
					
				} catch (LineUnavailableException ex) {
					JOptionPane.showMessageDialog(SwingSoundRecorder.this,
							"Error", "Could not start recording sound!",
							JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});
		recordThread.start();
		
	}

	/**
	 * Stop recording and save the sound into a WAV file
	 */
	private void stopRecording() {
		isRecording = false;
		try {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			recorder.stop();
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			saveFile();
			buttonFFT.setEnabled(true);
			buttonRecord.setText("Record");

		} catch (IOException ex) {
			JOptionPane.showMessageDialog(SwingSoundRecorder.this, "Error",
					"Error stopping sound recording!",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	/**
	 * Save the recorded sound into a WAV file.
	 */
	public void saveFile() {
		JFileChooser fileChooser = new JFileChooser();
		FileFilter wavFilter = new FileFilter() {
			@Override
			public String getDescription() {
				return "Sound file (*.WAV)";
			}

			@Override
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return true;
				} else {
					return file.getName().toLowerCase().endsWith(".wav");
				}
			}
		};

		fileChooser.setFileFilter(wavFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);

		int userChoice = fileChooser.showSaveDialog(this);
		if (userChoice == JFileChooser.APPROVE_OPTION) {
			saveFilePath = fileChooser.getSelectedFile().getAbsolutePath();
			if (!saveFilePath.toLowerCase().endsWith(".wav")) {
				saveFilePath += ".wav";
			}

			File wavFile = new File(saveFilePath);

			try {
				recorder.save(wavFile);

			} catch (IOException ex) {
				JOptionPane.showMessageDialog(SwingSoundRecorder.this, "Error",
						"Error saving to sound file!",
						JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * launch the program
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SwingSoundRecorder().setVisible(true);
			}
		});
	}

}