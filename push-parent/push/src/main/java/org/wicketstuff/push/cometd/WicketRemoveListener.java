/*
 * Copyright 2008 Kindleit Technologies. All rights reserved. This file, all
 * proprietary knowledge and algorithms it details are the sole property of
 * Kindleit Technologies unless otherwise specified. The software this file
 * belong with is the confidential and proprietary information of Kindleit
 * Technologies. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Kindleit.
 */


package org.wicketstuff.push.cometd;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.cometd.RemoveListener;

/** Extends a cometd RemoveListener with wicket related information
 * @author rhansen@kindleit.net
 */
public interface WicketRemoveListener extends RemoveListener {

  /** Retrieve the wicket application where the RemoveListener took place. */
  Application getApplication();

  /** Retrieve the listener's Wicket Session object. */
  Session getListenerSession();
}
