package org.wicketstuff.shiro.annotation;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadState;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.easymock.IAnswer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wicketstuff.shiro.ShiroAction;
import org.wicketstuff.shiro.ShiroConstraint;


/**
 * Unit test for AnnotationsShiroAuthorizationStrategy. For more information about unit tests with shiro see
 * http://shiro.apache.org/testing.html
 *
 * @author Martin Sturm
 */
public class AnnotationsShiroAuthorizationStrategyTest{

    private static final Action RENDER = new Action(Action.RENDER);
    private static final Action ENABLE = new Action(Action.ENABLE);

    private ThreadState threadSubject;

    private Subject subject;

    private Boolean isLoggedIn;
    private Boolean isAuthenticated;

    private String persmission;
    private String role;

    private AnnotationsShiroAuthorizationStrategy underTest;

    private static WicketTester tester;

    @BeforeAll
    public static void beforeClass() {
        // create a wicket tester since we need an application as ThreadLocal for instantiating components.
        tester = new WicketTester(new WebApplication() {
            @Override
            public Class<? extends Page> getHomePage() {
                return null;
            }
        });
    }

    @AfterAll
    public static void afterClass() {
        // destroy wicket tester.
        tester.destroy();
    }

    @BeforeEach
    public void setUp() {
        // reset values to default
        initDefaultValues();

        underTest = new AnnotationsShiroAuthorizationStrategy();

        // create and bind subject to ThreadLocal
        threadSubject = new SubjectThreadState(subject = createMockSubject());
        threadSubject.bind();
    }

    @AfterEach
    public void clearThreadSubject() {
        reset(subject);
        // remove subject after test is finished
        if (threadSubject != null) {
            threadSubject.clear();
            threadSubject = null;
        }
    }

    // ======================= Tests for instantiation

    @Test
    public void checkInstantiationWhenLoggedIn() {
        isLoggedIn = true;
        assertTrue(underTest.isInstantiationAuthorized(InstantiateWhen.LoggedInOnly.class));
    }

    @Test
    public void checkInstantiationWhenNotLoggedIn() {
        isLoggedIn = false;
        assertFalse(underTest.isInstantiationAuthorized(InstantiateWhen.LoggedInOnly.class));
    }

    @Test
    public void checkInstantiationWhenAuthenticated() {
        isAuthenticated = true;
        assertTrue(underTest.isInstantiationAuthorized(InstantiateWhen.AuthenticatedOnly.class));
    }

    @Test
    public void checkInstantiationWhenNotAuthenticated() {
        isLoggedIn = false;
        assertFalse(underTest.isInstantiationAuthorized(InstantiateWhen.AuthenticatedOnly.class));
    }

    @Test
    public void checkInstantiationForRole() {
        role = "ADMIN";
        assertTrue(underTest.isInstantiationAuthorized(InstantiateWhen.SubjectHasRole.class));
    }

    @Test
    public void checkInstantiationForRoleNotPresent() {
        role = "USER";
        assertFalse(underTest.isInstantiationAuthorized(InstantiateWhen.SubjectHasRole.class));
    }

    @Test
    public void checkInstantiationForPermission() {
        persmission = "any:permission";
        assertTrue(underTest.isInstantiationAuthorized(InstantiateWhen.SubjectHasPermission.class));
    }

    @Test
    public void checkInstantiationForPermissionNotPresent() {
        persmission = "no:permission";
        assertFalse(underTest.isInstantiationAuthorized(InstantiateWhen.SubjectHasPermission.class));
    }

    // ======================= Tests for rendering

    @Test
    public void checkRenderWhenLoggedIn() {
        isLoggedIn = true;
        assertTrue(underTest.isActionAuthorized(new RenderWhen.LoggedInOnly(), RENDER));
    }

    @Test
    public void checkRenderWhenNotLoggedIn() {
        isLoggedIn = false;
        assertFalse(underTest.isActionAuthorized(new RenderWhen.LoggedInOnly(), RENDER));
    }

