package org.wicketstuff.nashorn.resource.app;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.concurrent.TimeUnit;

import javax.script.Bindings;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.wicketstuff.nashorn.resource.NashornResourceReference;

public class NashornApplication extends WebApplication
{

	private static NashornObject nashornObject = new NashornObject();

	@Override
	public Class<? extends Page> getHomePage()
	{
		return NashornPage.class;
	}

	@Override
	protected void init()
	{
		// 31457280 => 30mb
		mountResource("/nashorn", new NashornResourceReference("nashorn", 10, 5, TimeUnit.SECONDS,
			10, TimeUnit.MILLISECONDS, 31457280)
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void setup(Attributes attributes, Bindings bindings)
			{
				bindings.put("nashornObject", nashornObject);
			}

			@Override
			protected Writer getWriter()
			{
				return new BufferedWriter(new OutputStreamWriter(System.out));
			}

			@Override
			protected Writer getErrorWriter()
			{
				return new BufferedWriter(new OutputStreamWriter(System.out));
			}

			@Override
			protected boolean isDebug()
			{
				return true;
			}
		});
	}
}
