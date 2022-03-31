Create Project
mvn org.ops4j:maven-pax-plugin:create-project -DgroupId=org.sonatype.mcookbook -DartifactId=osgi-project -Dversion=1.0-SNAPSHOT

Create bundle
mvn pax:create-bundle -Dpackage=NAME -Dname=NAME-bundle -Dversion="1.0-SNAPSHOT"