package org.wicketstuff.jslibraries;


public class Version implements Comparable<Version> {

	private final int[] mNumbers;
	
	public Version(int... numbers) {
		mNumbers = numbers;
	}
	
	public int[] getNumbers() {
		return mNumbers;
	}

	public int compareTo(Version other) {
		if (other.mNumbers.length != mNumbers.length) {
			throw new NullPointerException("other version does not have same depth as this version [this: " + this + "; other: " + other + "]");
		}
		for (int i = 0; i < mNumbers.length; i++) {
			if (mNumbers[i] != other.mNumbers[i]) {
				return mNumbers[i] - other.mNumbers[i];
			}
		}
		return 0;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("v");
		for (int num : mNumbers) {
			sb.append(num).append('.');
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}
	
}
