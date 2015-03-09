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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
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
	
	private JLabel labelInputChoice = new JLabel("1. Input can be recorded and then saved, or loaded straight from a file:");
	private JButton buttonRecord = new JButton("Record file");
	private JButton buttonOpen = new JButton("Open file");
	private JLabel labelAlgorithmChoice = new JLabel("2. Frequency estimation:");
	private JButton buttonFFT = new JButton("FFT");
	private JButton buttonCepstrum = new JButton("Cepstrum");
	private JButton buttonAdaptiveFiltering = new JButton("Adaptive Filtering");
	private JButton buttonAutocorrelation = new JButton("Autocorrelation");
	private JLabel labelResult = new JLabel("3. The resulting pitch values and confidence measures:");
	private JLabel resultFFT = new JLabel("  FFT: ");
	private JLabel resultCepstrum = new JLabel("  Cepstrum:");
	private JLabel resultAdaptiveFiltering = new JLabel("  Adaptive Filtering:");
	private JLabel resultAutocorrelation = new JLabel("  Autocorrelation:");
	private JLabel lineBreak = new JLabel("");


	private SoundRecordingUtil recorder = new SoundRecordingUtil();
	private boolean isRecording = false;
	private String saveFilePath;

	public SwingSoundRecorder() {
		super("Pitch Estimation: Testing Program");
		super.setSize(600,300);
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			    	.addComponent(labelInputChoice)
				    .addGroup(layout.createSequentialGroup()

			    		.addComponent(buttonRecord)
			    		 .addComponent(buttonOpen))

 			    		.addComponent(labelAlgorithmChoice)
				    .addGroup(layout.createSequentialGroup()				    
			    		.addComponent(buttonFFT)
		 			      .addComponent(buttonCepstrum)
		 			      .addComponent(buttonAdaptiveFiltering)
		 			      .addComponent(buttonAutocorrelation))
		 			.addComponent(labelResult)
		 			.addComponent(lineBreak)
		 			.addComponent(resultFFT)
		 			.addComponent(resultCepstrum)
		 			.addComponent(resultAdaptiveFiltering)
		 			.addComponent(resultAutocorrelation))
			);
			         
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createSequentialGroup()
				         .addComponent(labelInputChoice)
				         .addGroup(layout.createParallelGroup()
				                  .addComponent(buttonRecord)
				                  .addComponent(buttonOpen)))
				    .addComponent(labelAlgorithmChoice)
				    .addGroup(layout.createSequentialGroup()
				    	 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				 			      .addComponent(buttonFFT)
				 			      .addComponent(buttonCepstrum)
				 			      .addComponent(buttonAdaptiveFiltering)
				 			      .addComponent(buttonAutocorrelation))
				 	.addComponent(labelResult)
				 	.addComponent(lineBreak)
		 			.addComponent(resultFFT)
		 			.addComponent(resultCepstrum)
		 			.addComponent(resultAdaptiveFiltering)
				 	.addComponent(resultAutocorrelation))
				 	
				);
		
		buttonFFT.setEnabled(false);
		buttonRecord.addActionListener(this);
		buttonFFT.addActionListener(this);
		buttonCepstrum.addActionListener(this);
		buttonAdaptiveFiltering.addActionListener(this);
		buttonAutocorrelation.addActionListener(this);

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
				double FFTResult = getFFreq.FFT(saveFilePath);
				resultFFT.setText("  FFT: "+FFTResult+ "Hz with a confidence of 0.");

			} catch (IOException e) {
				e.printStackTrace();
			}

			}
		else if (button == buttonCepstrum) {
			
			try {
				double cepstrumResult = getFFreq.cepstrum(saveFilePath);
				resultCepstrum.setText("  Cepstrum: "+cepstrumResult+ "Hz with a confidence of 0.");

			} catch (IOException e) {
				e.printStackTrace();
			}

			}
		else if (button == buttonAdaptiveFiltering) {
	
			try {
				double adaptiveResult = getFFreq.adaptiveFilter(saveFilePath);
				resultAdaptiveFiltering.setText("  Adaptive Filtering: "+adaptiveResult+ "Hz with a confidence of 0.");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			}
		else if (button == buttonAutocorrelation) {
		
			try {
				double autoResult = getFFreq.autocorrelation(saveFilePath);
				resultAutocorrelation.setText("  Autocorrelation: "+autoResult+ "Hz with a confidence of 0.");

			} catch (IOException e) {
				e.printStackTrace();
			}
			
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