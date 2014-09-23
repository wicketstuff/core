/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 8:56:25 PM
 */
package org.wicketstuff.html5.media.audio;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.html5.BasePage;
import org.wicketstuff.html5.media.MediaSource;

/**
 * 
 * @author Andrew Lombardi
 */
public class AudioDemo extends BasePage
{
	private static final long serialVersionUID = 8364098763780612520L;

	public AudioDemo()
	{
		final List<MediaSource> mm = new ArrayList<MediaSource>();
		mm.add(new MediaSource("loser.wav"));
		mm.add(new MediaSource("loser.ogg"));

		IModel<List<MediaSource>> mediaSourceList = new AbstractReadOnlyModel<List<MediaSource>>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public List<MediaSource> getObject()
			{
				return mm;
			}
		};

		add(new Html5Audio("loser", mediaSourceList)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean isControls()
			{
				return true;
			}

		});
	}
}