# Upgrading wicket-kendo-ui libraries

1/ Download kendo core libraries
- http://www.telerik.com/download/kendo-ui-core

2/ Run the following script (change the 2 first lines)
- https://gist.github.com/sebfz1/65a9cefe556ec6918315

3/ Update trial version in:
- wicket-jquery-ui-samples/src/main/java/com/googlecode/wicket/jquery/ui/samples/pages/kendo/datatable/AbstractDataTablePage.html
- wicket-jquery-ui-samples/src/main/java/com/googlecode/wicket/jquery/ui/samples/pages/kendo/editor/AbstractEditorPage.html
- wicket-jquery-ui-samples/src/main/java/com/googlecode/wicket/jquery/ui/samples/pages/kendo/scheduler/AbstractSchedulerPage.html

4/ Verify trial dependencies are ok:
- http://docs.telerik.com/kendo-ui/install/custom#grid
- http://docs.telerik.com/kendo-ui/install/custom#editor
- http://docs.telerik.com/kendo-ui/install/custom#scheduler

