package org.wicketstuff.shiro;

public enum KiConstraint {
  HasRole,
  HasPermission,
  IsAuthenticated,  // in this session
  LoggedIn,  // could be from cookie or whatever
}