    @Test
    public void checkRenderWhenAuthenticated() {
        isAuthenticated = true;
        assertTrue(underTest.isActionAuthorized(new RenderWhen.AuthenticatedOnly(), RENDER));
    }

    @Test
    public void checkRenderWhenNotAuthenticated() {
        isLoggedIn = false;
        assertFalse(underTest.isActionAuthorized(new RenderWhen.AuthenticatedOnly(), RENDER));
    }

    @Test
    public void checkRenderForRole() {
        role = "ADMIN";
        assertTrue(underTest.isActionAuthorized(new RenderWhen.SubjectHasRole(), RENDER));
    }

    @Test
    public void checkRenderForRoleNotPresent() {
        role = "USER";
        assertFalse(underTest.isActionAuthorized(new RenderWhen.SubjectHasRole(), RENDER));
    }

    @Test
    public void checkRenderForPermission() {
        persmission = "any:permission";
        assertTrue(underTest.isActionAuthorized(new RenderWhen.SubjectHasPermission(), RENDER));
    }

    @Test
    public void checkRenderForPermissionNotPresent() {
        persmission = "no:permission";
        assertFalse(underTest.isActionAuthorized(new RenderWhen.SubjectHasPermission(), RENDER));
    }

    // ======================= Tests for enabling

    @Test
    public void checkEnableWhenLoggedIn() {
        isLoggedIn = true;
        assertTrue(underTest.isActionAuthorized(new EnableWhen.LoggedInOnly(), ENABLE));
    }

    @Test
    public void checkEnableWhenNotLoggedIn() {
        isLoggedIn = false;
        assertFalse(underTest.isActionAuthorized(new EnableWhen.LoggedInOnly(), ENABLE));
    }

    @Test
    public void checkEnableWhenAuthenticated() {
        isAuthenticated = true;
        assertTrue(underTest.isActionAuthorized(new EnableWhen.AuthenticatedOnly(), ENABLE));
    }

    @Test
    public void checkEnableWhenNotAuthenticated() {
        isLoggedIn = false;
        assertFalse(underTest.isActionAuthorized(new EnableWhen.AuthenticatedOnly(), ENABLE));
    }

    @Test
    public void checkEnableForRole() {
        role = "ADMIN";
        assertTrue(underTest.isActionAuthorized(new EnableWhen.SubjectHasRole(), ENABLE));
    }

    @Test
    public void checkEnableForRoleNotPresent() {
        role = "USER";
        assertFalse(underTest.isActionAuthorized(new EnableWhen.SubjectHasRole(), ENABLE));
    }

    @Test
    public void checkEnableForPermission() {
        persmission = "any:permission";
        assertTrue(underTest.isActionAuthorized(new EnableWhen.SubjectHasPermission(), ENABLE));
    }

    @Test
    public void checkEnableForPermissionNotPresent() {
        persmission = "no:permission";
        assertFalse(underTest.isActionAuthorized(new EnableWhen.SubjectHasPermission(), ENABLE));
    }

    private void initDefaultValues() {
        isLoggedIn = false;
        isAuthenticated = false;
        persmission = null;
        role = null;
    }

    /**
     * @return mocked subject instance.
     */
    private Subject createMockSubject() {
        Subject mock = createNiceMock(Subject.class);
        expect(mock.isAuthenticated()).andAnswer(new IAnswer<Boolean>() {
            @Override
            public Boolean answer() throws Throwable {
                return isAuthenticated;
            }
        });
        expect(mock.getPrincipal()).andAnswer(new IAnswer<Object>() {
            @Override
            public Object answer() throws Throwable {
                return isLoggedIn ? "LoggedIn... Yeah" : null;
            }
        });
        expect(mock.hasRole(isA(String.class))).andAnswer(new IAnswer<Boolean>() {
            @Override
            public Boolean answer() throws Throwable {
                final Object roleArg =  getCurrentArguments()[0];
                return roleArg != null && roleArg instanceof String && roleArg.equals(role);
            }
        });
        expect(mock.isPermitted(isA(String.class))).andAnswer(new IAnswer<Boolean>() {
            @Override
            public Boolean answer() throws Throwable {
                final Object role = getCurrentArguments()[0];
                return role != null && role instanceof String && role.equals(persmission);
            }
        });

        replay(mock);

        return mock;
    }


