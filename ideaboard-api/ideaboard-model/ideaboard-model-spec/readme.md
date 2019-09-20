#IdeaBoard Model Specification
---

The idea is to be able to declare the model.

##Todos
- Create a JSON schema for describing just models
    
    Since there is not such schema (yet) we use the 
    OpenAPI V3 schema for declaring objects.
    
- Schema has to be compatible with OpenAPI (or easily convertable)
- Schema has to be compatible with AsyncAPI (or easily convertable)
- Schema could be 'ripped' from OpenAPI or AsyncAPI
- Check for breaking changes

    It should be possible to verify schema against an older 
    version for breaking changes. This should be possible at
    based on the schema by checking required fields,
    new fields, removed fields. 