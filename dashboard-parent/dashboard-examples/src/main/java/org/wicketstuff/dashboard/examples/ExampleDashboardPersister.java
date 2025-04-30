package org.wicketstuff.dashboard.examples;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

import org.wicketstuff.dashboard.Dashboard;
import org.wicketstuff.dashboard.DashboardPersister;
import org.wicketstuff.dashboard.DefaultDashboard;
import org.wicketstuff.dashboard.WidgetComparator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

public class ExampleDashboardPersister implements DashboardPersister {

	@Override
	public Dashboard load() {
		return new XStreamDashboardPersister().load();
	}

	@Override
	public void save(Dashboard dashboard) {
		new XStreamDashboardPersister().save(dashboard);
	}

	public static class XStreamDashboardPersister implements DashboardPersister {
		private File file;
		private XStream xstream;

		public XStreamDashboardPersister() {
			this.file = new File("./dashboard.xml");

			xstream = new XStream(new DomDriver(UTF_8.name()));
			xstream.setMode(XStream.NO_REFERENCES);
			xstream.addPermission(NoTypePermission.NONE);
			xstream.addPermission(NullPermission.NULL);
			xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
			xstream.allowTypes(new Class[] {String.class});
			xstream.allowTypesByWildcard(new String[] {"org.wicketstuff.dashboard.**"});
			xstream.allowTypeHierarchy(ArrayList.class);
			xstream.alias("dashboard", DefaultDashboard.class);
		}

		@Override
		public Dashboard load() {
			if (!file.exists() || !file.isFile()) {
				return null;
			}

			try (InputStream is = new FileInputStream(file)) {
				return (Dashboard) xstream.fromXML(is);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public void save(Dashboard dashboard) {
			// sort widgets
			Collections.sort(dashboard.getWidgets(), new WidgetComparator());

			try (OutputStream os = new FileOutputStream(file)) {
				xstream.toXML(dashboard, os);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
