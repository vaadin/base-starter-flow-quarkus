# TOMBNG -- Theatre of the mind bot, next generation

> You don't need to be crazy to be my friend ... ok, maybe you do. It's just more fun that way.
>
> -- @blue_eyed_darkness on TikTok

![Maven](https://github.com/Paladins-Inn/tombng/workflows/CI/badge.svg)

## Abstract
This is the next version of a Theatre of the Mind system supporting role playing games played via Discord.

## License
The license for the software is LGPL 3.0 or newer. Parts of the software may be licensed under other licences like MIT
or Apache 2.0 - the files are marked appropriately.


## Architecture

tl;dr (ok, only the bullshit bingo words):
- Immutable Objects (where frameworks allow)
- Relying heavily on generated code
- 100 % test coverage of human generated code
- Every line of code not written is bug free!

Code test coverage for human generated code should be 100%, machinge generated code is considered bugfree until proven
wrong. Every line that needs not be written is a bug free line without need to test it. So aim for not writing code.


## Running the Application for development

Import the project to the IDE of your choosing as a Maven project.

Run application using `mvnw` (Windows), or `./mvnw` (Mac & Linux).

Open [http://localhost:8080/](http://localhost:8080/) in browser.

If you want to run your app locally in production mode, call `mvnw package -Pproduction` (Windows), or `./mvnw package -Pproduction` (Mac & Linux)
and then
```
java -jar target/quarkus-app/quarkus-run.jar
```


## Distribution
Currently there is no distribution of this software.


## Note from the author
This software is meant do be perfected not finished.

If someone is interested in getting it faster, we may team up. I'm open for that. But be warned: I want to do it
_right_. So no short cuts to get faster. And be prepared for some basic discussions about the architecture or software
design :-).

---
Bensheim, 2021-05-24