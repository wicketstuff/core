package org.wicketstuff.jwicket;


public class JQueryJavaScriptResourceReference extends JQueryResourceReference {

    private static final long serialVersionUID = 1L;


    public JQueryJavaScriptResourceReference(final Class<?> scope, final String name) {
        this(scope, name, (String) null);
    }

    public JQueryJavaScriptResourceReference(final Class<?> scope, final String name, final String id) {
        this(scope, name, id, JQueryResourceReferenceType.OVERRIDABLE);
    }

    public JQueryJavaScriptResourceReference(final Class<?> scope, final String name, final JQueryResourceReferenceType type) {
        this(scope, name, null, type);
    }

    public JQueryJavaScriptResourceReference(final Class<?> scope, final String name, final String id, final JQueryResourceReferenceType type) {
        super(scope, name, id, type);
    }

//TODO_WICKET15?
//    /**
//     * Copy of {@link JavaScriptResourceReference} from Apache Wicket 1.4.7
//     */
//    @Override
//    protected IResource newResource() {
//        PackageResource packageResource = JavaScriptPackageResource.newPackageResource(getScope(),
//                getName(), getLocale(), getStyle());
//        if (packageResource != null) {
//            locale = packageResource.getLocale();
//        } else {
//            throw new IllegalArgumentException("package resource [scope=" + getScope() + ",name=" +
//                    getName() + ",locale=" + getLocale() + "style=" + getStyle() + "] not found");
//        }
//        return packageResource;
//    }
}
