package ch.hslu.mobile.android.server.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * TCP Socket access class. Wraps socket and streams.
 * 
 * @author : aus dem Buch Parallele und Verteilte Anwendungen in Java von Rainer
 *         Oechsle Hanser Verlag ISBN 978-3-446-40714-5
 */
public class TCPSocket {
	private Socket socket;
	private BufferedReader istream;
	private BufferedWriter ostream;

	public TCPSocket(String serverAddress, int serverPort)
			throws UnknownHostException, IOException {
		socket = new Socket(serverAddress, serverPort);
		initializeStreams();
	}

	public TCPSocket(Socket socket) throws IOException {
		this.socket = socket;
		initializeStreams();
	}

	public void sendLine(String s) throws IOException {
		ostream.write(s);
		ostream.newLine();
		ostream.flush();
	}

	public String receiveLine() throws IOException {
		return istream.readLine();
	}

	public String getAddress() {
		String address = "";
		address = "Local  = " + socket.getLocalAddress() + ":"
				+ socket.getLocalPort() + "\n";
		address += "Remote = " + socket.getRemoteSocketAddress() + ":"
				+ socket.getPort();
		return address;
	}

	public void close() throws IOException {
		socket.close();
	}

	private void initializeStreams() throws IOException {
		ostream = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		istream = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
	}
}
