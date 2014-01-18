package com.googlecode.wicket.jquery.ui.samples.data.dao;

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
		this.list.add(new Genre(0, "Black Metal", "cover-black-metal.png"));
		this.list.add(new Genre(1, "Death Metal", "cover-death-metal.png"));
		this.list.add(new Genre(2, "Doom Metal", "cover-doom-metal.png"));
		this.list.add(new Genre(3, "Folk Metal", "cover-folk-metal.png"));
		this.list.add(new Genre(4, "Gothic Metal", "cover-gothic-metal.png"));
		this.list.add(new Genre(5, "Heavy Metal", "cover-heavy-metal.png"));
		this.list.add(new Genre(6, "Power Metal", "cover-power-metal.png"));
		this.list.add(new Genre(7, "Symphonic Metal", "cover-symphonic-metal.png"));
		this.list.add(new Genre(8, "Trash Metal", "cover-trash-metal.png"));
		this.list.add(new Genre(9, "Vicking Metal", "cover-vicking-metal.png"));
	}
}
