package org.wicketstuff.rest.contenthandling.webserialdeserial;

import org.wicketstuff.rest.contenthandling.mimetypes.RestMimeTypes;
import org.wicketstuff.rest.contenthandling.objserialdeserial.XmlSerialDeser;

public class XmlTestWebSerialDeserial extends TextualWebSerialDeserial
{

    public XmlTestWebSerialDeserial()
    {
	super("UTF-8", RestMimeTypes.APPLICATION_XML, new XmlSerialDeser());
    }

}
