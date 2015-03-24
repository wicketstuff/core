package org.wicketstuff.pageserializer.ui.analyze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public abstract class Packages {

	private Packages() {
		// no instance
	}

	public static int packageHashCode(Iterable<String> packageParts) {
		long hash=0;
		int divisor=1;
		Iterator<String> iterator = packageParts.iterator();
		while (iterator.hasNext()) {
			String value=iterator.next();
			int partHash=scaleHashToHalfMaxInt(value.hashCode());
			hash=hash+partHash/divisor;
			divisor=divisor*10;
		}
		return (int) hash;
	}

	private static int scaleHashToHalfMaxInt(int value) {
		if (value==Integer.MIN_VALUE) {
			return Integer.MAX_VALUE/2;
		}
		if (value<0) {
			return -value/2;
		}
		return value/2;
	}

	public static List<String> packagesOfClassname(String fullClassname) {
		ArrayList<String> ret = new ArrayList<String>();
		String[] parts = fullClassname.split("\\.");
		if (parts.length>1) {
			ret.addAll(Arrays.asList(parts).subList(0, parts.length-1));
		}
		return ret;
	}
}
