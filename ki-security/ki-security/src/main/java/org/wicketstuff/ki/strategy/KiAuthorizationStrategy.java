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
package org.wicketstuff.ki.strategy;

import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.jsecurity.SecurityUtils;
import org.jsecurity.subject.Subject;


public class KiAuthorizationStrategy implements IAuthorizationStrategy 
{  
  /**
   * @see org.apache.wicket.authorization.IAuthorizationStrategy#isInstantiationAuthorized(java.lang.Class)
   */
  public <T extends Component> boolean isInstantiationAuthorized( final Class<T> componentClass) 
  {
    // Check class annotation
    final KiInstantiationAuthorization classAnnotation = componentClass
        .getAnnotation(KiInstantiationAuthorization.class);

    if (classAnnotation != null && !isAuthorized(classAnnotation)) {
      return false;
    }
    
    // Check package annotation first
    final Package componentPackage = componentClass.getPackage();
    if (componentPackage != null) {
      final KiInstantiationAuthorization packageAnnotation = componentPackage
          .getAnnotation(KiInstantiationAuthorization.class);
      if (packageAnnotation != null && !isAuthorized(packageAnnotation)) {
        return false;
      }
    }
    return true;
  }

  public boolean isActionAuthorized(final Component component, final Action action) {
    // TODO -- check if action is permitted...
    return true;
  }
  

  protected boolean isAuthorized(KiInstantiationAuthorization ki) {
    Subject subject = SecurityUtils.getSubject();
    if (ki.authenticated() && !subject.isAuthenticated()) {
      return false;
    }

    // Check what roles are required
    if (ki.role().length > 0) {
      boolean ok = false;
      for (String r : ki.role()) {
        if (subject.hasRole(r)) {
          ok = true;
          break;
        }
      }
      if (!ok) {
        return false;
      }
    }

    // Check what permissions are required
    if (ki.permission().length > 0) {
      boolean ok = false;
      for (String r : ki.permission()) {
        if (subject.isPermitted(r)) {
          ok = true;
          break;
        }
      }
      if (!ok) {
        return false;
      }
    }
    return true;
  }
}
