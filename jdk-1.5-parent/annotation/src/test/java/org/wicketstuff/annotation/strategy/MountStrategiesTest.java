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
package org.wicketstuff.annotation.strategy;

import junit.framework.*;
import org.apache.wicket.*;
import org.apache.wicket.request.target.coding.*;
import org.wicketstuff.annotation.mount.*;
import org.wicketstuff.annotation.scan.*;

/**
 * @author Doug Donohoe
 */
public class MountStrategiesTest extends TestCase
{
    @MountPath(path = "primary", alt = {"alt"})
    @MountBookmarkablePageRequestTarget(pageMapName = "map")
    private static class BookmarkablePage extends Page { }

    @MountPath(path = "primary", alt = {"alt"})
    @MountHybrid(redirectOnBookmarkableRequest = false)
    private static class HybridPage extends Page { }

    @MountPath(path = "primary", alt = {"alt"})
    @MountIndexedHybrid
    private static class IndexedHybridPage extends Page { }

    @MountPath(path = "primary", alt = {"alt"})
    @MountIndexedParam(pageMapName = "map")
    private static class IndexedParamPage extends Page { }

    @MountPath(path = "primary", alt = {"alt"})
    @MountMixedParam(pageMapName = "map", parameterNames = {"one", "two", "three"})
    private static class MixedParamPage extends Page { }

    @MountPath(path = "primary", alt = {"alt"})
    @MountQueryString
    private static class QueryPage extends Page { }

    /**
     * test each mount strategy
     */
    public void testMountStrategies()
    {
        AnnotatedMountScanner scanner = new AnnotatedMountScanner();
        AnnotatedMountList list;
        int count = 0;

        list = scanner.scanClass(BookmarkablePage.class);
        validate(list, BookmarkablePageRequestTargetUrlCodingStrategy.class);
        count++;

        list = scanner.scanClass(HybridPage.class);
        validate(list, HybridUrlCodingStrategy.class);
        count++;

        list = scanner.scanClass(IndexedHybridPage.class);
        validate(list, IndexedHybridUrlCodingStrategy.class);
        count++;

        list = scanner.scanClass(IndexedParamPage.class);
        validate(list, IndexedParamUrlCodingStrategy.class);
        count++;

        list = scanner.scanClass(MixedParamPage.class);
        validate(list, MixedParamUrlCodingStrategy.class);
        count++;

        list = scanner.scanClass(QueryPage.class);
        validate(list, QueryStringUrlCodingStrategy.class);
        count++;

        count *= 2;
        list = scanner.scanPackage("org.wicketstuff.annotation.strategy");
        assertEquals("Should have gotten " + count + " items", count, list.size());
    }

    private void validate(AnnotatedMountList list, Class<?> c)
    {
        assertEquals("Should be 2 instances", 2, list.size());

        for (IRequestTargetUrlCodingStrategy strategy : list)
        {
            assertEquals("Class should match", c, strategy.getClass());
        }
    }

}