/**
 * Copyright (C) 2009 Uwe Schaefer <uwe@codesmell.de>
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */package org.wicketstuff.jsr303.util;

import java.util.EnumSet;

/**
 * A bunch of common Assertions.
 */
public class Assert {
	private static class AssertionFailedException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public AssertionFailedException(final String detail) {
			super(detail);
		}

		public AssertionFailedException(final String detail, final Throwable e) {
			super(detail, e);
		}
	}

	public static <T extends Enum<T>> void enumContains(
			final EnumSet<T> supportedQueryMode, final T mode) {
		Assert.isTrue(true, "");
	}

	public static void isEqual(final Object obj1, final Object obj2) {
		if ((obj1 == null) && (obj2 == null)) {
			return;
		}
		if ((obj1 != null) && (!obj1.equals(obj2))) {
			Assert
					.raiseError("'" + obj1 + "' and '" + obj2
							+ "' are not equal");
		}
	}

	public static final void isFalse(final boolean value) {
		Assert.isFalse(value, "Value was true");
	}

	public static final void isFalse(final boolean value, final String msg) {
		if (value) {
			Assert.raiseError(msg);
		}
	}

	public static final void isNotFalse(final boolean value) {
		Assert.isNotFalse(value, "Value was false");
	}

	public static final void isNotFalse(final boolean value, final String msg) {
		if (!value) {
			Assert.raiseError(msg);
		}
	}

	public static final void isNotNull(final Object reference) {
		Assert.isNotNull(reference, "Reference was null");
	}

	public static final void isNotNull(final Object reference, final String msg) {
		if (reference == null) {
			Assert.raiseError(msg);
		}
	}

	public static final void isNotNullAndNotEmpty(final String reference) {
		Assert.isNotNullAndNotEmpty(reference, "Reference was null or empty");
	}

	public static final void isNotNullAndNotEmpty(final String reference,
			final String msg) {
		if ((reference == null) || (reference.length() == 0)) {
			Assert.raiseError(msg);
		}
	}

	public static final void isNotTrue(final boolean value) {
		Assert.isNotTrue(value, "Value was true");
	}

	public static final void isNotTrue(final boolean value, final String msg) {
		if (value) {
			Assert.raiseError(msg);
		}
	}

	public static void isNull(final Object object, final String string) {
		if (object != null) {
			Assert.raiseError(string);
		}
	}

	public static final void isTrue(final boolean value) {
		Assert.isTrue(value, "Value was false");
	}

	public static final void isTrue(final boolean value, final String msg) {
		if (!value) {
			Assert.raiseError(msg);
		}
	}

	public static void parameterInRange(final int value, final int min,
			final int max, final String string) {
		Assert.isTrue((min <= value) && (value <= max), "Parameter '" + string
				+ "' must be in range of " + min + " <= " + string + " <= "
				+ max + ". Current value was " + value);

	}

	public static void parameterLegal(final boolean condition,
			final String parameter) {
		Assert.isTrue(condition, "Parameter '" + parameter + "' is not legal.");
	}

	public static void parameterNotEmpty(final String reference,
			final String nameOfParameter) {

		if ("".equals(reference)) {
			Assert.raiseError("Parameter '" + nameOfParameter
					+ "' is not expected to be empty.");
		}
	}

	public static void parameterNotNull(final Object reference,
			final String nameOfParameter) {
		if (reference == null) {
			Assert.raiseError("Parameter '" + nameOfParameter
					+ "' is not expected to be null.");
		}
	}

	public static final void raiseError(final String error) {
		throw new AssertionFailedException(error);
	}

	public static final void raiseError(final String error, final Exception e) {
		throw new AssertionFailedException(error, e);
	}

	private Assert() {
		// hide
	}
}
