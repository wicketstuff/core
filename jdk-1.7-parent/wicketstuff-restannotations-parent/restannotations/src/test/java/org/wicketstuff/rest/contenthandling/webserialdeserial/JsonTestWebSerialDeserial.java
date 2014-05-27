package org.wicketstuff.rest.contenthandling.webserialdeserial;

import org.wicketstuff.rest.contenthandling.mimetypes.RestMimeTypes;
import org.wicketstuff.rest.contenthandling.objserialdeserial.TestJsonDesSer;

public class JsonTestWebSerialDeserial extends TextualWebSerialDeserial
{
    public JsonTestWebSerialDeserial()
    {
	super("UTF-8", RestMimeTypes.APPLICATION_JSON, new TestJsonDesSer());
    }
}
