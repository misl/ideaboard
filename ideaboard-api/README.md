# ideaboard-api

This module describes the IdeaBoard API in an API first approach. 
For API first a number of tools are available. This project focuses
on both [OpenAPI](https://www.openapis.org/) (for Rest) and
[AsyncAPI](https://www.asyncapi.com/) (for messaging).

Both these approaches not only describe the interface itself, but also
the models of the data to interact with the interfaces. Since both work
on the same domain (IdeaBoard) there it bound to be overlap in the 
declared models.

To prevent redundancy in declaring models we separated the domain model
into a separate module. With the help of some maven plugins we were able
to stitch the separation between interface methods and model back into a
single file. 

**Note:** The model should be used to automatically generate domain 
classes. Unfortunately, due to lack of the right tooling, domain classes
are currently still coded.{: .note}