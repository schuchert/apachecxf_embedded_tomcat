# apachecxf_embedded_tomcat
Minimal example based on a Maven archetype and adding in embedded Tomcat.

The original source for this is a maven archetype:
* http://cxf.apache.org/docs/jax-rs-maven-plugins.html

In addition the embedded Tomcat 7 code comes from:
* https://devcenter.heroku.com/articles/create-a-java-web-application-using-embedded-tomcat

There's not really anthing new here, mostly a remix and then a trivial service that does ... wait for it ... calculation.

For this to work, you need Java 8 and Maven 3 installed. You can do this manually, or you can use [this VM configuration](https://github.com/schuchert/vagrant_java)

When you have an appropriate envrionment:
* Clone the repo
* In switch to the cloned directory
* mvn test

If you use [the VM configuration](https://github.com/schuchert/vagrant_java), try opening the pom.xml file in IntelliJ.
