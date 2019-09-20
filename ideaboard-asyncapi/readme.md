# IdeaBoard AsyncAPI
---

The async/messaging API for IdeaBoard. The API is declared
using [AsyncAPI](https://www.asyncapi.com/)

## Todos
- Add validation of AsyncAPI based Apis
- Validation should merge model and API into a single 
declaration
    
    Since not validation and merging exists yet we copied
    the model over to the async api declaration file.
    
- Maybe create a generic java based JSON schema validation 
tool

    Currently available tools can not cope well with
    $ref referring to external files
    
## References
- [LeadPony Justify](https://github.com/leadpony/justify)
- [Apache Johnzon](https://johnzon.apache.org/) schema module