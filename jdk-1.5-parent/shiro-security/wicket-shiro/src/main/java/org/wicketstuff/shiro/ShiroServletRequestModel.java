package org.wicketstuff.shiro;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.cycle.RequestCycle;


public class ShiroServletRequestModel extends LoadableDetachableModel<HttpServletRequest>
{
  @Override
  protected HttpServletRequest load() {
    return ((WebRequestCycle)RequestCycle.get()).getWebRequest().getHttpServletRequest();
  }
}
