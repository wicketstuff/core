package org.wicketstuff.rest.resource;

import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.wicketstuff.rest.Developer;
import org.wicketstuff.rest.Person;
import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.annotations.parameters.RequestBody;
import org.wicketstuff.rest.contenthandling.webserialdeserial.TextualWebSerialDeserial;
import org.wicketstuff.restutils.http.HttpMethod;

public class ExtendsRestResource extends RestResourceFullAnnotated {

    public ExtendsRestResource(TextualWebSerialDeserial jsonSerialDeserial,
                                     IRoleCheckingStrategy roleCheckingStrategy)
    {
        super(jsonSerialDeserial, roleCheckingStrategy);
    }

    @MethodMapping(value = "/", httpMethod = HttpMethod.POST)
    public Developer testMethodPost()
    {
        Developer d = new Developer();
        d.setName("Mary");
        d.setSurname("Smith");
        d.setEmail("m.smith@gmail.com");
        return d;
    }
}
