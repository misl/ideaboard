# IdeaBoard Model implementation (Jackson)
---

This module contains POJOs according to the model 
specification. Preferably we want this to be generated
from the model. However there is no such thing yet for
only model files.

As a workaround we could use OpenAPI to only generate 
the model classes. Unfortunately I prefer my model to 
be immutable with a fluent builder pattern. The OpenAPI
generator has no support for that.

Of course the OpenAPI generator can be extended with 
custom generators. But that still does not give us
something that only works on model description files. 
For instance what if we only have messaging APIs 
according to AsyncAPI. How would we create the model then?

Also we preferred to use JSON-B for implementing the 
model, since it is a standard. Unfortunately current
JSON-B does **not** [support the builder pattern](https://github.com/eclipse-ee4j/jsonb-api/issues/191) yet.

## Todos
- We would first need a Model json schema.
- Java generator for for model description files.
- Maven plugin for the generator
- Generate the POJO model.
- Maybe we can 'rip' the generator from OpenAPI.
    
## References
- [Jackson](https://github.com/FasterXML/jackson)