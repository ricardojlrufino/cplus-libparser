cplus-libparser
========

Library for metadata extraction (information about classes, methods, variables) of source code in C/C++.  
This library uses the Eclipse parser (standalone), and extracts the most relevant information (primarily used to autocomplete).

The main advantage is that it is entirely in **Java** and no requires native code (like JNI) .  
The library makes the scanner in **asynchronous** mode and **multi-thread**, and can make the data parser **in-memory** (from strings)

This library was created with the main objective to be used in the **Arduino IDE autocomplete system**.


Build
====

Use maven to build, the generated jar include the necessary dependencies  
> mvn package

I believe this is a good example of using the CDT Parser outside of Eclipse, because there is almost no information or example of using.

The code has been optimized to use minimal Eclipse dependencies.

TIP: You can use the project [eclipse-cdt-standalone-astparser](https://github.com/ricardojlrufino/eclipse-cdt-standalone-astparser) to see a TreeView code of the source code structure, serving for debugging purposes

*Thank you to everyone who has reported bugs and suggested fixes.*

Diagram
====

![Diagram](https://github.com/ricardojlrufino/cplus-libparser/raw/master/docs/diagram.jpg "Diagram")
