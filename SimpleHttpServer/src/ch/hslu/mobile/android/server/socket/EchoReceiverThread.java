package ch.hslu.mobile.android.server.socket;

import java.io.IOException;

/**
 * A receiver thread for a single socket. Returns received request strings (echo
 * behavior). Terminates when it receives the message "EXIT" and closes the
 * socket.
 */
public class EchoReceiverThread extends Thread {
	private final static String EXIT = "EXIT";
	TCPSocket tcpSocket = null;

	// Initialize thread with socket
	public EchoReceiverThread(TCPSocket socket) {
		this.tcpSocket = socket;
	}

	public void run() {

		String address = tcpSocket.getAddress();
		while (tcpSocket != null) { 
			// loop untill exit received
			try {
				System.out.println(address + " wait for request ....");
				String request = tcpSocket.receiveLine();
				String response = createResponseFor(request);
				if (response == null) {
					tcpSocket.sendLine(EXIT);
					tcpSocket.close();
					tcpSocket = null; // cancels the loop
				} else {
					tcpSocket.sendLine(response);
				}
			} catch (IOException e) {
				System.out.println("error socket.receive() " + e.toString());
				tcpSocket = null;
			}
		}
		System.out.println(address + " Receiver-Thread terminated !");
	}

	// Create response for request
	private String createResponseFor(String request) {
		String response = null;
		
		// ECHO message
		if (request != null) {
			if (request.equals(EXIT)) {
				response = null;
			} else {
				System.out.println(tcpSocket.getAddress() + " received '" + request + "'");
				response = "Response from Server: " + request;
			}
		} else {
			System.out.println(tcpSocket.getAddress() + " received = null");
			response = "empty";
		}
		
		return response;
	}
}
