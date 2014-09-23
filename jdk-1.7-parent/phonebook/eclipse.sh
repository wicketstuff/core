#/bin/sh
mvn eclipse:eclipse

sed -i -e "s/org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.launching.JRE_CONTAINER\/org.eclipse.jdt.intern al.debug.ui.launcher.StandardVMType\/J2SE-1.5/g" .classpath

