package org.wicketstuff.jslibraries;

import org.apache.wicket.Application;
import org.apache.wicket.ResourceReference;

public class JSReference {

	public static ResourceReference getReference(VersionDescriptor versionDescriptor) {
		Version version = versionDescriptor.getVersion();
		Library lib = versionDescriptor.getLibrary();
		StringBuffer sb = new StringBuffer();
		sb.append("js/").append(lib.getLibraryName()).append('-');
		for (int i = 0; i < version.getNumbers().length; i++) {
			sb.append(version.getNumbers()[i]);
			if (i < (version.getNumbers().length - 1)) {
				sb.append('.');
			}
		}
		if (Application.DEPLOYMENT.equals(Application.get().getConfigurationType())) {
			sb.append(lib.getProductionVersionSignifier());
		}
		sb.append(".js");
		return new ResourceReference(JSReference.class, sb.toString());
	}
}
