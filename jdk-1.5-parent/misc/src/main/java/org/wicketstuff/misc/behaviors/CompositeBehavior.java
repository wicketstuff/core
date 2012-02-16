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
package org.wicketstuff.misc.behaviors;

import java.util.Arrays;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;


/**
 * Represents a composite behavior allowing the user to attach multiple behaviors to a
 * component at once.
 *
 * @author David Bernard
 * @author Erik Brakkee
 * @see http://cwiki.apache.org/WICKET/composite-behaviors.html
 */
@SuppressWarnings("serial")
public class CompositeBehavior implements IBehavior, IHeaderContributor {
    private Iterable<IBehavior> behaviors_;

    public CompositeBehavior(IBehavior... behaviors) {
        this(Arrays.asList(behaviors));
    }

    public CompositeBehavior(Iterable<IBehavior> behaviors) {
        behaviors_ = behaviors;
    }

    public void exception(Component aComponent, RuntimeException aException) {
        for (IBehavior behavior: behaviors_) {
            behavior.exception(aComponent, aException);
        }
    }

    public void onComponentTag(Component aComponent, ComponentTag aTag) {
        for (IBehavior behavior: behaviors_) {
            behavior.onComponentTag(aComponent, aTag);
        }
    }

    public void afterRender(Component arg0) {
        for (IBehavior behavior: behaviors_) {
            behavior.afterRender(arg0);
        }
    }

    public void beforeRender(Component arg0) {
        for (IBehavior behavior: behaviors_) {
            behavior.beforeRender(arg0);
        }
    }

    public void bind(Component arg0) {
        for (IBehavior behavior: behaviors_) {
            behavior.bind(arg0);
        }
    }

    public void detach(Component arg0) {
        for (IBehavior behavior: behaviors_) {
            behavior.detach(arg0);
        }
    }

    public boolean getStatelessHint(Component arg0) {
        boolean back = true;
        for (IBehavior behavior: behaviors_) {
            back = back && behavior.getStatelessHint(arg0);
        }
        return back;
    }

    public boolean isEnabled(Component arg0) {
        boolean back = true;
        for (IBehavior behavior: behaviors_) {
            back = back && behavior.isEnabled(arg0);
        }
        return back;
    }

    public boolean isTemporary() {
        boolean back = true;
        for (IBehavior behavior: behaviors_) {
            back = back && behavior.isTemporary();
        }
        return back;
    }

    public void renderHead(IHeaderResponse arg0) {
        for (IBehavior behavior: behaviors_) {
            if ( behavior instanceof IHeaderContributor) {
                ((IHeaderContributor)behavior).renderHead(arg0);
            }
        }
    }

}
