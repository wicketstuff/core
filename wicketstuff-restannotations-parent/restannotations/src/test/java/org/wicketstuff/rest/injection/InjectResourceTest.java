/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.rest.injection;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.ResourceReferenceRegistry;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.rest.resource.MountedResource;

public class InjectResourceTest 
{

    private WicketTester tester;
    private final Roles roles = new Roles();

    @Before
    public void setUp()
    {
        tester = new WicketTester(new SpringApplication(roles));
    }

    @After
    public void tearDown()
    {
        // session must remain temporary.
        assertTrue(Session.get().isTemporary());
        tester.destroy();
    }

    @Test
    public void testInjection() throws Exception 
    {
        ResourceReferenceRegistry registry = Application.get().getResourceReferenceRegistry();
        ResourceReference resourceReference = registry.getResourceReference(Application.class, "MountedResource", null, null, null, false, false);
        MountedResource resource = (MountedResource) resourceReference.getResource();
        
        assertEquals(SpringApplication.INJECTED_VALUE, resource.getInjectedValue());
    }
}
