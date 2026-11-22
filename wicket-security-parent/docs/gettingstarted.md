---
title: Getting started with Swarm
---

> This documentation was written for Wicket Security 1.x.

This section will show you how to get started as soon as possible using Swarm.
If you feel there are certain things you must do differently, you should check
the FAQ or ask the wicket mailing list.

## The WebApplication

The easiest way is to let your web app extend SwarmWebApplication as it takes
care of most of the grunt work for you.

```java
package org.apache.wicket.security.examples.multilogin;

public class MyApplication extends SwarmWebApplication {
    public MyApplication() {
    }

    @Override
    protected Object getHiveKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void setUpHive() {
        // TODO Auto-generated method stub
    }

    @Override
    public Class getHomePage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class getLoginPage() {
        // TODO Auto-generated method stub
        return null;
    }
}
```

These are 4 methods you must provide (one of them is already required by wicket anyway):

 * `getHiveKey()`: All Swarm applications use a hive, most of them are the
   only ones using that hive, but it is possible to share your hive between
   multiple applications in the same vm. Hives are stored and accessed by a
   key and swarm needs to know what that key is. I suggest you use the
   contextname of you're web app, if you are using servlet-api 2.5 you could
   do return getServletContext().getContextPath(); If you are not you could
   set some init-params in the web.xml and read those or hardcode something.
   Just remember if you hardcode a key and your app gets deployed twice they
   will be using the same key and thus sharing the hive, which might not be
   what you want.

 * `setupHive()`: This gives you the chance to register the hive. If you are
   the only one using this hive this is required, if you are sharing this
   hive, you need to check first if the hive was not already registered, as
   the registry does not allow you to register multiple hives with the same
   key.

 * `getHomePage()`: This is already required by wicket, it is the page you see
   when no other page is specified in the url. This can be both a secure page
   or a regular page, if it is a secure page you will be redirected to the
   login page, see below.

 * `getLoginPage()`: This is the default page for authenticating users, if you
   need to use multi-login with multiple different login pages you need to
   specify the one for the lowest user class (in effect the page where every
   user has to login anyway).

So our application could look like this:

```java
package org.apache.wicket.security.examples.multilogin;

public class MyApplication extends SwarmWebApplication {
    public MyApplication() {
    }

    @Override
    protected Object getHiveKey() {
        //if you are using servlet api 2.5 I would suggest using:
        //return getServletContext().getContextPath();

        //if not you have several options:
        //-an initparam in web.xml
        //-a static object
        //-a random object
        //-whatever you can think of

        //for this example we will be using a fixed string
        return "multi-login";
    }

    @Override
    protected void setUpHive() {
        //create factory
        // For 1.4 use new SwarmPolicyFileHiveFactory(getActionFactory());
        PolicyFileHiveFactory factory = new PolicyFileHiveFactory();

        try {
            //this example uses 1 policy file but you can add as many as you like
            factory.addPolicyFile(getServletContext().getResource("/WEB-INF/multilogin.hive"));
        }
        catch (MalformedURLException e) {
            throw new WicketRuntimeException(e);
        }
        //register factory
        HiveMind.registerHive(getHiveKey(), factory);
        //note we are not checking if a hive already exist because this app will only be deployed once
    }

    @Override
    public Class getHomePage() {
        return HomePage.class;
    }

    @Override
    public Class getLoginPage() {
        return LoginPage.class;
    }
}
```

## The Hive

As you can see we instructed our factory to configure our hive based on a
file. For those of you familiar with <a title="Java Authentication and
Authorisation Service"><abbr>JAAS</abbr></a>, you will find the structure
strikingly similar. It lays out a number of Principals each containing
Permissions for components or models or something you thought of.

    grant
    {
        permission org.apache.wicket.security.hive.authorization.permissions.ComponentPermission "org.apache.wicket.security.examples.multilogin.pages.HomePage", "inherit, render";
        permission org.apache.wicket.security.hive.authorization.permissions.ComponentPermission "org.apache.wicket.security.examples.multilogin.pages.HomePage", "enable";
    };

What we just did is grant everyone the right to see (render) our `HomePage`, if
there are secure components on the homepage we can see them too (inherit). In
addition we granted links to our homepage the right to be clicked (enable).
Because we do not want to give links on our homepage the right to be clicked
we did not place the enable action on the previous line with the inherit. If
we know for a fact that there are absolutely no links pointing to the homepage
we could delete the second line, but generally you will want these two lines
for any given secure page.

The following example might show more clear what I mean with the above.
Suppose I have put a SecurePageLink on my homepage pointing to `PageA`. Given
our previous policy the link will never be rendered because it lacks the
render action for `PageA`. So if we change our policy to this.

    grant
    {
        permission org.apache.wicket.security.hive.authorization.permissions.ComponentPermission "org.apache.wicket.security.examples.multilogin.pages.HomePage", "inherit, render";
        permission org.apache.wicket.security.hive.authorization.permissions.ComponentPermission "org.apache.wicket.security.examples.multilogin.pages.HomePage", "enable";
        permission org.apache.wicket.security.hive.authorization.permissions.ComponentPermission "org.apache.wicket.security.examples.multilogin.pages.PageA", "inherit, render";
    };

