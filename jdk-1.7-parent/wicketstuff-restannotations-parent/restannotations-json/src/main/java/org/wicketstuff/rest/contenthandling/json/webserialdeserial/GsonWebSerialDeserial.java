package org.wicketstuff.rest.contenthandling.json.webserialdeserial;

import org.wicketstuff.rest.contenthandling.json.objserialdeserial.GsonObjectSerialDeserial;

public class GsonWebSerialDeserial extends JsonWebSerialDeserial
{

    public GsonWebSerialDeserial(GsonObjectSerialDeserial objectSerialDeserial)
    {
	super(objectSerialDeserial);
    }
    
    public GsonWebSerialDeserial()
    {
	super(new GsonObjectSerialDeserial());
    }

}
