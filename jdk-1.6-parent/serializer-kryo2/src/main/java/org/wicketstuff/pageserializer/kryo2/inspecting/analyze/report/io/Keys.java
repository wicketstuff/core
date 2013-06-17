package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.io;


public class Keys {
	private Keys() {
		// no instance
	}
	
	public static IReportKeyGenerator join(String separator, IReportKeyGenerator... generator) {
		return new JoiningKeyGenerator(separator, generator);
	}
	
	public static IReportKeyGenerator with(String staticValue) {
		return new StaticKeyGenerator(staticValue);
	}
	
	public static IReportKeyGenerator defaultTimeStamp() {
		return TimeStampKeyGenerator.withMilliseconds();
	}
	
	public static IReportKeyGenerator typeKey() {
		return new TreeTypeKeyGenerator("UNKNOWN");
	}
	
	public static IReportKeyGenerator withNameAndFileExtension(String name, String fileExtension) {
		return join("-",typeKey(),with(name),defaultTimeStamp(),with("."+fileExtension));
	}
}
