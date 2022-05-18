## Summary

### Price Surge Design

```mermaid

graph LR
A([View Item]) --> B[Item View Count increased by 1]
B --> C[Event thrown to check for price surging]
B --> D[View Item response returned]
C --> E{Requires Surging?}
E -- "Yes(View count is >= 10 and <br/> the last updated datetime <br/> is withing 1 hour from now )" --> F[Surge Price]
E -- No --> G([Do Nothing])
F --> H[Update the new price in DB <br> and reset the view count to 0]
H --> I([End])
G --> I
```

### API Endpoints Design
Following are the layers of the application:
1. Controllers (Endpoint)
2. Service (Business Logic)
3. DAO (Repositories)

A detailed swagger doc is available at http://localhost:8088/swagger-ui.html.

### Choice of Data Format
The data format being used for communication between client and server is Json. The reason for opting this data format is that it is easily parsable and is the most commonly used data format for REST APIs.
#### Request Example:
```shell
curl --location --request GET 'http://localhost:8088/item/view/3nkIuKDjnu' \
--header 'Cookie: JSESSIONID=A4B0117A27E2F2D8012BAA2AD8FD63EC'
```
#### Response Example:
```json
{
    "item": {
        "uid": "3nkIuKDjnu",
        "created": null,
        "lastUpdated": null,
        "itemName": "Orchid",
        "itemDescription": "Orchid",
        "itemPrice": 7.0
    }
}
```

### Authentication Mechanism
The authentication mechanism chosen is token based. A user who needs to access (to buy an item in this case), will need an access token which will be provided to them.
The token(alphabetic) is generated via apache's RandomStringUtils of 20 characters.
```mermaid
graph TD
A([Start]) --> B[Spring Filter intercepts the URL for authentication]
B --> C{Requires authentication?}
C -- "Yes (/item/buy)" --> D[Authentication Provider checks for <br/> the token sent in the header of the request]
C -- No --> E[Forwards the request to the corresponding controller]
D --> F{Token found in DB?}
F -- Yes --> E
F -- No --> H[Returns Unauthenticated Error]
E --> I([End])
H --> I([End])
```
### Testing Structure
Integration Tests:
1. listItems
2. viewItem
3. buyItem
4. unsuccessfulAuthorization

Unit Tests:
1. surgePriceTest