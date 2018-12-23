QuickView Library
---------------------

The library comes with RepeatingViews which support partial updates in ajax use case. There are other components and behaviors available in library which can be used with these repeating views. 

The library is meant to be used with Apache Wicket.


QuickView is a wicket component(RepeatingView) ,it lets you add or remove the rows without the need to re-render the whole repeater again in ajax use cases.
Quickview can be also be used with PagingNavigator or AjaxPagingNavigator .


QuickGridView extends QuickView ,it renders items in rows and columns in table/grid format. it also lets you add or remove the rows without the need to 
re-render the whole repeater again in ajax use cases. it works well with AjaxItemsNavigator or PagingNavigator.

**Usage in maven project**

     <!--quickviewlib dependency-->
        <dependency>
            <groupId>org.wicketstuff</groupId>
            <artifactId>quickview</artifactId>
            <version>theversion</version>
        </dependency>



read more about QuickView and QuickGridView at https://github.com/vineetsemwal/quickview/wiki


Please use simple examples which comes with the package .

thank you !

Please note the idea/inspiration to write quickview came from Igor Vaynberg's article 
http://wicketinaction.com/2008/10/repainting-only-newly-created-repeater-items-via-ajax/


QuickView is based on Apache Wicket.


Copyright 2012 Vineet Semwal

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
