package org.wicketstuff.syringe.webbeans.example;

import org.slf4j.LoggerFactory;

import javax.annotation.Named;
import javax.webbeans.Model;
import javax.context.SessionScoped;
import javax.context.ApplicationScoped;
import javax.context.RequestScoped;
import java.util.UUID;

/**
 * @since 1.4
 */
@Named("messageService")
@RequestScoped
public class MessageServiceImpl implements IMessageService
{
    public MessageServiceImpl()
    {
        LoggerFactory.getLogger(getClass()).info("Instantiating...");
    }

    public String getMessage()
    {
        return "Hello, World (" + UUID.randomUUID().toString() + ")!";
    }
}
