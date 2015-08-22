/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.event.annotation;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Implementation of {@link IPayloadEvent}.
 *
 * @param <T>
 *            type of the payload
 */
public abstract class AbstractPayloadEvent<T> extends AbstractAjaxAwareEvent implements
		IPayloadEvent<T> {

	private final T payload;

	/**
	 * Constructor.
	 * 
	 * @param target
	 *            {@link AjaxRequestTarget} associated with the event
	 * @param payload
	 *            payload on the event
	 */
	public AbstractPayloadEvent(final AjaxRequestTarget target, final T payload) {
		super(target);
		this.payload = payload;
	}

	@Override
	public T getPayload() {
		return payload;
	}

}
