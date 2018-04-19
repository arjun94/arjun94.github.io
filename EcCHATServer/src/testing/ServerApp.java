package testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 */

/**
 * 
 * 
 */
public class ServerApp {
	@SuppressWarnings("rawtypes")
	ArrayList clientOutputStreams;

	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket sock;

		public ClientHandler(Socket clientSocket) {

			try {
				sock = clientSocket;
				InputStreamReader isr = new InputStreamReader(
						sock.getInputStream());
				reader = new BufferedReader(isr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("readtyt :" + message);
					tellEveryone(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void tellEveryone(String message) {
			@SuppressWarnings("rawtypes")
			Iterator itr = clientOutputStreams.iterator();
			while (itr.hasNext()) {
				PrintWriter pWriter = (PrintWriter) itr.next();
				pWriter.println(message);
				pWriter.flush();
			}
		}

	}

	public static void main(String[] args) {
		new ServerApp().createSocket();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void createSocket() {
		clientOutputStreams = new ArrayList();
		try {
			ServerSocket socket = new ServerSocket(5000);
			while (true) {
				Socket clientSocket = socket.accept();
				PrintWriter writer = new PrintWriter(
						clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
				System.out.println("Got a Connection to the Client");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}