The link to `PageA` will now be rendered, however because we did not grant the
enable action wicket will render the link disabled (by default wicket converts
it to a span tag) so you cannot click the link. So if we make one final change
to our policy.

    grant
    {
        permission org.apache.wicket.security.hive.authorization.permissions.ComponentPermission "org.apache.wicket.security.examples.multilogin.pages.HomePage", "inherit, render";
        permission org.apache.wicket.security.hive.authorization.permissions.ComponentPermission "org.apache.wicket.security.examples.multilogin.pages.HomePage", "enable";
        permission org.apache.wicket.security.hive.authorization.permissions.ComponentPermission "org.apache.wicket.security.examples.multilogin.pages.PageA", "inherit, render";
        permission org.apache.wicket.security.hive.authorization.permissions.ComponentPermission "org.apache.wicket.security.examples.multilogin.pages.PageA", "enable";
    };

This will render the link such that it can be clicked. For those of you
wanting to read more about this, I have described it in more detail and with
an alternative on the wicket mailing list here. Note that the inherit action
in "inherit, render" is not strictly required but it usually is a good idea to
put it there as it allows all secure components on the HomePage to inherit the
render action (see alternative on the mailing list for more details).

If you think, what a long line isn't there a shorter way, then I have good
news for you. Hive supports aliases. This means that besides the build in
aliases for permissions you can add your own aliases for permissions,
principals and names, just not for actions!. aliases can be concatenated but
not nested. so if we rewrite our policy to use aliases we get

    grant
    {
        permission ${ComponentPermission} "${hp}", "inherit, render";
        permission ${ComponentPermission} "${hp}", "enable";
    };

${ComponentPermission} is a build in permission but ${hp} is not so we need to
define our alias like this

    protected void setUpHive() {
        //create factory
        PolicyFileHiveFactory factory = new PolicyFileHiveFactory();
        try {
            //this example uses 1 policy file but you can add as many as you like
            factory.addPolicyFile(getServletContext().getResource("/WEB-INF/multilogin.hive"));
            factory.setAlias("hp", "org.apache.wicket.security.examples.multilogin.pages.HomePage");
        }
        catch (MalformedURLException e) {
            throw new WicketRuntimeException(e);
        }
        //register factory
        HiveMind.registerHive(getHiveKey(), factory);
        //note we are not checking if a hive already exist because this app will only be deployed once
    }

## Pages

Earlier I mentioned secure pages and regular pages. What I meant with those is
this:

_Secure pages_ are pages requiring an authenticated user with certain
privileges.

_Regular pages_ are pages anyone can see.

In swarm you achieve this by having the secure pages either extend
`SecureWebPage` or implement `ISecurePage`. By default every page that is an
instance of `ISecurePage` needs to have a logged in user or the user will be
redirected to the login page. Although swarm provides ways for you to
customize the class that is checked, it is unlikely you will need to change
this default behavior. Off course having the login page implement
`ISecurePage` is not a good idea.

## Authentication and authorization

There are many frameworks for authenticating, all of them should be relatively
easy to use with swarm and some of them will no doubt be made available by
default in Swarm. Thus minimizing the effort on your side but until they are
available you will need to get your hands dirty

I will show authentication in a moment, lets focus on authorization first.

Authorization in Swarm is handled by permissions and principals. principals
are used to group permissions and are granted to users. permissions are never
granted to users but are instead defined by the system. as shown swarm stores
them in a policy file by default. Once you get to know the full power of
principals you may want to build your own by implementing the Principal
interface, but for now you can use SimplePrincipal. As the name suggests it is
very simple and does not use any of the advanced features like principal
inheritance. Note that although it is not shown here the class and name of the
principal can be aliased too.

    grant principal org.apache.wicket.security.hive.authorization.SimplePrincipal "basic"
    {
        permission ${ComponentPermission} "org.apache.wicket.security.examples.multilogin.pages.BankAccountBalancePage", "inherit, render";
        permission ${ComponentPermission} "org.apache.wicket.security.examples.multilogin.pages.BankAccountBalancePage", "enable";
        permission ${ComponentPermission} "org.apache.wicket.security.examples.multilogin.pages.InitiateTransferMoneyPage", "inherit, render";
        permission ${ComponentPermission} "org.apache.wicket.security.examples.multilogin.pages.InitiateTransferMoneyPage", "enable";
    };

By dividing our pages over several principals we can establish a firm base for
a role based secure application.

Now we are ready to grant users some principals. The easiest way to login (or
logoff for that matter) is through the session. The (Wasp)session has a login
method that accepts any object passed in. Swarm is a little bit more picky and
requires a LoginContext. A LoginContext is nothing more then a callback
function to get the user. LoginContexts are throw away obects and therefor
ideal for storing user credentials (like passwords) you don't want hanging
around in your session. For more advanced stuff you will want to extend
LoginContext directly but for the simple username password stuff you can
extend UsernamePasswordContext

    public class MyContext extends UsernamePasswordContext {
        public MyContext(String username, String password) {
            super(username, password);
        }

        @Override
        protected Subject getSubject(String username, String password) throws LoginException {
            //authenticate username, password, throw exception if not found
            ......
            DefaultSubject user=new DefaultSubject();
            //grant principals: (user.addPrincipal(Principal))
            //Note Subjects are read only, but implementations like DefaultSubject will usually have some sort of way for you to add principals. Implementations are required to honor the readonly flag which swarm sets immediately after obtaining the subject.
            ....

            return user;
        }
    }

