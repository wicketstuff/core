package org.wicketstuff.syringe.spring.example;

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
