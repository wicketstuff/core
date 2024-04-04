/*
 * $Id$
 * $Revision$
 * $Date$
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

/**
 * <b>Wicket-PhoneBook</b> - An example of
 * <a href="http://www.springframework.org/">Spring</a> &amp;
 * <a href="http://www.hibernate.org/">Hibernate</a> usage in a
 * <a href="http://wicketframework.org/">Wicket</a> web-app.
 * </p>
 * <ul>
 *   <li>The database is an in-memory <a href="http://www.hsqldb.org/">HSQLDB</a> DB and the schema gets generated automatically everytime the app starts up.</li>
 *   <li>Hooking up to a different database should be a simple matter of editing <tt>src/conf/application.properties</tt>. </li>
 *   <li>All database-related code is inside a dao object, so Wicket never touches anything database related.</li>
 *   <li>All Hibernate session management and transaction management is handled by Spring.</li>
 * </ul>
 */
package wicket.contrib.phonebook;

