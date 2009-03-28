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
package org.wicketstuff.ki.page;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.IRedirectListener;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.jsecurity.SecurityUtils;


public class LogoutPage extends WebPage
{
  public static final String REDIRECTPAGE_PARAM = "redirectpage";

  /**
   * Constructor. The page will immediately redirect to the given url.
   * 
   * @param url
   *            The url to redirect to
   */
  public LogoutPage(final CharSequence url)
  {
    doLogoutAndAddRedirect(url, 0);
  }
  

  public LogoutPage( final PageParameters parameters ) {
    String page = parameters.getString(REDIRECTPAGE_PARAM);
    Class<? extends Page> pageClass;
    if ( page != null ) {
      try {
        pageClass = (Class<? extends Page>)Class.forName(page);
      } 
      catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    } 
    else {
      pageClass = getApplication().getHomePage();
    }
    
    doLogoutAndAddRedirect( urlFor(pageClass, null ), 0 );
  }
  
  public LogoutPage( Class<? extends Page> pageClass ) {
    doLogoutAndAddRedirect( urlFor(pageClass, null ), 0 );
  }
  

  /**
   * Constructor. The page will redirect to the given url after waiting for the given number of
   * seconds.
   * 
   * @param url
   *            The url to redirect to
   * @param waitBeforeRedirectInSeconds
   *            The number of seconds the browser should wait before redirecting
   */
  private void doLogoutAndAddRedirect(final CharSequence url, final int waitBeforeRedirectInSeconds)
  {
    SecurityUtils.getSubject().logout();
    // TODO? invalidate the web session?
    
    final WebMarkupContainer redirect = new WebMarkupContainer("redirect");
    final String content = waitBeforeRedirectInSeconds + ";URL=" + url;
    redirect.add(new AttributeModifier("content", new Model<String>(content)));
    add(redirect);
  }

  
//  
//  /**
//   * Construct. The page will redirect to the given Page.
//   * 
//   * @param page
//   *            The page to redirect to.
//   */
//  public RedirectPage(final Page page)
//  {
//    this(page.urlFor(IRedirectListener.INTERFACE), 0);
//  }
//
//  /**
//   * Construct. The page will redirect to the given Page after waiting for the given number of
//   * seconds.
//   * 
//   * @param page
//   *            The page to redirect to.
//   * @param waitBeforeRedirectInSeconds
//   *            The number of seconds the browser should wait before redirecting
//   */
//  public RedirectPage(final Page page, final int waitBeforeRedirectInSeconds)
//  {
//    this(page.urlFor(IRedirectListener.INTERFACE), waitBeforeRedirectInSeconds);
//  }

  /**
   * @see org.apache.wicket.Component#isVersioned()
   */
  @Override
  public boolean isVersioned()
  {
    return false;
  }
}
