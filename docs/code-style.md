---
title: Code formatting and test naming
---

## Code formatting

In April 2011 the Wicket Stuff committers decided to unify the code formatting of the different Wicket Stuff projects (see the [discussion](http://mail-archives.apache.org/mod_mbox/wicket-dev/201104.mbox/%3CBANLkTinmpWCDfY5a2MMgVBQX%2BAcFtxrung%40mail.gmail.com%3E) and the [vote](http://mail-archives.apache.org/mod_mbox/wicket-dev/201104.mbox/%3CBANLkTimLkcsveBnJCwixYZ0R8bz-wtJmyQ@mail.gmail.com%3E) if you are interested in the details). This applies to existing and future projects.

The main rule is: we follow the [Apache Wicket style of formatting](https://cwiki.apache.org/confluence/display/WICKET/Wicket+Source+Code+Style), plus clean up, in Java files. In XML, HTML, JavaScript and CSS files a tab is used for indentation.

These rules are not enforced or checked by an automated process. The main reason is that contributors use different kinds of development environments, so the repository is kept IDE independent: no IDE specific files in locations where they would be picked up automatically. Developers can provide and use IDE specific settings in the [`config`](https://github.com/wicketstuff/core/tree/wicket-11.x/config) directory of the repository.

To keep the code consistent the whole Java code is reformatted from time to time. These formatting changes are committed separately, without any functional change.

## Naming convention for test cases

By default test cases for Wicket Stuff modules must have a class name ending with **Test**. If you need a different name, specify a different name pattern for the `maven-surefire-plugin` in your pom.
