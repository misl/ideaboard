# ideaboard-web

This module contains various implementations of the IdeaBoard API as
described/declared in [ideaboard-api-openapi](https://github.com/misl/ideaboard/tree/master/ideaboard-api/ideaboard-api-openapi).

## Module structure

- ideaboard-web-resteasy

    This implementation makes use of RestEasy to implement the Rest based 
    IdeaBoard API. However, it does not make any use of the OpenApi 
    specification other than implementing what is specified. So in essence
    the implementation itself is nearly the same as a code first approach.
    
    Since there is no other connection between specification and 
    implementation than the human mind, this is bound to lead to 
    mistakes. If not closely watched this eventually will result in 
    an implementation that diverges from the specification. 

- ideaboard-web-vertx

    With the help of [Vert.X Web API Contract](https://vertx.io/docs/vertx-web-api-contract/java/)
    this implementation is able to actually use the API specification. Like
    this the specification is used to perform:
    - Automatic request validation
    - Automatic mount of security validation handlers