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
package org.wicketstuff.datatable_autocomplete.comparator;

import java.util.Comparator;

import org.apache.wicket.IClusterable;
import org.slf4j.LoggerFactory;


/**
 * @author mocleiri
 * 
 * Used for sortable columns in a DTATable.  clicking on the link will toggle between sort ascending and sort descending.
 *
 */
public abstract class DTAComparator<E> implements Comparator<E>, IClusterable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4782414530696776582L;

	private static final org.slf4j.Logger	log	= LoggerFactory.getLogger(DTAComparator.class);

	private boolean ascending = true;
	/**
	 * 
	 */
	public DTAComparator(boolean ascending) {
		this.ascending  = ascending;

	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public final int compare(E o1, E o2) {

		if (ascending) {
			return compareAscending (o1, o2);
		}
		else { 
			// switch the results since we want the opposite
			
			int test =  compareAscending(o1, o2);
			
			if (test >= 1)
				return -1;
			else if (test <= -1)
				return +1;
			else
				return 0;
		}
	}

	
	/**
	 * @param o1
	 * @param o2
	 * @return
	 */
	protected abstract int compareAscending(E o1, E o2);

	
}
