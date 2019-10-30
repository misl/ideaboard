# IdeaBoard

IdeaBoard is a simple application intended to experiment with 
[API first](https://swagger.io/resources/articles/adopting-an-api-first-approach/) 
development in combination with [Quarkus](https://quarkus.io/).

## Project structure
- ideaboard-api

    This module describes the IdeaBoard API in an API first approach. 
    For API first a number of tools are available. This project focuses
    on both [OpenAPI](https://www.openapis.org/) (for Rest) and
    [AsyncAPI](https://www.asyncapi.com/) (for messaging).
    
- ideaboard-messaging

    Implementations of the messaging interfaces as described/declared with
    [AsyncAPI](https://www.asyncapi.com/).

- ideaboard-web

    Implementations of the Rest interfaces as described/declared with
    [OpenAPI](https://www.openapis.org/).
