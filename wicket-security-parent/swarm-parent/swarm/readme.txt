Setting up an eclipse project:
-check out project from svn
-open a commandline in the project directory
-run mvn eclipse:eclipse
-refresh eclipse project
-go to the project properties
-select java build path
-select Libraries
-remove all JRE System Libraries, except [J2SE-1.4]. There is a bug in the maven eclipse plugin that generates additional JRE libraries.

Required dependencies:
- Wicket
- Wasp
- any slf logging implementation (e.g. slf4j-reload4j with log4j)