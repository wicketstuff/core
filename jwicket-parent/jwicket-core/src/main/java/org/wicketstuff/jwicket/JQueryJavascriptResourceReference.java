package org.wicketstuff.jwicket;


import org.apache.wicket.Resource;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.markup.html.PackageResource;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;


public class JQueryJavascriptResourceReference extends JQueryResourceReference {

	private static final long serialVersionUID = 1L;


	public JQueryJavascriptResourceReference(final Class<?> scope, final String name) {
		this(scope, name, (String)null);
	}

	public JQueryJavascriptResourceReference(final Class<?> scope, final String name, final String id) {
		this(scope, name, id, JQueryResourceReferenceType.OVERRIDABLE);
	}

	public JQueryJavascriptResourceReference(final Class<?> scope, final String name, final JQueryResourceReferenceType type) {
		this(scope, name, null, type);
	}

	public JQueryJavascriptResourceReference(final Class<?> scope, final String name, final String id, final JQueryResourceReferenceType type) {
		super(scope, name, id, type);
	}

	
	
	/**	Copy of {@link JavascriptResourceReference} from Apache Wicket 1.4.7
	 */
	@Override
	protected Resource newResource() {
		PackageResource packageResource = JavascriptPackageResource.newPackageResource(getScope(),
			getName(), getLocale(), getStyle());
		if (packageResource != null)
		{
			locale = packageResource.getLocale();
		}
		else
		{
			throw new IllegalArgumentException("package resource [scope=" + getScope() + ",name=" +
				getName() + ",locale=" + getLocale() + "style=" + getStyle() + "] not found");
		}
		return packageResource;
	}

}
