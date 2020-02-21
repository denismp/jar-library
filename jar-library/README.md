# Selenium Test Library

This project contains the java library to use in common for selenium testing.

## Local build

On you local machine run 'mvn clean compile install' to create the target/selenium-test-lib-0.0.1.jar.  This can be done in eclipse, if you wish to setup 
a run configuration.  This must be done to create the jar file.

## Install to the .m2 directory for pom dependencies

After the local build, run 'mvn install:install-file -Dfile=target/selenium-test-lib-0.0.1.jar -DgroupId=com.selenium.test -Dversion=0.0.1 -DartifactId=selenium-test-lib -Dpackaging=jar'
This will need to be run in the higher environments to ensure that the selenium testing projects can use the dependency in their builds.
