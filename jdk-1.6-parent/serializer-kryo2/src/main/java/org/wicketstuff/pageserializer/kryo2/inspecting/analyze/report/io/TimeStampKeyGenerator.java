package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.io;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;


public class TimeStampKeyGenerator implements IReportKeyGenerator {

	protected static final String DEFAULT_WITH_MILLISECONDS = "yyyy-MM-dd--hhmmss-SSS";
	private final String _pattern;
	
	public TimeStampKeyGenerator(String pattern) {
		_pattern = pattern;
	}

	@Override
	public String keyOf(ISerializedObjectTree tree) {
		return format(_pattern, new Date());
	}
	
	protected static String format(String pattern, Date currentTime) {
		return new SimpleDateFormat(pattern).format(currentTime);
	}

	public static IReportKeyGenerator withMilliseconds() {
		return new TimeStampKeyGenerator(DEFAULT_WITH_MILLISECONDS);
	}
}
