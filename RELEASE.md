# wicket-jquery-ui
**jQuery UI integration in Wicket 1.5.x &amp; Wicket 6.x**

## Release process
This document explains the steps to release a new version of this project.  
More info: http://central.sonatype.org/pages/apache-maven.html

### Prerequisites

Open Maven settings.xml (i.e. `~/.m2/settings.xml`) file and add the needed `servers` and  with the following content:

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
        <gpg.executable>gpg2</gpg.executable>
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
* `mvn versions:set -DnewVersion=x.y.z-SNAPSHOT` (where x.y.z is the new version, this will prevent to enter the new version for each artifact)
* commit and push 
* `mvn release:clean`
* `mvn release:prepare`
* `mvn release:perform`
* Go to [Sonatype OSS](https://oss.sonatype.org) and login with the same credentials as in settings.xml's server configuration
* Navigate to `Staging Repositories`, find the deployed bundle in the grid, select it and close it (button *Close* in the toolbar)
* Wait few seconds until Nexus closes it and then select the bundle and release it (button *Release* in the toolbar)