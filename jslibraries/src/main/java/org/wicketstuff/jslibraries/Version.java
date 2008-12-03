package org.wicketstuff.jslibraries;

import java.util.Arrays;


public class Version implements Comparable<Version> {

	private final int[] mNumbers;
	
	public Version(int... numbers) {
		mNumbers = numbers;
	}
	
	public int[] getNumbers() {
		return mNumbers;
	}

	public int compareTo(Version other) {
		for (int i = 0; i < mNumbers.length; i++) {
			if (mNumbers[i] != other.mNumbers[i]) {
				return mNumbers[i] - other.mNumbers[i];
			}
			int next = i + 1;
			if (next < mNumbers.length && next >= other.mNumbers.length) {
				// i have another level and the other doesn't, so I come after
				return +1;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(mNumbers);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Version other = (Version) obj;
		if (!Arrays.equals(mNumbers, other.mNumbers))
			return false;
		return true;
	}

}
