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

import junit.framework.*;
import org.apache.wicket.*;
import org.apache.wicket.request.target.coding.*;
import org.wicketstuff.annotation.mount.*;

import java.lang.annotation.*;
import java.util.*;

/**
 * @author Doug Donohoe
 */
public class AnnotationTest extends TestCase
{
    /**
     * Correct mount definition
     */
    @Retention(RetentionPolicy.RUNTIME)
    @MountDefinition(strategyClass = TestCodingStrategy.class, argOrder = {"map", "params"})
    private @interface TestMountDefinition
    {
        public abstract String map() default MountDefinition.NULL;
        public abstract String[] params();
    }

    /**
     * Page with no annotation
     */
    private static class NoMount extends Page
    {

    }

    /**
     * mount name for correctly annotated page
     */
    private static final String DEFAULT_MOUNT = "default";


    /**
     * Page with missing mount details (should use default)
     */
    @MountPath(path = DEFAULT_MOUNT + 0, alt = {DEFAULT_MOUNT + 1})
    private static class MissingDetails extends Page
    {

    }

    /**
     * mount name for correctly annotated page
     */
    private static final String CORRECT_MOUNT = "foobar";

    /**
     * Page correctly annotated
     */
    @MountPath(path = CORRECT_MOUNT + 0, alt = {CORRECT_MOUNT + 1, CORRECT_MOUNT + 2})
    @TestMountDefinition(params = { "dexter", "zorro" })
    private static class CorrectPage extends Page
    {

    }

    /**
     * Subclass to provide access to reference to Page class
     */
    @SuppressWarnings({"PublicInnerClass"})
    public static class TestCodingStrategy extends MixedParamUrlCodingStrategy
    {
        public TestCodingStrategy(String mountPath, Class<?> bookmarkablePageClass, String pageMapName, String[] parameterNames)
        {
            super(mountPath, bookmarkablePageClass, pageMapName, parameterNames);
        }

        public Class<?> getBookmarkClass()
        {
            return (Class<?>) bookmarkablePageClassRef.get();
        }
    }

    /**
     * test annotation logic
     */
    public void testAnnotation()
    {
        AnnotatedMountScanner scanner = new AnnotatedMountScanner();
        IRequestTargetUrlCodingStrategy def = scanner.getDefaultStrategy("default", Page.class);

        AnnotatedMountList list;

        // test no annotation
        list = scanner.scanClass(NoMount.class);
        assertEquals("No annotation should return zero", 0, list.size());

        // test if details annotation is missing - should return default
        list = scanner.scanClass(MissingDetails.class);
        validateDefault(list.get(0), def.getClass(), 0);
        validateDefault(list.get(1), def.getClass(), 1);

        // test correct page
        list = scanner.scanClass(CorrectPage.class);
        validateCorrect(list.get(0), 0);
        validateCorrect(list.get(1), 1);
        validateCorrect(list.get(2), 2);
    }

    /**
     * Test scanner by package
     */
    public void testScanPackage()
    {
        String[] packages = new String[] {"org.wicketstuff.annotation.scan",
                                          "org/wicketstuff/annotation/scan",
                                          "org\\wicketstuff\\annotation\\sc*"};
        for (String pack : packages)
        {
            doPackage(pack);
        }
    }

    /**
     * Test scanner by pattern
     */
    public void testScanPattern()
    {
        String[] patterns = new String[] {"classpath*:org/wicketstuff/annotation/scan/*.class",
                                          "classpath*:org/wicketstuff/**/A*.class",
                                          "classpath*:org/wicketstuff/**/AnnotationTest*.class"};
        for (String pattern : patterns)
        {
            doPattern(pattern);
        }
    }

    /**
     * Run scanPackage and validate results
     */
    private void doPackage(String pack)
    {
        AnnotatedMountScanner scanner = new AnnotatedMountScanner();
        IRequestTargetUrlCodingStrategy def = scanner.getDefaultStrategy("default", Page.class);

        List<Class<?>> matches = scanner.getPackageMatches(pack);
        validateMatches(matches);

        AnnotatedMountList list = scanner.scanPackage(pack);
        validateList(list, pack, def.getClass());
    }

    /**
     * Run scanPattern and validate results
     */
    private void doPattern(String pattern)
    {
        AnnotatedMountScanner scanner = new AnnotatedMountScanner();
        IRequestTargetUrlCodingStrategy def = scanner.getDefaultStrategy("default", Page.class);

        List<Class<?>> matches = scanner.getPatternMatches(pattern);
        validateMatches(matches);

        AnnotatedMountList list = scanner.scanPattern(pattern);
        validateList(list, pattern, def.getClass());
    }

    /**
     * Validate a list of classes matches what we'd expect in this test environment
     * @param matches
     */
    private void validateMatches(List<Class<?>> matches)
    {
        assertTrue(matches.size() == 2);
        assertTrue(matches.contains(CorrectPage.class));
        assertTrue(matches.contains(MissingDetails.class));
    }

    /**
     * Validate a list returned is what we'd expect in this test environment
     */
    private void validateList(AnnotatedMountList list, String info, Class<? extends IRequestTargetUrlCodingStrategy> defClass)
    {
        int expected = 5;
        assertEquals("Should have found " + expected + " for " + info, expected, list.size());

        int correct = 0;
        int def = 0;
        for (IRequestTargetUrlCodingStrategy strategy : list)
        {
            if (strategy instanceof TestCodingStrategy)
            {
                validateCorrect(strategy, correct);
                correct++;
            }
            else if (strategy.getClass().equals(defClass))
            {
                validateDefault(strategy, defClass, def);
                def++;
            }
            else
            {
                fail("Unexpected strategy type: " + strategy.getClass());
            }
        }
    }

    /**
     * Validate return for CorrectPage annotations
     */
    private void validateCorrect(IRequestTargetUrlCodingStrategy cs, int i)
    {
        assertNotNull(cs);
        assertEquals(TestCodingStrategy.class, cs.getClass());

        TestCodingStrategy fixed = (TestCodingStrategy) cs;
        assertEquals(CORRECT_MOUNT + i, fixed.getMountPath());
        assertEquals(CorrectPage.class, fixed.getBookmarkClass());
    }

    /**
     * Validate return for MissingDetails annotations
     */
    private void validateDefault(IRequestTargetUrlCodingStrategy cs, Class<? extends IRequestTargetUrlCodingStrategy> defClass, int i)
    {
        assertNotNull(cs);
        assertEquals(defClass, cs.getClass());

        assertEquals(DEFAULT_MOUNT + i, cs.getMountPath());
    }
}