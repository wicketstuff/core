package org.wicketstuff.jwicket;


import org.apache.wicket.Resource;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.markup.html.PackageResource;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;


public class JQueryJavascriptResourceReference extends JQueryResourceReference {

	private static final long serialVersionUID = 1L;


	public JQueryJavascriptResourceReference(final Class<?> scope, final String name) {
		this(scope, name, JQueryResourceReferenceType.OVERRIDABLE);
	}

	public JQueryJavascriptResourceReference(final Class<?> scope, final String name, final JQueryResourceReferenceType type) {
		super(scope, name, type);
	}

	
	
	/**	Copy of {@link JavascriptResourceReference} from Apache Wicket 1.4.7
	 */
	@Override
	protected Resource newResource()
	{
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
