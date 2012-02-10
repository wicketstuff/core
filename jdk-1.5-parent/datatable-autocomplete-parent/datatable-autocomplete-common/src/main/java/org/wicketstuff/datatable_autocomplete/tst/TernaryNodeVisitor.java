/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.wicketstuff.datatable_autocomplete.tst;

import org.apache.wicket.IClusterable;

/**
 * @author Michael O'Cleirigh (michael.ocleirigh@rivulet.ca)
 * 
 */
public interface TernaryNodeVisitor<C> extends IClusterable
{

	public void preVisit();

	public void visit(TernaryNode<C> node);

	public void postVisit();
}
