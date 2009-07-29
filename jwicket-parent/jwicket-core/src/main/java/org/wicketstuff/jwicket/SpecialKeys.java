package org.wicketstuff.jwicket;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.Request;
import org.apache.wicket.util.string.Strings;


public class SpecialKeys implements Serializable {

	private static final long serialVersionUID = 1L;

	public SpecialKeys(final SpecialKey...additionalSpecialKeys) {
		Collections.addAll(specialKeys, additionalSpecialKeys);
	}

	public SpecialKeys(final Request request) {
		String rawKeys = request.getParameter("keys");
		if (rawKeys != null && rawKeys.length() > 0) {
			String[] strings = Strings.split(rawKeys, ',');
			for (String string : strings)
				specialKeys.add(SpecialKey.getSpecialKey(string));
		}
	}


	private final List<SpecialKey> specialKeys = new ArrayList<SpecialKey>();

	public List<SpecialKey> getSpecialKeys() {
		return specialKeys;
	}

	public void addSpecialKeys(final SpecialKey...additionalSpecialKeys) {
		Collections.addAll(specialKeys, additionalSpecialKeys);
	}

	public void removeSpecialKeys(final SpecialKey...additionalSpecialKeys) {
		for (SpecialKey key : additionalSpecialKeys)
			specialKeys.remove(key);
	}


	public boolean is(final SpecialKey...keys) {
		for (SpecialKey key : keys)
			if (!specialKeys.contains(key))
				return false;
		return true;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (SpecialKey key : specialKeys) {
			if (sb.length() > 0)
				sb.append(',');
			sb.append(key);
		}
			

		return sb.toString();
	}
}
