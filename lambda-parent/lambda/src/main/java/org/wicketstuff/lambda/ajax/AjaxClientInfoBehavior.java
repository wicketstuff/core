package org.wicketstuff.lambda.ajax;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.util.time.Duration;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;

/**
 * Subclass of and drop in replacement for
 * {@link org.apache.wicket.ajax.AjaxClientInfoBehavior} that uses a lambda for
 * event handling.
 * 
 * @see org.apache.wicket.ajax.AjaxClientInfoBehavior
 */
public class AjaxClientInfoBehavior extends org.apache.wicket.ajax.AjaxClientInfoBehavior {

	private static final long serialVersionUID = 1L;

	private SerializableBiConsumer<AjaxRequestTarget, WebClientInfo> clientInfoHandler;

	/**
	 * Constructor.
	 *
	 * Auto fires after 50 millis.
	 */
	public AjaxClientInfoBehavior() {
		this(null, null);
	}

	/**
	 * Constructor.
	 *
	 * Auto fires after 50 millis.
	 * 
	 * @param clientInfoHandler
	 *            handler to call to process the {@link WebClientInfo} data
	 */
	public AjaxClientInfoBehavior(SerializableBiConsumer<AjaxRequestTarget, WebClientInfo> clientInfoHandler) {
		this(null, clientInfoHandler);
	}

	/**
	 * Constructor. Auto fires after {@code duration}.
	 * 
	 * @param duration
	 *            the duration of the client info behavior
	 */
	public AjaxClientInfoBehavior(Duration duration) {
		this(duration, null);
	}

	/**
	 * Constructor. Auto fires after {@code duration}.
	 * 
	 * @param duration
	 *            the duration of the client info behavior
	 * @param clientInfoHandler
	 *            handler to process the {@link WebClientInfo} data
	 */
	public AjaxClientInfoBehavior(Duration duration,
			SerializableBiConsumer<AjaxRequestTarget, WebClientInfo> clientInfoHandler) {
		super(duration);
		this.clientInfoHandler = clientInfoHandler;
	}

	/**
	 * Sets the handler to deal with the {@link WebClientInfo} data
	 * 
	 * @param duration
	 *            handler to process the {@link WebClientInfo} data
	 * @return this
	 */
	public AjaxClientInfoBehavior clientInfoHandler(
			SerializableBiConsumer<AjaxRequestTarget, WebClientInfo> clientInfoHandler) {
		this.clientInfoHandler = clientInfoHandler;
		return this;
	}

	@Override
	protected final void onClientInfo(AjaxRequestTarget target, WebClientInfo clientInfo) {
		if (clientInfoHandler != null) {
			clientInfoHandler.accept(target, clientInfo);
		} else {
			throw new WicketRuntimeException("clientInfoHandler not specified");
		}
	}

}
