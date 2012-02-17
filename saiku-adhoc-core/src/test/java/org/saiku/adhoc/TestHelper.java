package org.saiku.adhoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.saiku.adhoc.model.master.SaikuMasterModel;

public class TestHelper {

	public static SaikuMasterModel loadModel(String string) throws IOException {

		InputStream in = TestHelper.class.getResourceAsStream(string);

		StringWriter writer = new StringWriter();
		IOUtils.copy(in, writer, "UTF8");
		String modelString = writer.toString();

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(modelString, SaikuMasterModel.class);

	}

}
