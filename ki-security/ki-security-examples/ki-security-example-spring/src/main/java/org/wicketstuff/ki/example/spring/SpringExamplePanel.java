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
package org.wicketstuff.ki.example.spring;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.jsecurity.realm.jdbc.JdbcRealm;
import org.jsecurity.web.DefaultWebSecurityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.wicketstuff.ki.example.realm.AnonymousFullAccessRealm;



public class SpringExamplePanel extends Panel
{ 
// HYMM, CGLIB messes up with this...
//  @SpringBean( name="securityManager" ) 
//  DefaultWebSecurityManager securityManager;
  
  @SpringBean( name="jdbcRealm" ) 
  JdbcRealm jdbcRealm;
  
	public SpringExamplePanel(String id)
	{
	  super( id );
	  
	  
    final IModel<DefaultWebSecurityManager> securityManagerModel = new LoadableDetachableModel<DefaultWebSecurityManager>() {
      @Override
      protected DefaultWebSecurityManager load() {
        ApplicationContext ctx = WebApplicationContextUtils
          .getRequiredWebApplicationContext( ((WebApplication)getApplication()).getServletContext());
    
        return (DefaultWebSecurityManager) ctx.getBean( "securityManager" );
      }
    };
    
    add( new Link<Void>( "useFullAccess" ) {
      @Override
      public void onClick() {
        securityManagerModel.getObject().setRealm( new AnonymousFullAccessRealm() );
      }
    });
    
    // 
    add( new Link<Void>( "useJdbc" ) {
      @Override
      public void onClick() {
        securityManagerModel.getObject().setRealm( jdbcRealm );
      }
    });
    
    
    WebMarkupContainer sminfo = new WebMarkupContainer( "sminfo",
        new CompoundPropertyModel( securityManagerModel ) );
    sminfo.add( new Label("toString", securityManagerModel ));
    sminfo.add( new Label("class.name" ));
    sminfo.add( new Label("realms" ));
    sminfo.add( new Label("authenticator" ));
    sminfo.add( new Label("authorizer" ));
    sminfo.add( new Label("sessionManager" ));
    sminfo.add( new Label("sessionMode" ));
    add( sminfo );
	}
}
