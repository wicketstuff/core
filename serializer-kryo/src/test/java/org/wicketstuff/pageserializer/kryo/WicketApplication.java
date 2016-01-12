package org.wicketstuff.pageserializer.kryo;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.lang.Bytes;

import com.esotericsoftware.kryo.Kryo;

/**
 * Application object for your web application. If you want to run this application without
 * deploying, run the Start class.
 * 
 * @see org.wicketstuff.pageserializer.kryo.mycompany.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	public void init()
	{
		super.init();

		getFrameworkSettings().setSerializer(new KryoSerializer(Bytes.bytes(1000))
		{

			@Override
			protected Kryo createKryo()
			{
				return new DebuggingKryo()/* .blacklist(Some.class) */;
			}
		});
	}
}
