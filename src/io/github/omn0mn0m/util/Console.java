package io.github.omn0mn0m.util;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class Console extends JFrame implements KeyListener {

	private JTextField prompt;
	private JTextArea log;

	private final PipedInputStream inPipe = new PipedInputStream();
	private final PipedInputStream outPipe = new PipedInputStream();

	private PrintWriter inWriter;

	public Console(String title) {
		super(title);

		System.setIn(inPipe);

		try {
			System.setOut(new PrintStream(new PipedOutputStream(outPipe), true));
			inWriter = new PrintWriter(new PipedOutputStream(inPipe), true);
		} catch (IOException e) {
			System.out.println("Error: " + e);
			return;
		}

		JPanel p = new JPanel();
		p.setLayout(null);
		log = new JTextArea();
		log.setEditable(false);
		log.setBounds(10, 10, 345, 250);
		p.add(log);
		prompt = new JTextField();
		prompt.setBounds(10, 270, 356, 80);
		prompt.addKeyListener(this);
		p.add(prompt);

		getContentPane().add(p);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(392, 400);
		setLocationRelativeTo(null);

		(new SwingWorker<Void, String>() {
			protected Void doInBackground() throws Exception {
				Scanner s = new Scanner(outPipe);
				while (s.hasNextLine()) {
					String line = s.nextLine();
					publish(line);
				}
				return null;
			}

			@Override
			protected void process(java.util.List<String> chunks) {
				for (String line : chunks) {
					if (line.length() < 1)
						continue;
					log.append(line.trim() + "\n");
				}
			}
		}).execute();

	}

	public void execute() {
		String text = prompt.getText();
		prompt.setText("");
		System.out.println(text);
		inWriter.print(text.trim().replaceAll("\r\n", ""));
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			execute();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			execute();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			execute();

	}

	// this is the method called from the original application
	public static void setConsole(final String title) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Console(title);
				// System.out.println("somewhat");
			}
		});

	}

}