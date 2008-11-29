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
package org.wicketstuff.annotation.scan;

import org.apache.wicket.*;
import org.apache.wicket.request.target.coding.*;
import org.wicketstuff.config.*;
import org.wicketstuff.annotation.mount.*;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * Looks for mount information by scanning for classes annotated with {@link MountPath}.
 * You can specify a package to scan (e.g., "org.mycompany.wicket.pages").  Wildcards
 * also work (e.g., "org.mycompany.*.pages" or "org.mycompany.**.pages").
 *
 * <p>You can also go more advanced, using any pattern supported by
 * {@link MatchingResources}.  For example, the first package example above is turned into
 * "classpath*:/org/mycompany/wicket/pages/**&#47;*.class".
 *
 * <p>For each class that is annotated, an appropriate {@link IRequestTargetUrlCodingStrategy}
 * implementing class is created using the information in the {@link MountPath} annotation
 * and any supplemental annotations.  Each instance is added to the list to return.  Each item
 * in the returned list can then be mounted.
 *
 * <p>Typical usage is in your {@link Application#init()} method and utilizes the
 * {@link AnnotatedMountList#mount} convenience method.
 *
 * <pre>
 *  protected void init()
 *  {
 *      new AnnotatedMountScanner().scanPackage("org.mycompany.wicket.pages").mount(this);
 *  }
 * </pre>
 *
 * <p>You could scan the entire classpath if you wanted by passing in null, but that might
 * require more time to run than limiting it to known packages which have annotated classes.
 *
 * <p>Page classes annotation usage is as follows:
 *
 * <pre>
 *  &#64;MountPath(path = "hello")
 *  private class HelloPage extends Page
 *  {
 *  }
 *
 *  &#64;MountPath(path = "dogs", alt = {"canines", "k9s"})
 *  &#64;MountMixedParam(parameterNames = {"dexter", "zorro"})
 *  private class DogsPage extends Page
 *  {
 *  }
 * </pre>
 *
 * <p>The first example will mount HelloPage to /hello using the default encoding strategy (as
 * returned by {@link #getDefaultStrategy} which is {@link BookmarkablePageRequestTargetUrlCodingStrategy}.
 *
 * <p>The second example will mount DogsPage at "/dogs" (as the primary) and as "/canines" and "/k9s" as
 * alternates using the {@link MixedParamUrlCodingStrategy}.  Further, the second example specifies that
 * {"dexter", "zorro"} String array is to be passed to the constructor.  The value for the pageMapName
 * argument is null.
 *
 * @author Doug Donohoe
 */
public class AnnotatedMountScanner
{
    //private static Logger logger = LoggerFactory.getLogger(AnnotatedMountScanner.class);

    /**
     * Get the Spring search pattern given a package name or part of a package name
     * @param packageName a package name
     * @return a Spring search pattern for the given package
     */
    public String getPatternForPackage(String packageName)
    {
        if (packageName == null) packageName = "";
        packageName = packageName.replace('.', '/');
        if (!packageName.endsWith("/"))
        {
            packageName += '/';
        }

        return "classpath*:" + packageName + "**/*.class";
    }


    /**
     * Scan given a package name or part of a package name and return list of classes with MountPath annotation.
     * @return A List of classes annotated with &#64;MountPath
     */
    public List<Class<?>> getPackageMatches(String pattern)
    {
        return getPatternMatches(getPatternForPackage(pattern));
    }

    /**
     * Scan given a Spring search pattern and return list of classes with MountPath annotation.
     * @return A List of classes annotated with &#64;MountPath
     */
    public List<Class<?>> getPatternMatches(String pattern)
    {
        MatchingResources resources = new MatchingResources(pattern);
        List<Class<?>> mounts = resources.getAnnotatedMatches(MountPath.class);
        for (Class<?> mount : mounts)
        {
            if (!(Page.class.isAssignableFrom(mount)))
            {
                throw new RuntimeException("@MountPath annotated class should subclass Page: " + mount);
            }
        }
        return mounts;
    }

    /**
     * Scan given package name or part of a package name
     * @param packageName a package to scan (e.g., "org.mycompany.pages)
     * @return An {@link AnnotatedMountList}
     */
    public AnnotatedMountList scanPackage(String packageName)
    {
        return scanList(getPackageMatches(packageName));
    }

    /**
     * Scan given a Spring search pattern.
     * @param pattern
     * @return An {@link AnnotatedMountList}
     */
    public AnnotatedMountList scanPattern(String pattern)
    {
        return scanList(getPatternMatches(pattern));
    }


    /**
     * Scan a list of classes which are annotated with MountPath
     * @param mounts
     * @return An {@link AnnotatedMountList}
     */
    @SuppressWarnings({"unchecked"})
    protected AnnotatedMountList scanList(List<Class<?>> mounts)
    {
        AnnotatedMountList list = new AnnotatedMountList();
        for (Class<?> mount : mounts)
        {
            Class<? extends Page> page = (Class<? extends Page>) mount;
            scanClass(page, list);
        }
        return list;
    }

    /**
     * Scan given a class that is a sublass of {@link Page}.
     * @param pageClass {@link Page} subclass to scan
     * @return An {@link AnnotatedMountList} containing the primary and alternate strategies created for the class.
     */
    public AnnotatedMountList scanClass(Class<? extends Page> pageClass)
    {
        AnnotatedMountList list = new AnnotatedMountList();
        scanClass(pageClass, list);
        return list;
    }

    /**
     * Magic of all this is done here.
     * @param pageClass
     * @param list
     */
    private void scanClass(Class<? extends Page> pageClass, AnnotatedMountList list)
    {
        MountPath mountPath = pageClass.getAnnotation(MountPath.class);
        if (mountPath == null) return;

        // find first annotation that is annotated with @MountDefinition
        Annotation pageSpecificMountDetails = null;
        Class<? extends Annotation> mountStrategyAnnotationClass = null;
        MountDefinition mountDefinition = null;
        Annotation[] annos = pageClass.getAnnotations();
        for (Annotation anno : annos)
        {
            mountDefinition = anno.annotationType().getAnnotation(MountDefinition.class);
            if (mountDefinition != null)
            {
                pageSpecificMountDetails = anno;
                mountStrategyAnnotationClass = anno.getClass();
                break;
            }
        }

        // default if no @MountDefinition annotated annotation is available
        if (pageSpecificMountDetails == null)
        {
            // primary
            list.add(getDefaultStrategy(mountPath.path(), pageClass));

            // alternates
            for (String alt : mountPath.alt())
            {
                list.add(getDefaultStrategy(alt, pageClass));
            }
            return;
        }

        // get the actual strategy we'll be creating
        Class<? extends IRequestTargetUrlCodingStrategy> strategyClass = mountDefinition.strategyClass();

        // need to determine the constructor - we support constructors that
        // take a String (mount path) and a Class (page)
        int STANDARD_ARGS = 2;
        String[] argOrder = mountDefinition.argOrder();
        Class<?>[] paramTypes = new Class<?>[argOrder.length + STANDARD_ARGS];
        Object[] initArgs = new Object[paramTypes.length];

        // deafult first two arguments - mount path and page class
        paramTypes[0] = String.class;
        paramTypes[1] = Class.class;
        initArgs[0] = null; // provided below
        initArgs[1] = pageClass;

        // get remaining constructor args which match those specified by 'argOrder'
        for (int i = 0; i < argOrder.length; i++)
        {
            int index = i + STANDARD_ARGS;
            Method method = null;

            try
            {
                method = mountStrategyAnnotationClass.getDeclaredMethod(argOrder[i]);
                paramTypes[index] = method.getReturnType();
                initArgs[index] = method.invoke(pageSpecificMountDetails);

                // can't default an annotation to null, so use this as a workaround
                if (initArgs[index].equals(MountDefinition.NULL)) initArgs[index] = null;
            }
            catch (NoSuchMethodException e)
            {
                throw new RuntimeException("argOrder[" + i + "] = " + argOrder[i] + " not found in annotation " +
                                            mountStrategyAnnotationClass.getName(), e);
            }
            catch (Exception e)
            {
                throw new RuntimeException("Unable to invoke method " + method + " on annotation " +
                                            pageSpecificMountDetails.getClass().getName(), e);
            }
        }

        // get matching constructor
        Constructor<? extends IRequestTargetUrlCodingStrategy> ctx = null;
        try
        {
            ctx = strategyClass.getConstructor(paramTypes);
        }
        catch (NoSuchMethodException e)
        {
            throw new RuntimeException("No constructor matching parameters defined by 'argOrder' found for " +
                                       strategyClass, e);
        }

        // create new instances
        try
        {
            // primary mount path
            initArgs[0] = mountPath.path();
            list.add(ctx.newInstance(initArgs));

            // alternate paths
            for (String alt : mountPath.alt())
            {
                initArgs[0] = alt;
                list.add(ctx.newInstance(initArgs));
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to invoke constructor " + ctx + " for " + strategyClass, e);
        }
    }

    /**
     * Returns the default coding strategy given a mount path and class.
     * @param mountPath
     * @param pageClass
     * @return {@link BookmarkablePageRequestTargetUrlCodingStrategy}
     */
    public IRequestTargetUrlCodingStrategy getDefaultStrategy(String mountPath, Class<? extends Page> pageClass)
    {
        return new BookmarkablePageRequestTargetUrlCodingStrategy(mountPath, pageClass, null);
    }
}
