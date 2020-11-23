package tharsiscampos.poketrader;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class UtilJson {
	
//	private static final Logger logger = Logger.getLogger(UtilJson.class.getName());

	public final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		OBJECT_MAPPER.registerModule(new JavaTimeModule());
	}
	
	public static String converterLegivel(Object o) {
		try {
			return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String converter(Object o) throws JsonProcessingException {
		return OBJECT_MAPPER.writerFor(o.getClass()).writeValueAsString(o);
	}
	
	public static <T> T converter(Class<T> c, String s) throws IOException {
		return OBJECT_MAPPER.readerFor(c).readValue(s);
	}
	
	public static <T> T converter(Class<T> c, Object o) throws IOException {
		return OBJECT_MAPPER.convertValue(o, c);
	}
	
	public static <T> T converter(Class<T> c, JsonNode jsonNode) throws IOException {
		return OBJECT_MAPPER.readerFor(c).readValue(jsonNode);
	}
	
	public static <T> T converter(Class<T> c, InputStream is) throws IOException {
		return OBJECT_MAPPER.readerFor(c).readValue(is);
	}
}