package org.apache.wicket.portlet;

import org.apache.wicket.Component;
import org.apache.wicket.IMarkupIdGenerator;
import org.apache.wicket.Session;
import org.apache.wicket.util.string.Strings;

public class PortletMarkupIdGenerator implements IMarkupIdGenerator {
	@Override
	public String generateMarkupId(Component component, boolean createIfDoesNotExist) {
		Object storedMarkupId = component.getMarkupIdImpl();
		if (storedMarkupId instanceof String) {
			return (String) storedMarkupId;
		}

		if (storedMarkupId == null && createIfDoesNotExist == false) {
			return null;
		}

		Session session = component.getSession();
		int generatedMarkupId = storedMarkupId instanceof Integer ? (Integer) storedMarkupId : session.nextSequenceValue();

		if (generatedMarkupId == 0xAD) {
			// WICKET-4559 skip suffix 'ad' because some ad-blocking solutions may hide the component
			generatedMarkupId = session.nextSequenceValue();
		}

		if (storedMarkupId == null) {
			component.setMarkupIdImpl(generatedMarkupId);
		}

		String markupIdPrefix = "id";
		if (component.getApplication().usesDevelopmentConfig()) {
			// in non-deployment mode we make the markup id include component id
			// so it is easier to debug
			markupIdPrefix = component.getId();
		}

		String markupIdPostfix = Integer.toHexString(generatedMarkupId).toLowerCase();

		String markupId = markupIdPrefix + markupIdPostfix + "_" + ThreadPortletContext.getNamespace();

		// make sure id is compliant with w3c requirements (starts with a letter)
		char c = markupId.charAt(0);
		if (!Character.isLetter(c)) {
			markupId = "id" + markupId;
		}

		// escape some noncompliant characters
		markupId = Strings.replaceAll(markupId, "_", "__").toString();
		markupId = markupId.replace('.', '_');
		markupId = markupId.replace('-', '_');
		markupId = markupId.replace(' ', '_');

		return markupId;
	}
}
