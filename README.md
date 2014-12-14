cplus-libparser
========

Library metadata extraction (information about classes, methods, variables) of source code in C / C ++.
This library uses the Eclipse parser (standalone), and extracts the most relevant information (primarily used to autocomplete).
The main advantage is that it is entirely in **Java** and no requires nothing native (like JNI) .
The library makes the scanner **asynchronous** mode and **multi-thread**, and can make the data parser *in memory* (from strings)

This library was created with the main objective to be used in the **Arduino IDE autocomplete system**.

I believe this is a good example of using the CDT Parser outside of Eclipse, because there is almost no information or example of using.

The code has been optimized to use minimal Eclipse dependencies.

*Thank you to everyone who has reported bugs and suggested fixes.*