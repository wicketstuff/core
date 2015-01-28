package org.wicketstuff.html5.shape;

public interface Shape {

	String getName();

	String getValues();

	String getTransitionTime();

	Shape transitionTime(String transitionTime);
}
