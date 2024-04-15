package com.googlecode.wicket.jquery.ui.samples.data.dao;

import java.util.List;

import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.ui.samples.data.bean.Band;
import com.googlecode.wicket.kendo.ui.utils.TreeNodeUtils;
import com.googlecode.wicket.kendo.ui.widget.treeview.TreeNode;

public class BandsDAO
{
	private static BandsDAO instance = null;

	private final List<TreeNode<?>> list;

	public BandsDAO()
	{
		this.list = Generics.newArrayList();

		TreeNode<String> ar = TreeNode.of("Argentina");
		this.list.add(ar);
		TreeNode<String> au = TreeNode.of("Australia");
		this.list.add(au);
		TreeNode<String> ca = TreeNode.of("Canada");
		this.list.add(ca);
		TreeNode<String> dk = TreeNode.of("Denmark");
		this.list.add(dk);
		TreeNode<String> fi = TreeNode.of("Finland");
		this.list.add(fi);
		TreeNode<String> fr = TreeNode.of("France");
		this.list.add(fr);
		TreeNode<String> de = TreeNode.of("Germany");
		this.list.add(de);
		TreeNode<String> ie = TreeNode.of("Ireland");
		this.list.add(ie);
		TreeNode<String> it = TreeNode.of("Italy");
		this.list.add(it);
		TreeNode<String> jp = TreeNode.of("Japan");
		this.list.add(jp);
		TreeNode<String> nl = TreeNode.of("Netherlands");
		this.list.add(nl);
		TreeNode<String> pl = TreeNode.of("Poland");
		this.list.add(pl);
		TreeNode<String> za = TreeNode.of("South Africa");
		this.list.add(za);
		TreeNode<String> es = TreeNode.of("Spain");
		this.list.add(es);
		TreeNode<String> se = TreeNode.of("Sweden");
		this.list.add(se);
		TreeNode<String> ch = TreeNode.of("Switzerland");
		this.list.add(ch);
		TreeNode<String> uk = TreeNode.of("United Kingdom");
		this.list.add(uk);
		TreeNode<String> us = TreeNode.of("United States");
		this.list.add(us);

		this.list.add(new BandTreeNode(jp.getId(), new Band("44 Magnum", "1977–1989, 2002–present")));
		this.list.add(new BandTreeNode(se.getId(), new Band("220 Volt", "1979–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("A II Z", "1979-1982")));
		this.list.add(new BandTreeNode(au.getId(), new Band("AC/DC", "1973–present")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Accept", "1976–1989, 1992–1997, 2004–2005, 2009–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Aerosmith", "1970–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Alice Cooper", "1968–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Angel", "1975–1981, 1987, 1998–present")));
		this.list.add(new BandTreeNode(es.getId(), new Band("Ángeles del Infierno", "1978–present")));
		this.list.add(new BandTreeNode(au.getId(), new Band("The Angels", "1974-2000, 2008–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Angel Witch", "1977-1982, 1984-1998, 2000–present")));
		this.list.add(new BandTreeNode(jp.getId(), new Band("Anthem", "1980–1992, 2000–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Anthrax", "1981–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Avenged Sevenfold", "1999–present")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("Anvil", "1978–present")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("April Wine", "1969-1986, 1992–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Armageddon", "1974–1976")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Atomic Rooster", "1969–1975, 1980–1983")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Atomkraft", "1979–1988, 2005")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Attila", "1969-1970")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Axe", "1979-1984, 1997-2004")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Babe Ruth", "1970-1976, 2005–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Bang", "1970–1973, 2001–2004")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Barnabas", "1977-1986")));
		this.list.add(new BandTreeNode(es.getId(), new Band("Barón Rojo", "1980–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Battleaxe", "1980–1988, 2010–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("The Beatles", "1960-1970")));
		this.list.add(new BandTreeNode(au.getId(), new Band("Bengal Tigers", "1979–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Beowülf", "1981–1995, 2000–present")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Birth Control", "1966-1983, 1993-2014")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Bitch", "1980–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Black Death", "1977–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Black 'n Blue", "1981–1989, 1997, 2003, 2007–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Black Rose", "1980-1989, 2006–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Black Sabbath", "1968–2006, 2011–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Black Widow", "1966–1973, 2007–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Blitzkrieg", "1980–1981, 1984–1991, 1992–1994, 1996–1999, 2001–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Bloodrock", "1969–1975")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Blue Cheer", "1966–1972, 1974–1976, 1978–1979, 1984–1994, 1999–2009")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Blue Öyster Cult", "1967–present")));
		this.list.add(new BandTreeNode(nl.getId(), new Band("Bodine", "1978-1984")));
		this.list.add(new BandTreeNode(au.getId(), new Band("Boss", "1979-1986")));
		this.list.add(new BandTreeNode(jp.getId(), new Band("Bow Wow", "1976–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Bronz", "1976-1985, 1999-2000, 2003–2005, 2010–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Edgar Broughton Band", "1968–2010")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Budgie", "1967–present")));
		this.list.add(new BandTreeNode(au.getId(), new Band("Buffalo", "1971–1977")));
		this.list.add(new BandTreeNode(it.getId(), new Band("Bulldozer", "1980–1990, 2008–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Cactus", "1970–1972, 2006–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Captain Beyond", "1971-1973, 1976-1978, 1998-2003")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Chateaux", "1981-1985")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Cirith Ungol", "1972–1992")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Cloven Hoof", "1979–1990, 2000–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Coven", "1969–1975, 2007–2008")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Cream", "1966–1969")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Crimson Glory", "1979–1992, 1998–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Crushed Butler", "1969-1971")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Dark Angel", "1981–1992, 2002–2005, 2013–present")));
		this.list.add(new BandTreeNode(it.getId(), new Band("Death SS", "1977-1984, 1988–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Dedringer", "1977-1985")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Deep Machine", "1979-1982, 2009–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Deep Purple", "1968-1976, 1984–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Def Leppard", "1977–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Demon", "1979–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("The Deviants", "1967–1969, 1978, 1984, 1996, 2002, 2011–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Diamond Head", "1976–1985, 1991–1994, 2002–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Dokken", "1978–1989, 1993–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Dust", "1969-1972")));
		this.list.add(new BandTreeNode(jp.getId(), new Band("Earthshaker", "1978-1994, 1999–present")));
		this.list.add(new BandTreeNode(se.getId(), new Band("Easy Action", "1981–1986, 2006–present")));
		this.list.add(new BandTreeNode(se.getId(), new Band("E.F. Band", "1979-1986")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Electric Sun", "1978-1986")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Elf", "1967-1975")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Eloy", "1969-1984, 1988-present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Emerson, Lake & Palmer", "1970-1979, 1991-1998")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Ethel the Frog", "1976-1980")));
		this.list.add(new BandTreeNode(se.getId(), new Band("Europe", "1979–1992, 1999 (partial reunion), 2003–present")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("Exciter", "1978–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Exodus", "1980–1993, 1997–1998, 2001–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Faith No More", "1979–1998, 2009–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Fallout", "1979-1982")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Fist", "1978-1982, 2001–2006")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Flotsam and Jetsam", "1981–present")));
		this.list.add(new BandTreeNode(jp.getId(), new Band("Flower Travellin' Band", "1968–1973, 2007–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("The Flying Hat Band", "1971–1974")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Gamma", "1978–1983, 2000")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Genesis", "1967-1998, 2006–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Geordie", "1972–1980, 1982–1985, 2001")));
		this.list.add(new BandTreeNode(us.getId(), new Band("GG Allin", "1972-1993")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Gillan", "1978–1983")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Girl", "1979-1982")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Girlschool", "1978–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Grand Funk Railroad", "1968–1977, 1980–1983, 1996–present")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Grave Digger", "1980–1987, 1991–present")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Gravestone", "1977-1986")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Great White", "1977–2001, 2002–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Grim Reaper", "1979–1988, 2006–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("The Gun", "1967-1970")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Sammy Hagar", "1967–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("The Handsome Beasts", "1972–present")));
		this.list.add(new BandTreeNode(fi.getId(), new Band("Hanoi Rocks", "1979–1985, 2001–2009")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Hard Stuff", "1971–1973")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Hawkwind", "1969–present")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("Headpins", "1979–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Heart", "1973–present")));
		this.list.add(new BandTreeNode(au.getId(), new Band("Heaven", "1980–2000")));
		this.list.add(new BandTreeNode(se.getId(), new Band("Heavy Load", "1976–1985")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Heavy Metal Kids", "1972-1985, 2002–present")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("Helix", "1974–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("The Jimi Hendrix Experience", "1966–1970")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("High Tide", "1969–1970")));
		this.list.add(new BandTreeNode(fi.getId(), new Band("HIM", "1991 - present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Hollow Ground", "1979-1982, 2007, 2013")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Holocaust", "1977–present")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Holy Moses", "1980–1994, 2000–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Icon", "1981–1990, 2008–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Iron Butterfly", "1966–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Iron Claw", "1969-1974, 1993, 2010–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Iron Maiden", "1975–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Jag Panzer", "1981–1988, 1994–2011")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Jaguar", "1979–1985, 1998–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Jameson Raid", "1975–1983, 2008–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Jerusalem", "1972–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Jethro Tull", "1967-2014")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Josefus", "1969–2005")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Journey", "1973–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("JPT Scare Band", "1973–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Judas Priest", "1969–present")));
		this.list.add(new BandTreeNode(pl.getId(), new Band("Kat", "1979-1987, 1990-1999, 2002–present")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("Kick Axe", "1976–1988, 2003–present")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("Killer Dwarfs", "1981–1995, 2001–2008")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Killing Joke", "1978-1996, 2002–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("King Crimson", "1968-1974, 1981-1984, 1994-present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("King's X", "1980–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Kiss", "1973–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Kix", "1977–1996, 2003–present")));
		this.list.add(new BandTreeNode(ch.getId(), new Band("Krokus", "1974–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Leaf Hound", "1969-1971, 2004–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Leatherwolf", "1981–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Led Zeppelin", "1968–1980, 2007, 2011")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Legs Diamond", "1975–present")));
		this.list.add(new BandTreeNode(es.getId(), new Band("Leño", "1978-1983")));
		this.list.add(new BandTreeNode(se.getId(), new Band("Leviticus", "1981–1990")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Lionheart", "1980-1986")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Living Death", "1980-1991")));
		this.list.add(new BandTreeNode(us.getId(), new Band("London", "1978–1981, 1984–1990, 2006–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Lone Star", "1975–1978")));
		this.list.add(new BandTreeNode(es.getId(), new Band("Los Suaves", "1980–present")));
		this.list.add(new BandTreeNode(jp.getId(), new Band("Loudness", "1980–present")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("Loverboy", "1979-1988, 1989, 1991–present")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Lucifer's Friend", "1970–1982, 1993–1997")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("Mahogany Rush", "1970–1980, 1998–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Malice", "1980-1989, 2006–present")));
		this.list.add(new BandTreeNode(se.getId(), new Band("Yngwie Malmsteen", "1978–present")));
		this.list.add(new BandTreeNode(ie.getId(), new Band("Mama's Boys", "1978–1993")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Manilla Road", "1977-1990, 2001–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Manowar", "1980–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Marseille", "1976–present")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("Max Webster", "1973–1982")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("May Blitz", "1969–1972")));
		this.list.add(new BandTreeNode(us.getId(), new Band("MC5", "1963-1972, 1992, 2003-2012")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Mentors", "1977–present")));
		this.list.add(new BandTreeNode(dk.getId(), new Band("Mercyful Fate", "1981–1985, 1992–1999")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Metal Church", "1980–1994, 1998–2009")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Metallica", "1981–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Ministry", "1981-2008, 2011–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Misfits", "1977–1983, 1995–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Montrose", "1973–1976, 2005")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("More", "1980–1982, 1985, 1998–2000")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Mötley Crüe", "1981–2015")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Motörhead", "1975–2015")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Mountain", "1969–1972, 1973–present")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("Moxy", "1974-1983, 1999-2009")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Napalm Death", "1981–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Nazareth", "1968–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Necromandus", "1970–1973")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("The Next Band", "1978-1982")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Night Sun", "1970–1973")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Nightwing", "1978–1987, 1996–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Ted Nugent", "1975–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("The Obsessed", "1976–1986, 1990–1995, 2011–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Overkill", "1980–present")));
		this.list.add(new BandTreeNode(fi.getId(), new Band("Oz", "1977-1991, 2010–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Ozzy Osbourne", "1980–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Pagan Altar", "1978–1982, 2004–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Pantera", "1981-2003")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Pentagram", "1971–1976, 1978–1979, 1981–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Persian Risk", "1979–1986")));
		this.list.add(new BandTreeNode(nl.getId(), new Band("Picture", "1979–1987, 1997–1999, 2007–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Pink Fairies", "1970-1976, 1987-1988")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Praying Mantis", "1974–present")));
		this.list.add(new BandTreeNode(dk.getId(), new Band("Pretty Maids", "1981–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Primevil", "1973-1974")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Quartz", "1974-1983, 1996, 2011")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Queen", "1970-1991")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Queensrÿche", "1981–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Quiet Riot", "1975–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Rainbow", "1975–1984, 1993–1997")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Ratt", "1976–1992, 1996–Present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Raven", "1974–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Riot", "1975–1984, 1986–2012")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Rock Goddess", "1977–1987, 1994–1995, 2009, 2015")));
		this.list.add(new BandTreeNode(us.getId(), new Band("The Rods", "1978-1986, 2008–present")));
		this.list.add(new BandTreeNode(au.getId(), new Band("Rose Tattoo", "1976–1987, 1993, 1998–present")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Uli Jon Roth", "1968–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Rough Cutt", "1981–1987, 2000–2002")));
		this.list.add(new BandTreeNode(us.getId(), new Band("The Runaways", "1975–1979")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Running Wild", "1976–2009, 2011–present")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("Rush", "1968–present")));
		this.list.add(new BandTreeNode(jp.getId(), new Band("Saber Tiger", "1981–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Sacred Rite", "1980-1990")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Saint", "1980–1989, 1999–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Saint Vitus", "1978–1996, 2003, 2008–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Salem", "1980–1983, 2009–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Paul Samson", "1978–2002")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Satan", "1979–88, 2005–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Savage", "1976–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Savage Grace", "1981–1993, 2009-2010")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Savatage", "1978–2002")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Saxon", "1976–present")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Michael Schenker Group", "1979–present")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Scorpions", "1965–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Shark Island", "1979-1992")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Sir Lord Baltimore", "1968-1976, 2006–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Sister", "1976–1978")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Skitzo", "1981–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Slayer", "1981–present")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Sodom", "1981–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Sorcery", "1976-1987")));
		this.list.add(new BandTreeNode(fr.getId(), new Band("Sortilège", "1981–1986")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Sound Barrier", "1980-1987")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Spinal Tap", "1979–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Spider", "1976-1986")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Stampede", "1981-1983, 2009–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Starz", "1975–1979, 1980, 1990, 2003–present")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Steeler", "1981–1988")));
		this.list.add(new BandTreeNode(de.getId(), new Band("Stormwitch", "1979–1994, 2002–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Stray", "1966–present")));
		this.list.add(new BandTreeNode(za.getId(), new Band("Suck", "1970–1971")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Suicidal Tendencies", "1981–1995, 1997–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Sweet Savage", "1979–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Tank", "1980-1989, 1997–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Tesla", "1981–1996, 2000–present")));
		this.list.add(new BandTreeNode(ie.getId(), new Band("Thin Lizzy", "1969–1984, 1996–2012")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("Thor", "1978, 1983–1986, 1997–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("TKO", "1977–2001")));
		this.list.add(new BandTreeNode(ch.getId(), new Band("Toad", "1970–1995")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Tobruk", "1981–1987")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Trespass", "1978–1982, 1992–1993")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("Triumph", "1975–1993, 2008–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Trouble", "1979–present")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("Trooper", "1974–present")));
		this.list.add(new BandTreeNode(fr.getId(), new Band("Trust", "1977-1985, 1988, 1996-2000, 2006")));
		this.list.add(new BandTreeNode(pl.getId(), new Band("TSA", "1979–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Tucky Buzzard", "1969-1974")));
		this.list.add(new BandTreeNode(pl.getId(), new Band("Turbo", "1980–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Twisted Sister", "1972–1988, 1997–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Tygers of Pan Tang", "1978–1987, 1999–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Tytan", "1981-1983, 2012–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("UFO", "1969–1988, 1992–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Urchin", "1972-1980")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Uriah Heep", "1969–present")));
		this.list.add(new BandTreeNode(ar.getId(), new Band("V8", "1979–1987")));
		this.list.add(new BandTreeNode(nl.getId(), new Band("Vandenberg", "1981–1987")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Van Halen", "1972–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Vanilla Fudge", "1967–1970, 1982–1984, 1987–1988, 1991, 1999–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Vardis", "1973–1986, 2013–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Venom", "1979–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Vicious Rumors", "1979–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Virgin Steele", "1981–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Vixen", "1980–1991, 1997–1998, 2001–present")));
		this.list.add(new BandTreeNode(fr.getId(), new Band("Vulcain", "1981-1998, 2009–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Warhorse", "1970–1974, (partial reunions: 1985, 2005)")));
		this.list.add(new BandTreeNode(fr.getId(), new Band("Warning", "1980-1985")));
		this.list.add(new BandTreeNode(us.getId(), new Band("White Sister", "1980-1986, 2008-2009")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Whitesnake", "1978–1990, 1994, 1997, 2002–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("White Spirit", "1975-1981")));
		this.list.add(new BandTreeNode(ca.getId(), new Band("White Wolf", "1975-1986, 2007–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Wild Dogs", "1981–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Wild Horses", "1978–1981")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Wishbone Ash", "1969–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Witchfinder General", "1979–1984, 2006–2008")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Witchfynde", "1974–1984, 1999–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("The Who", "1964-1982, 1989, 1996–present")));
		this.list.add(new BandTreeNode(uk.getId(), new Band("Wrathchild", "1980–1990, 2009–present")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Wrathchild America", "1978-1993")));
		this.list.add(new BandTreeNode(us.getId(), new Band("Y&T", "1974–1991, 1995–present")));
	}

	private static synchronized BandsDAO get()
	{
		if (instance == null)
		{
			instance = new BandsDAO();
		}

		return instance;
	}

	public static List<? extends TreeNode<?>> get(int parentId)
	{
		return TreeNodeUtils.getChildren(parentId, get().list);
	}
	
	static class BandTreeNode extends TreeNode<Band>
	{
		private static final long serialVersionUID = 1L;

		public BandTreeNode(int countryId, Band band)
		{
			super(nextSequence(), countryId, band);
		}

		@Override
		public String getText()
		{
			return this.getObject().getName();
		}

		@Override
		public boolean hasChildren()
		{
			return false;
		}
	}
}
