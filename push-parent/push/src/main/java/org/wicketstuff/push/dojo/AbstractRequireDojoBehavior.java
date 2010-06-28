/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.wicketstuff.push.dojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * <p>
 * This behavior has to be extended to write a new Behavior (Handler) using
 * Dojo. Adding the required dojo libs (see <tt>dojo.require(...)</tt>) can be
 * achieved using the <code>setRequire()</code> method and adding the required
 * libs to the <tt>libs</tt> variable.
 * </p>
 * <p>
 * This behavior also takes care to add needed require on a ajaxRequestTarget
 * </p>
 * <p>
 * Since 1.3.0-beta<br/> This method is not adapted to be used with widget, see
 * {@link AbstractDojoWidgetBehavior}. This behavior only deals with dojoRequire
 * statements ans is not in charge of the widget dojo parsing
 * </p>
 * @author vdemay
 */
public abstract class AbstractRequireDojoBehavior extends
    AbstractDefaultDojoBehavior {

  private static final long serialVersionUID = 1L;

  private final Set<String> libs = new HashSet<String>();

  /*
   * (non-Javadoc)
   * @see
   * wicket.contrib.dojo.AbstractDefaultDojoBehavior#renderHead(wicket.markup
   * .html.IHeaderResponse) TODO : is there a way to put all dojo.require at the
   * same place on the rendered page??????
   */
  @Override
  public void renderHead(final IHeaderResponse response) {
    super.renderHead(response);

    response.renderJavascript(getRequire(), AbstractRequireDojoBehavior.class
        .getName());
  }

  /**
   * Method to generate list of the <code>dojo.require</code> Also called in
   * {@link TargetRefresherManager}
   * @return the stringBuffer containing the <code>dojo.require</code> list
   */
  public final StringBuffer getRequire() {
    setRequire(libs); // will be implemented by childs
    final StringBuffer require = new StringBuffer();

    final Iterator<String> ite = libs.iterator();
    while (ite.hasNext()) {
      require.append("dojo.require(\"");
      require.append(ite.next());
      require.append("\");\n");
    }

    require.append("\n");

    return require;
  }

  /**
   * allow subclass to register new Dojo require libs
   * @param libs
   */
  public abstract void setRequire(Set<String> libs);

  /**
   * this method is used to interpret dojoWidgets rendered via XMLHTTPRequest
   * FIXME : in TargetRefresherMPanager differency AbstractRequire and
   * DojoWidgetBehavior
   */
  @Override
  protected void onComponentRendered() {

    // if a Dojo Widget is rerender needs to run some javascript to refresh
    // it. TargetRefresherManager contains top level dojo widgets
    if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget) {
      final AjaxRequestTarget target =
          (AjaxRequestTarget) RequestCycle.get().getRequestTarget();
      // and register listener
      target.addListener(TargetRefresherManager.getInstance());
      TargetRefresherManager.getInstance().addComponent(getComponent());
      onComponentReRendered(target);
    }
  }

  /**
   * Add Javascript scripts when a dojo component is Rerender in a
   * {@link AjaxRequestTarget}
   * @param ajaxTarget
   */
  public void onComponentReRendered(final AjaxRequestTarget ajaxTarget) {

  }

}
