package org.wicketstuff.minis.request.target.coding;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.application.DefaultClassResolver;
import org.apache.wicket.application.IClassResolver;
import org.apache.wicket.request.target.coding.PackageRequestTargetUrlCodingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

/**
 * Class resolver that wraps a delegate {@link DefaultClassResolver}.  If the
 * delegate cannot resolve the class, this class tries to resolve the class
 * from a case insensitive name.  Useful for {@link PackageRequestTargetUrlCodingStrategy}
 * and letting url paths be completely case-insensitive.
 *   
 * @author mhanlon
 *
 */
public class CaseInsensitiveClassResolver implements IClassResolver {

	private static final Logger logger = LoggerFactory.getLogger(CaseInsensitiveClassResolver.class);
	
	private DefaultClassResolver resolver = new DefaultClassResolver();
	
	public Iterator<URL> getResources(String name) {
		return resolver.getResources(name);
	}
	
	private Map<String, Map<String, Class<?>>> cache = new HashMap<String, Map<String,Class<?>>>();
	
	public Class<?> resolveClass(String classname) throws ClassNotFoundException {
		Class<?> clazz = null;
		try {
			clazz = resolver.resolveClass(classname);
		} catch (ClassNotFoundException e1) {
			clazz = resolveClassCaseInsensitive(classname);
		} catch (NoClassDefFoundError e2) {
			clazz = resolveClassCaseInsensitive(classname);
		}
		if (clazz == null) {
			throw new ClassNotFoundException("Unable to resolve class for name " + classname);
		}
		return clazz;
	}
	
	public Class<?> resolveClassCaseInsensitive(String classname) {
		if (logger.isDebugEnabled()) {
			logger.debug("Class not found for " + classname + ".  Trying to look up case-insensitive.");
		}
		String packageName = classname.substring(0, classname.lastIndexOf('.'));
		
		if (! cache.containsKey(packageName)) {
			cache.put(packageName, scan(getPatternForPackage(packageName)));
		}
		
		return cache.get(packageName).get(classname.toLowerCase());
	}
	
	/**
     * Get the Spring search pattern given a package name or part of a package name
     * @param packageName a package name
     * @return a Spring search pattern for the given package
     */
    public String getPatternForPackage(String packageName)
    {
        if (packageName == null) packageName = "";
        packageName = packageName.replace('.', '/');
        if (!packageName.endsWith("/"))
        {
            packageName += '/';
        }

        return "classpath*:" + packageName + "**/*.class";
    }
    
    /**
     * 
     * @param pattern
     */
    private Map<String, Class<?>> scan(final String pattern) {
    	Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		
    	PathMatchingResourcePatternResolver match = new PathMatchingResourcePatternResolver();
    	Resource[] resources;
    	try {
            resources = match.getResources(pattern);
            logger.debug("Found " + resources.length + " resource(s) for: " + pattern);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MetadataReaderFactory f = new SimpleMetadataReaderFactory();
		for (Resource r : resources) {
			MetadataReader meta = null;
            try
            {
                meta = f.getMetadataReader(r);
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to get MetadataReader for " + r, e);
            }
            try {
	            ClassMetadata cmd = meta.getClassMetadata();
	            String classname = cmd.getClassName();
	            try {
	            	classMap.put(classname.toLowerCase(), getClass().getClassLoader().loadClass(classname));
				} catch (ClassNotFoundException e) {
					logger.error("Error loading class for name " + classname);
				}
            } catch (Throwable e) {
				logger.error("Unknown Error.", e);
            }
		}
		return classMap;
	}

}
