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
package org.wicketstuff.ki.component;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.jsecurity.SecurityUtils;
import org.jsecurity.authc.AuthenticationException;
import org.jsecurity.authc.IncorrectCredentialsException;
import org.jsecurity.authc.UnknownAccountException;
import org.jsecurity.authc.UsernamePasswordToken;
import org.jsecurity.mgt.SecurityManager;
import org.jsecurity.subject.Subject;
import org.wicketstuff.ki.KiServletRequestModel;
import org.wicketstuff.ki.KiSubjectModel;
import org.wicketstuff.ki.page.LogoutPage;



public class KiConfigInfoPanel extends Panel
{ 
	public KiConfigInfoPanel(String id)
	{
	  super( id );
	  
	  IModel<?> model = new KiServletRequestModel();
    WebMarkupContainer request = new WebMarkupContainer( "request",
        new CompoundPropertyModel( model ) );
    request.add( new Label("toString", model ));
    request.add( new Label("class.name" ));
    request.add( new Label("RemoteUser" ));
    request.add( new Label("RequestedSessionId" ));
    request.add( new Label("UserPrincipal" ));
    request.add( new Label("HttpSessions" ));
    request.add( new Label("RequestedSessionIdFromCookie" ));
    request.add( new Label("RequestedSessionIdFromUrl" ));
    request.add( new Label("RequestedSessionIdFromURL" ));
    request.add( new Label("RequestedSessionIdValid" ));
    add( request );
    
	  model = new KiSubjectModel();
    WebMarkupContainer subject = new WebMarkupContainer( "subject",
        new CompoundPropertyModel<SecurityManager>( model ) );
    subject.add( new Label("toString", model ));
    subject.add( new Label("class.name" ));
    subject.add( new Label("authenticated" ));
    subject.add( new Label("principal" ));
    subject.add( new Label("session" ));
    add( subject );
	}
}
