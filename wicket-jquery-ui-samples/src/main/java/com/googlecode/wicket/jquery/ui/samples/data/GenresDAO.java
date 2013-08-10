package com.googlecode.wicket.jquery.ui.samples.data;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.data.bean.Genre;

public class GenresDAO
{
	private static GenresDAO instance = null;

	private static synchronized GenresDAO get()
	{
		if (instance == null)
		{
			instance = new GenresDAO();
		}

		return instance;
	}

	public static List<Genre> all()
	{
		return GenresDAO.get().list;
	}

	public static Genre get(int i)
	{
		return GenresDAO.all().get(i);
	}

	public static Genre newGenre()
	{
		return new Genre("");
	}

	private final List<Genre> list;

	public GenresDAO()
	{
		this.list = new ArrayList<Genre>();
		this.list.add(new Genre("Black Metal", "cover-black-metal.png"));
		this.list.add(new Genre("Death Metal", "cover-death-metal.png"));
		this.list.add(new Genre("Doom Metal", "cover-doom-metal.png"));
		this.list.add(new Genre("Folk Metal", "cover-folk-metal.png"));
		this.list.add(new Genre("Gothic Metal", "cover-gothic-metal.png"));
		this.list.add(new Genre("Heavy Metal", "cover-heavy-metal.png"));
		this.list.add(new Genre("Power Metal", "cover-power-metal.png"));
		this.list.add(new Genre("Symphonic Metal", "cover-symphonic-metal.png"));
		this.list.add(new Genre("Trash Metal", "cover-trash-metal.png"));
		this.list.add(new Genre("Vicking Metal", "cover-vicking-metal.png"));
	}
}
