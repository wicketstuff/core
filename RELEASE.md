# wicket-jquery-ui
**jQuery UI integration in Wicket 1.5.x &amp; Wicket 6.x**

## Release process
This document explains the steps to release a new version of this project.  
More info: http://central.sonatype.org/pages/apache-maven.html

### Prerequisites

Open Maven settings.xml (i.e. `~/.m2/settings.xml`) file and add the needed `server` and `profile` with the following content:

```xml
<settings>
  <servers>
    <server>
      <id>ossrh</id>
      <username>[THE USERNAME]</username>
      <password>[THE PASSWORD]</password>
    </server>
  </servers>
  <profiles>
    <profile>
      <id>ossrh</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <properties>
        <gpg.executable>gpg</gpg.executable> <!-- or gpg2 -->
        <gpg.passphrase>[MY GPG PASSPHRASE]</gpg.passphrase>
      </properties>
    </profile>
  </profiles>
</settings>
```

Make sure to provide username and password of a user that is allowed to manage this project at [Sonatype OSS](https://oss.sonatype.org).

The GPG passphrase is used to sign the artifacts before uploading them to Sonatype Maven repository.

### Deploy a Snapshot version
```
$ mvn clean deploy -P snapshot
```

After finishing the deployment you can check that the new snapshot version is at [Sonatype OSS Snapshots](https://oss.sonatype.org/content/repositories/snapshots/com/googlecode/wicket-jquery-ui/)

### Deploy a Release version
Releases should be signed with a PGP key.
http://central.sonatype.org/pages/working-with-pgp-signatures.html

* update `<version>6.x.y</version>` in **README.md** and **wicket-jquery-ui-samples/src/main/java/com/googlecode/wicket/jquery/ui/samples/HomePage.html**
* `mvn release:update-versions -DautoVersionSubmodules=true` (if your development version does not match the incoming release version)
* `git commit` and `git push` 
* `mvn release:clean`
* `mvn release:prepare`
* `mvn release:perform`
* Go to [Sonatype OSS](https://oss.sonatype.org) and login with the same credentials as in settings.xml's server configuration
* Navigate to `Staging Repositories`, find the deployed bundle in the grid, select it and release it (button *Release* in the toolbar)
