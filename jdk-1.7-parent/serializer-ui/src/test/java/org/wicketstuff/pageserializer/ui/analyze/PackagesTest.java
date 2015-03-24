package org.wicketstuff.pageserializer.ui.analyze;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


public class PackagesTest {

	@Test
	public void packageListWorks() {
		assertTrue(Packages.packagesOfClassname("").isEmpty());
		assertTrue(Packages.packagesOfClassname("Foo").isEmpty());
		assertEquals("[bla]",Packages.packagesOfClassname("bla.Foo").toString());
		assertEquals("[bla, blub]",Packages.packagesOfClassname("bla.blub.Foo").toString());
	}

	@Test
	public void packageHashGivesStableValuesForSamePackages() {
		List<String> packageParts = Packages.packagesOfClassname("bla.Foo");
		int hash=Packages.packageHashCode(packageParts);
		System.out.println("Hash: "+hash);
		
		packageParts = Packages.packagesOfClassname("bla.blub.Foo");
		hash=Packages.packageHashCode(packageParts);
		System.out.println("Hash: "+hash);
		
		packageParts = Packages.packagesOfClassname("bla.puh.Foo");
		hash=Packages.packageHashCode(packageParts);
		System.out.println("Hash: "+hash);
	}
	
}