    // Annotated inner classes for testing different action and constraint combinations

    private static class InstantiateWhen {
        @ShiroSecurityConstraint(action = ShiroAction.INSTANTIATE, constraint = ShiroConstraint.LoggedIn)
        private static class LoggedInOnly extends Panel {
			private static final long serialVersionUID = 1L;

			private LoggedInOnly() {
                super("id");
            }
        }

        @ShiroSecurityConstraint(action = ShiroAction.INSTANTIATE, constraint = ShiroConstraint.IsAuthenticated)
        private static class AuthenticatedOnly extends Panel {
			private static final long serialVersionUID = 1L;

			private AuthenticatedOnly() {
                super("id");
            }
        }

        @ShiroSecurityConstraint(action = ShiroAction.INSTANTIATE, constraint = ShiroConstraint.HasRole, value = "ADMIN")
        private static class SubjectHasRole extends Panel {
			private static final long serialVersionUID = 1L;

			private SubjectHasRole() {
                super("id");
            }
        }

        @ShiroSecurityConstraint(action = ShiroAction.INSTANTIATE, constraint = ShiroConstraint.HasPermission, value = "any:permission")
        private static class SubjectHasPermission extends Panel {
			private static final long serialVersionUID = 1L;

			private SubjectHasPermission() {
                super("id");
            }
        }
    }

    private static class RenderWhen {
        @ShiroSecurityConstraint(action = ShiroAction.RENDER, constraint = ShiroConstraint.LoggedIn)
        private static class LoggedInOnly extends Panel {
			private static final long serialVersionUID = 1L;

			private LoggedInOnly() {
                super("id");
            }
        }

        @ShiroSecurityConstraint(action = ShiroAction.RENDER, constraint = ShiroConstraint.IsAuthenticated)
        private static class AuthenticatedOnly extends Panel {
			private static final long serialVersionUID = 1L;

			private AuthenticatedOnly() {
                super("id");
            }
        }

        @ShiroSecurityConstraint(action = ShiroAction.RENDER, constraint = ShiroConstraint.HasRole, value = "ADMIN")
        private static class SubjectHasRole extends Panel {
			private static final long serialVersionUID = 1L;

			private SubjectHasRole() {
                super("id");
            }
        }

        @ShiroSecurityConstraint(action = ShiroAction.RENDER, constraint = ShiroConstraint.HasPermission, value = "any:permission")
        private static class SubjectHasPermission extends Panel {
			private static final long serialVersionUID = 1L;

			private SubjectHasPermission() {
                super("id");
            }
        }
    }

    private static class EnableWhen {
        @ShiroSecurityConstraint(action = ShiroAction.ENABLE, constraint = ShiroConstraint.LoggedIn)
        private static class LoggedInOnly extends Panel {
			private static final long serialVersionUID = 1L;

			private LoggedInOnly() {
                super("id");
            }
        }

        @ShiroSecurityConstraint(action = ShiroAction.ENABLE, constraint = ShiroConstraint.IsAuthenticated)
        private static class AuthenticatedOnly extends Panel {
			private static final long serialVersionUID = 1L;

			private AuthenticatedOnly() {
                super("id");
            }
        }

        @ShiroSecurityConstraint(action = ShiroAction.ENABLE, constraint = ShiroConstraint.HasRole, value = "ADMIN")
        private static class SubjectHasRole extends Panel {
			private static final long serialVersionUID = 1L;

			private SubjectHasRole() {
                super("id");
            }
        }

        @ShiroSecurityConstraint(action = ShiroAction.ENABLE, constraint = ShiroConstraint.HasPermission, value = "any:permission")
        private static class SubjectHasPermission extends Panel {
			private static final long serialVersionUID = 1L;

			private SubjectHasPermission() {
                super("id");
            }
        }
    }

}
