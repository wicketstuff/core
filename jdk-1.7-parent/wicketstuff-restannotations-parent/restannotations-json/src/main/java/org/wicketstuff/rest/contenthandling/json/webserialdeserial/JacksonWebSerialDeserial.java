package org.wicketstuff.rest.contenthandling.json.webserialdeserial;

import org.wicketstuff.rest.contenthandling.json.objserialdeserial.JacksonObjectSerialDeserial;

public class JacksonWebSerialDeserial extends JsonWebSerialDeserial
{

    public JacksonWebSerialDeserial(
	    JacksonObjectSerialDeserial objectSerialDeserial)
    {
	super(objectSerialDeserial);
    }

    public JacksonWebSerialDeserial()
    {
	super(new JacksonObjectSerialDeserial());
    }    
}
