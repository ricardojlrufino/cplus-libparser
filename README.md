cplus-libparser
========

Library metadata extraction (information about classes, methods, variables) of source code in C / C ++.
This library uses the parser Eclipse, and extracts the most relevant information (primarily used to autocomplete).
The main advantage is that it is entirely in **Java** and no requires nothing native (like JNI) .
The library makes the scanner **asynchronous** mode and **multi-thread**, and can make the data parser *in memory* (from strings)

This library was created with the main objective to be used in the **Arduino IDE autocomplete system**.

*Thank you to everyone who has reported bugs and suggested fixes.*