Please note that you should not create anonymous subclasses of `Subject`s in
the `LoginContext` because if the subject is serialized along with the session
and being an anonymous subclass it would also cause the LoginContext to be
serialized. Instead create a static named subclass.

## Actions

The default actions access, inherit, render and enable might not be enough for
you. You might want to make your own custom actions. This is where the
ActionFactory comes in. Suppose I want to use actions to create a vertical
authorization separation between my users/rights. For example if I have a
principal "edit staff personalia" and for some users this principal applies to
the entire organization whereas for other users it only applies to staff for
there own department.

First we need to define our actions

    /**
     * Represents actions granted at the department level.
     */
    public interface Department extends WaspAction {
    }

    /**
     * Represents actions granted at the organization level.
     */
    public interface Organization extends WaspAction {
    }

Next we need to use these in our action factory.

    public class MyActionFactory extends SwarmActionFactory {
        public MyActionFactory() {
            super();
            try
            {
                // note none of the actions registered this way will
                // implement the interface defined here, you will
                // simply get the default action
                register(Department.class,"department");

                // registering an action this way will return the
                // actual implementation specified here however the
                // reason we are using a custom implementation here
                // is because we need to inherit the department
                // action
                register(Organisation.class, new ImpliesOtherAction(nextPowerOf2(),"organization",this,Department.class));
            }
            catch (RegistrationException e) {
                throw new WicketRuntimeException("actionfactory was not setup correctly",e);
            }
        }
    }

The `ImpliesOtherAction` comes with swarm and allows your action
(`Organisation` in this case) to imply 1 or more previously registered
actions.

Now that we have registered our actions we can start using them in our
`ISecurityCheck`s. In the following example I chose to extend the security
check for links allowing me to hide/show links whether a user has organization
or department rights.

    public class DepartmentLinkCheck extends LinkSecurityCheck {
        private static final long serialVersionUID = 1L;

        private boolean secureDepartment;

        public DepartmentLinkCheck(AbstractLink component, Class clickTarget, Department department)
        {
            super(component, clickTarget);

            // our department entity has a flag letting us know if it should
            // be protected or not, but you could ofcourse base the decision
            // on anything you like
            secureDepartment = department.secure;
        }

        ......

        @Override
        public boolean isActionAuthorized(WaspAction action) {
            // for secure departments you need organization rights, else
            // department rights are sufficient
            WaspAction myAction = action.add(getActionFactory().getAction(
                secureDepartment
                    ? Organization.class
                    : org.apache.wicket.security.examples.customactions.authorization.Department.class)
                );

            // this is as easy as adding the required actions and
            // then let the super implementation take over.
            return super.isActionAuthorized(myAction);
        }

    }

Besides using a custom security check you can also use your custom actions in
secure models.

## Secure models

Secure models can be used if you want to have many components using the same
check, so instead of assigning a security check to each of them you could use
a `SwarmCompoundPropertyModel` on a shared parent component and authorization
is automatically handled.

By default Wasp first checks if a component has been assigned a security check
and if so the check has the final say, if no check is found the model of the
component is inspected, if it is a subclass of `ISecureModel` the model has the
final say. This does not mean you can not use security checks an secure models
together, you just need to have the security check also check the model. For
example `ComponentSecurityCheck` offers this option. In your policy you need
to grant a `DataPermission` instead of a `ComponentPermission` like this.

    permission ${DataPermission} "department", "inherit, render, department";

The name of the permission "department" is specified in the secure model and
is nothing more than a reference, like the id of a component. To conclude this
getting started some code showing a secure model with custom actions.

    public class DepartmentModel extends SwarmCompoundPropertyModel {
        private static final long serialVersionUID = 1L;

        public DepartmentModel(Object object) {
            super(object);
        }

        /**
         * @see org.apache.wicket.security.swarm.models.SwarmModel#getSecurityId(org.apache.wicket.Component)
         */
        public String getSecurityId(Component component) {
            return "department"; //remember the name of the datapermission? this is it
        }

        /**
         * @see org.apache.wicket.security.models.ISecureModel#isAuthorized(org.apache.wicket.Component,
         *      org.apache.wicket.security.actions.WaspAction)
         */
        public boolean isAuthorized(Component component, WaspAction action) {
            WaspAction myAction=action;
            Object obj = getObject();
            //the department entity not to be confused with the department action further down.
            if (obj instanceof Department) {
                Department department = (Department)obj;

                // for secure departments you need organization rights, else
                // department rights are sufficient
                myAction = action.add(getActionFactory().getAction(
                    department.secure
                        ? Organization.class
                        : org.apache.wicket.security.examples.customactions.authorization.Department.class)
                    );
            }
            return super.isAuthorized(component, myAction);
        }
    }
