package ch.hslu.mobile.android.server.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

class DummyRequestHandler extends AbstractRequestHandler {

	public DummyRequestHandler() {
		super("/dummy");
	}

	@Override
	protected long prepareContent(Map<String, String> queryParams)
			throws FileNotFoundException {
		return 0;
	}

	@Override
	protected void writeContent(OutputStream out) throws IOException {
		out.write("Dummy-Response".getBytes());
	}
}