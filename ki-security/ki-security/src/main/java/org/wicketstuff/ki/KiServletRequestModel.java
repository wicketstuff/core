package org.wicketstuff.ki;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Application;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.jsecurity.SecurityUtils;
import org.jsecurity.mgt.SecurityManager;
import org.jsecurity.subject.Subject;
import org.jsecurity.web.servlet.JSecurityFilter;


public class KiServletRequestModel extends LoadableDetachableModel<HttpServletRequest>
{
  @Override
  protected HttpServletRequest load() {
    return ((WebRequestCycle)RequestCycle.get()).getWebRequest().getHttpServletRequest();
  }
}
