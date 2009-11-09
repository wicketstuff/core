package org.wicketstuff.maven;

import org.apache.wicket.util.file.File;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.locator.ResourceStreamLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MavenDevResourceStreamLocator extends ResourceStreamLocator
{
	private static final Logger _logger = LoggerFactory.getLogger(MavenDevResourceStreamLocator.class);
	
	final static String MAVEN_RESOURCE_PATH="src/main/resources/";
	final static String MAVEN_JAVASOURCE_PATH="src/main/java/";
	
	public IResourceStream locate(final Class<?> clazz, final String path)
	{
		IResourceStream located=getFileSysResourceStream(MAVEN_RESOURCE_PATH,path);
		if (located==null)
		{
			located=getFileSysResourceStream(MAVEN_JAVASOURCE_PATH,path);
		}
		if (located != null)
		{
			_logger.info("Locate: {} in {} -> {}",new Object[] {clazz,path,located});
			return located;
		}
		located=super.locate(clazz, path);
		_logger.info("Locate: {} in {} -> {}(Fallback)",new Object[] {clazz,path,located});		
		return located;
	}

	private static IResourceStream getFileSysResourceStream(String prefix, String path)
	{
		File f=new File(prefix+path);
		if ((f.exists()) && (f.isFile()))
		{
			return new FileResourceStream(f);
		}
		return null;
	}
}
