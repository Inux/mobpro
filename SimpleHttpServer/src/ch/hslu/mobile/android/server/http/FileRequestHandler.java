package ch.hslu.mobile.android.server.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.IOUtils;


class FileRequestHandler extends AbstractRequestHandler {
	
	private String fileName;
	
	public FileRequestHandler() {
		super("/file");
	}

	@Override
	protected long prepareContent(Map<String, String> queryParams) throws FileNotFoundException {
		fileName = queryParams.get("name");
		if (fileName != null) {
			URL imageUrl = FileRequestHandler.class.getResource("res/"+fileName);
			if (imageUrl != null) {
				return 0;
			}
		}
		throw new FileNotFoundException(fileName);
	}

	@Override
	protected void writeContent(OutputStream out) throws IOException {
		InputStream in = null;
		try {
			in = FileRequestHandler.class.getResourceAsStream("res/"+fileName);
			IOUtils.copy(in, out);
		}
		finally {
			IOUtils.closeQuietly(in);
		}
	}
}