package ch.hslu.mobile.android.server.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import com.sun.net.httpserver.HttpExchange;

abstract class AbstractRequestHandler {
	private final String pathToHandle;
	
	/**
	 * @param pathToHandle below context, e.g. "/foo"
	 */
	protected AbstractRequestHandler(String pathToHandle) {
		this.pathToHandle = pathToHandle;
	}
	
	protected boolean match(String path) {
		return pathToHandle.equalsIgnoreCase(path);
	}
	
	final void handle(HttpExchange exchange) {
		long contentLength = 0;
		try {
			List<NameValuePair> queryParams = URLEncodedUtils.parse(exchange.getRequestURI(), "UTF-8");
			contentLength = prepareContent(makeMap(queryParams));
		}
		catch (FileNotFoundException ex) {
			SimpleHttpServer.handleNotFound(exchange);
			return;
		}
		
		try {
			exchange.sendResponseHeaders(200, contentLength); // OK
			OutputStream out = exchange.getResponseBody();
			writeContent(out);
			out.flush();
			out.close();
		} 
		catch (IOException ex) {
			// log on server - client is lost (headers already sent or sending failed)
			System.err.println(ex);
		}
	}

	private Map<String, String> makeMap(List<NameValuePair> queryParams) {
		Map<String, String> params = new HashMap<String, String>();
		for (NameValuePair queryParam : queryParams) {
			params.put(queryParam.getName(), queryParam.getValue());
		}
		return params;
	}

	/** 
	 * Identify content to be delivered and return length of requested content. Return 0 if length is unknown. 
	 * @param queryParams
	 * @return length of content in bytes or 0 if unknown
	 * @throws FileNotFoundException if content can not be identified/found
	 */
	protected abstract long prepareContent(Map<String, String> queryParams) throws FileNotFoundException;
	
	/**
	 * Write content previously identified in {@link #prepareContent(List)}. Must write exactly
	 * as many bytes as predicted there (unless 0 was returned).
	 * @param out
	 * @throws IOException
	 */
	protected abstract void writeContent(OutputStream out) throws IOException;
}