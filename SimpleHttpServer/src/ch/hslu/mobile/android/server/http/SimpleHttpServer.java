package ch.hslu.mobile.android.server.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * A simple extensible HTTP server. Add more request handlers if desired.
 */
public class SimpleHttpServer implements HttpHandler {
	
	private List<AbstractRequestHandler> requestHandlers = new ArrayList<AbstractRequestHandler>();

	private void addRequestHandler(AbstractRequestHandler handler) {
		requestHandlers.add(handler);
	}
	
	private AbstractRequestHandler getResponsibleRequestHandler(String pathSegment) {
		for (AbstractRequestHandler handler : requestHandlers) {
			if (handler.match(pathSegment)) {
				return handler;
			}
		}
		throw new IllegalArgumentException("No handler registered for path segment: "+pathSegment);
	}
	
	public void handle(HttpExchange exchange) {
		String requestPath = exchange.getRequestURI().getPath();
		String pathSegment = requestPath.substring(exchange.getHttpContext().getPath().length());
		
		try {
			getResponsibleRequestHandler(pathSegment).handle(exchange);
		}
		catch (IllegalArgumentException ex) {
			handleNotFound(exchange);
		}
	}
	
	static void handleNotFound(HttpExchange exchange) {
		try {
			exchange.sendResponseHeaders(HttpStatus.SC_NOT_FOUND, 0);
			exchange.getResponseBody().close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
	}
	
	/**
	 * Starts a simple HTTP-Server under address localhost:8080, 8080 is default port, can be set as argument.
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{	 
		SimpleHttpServer simpleServer = new SimpleHttpServer();
		simpleServer.addRequestHandler(new DummyRequestHandler());
		simpleServer.addRequestHandler(new FileRequestHandler());
		
		final int maxBacklogSize = 10; // maximum of pending requets
		int port = 8080;
		if (args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException nfe) {
				// nop.
			}
		}
		HttpServer server = HttpServer.create(new InetSocketAddress(port), maxBacklogSize);
		server.createContext("/hslu", simpleServer);
		server.setExecutor(null); // creates a default executor
		server.start();
		
		InetSocketAddress address = server.getAddress();
		System.out.println("Simple HTTP server is up and running: "+address.getHostName()+":"+address.getPort());
	}
}
