package org.wicketstuff.pageserializer.ui.colors;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;
import org.wicketstuff.pageserializer.ui.colors.Colors;


public class ColorsTest {

	static final Colors.HSB hsb=Colors.hsb(0f, 1f, 1f);
	@Test
	public void colorForHashZeroShouldGiveRed() {
		Color result = Colors.colorFromHashcode(0, 10,hsb);
		assertEquals(255, result.getRed());
		assertEquals(0, result.getBlue());
		assertEquals(0, result.getGreen());
	}

	@Test
	public void colorForHashMaxShouldGiveRed() {
		Color result = Colors.colorFromHashcode(10, 10,hsb);
		assertEquals(255, result.getRed());
		assertEquals(0, result.getBlue());
		assertEquals(0, result.getGreen());
	}
	
	@Test
	public void colorForHalfMaxShouldGiveOpositeOfRed() {
		Color result = Colors.colorFromHashcode(5, 10,hsb);
		assertEquals(0, result.getRed());
		assertEquals(255, result.getBlue());
		assertEquals(255, result.getGreen());
	}

	@Test
	public void colorForQuarterShouldGivSomething() {
		Color result = Colors.colorFromHashcode(2, 10,hsb);
		assertEquals(204, result.getRed());
		assertEquals(0, result.getBlue());
		assertEquals(255, result.getGreen());
	}

	@Test
	public void colorForQuarterShouldGivSomethingX() {
		int max=100;
		for (int i=0;i<max;i++) {
			Color result = Colors.colorFromHashcode(i, max,hsb);
			System.out.println(i+":"+result);
		}
	}
	
	@Test
	public void colorToHexShoulGiveValidRGBHexCode() {
		Color src=new Color(0,128,255);
		assertEquals("0080ff", Colors.asRGBHex(src));
	}
}