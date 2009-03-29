package org.wicketstuff.ki.example.realm;

import org.jsecurity.authc.AuthenticationException;
import org.jsecurity.authc.AuthenticationInfo;
import org.jsecurity.authc.AuthenticationToken;
import org.jsecurity.authz.AuthorizationInfo;
import org.jsecurity.authz.SimpleAuthorizationInfo;
import org.jsecurity.authz.permission.AllPermission;
import org.jsecurity.realm.AuthorizingRealm;
import org.jsecurity.subject.PrincipalCollection;

public class AnonymousFullAccessRealm extends AuthorizingRealm 
{
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException 
  {
    return null;
//    UsernamePasswordToken upToken = (UsernamePasswordToken)token;
//    return new SimpleAccount( upToken.getUsername(), upToken.getUsername(), getName() );
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection p) 
  {
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    info.addObjectPermission( new AllPermission() );
    return info;
  }
}
