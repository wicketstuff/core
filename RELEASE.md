# wicket-jquery-ui
**jQuery UI integration in Wicket 1.5.x &amp; Wicket 6.x**

## Release process
This document explains the steps to release a new version of this project.

### Prerequisites

Create a new Maven settings.xml (e.g. `~/.m2/settings.wjui.xml`) file with the following content:

````xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

    <interactiveMode>false</interactiveMode>

    <servers>
      <server>
        <id>sonatype-nexus-snapshots</id>
        <username>[THE USERNAME]</username>
        <password>[THE PASSWORD]</password>
      </server>
      <server>
        <id>sonatype-nexus-staging</id>
        <username>[THE USERNAME]</username>
        <password>[THE PASSWORD]</password>
      </server>
    </servers>
  
</settings>

````

Make sure to provide username and password of a user that is allowed to manage this project at [Sonatype OSS](https://oss.sonatype.org)

### Deploy a Snapshot version
````
$ mvn -s ~/.m2/settings.wjui.xml -Dpgp.passphrase=[YOUR GPG PASSPHRASE] clean deploy
````

After finishing the deployment you can check that the new snapshot version is at [Sonatype OSS Snapshots](https://oss.sonatype.org/content/repositories/snapshots/com/googlecode/wicket-jquery-ui/)

### Deploy a release version
* update `<version>6.x.y</version>` in **README.md** and **homepage.html** (*wicket-jquery-ui-samples*)

* mvn release:clean
* mvn release:prepare
* mvn release:perform

TODO: improve this section
