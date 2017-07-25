In this repository we collect examples of the application of the MetaBorg and Spoofax Core APIs.

# Using Spoofax languages in Java

The following projects illustrate how to invoke various language processors from Java.

* `org.metaborg.example.expressions`: Spoofax project for a simple expression language. The remaining projects access the implementation of this language to perform standard language processing tasks such as parsing, static analysis, and transformation.
* `org.metaborg.example.api.parsing`: Java project demonstrating how to use the Spoofax Core API to parse an expression.
* `org.metaborg.example.api.analysis`: Java project demonstrating how to use the Spoofax Core API to parse an expression and perform static analysis.
* `org.metaborg.example.api.transform`: Java project demonstrating how to use the Spoofax Core API to parse an expression, perform static analysis, and apply a transformation to the expression.

# Spoofax languages with external references

In some languages, programs can refer to external elements.
For example, SQL queries can refer to tables and columns by name.
The following projects illustrate how to setup reference resolution for external references in Java and how to integrate this resolution into name and type analysis in Spoofax.

* `org.metaborg.example.externalref.context`: Java project specifying an abstract context for external references. Defines interfaces for typed declarations and contexts to lookup these declarations. Also provides a Java enumeration of types and a Java implementation of a strategy to lookup the type of external declarations.
* `org.metaborg.example.externalref.expressions`: Spoofax project for a simple expression language with external references. Uses the strategy from the context project to determine the type of external references.
* `org.metaborg.example.externalref.app`: Java project demonstrating how to bind the abstract context to an actual implementation.
