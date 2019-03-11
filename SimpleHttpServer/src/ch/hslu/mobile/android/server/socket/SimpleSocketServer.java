package ch.hslu.mobile.android.server.socket;

import java.net.ServerSocket;

/**
 * A simple socket server.
 */
public class SimpleSocketServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		TCPSocket tcpSocket = null;
		int port = 4711;

		if (args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException nfe) {
				// nop.
			}
		}

		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Connection-Server started at port '" + port + "'...........");
		} catch (Exception e) {
			System.err.println("Could not create server socket.");
			e.printStackTrace();
		}

		// loop forever
		while (serverSocket != null) {
			try {
				System.out.println("Waiting for connection: "
						+ serverSocket.getInetAddress().toString());
				tcpSocket = new TCPSocket(serverSocket.accept());
				System.out.println("Connection to: " + tcpSocket.getAddress());

				// Create dedicated handler thread for connection
				new EchoReceiverThread(tcpSocket).start();
			} catch (Exception e) {
				System.out.println("Error accept ServerSocket " + e.toString());
			}
		}
		System.out.println("...... terminated Connection-Server!");
	}

}
