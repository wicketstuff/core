package org.wicketstuff.mbeanview;

import java.lang.reflect.InvocationTargetException;

import javax.management.Attribute;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.RuntimeMBeanException;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public class AttributeModel extends LoadableDetachableModel<Object>
{
	private static final long serialVersionUID = 1L;

	private final IModel<MBeanServer> server;

	private final MBeanAttributeInfo attribute;

	private final ObjectName objectName;

	public AttributeModel(IModel<MBeanServer> server, ObjectName objectName,
		MBeanAttributeInfo attribute)
	{
		this.server = server;
		this.objectName = objectName;
		this.attribute = attribute;
	}

	@Override
	protected Object load()
	{
		if (attribute.isReadable())
		{
			try
			{
				return server.getObject().getAttribute(objectName, attribute.getName());
			}
			catch (Exception ex)
			{
				onError(ex);
			}
		}
		return null;
	}

	public void setObject(Object object)
	{
		Attribute attribute = new Attribute(this.attribute.getName(), object);
		try
		{
			server.getObject().setAttribute(objectName, attribute);
		}
		catch (Exception ex)
		{
			Throwable throwable = ex;
			while (true)
			{
				if (throwable instanceof RuntimeMBeanException)
				{
					throwable = ((RuntimeMBeanException)throwable).getTargetException();
				}
				else if (throwable instanceof InvocationTargetException)
				{
					throwable = ((InvocationTargetException)throwable).getTargetException();
				}
				else if (throwable instanceof RuntimeException && throwable.getCause() != throwable)
				{
					throwable = (Exception)throwable.getCause();
				}
				else
				{
					break;
				}
			}
			onError(throwable);
		}
	}

	public void detach()
	{
		server.detach();
	}

	protected void onError(Throwable throwable)
	{

	}
}