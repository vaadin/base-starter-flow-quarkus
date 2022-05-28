# Project Base for Vaadin Flow and Quarkus with native-image support

This is a special branch for Vaadin Base starter for Quarkus, that supports native image generation.
This branch is not constantly maintained, but shows you what to do if you want to do native compilation of your Vaadin+Quarkus application.
Check the change history for esential steps.

To build a native image, you need a working environment.
Refer to Quarkus documentation for that.

The vaadin-quarkus-extension, don't currently provide reflection hints needed for native compilation.
Thus, those are now added to this example project.
In your own project, those hints most likely needs to be adapted and extended.

Note, that Push is hard to set up for native compilation, because of Atmosphere framework.
In this example we exclude flow-push dependency, which makes things much easier.

The native image compilation happens as with Quarkus example projects, using `native` profile.
But be sure to also enable production mode for Vaadin, it will not work in native mode, and you certainly want to develop in JVM anyways, even if you do native compilation for deployement.
A command to compile the native binary:

```
./mvnw clean install -Pnative,production
```

The native binary is generated to target directory with `-runner` postfix. 
To start it from the command line from the top level directory:

```
./target/base-starter-flow-quarkus-1.0-SNAPSHOT-runner
```
