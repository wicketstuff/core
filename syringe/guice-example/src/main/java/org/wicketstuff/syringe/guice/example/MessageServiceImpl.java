package org.wicketstuff.syringe.guice.example;

import java.util.UUID;

/**
 * @since 1.4
 */
public class MessageServiceImpl implements IMessageService
{
    public String getMessage()
    {
        return "Hello, World (" + UUID.randomUUID().toString() + ")!";
    }
}
