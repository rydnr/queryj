QueryJ is a generation tool aimed to simplify and speed-up development when
following a MDA-like approach: the source of data is the database. Such model
is used to build a 'mirror' persistence layer in Java. Such approach makes the
synchronization backwards unnecessary: all missing details about the model are
provided at database level, as metadata within table or column comments.

To be specific, QueryJ is composed of three separate components:

* A generation tool based on database dictionaries.
* A set of templates to build a Java persistence layer.
* A (legacy) framework to build syntax-safe complex queries.

Originally bound to Ant, it now supports command-line, as well as Maven 1.x,
2.x and 3.x.

It can retrieve all information offered by the database dictionary, or a
explicit subset. Also, supports ISA relationships, constant tables (to be
exported as Java constants or enums afterwards), boolean columns, sequences...

The currently supported template bundle generates a Java persistence layer
based on immutable value objects, and a DAO layer to perform operations on the
database.
