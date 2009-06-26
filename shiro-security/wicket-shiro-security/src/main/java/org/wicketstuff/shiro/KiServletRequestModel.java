package org.wicketstuff.shiro;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.protocol.http.WebRequestCycle;


public class KiServletRequestModel extends LoadableDetachableModel<HttpServletRequest>
{
  @Override
  protected HttpServletRequest load() {
    return ((WebRequestCycle)RequestCycle.get()).getWebRequest().getHttpServletRequest();
  }
}
