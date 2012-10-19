package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;


public class Reports {
	private static final Ident NO_IDENT = new Ident(0,' ');

	private Reports() {
		// no instance
	}
	
	public static void label(StringBuilder sb, String label, int columnSize, char filler) {
		label(sb,NO_IDENT,label,columnSize,filler);
	}
	
	public static void label(StringBuilder sb, Ident ident, String label, int columnSize, char filler) {
		for (int i=0;i<ident.size();i++) {
			sb.append(ident.value());
		}
		int left=columnSize-ident.size();
		int labelLength = label.length();
		if (labelLength>left) {
			sb.append(label.substring(0,left));
		} else {
			sb.append(label);
			for (int i=0;i<(left-labelLength);i++) {
				sb.append(filler);
			}
		}
	}

	public static void rightColumn(StringBuilder sb, int columnSize, char filler, String value, char overflow) {
		if (value!=null) {
			int left=columnSize-value.length();
			if (left>=0) {
				for (int i=0;i<left;i++) {
					sb.append(filler);
				}
				sb.append(value);
			} else {
				sb.append(overflow);
				sb.append(value.substring(value.length()-(columnSize-1)));
			}
		}
	}
	
	public static class Ident {
		private final int count;
		private final char c;

		public Ident(int count, char c) {
			this.count = count;
			this.c = c;
		}
		
		public int size() {
			return count;
		}
		
		public char value() {
			return c;
		}
	}
}
