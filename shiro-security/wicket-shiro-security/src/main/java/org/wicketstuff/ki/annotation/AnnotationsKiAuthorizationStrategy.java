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
package org.wicketstuff.ki.annotation;

import java.lang.annotation.Annotation;

import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.jsecurity.SecurityUtils;
import org.jsecurity.mgt.SecurityManager;
import org.jsecurity.subject.Subject;
import org.jsecurity.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.ki.KiAction;


public class AnnotationsKiAuthorizationStrategy implements IAuthorizationStrategy 
{ 
  static final Logger log = LoggerFactory.getLogger( AnnotationsKiAuthorizationStrategy.class );
    
  /**
   * @see org.apache.wicket.authorization.IAuthorizationStrategy#isInstantiationAuthorized(java.lang.Class)
   */
  public <T extends Component> boolean isInstantiationAuthorized( final Class<T> componentClass) 
  {
    Annotation fail = checkInvalidInstantiation( componentClass );
    if( fail != null ) {
      log.info( "Unuthorized Instantiation :: component={} reason={} subject={}", 
          new Object[] { componentClass, fail, SecurityUtils.getSubject() } );
      return false;
    }
    return true;
  }

  public <T extends Component> KiSecurityConstraint checkInvalidInstantiation( final Class<T> componentClass )
  {
    KiSecurityConstraint fail = checkInvalidInstantiation( componentClass.getAnnotations(), KiAction.INSTANTIATE );
    if( fail == null ) {
      fail = checkInvalidInstantiation( componentClass.getPackage().getAnnotations(), KiAction.INSTANTIATE );
    }
    return fail;
  }
  
  /**
   * @param <T>
   * @param clazz
   * @return null if ok, or the Annotation that failed
   */
  protected KiSecurityConstraint checkInvalidInstantiation( Annotation[] annotations, KiAction action )
  {
    if( annotations == null ) {
      return null;
    }
    
    for( Annotation annotation : annotations ) {
      // Check Permissions
      if( annotation instanceof KiSecurityConstraint ) {
        KiSecurityConstraint constraint = (KiSecurityConstraint)annotation;
        if( action == constraint.action() ) {
          SecurityManager sm = ThreadContext.getSecurityManager();
          Subject subject = SecurityUtils.getSubject();
          switch( constraint.constraint() ) {
            case HasRole: {
              if(!sm.hasRole( subject.getPrincipals(), constraint.value() ) ) {
                return constraint;
              }
              break;
            }
            
            case HasPermission: {
              if(!sm.isPermitted( subject.getPrincipals(), constraint.value() ) ) {
                return constraint;
              }
              break;
            }
            
            case IsAuthenticated: {
              if(!subject.isAuthenticated() ) {
                return constraint;
              }
              break;
            }
            
            case LoggedIn: {
              if( subject.getPrincipal() == null ) {
                return constraint;
              }
              break;
            }
          }
        }
      } // end if KiSecurityConstraint
    }
    return null;
  }

  public boolean isActionAuthorized(final Component component, final Action action) {
    
    KiAction _action = (action.getName().equals( Action.RENDER ) ) 
      ? KiAction.RENDER : KiAction.ENABLE;
    
    Class<? extends Component> clazz = component.getClass();
    KiSecurityConstraint fail = checkInvalidInstantiation( clazz.getAnnotations(), _action );
    if( fail == null ) {
      fail = checkInvalidInstantiation( clazz.getPackage().getAnnotations(), _action );
    }
    return fail == null;
  }
  
}
