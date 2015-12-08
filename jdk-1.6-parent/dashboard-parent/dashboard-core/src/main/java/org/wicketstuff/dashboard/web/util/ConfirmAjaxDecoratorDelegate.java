/*
 * Copyright 2012 Decebal Suiu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.dashboard.web.util;

import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxPreprocessingCallDecorator;

/**
 * @author Decebal Suiu
 */
public class ConfirmAjaxDecoratorDelegate extends AjaxPreprocessingCallDecorator {
	
    private static final long serialVersionUID = 1L;
    
    private String message;

    public ConfirmAjaxDecoratorDelegate(IAjaxCallDecorator delegate, String message) {
        super(delegate);
        
        this.message = message;
    }

    @Override
    public CharSequence preDecorateScript(CharSequence script) {
        return new StringBuilder("if(!confirm('").append(message).append("')) { return false; };").append(script);
    }

}
