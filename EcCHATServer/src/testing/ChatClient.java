package testing;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 * 
 */

/**
 * @author Vishnu C Nair
 * 
 */
public class ChatClient {
	JTextField outgoing;
	JTextArea incoming;
	BufferedReader reader;
	PrintWriter writer;
	Socket sock;

	public void layOutDesign() throws IOException {
		JFrame frame = new JFrame("Chat Client");
		JPanel mainPanel = new JPanel();
		
		
		/*BufferedImage img = ImageIO.read(new File("E:\\vis.jpg"));
        //ImageIcon icon = new ImageIcon(img);
        BufferedImage resizedImage = new BufferedImage(150, 110, BufferedImage.TYPE_INT_ARGB);  
        Graphics2D g = resizedImage.createGraphics();  
        g.drawImage(img, 0, 0, 150, 110, null);  
        g.dispose();  
        ImageIcon icon1 = new ImageIcon(resizedImage);
        JLabel label = new JLabel(icon1);
        label.setSize(3, 3);
        JOptionPane.showMessageDialog(null, label);
        mainPanel.add(label);*/
		
		
        incoming = new JTextArea(15, 25);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane qScroller = new JScrollPane(incoming);
		qScroller
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outgoing = new JTextField(20);
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());
		mainPanel.add(qScroller);
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		setupNetworking();
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(400, 500);
		frame.setVisible(true);
	}

	private void setupNetworking() {
		// TODO Auto-generated method stub
		try {
			sock = new Socket("192.168.2.16", 5000);
			InputStreamReader isR = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(isR);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("Network Established.");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class SendButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ev) {
			try {
				writer.println("amn:"+outgoing.getText());
				writer.flush();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			outgoing.setText("");
			outgoing.requestFocus();
		}

	}

	public static void main(String[] args) throws IOException {
		new ChatClient().layOutDesign();
	}

	public class IncomingReader implements Runnable {

		String message;

		@Override
		public void run() {
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("Readddf :" + message);
					incoming.append(message + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
