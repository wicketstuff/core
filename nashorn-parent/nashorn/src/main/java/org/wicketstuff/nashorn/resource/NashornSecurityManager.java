package org.wicketstuff.nashorn.resource;

import java.security.Permission;

/**
 * A security manager which is going to be enabled within a nashorn script callable.<br>
 * <br>
 * Example to enable the manager programmatically:
 * 
 * <pre>
 * <code>
 * System.setProperty("java.security.policy", MyClass.class.getResource("nashorn.policy").toString());
 * System.setSecurityManager(new NashornSecurityManager(false));
 * </code>
 * </pre>
 * 
 * See also https://docs.oracle.com/javase/tutorial/essential/environment/security.html<br>
 * <br>
 * Examples for policy file (note that you have to replace the version of the jar file):
 * 
 * <pre>
 * <code>
 * grant {
 *  permission java.security.AllPermission; 
 * };
 * 
 * --- or ---
 * 
 * grant {
 *  permission org.wicketstuff.nashorn.resource.NashornSecurityManagerPermission;
 *  permission java.lang.RuntimePermission "nashorn.setConfig";
 *  permission java.lang.RuntimePermission "createClassLoader";
 *  permission java.io.FilePermission "/Library/Java/JavaVirtualMachines/jdk1.8.0_66.jdk/Contents/Home/jre/lib/ext/nashorn.jar","read";
 *  //permission java.io.FilePermission "${user.home}/git/core/nashorn-parent/nashorn/target/classes/org/wicketstuff/nashorn/resource/esprima.js","read";
 *  //permission java.io.FilePermission "${user.home}/git/core/nashorn-parent/nashorn/target/classes/org/wicketstuff/nashorn/resource/escodegen.browser.js","read";
 *  permission java.io.FilePermission "${user.home}/.m2/repository/org/wicketstuff/wicketstuff-nashorn/7.2.0-SNAPSHOT/wicketstuff-nashorn-7.2.0-SNAPSHOT.jar","read";
 *  permission java.io.FilePermission "/Library/Java/JavaVirtualMachines/jdk1.8.0_66.jdk/Contents/Home/jre/lib/libnio.dylib","read";
 *  permission java.io.FilePermission "/Library/Java/JavaVirtualMachines/jdk1.8.0_66.jdk/Contents/Home/jre/lib/content-types.properties","read";
 *  permission java.lang.RuntimePermission "nashorn.createContext";
 *  permission java.lang.RuntimePermission "accessDeclaredMembers";
 *  permission java.lang.reflect.ReflectPermission "suppressAccessChecks";
 *  permission java.util.PropertyPermission "sun.misc.ProxyGenerator.saveGeneratedFiles","read";
 *  permission java.lang.RuntimePermission "shutdownHooks";
 *  permission java.lang.RuntimePermission "setContextClassLoader";
 *  permission java.util.logging.LoggingPermission "control";
 *  permission java.util.PropertyPermission "sun.util.logging.disableCallerCheck","read";
 *  permission java.util.PropertyPermission "java.util.logging.SimpleFormatter.format","read";
 *  permission java.lang.RuntimePermission "accessClassInPackage.jdk.internal.org.objectweb.asm";
 *  permission java.lang.RuntimePermission "nashorn.createGlobal";
 *  permission java.lang.RuntimePermission "accessClassInPackage.jdk.internal.org.objectweb.asm.util";
 *  permission java.lang.RuntimePermission "accessClassInPackage.jdk.nashorn.internal.runtime";
 *  permission java.lang.RuntimePermission "accessClassInPackage.jdk.nashorn.internal.scripts";
 *  permission java.lang.RuntimePermission "accessClassInPackage.jdk.nashorn.internal.objects";
 *  permission java.lang.RuntimePermission "accessClassInPackage.jdk.nashorn.internal.runtime.linker";
 *  permission java.util.PropertyPermission "java.util.logging.manager", "read";
 *  permission java.lang.RuntimePermission "fileSystemProvider";
 *  permission java.util.PropertyPermission "user.dir", "read";
 *  permission java.util.PropertyPermission "user.home", "read";
 *  permission java.util.PropertyPermission "hotjava.home", "read";
 *  permission java.util.PropertyPermission "java.home", "read";
 *  permission java.util.PropertyPermission "sun.jnu.encoding", "read";
 *  permission java.util.PropertyPermission "java.net.ftp.imagepath.*", "read";
 *  permission java.util.PropertyPermission "sun.nio.fs.chdirAllowed", "read";
 *  permission java.util.PropertyPermission "content.types.user.table", "read";
 *  permission java.util.PropertyPermission "java.nio.file.spi.DefaultFileSystemProvider", "read";
 *  permission java.util.PropertyPermission "content.types.temp.file.template", "read";
 *  permission java.util.PropertyPermission "user.mailcap", "read";
 *  permission java.lang.RuntimePermission "loadLibrary.nio";
 *  permission java.lang.RuntimePermission "setFactory";
 *  permission java.util.PropertyPermission "java.protocol.handler.pkgs","read";
 * };
 * </code>
 * </pre>
 * The permissions can be different depending on the java version which is used.
 * 
 * @author Tobias Soloschenko
 */
public class NashornSecurityManager extends SecurityManager
{

	private static final NashornSecurityManagerPermission TOGGLE_PERMISSION = new NashornSecurityManagerPermission();

	private ThreadLocal<Boolean> enabledFlag = null;

	/**
	 * Creates a new nashorn security manager
	 * 
	 * @param enabledByDefault
	 *            if the security manager should be enabled by default
	 */
	public NashornSecurityManager(final boolean enabledByDefault)
	{

		enabledFlag = new ThreadLocal<Boolean>()
		{

			@Override
			protected Boolean initialValue()
			{
				return enabledByDefault;
			}

			@Override
			public void set(Boolean value)
			{
				SecurityManager securityManager = System.getSecurityManager();
				if (securityManager != null)
				{
					securityManager.checkPermission(TOGGLE_PERMISSION);
				}
				super.set(value);
			}
		};
	}

	/**
	 * Check the permissions based on the given permissions
	 */
	@Override
	public void checkPermission(Permission permission)
	{
		if (shouldCheck(permission))
		{
			super.checkPermission(permission);
		}
	}

	/**
	 * Checks the permissions
	 */
	@Override
	public void checkPermission(Permission permission, Object context)
	{
		if (shouldCheck(permission))
		{
			super.checkPermission(permission, context);
		}
	}

	/**
	 * If the permissions should be checked
	 */
	private boolean shouldCheck(Permission permission)
	{
		return isEnabled() || permission instanceof NashornSecurityManagerPermission;
	}

	/**
	 * Enables the security manager
	 */
	public void enable()
	{
		enabledFlag.set(true);
	}

	/**
	 * Disables the security manager
	 */
	public void disable()
	{
		enabledFlag.set(false);
	}

	/**
	 * Checks if the security manager is enabled
	 * 
	 * @return if the security manager is enabled
	 */
	public boolean isEnabled()
	{
		return enabledFlag.get();
	}

}