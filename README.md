# ShoppingCart
AMAD Project 6

**An Android application where users can add remove products from their cart and checkout their card using a card.**

### Use cases -
- ECommerce App

### Libraries used - 
- Material Design UI
- ExpressJS
- JSONWebToken
- BCrypt
- MongoDB
- NodeJS
- JetPack
- BrainTree

### Features -
- Authentication (Login, Register, Update and View Profile).
- Registration includes User's Fullname, Email, Password, Address.
- You can update your account information easily.
- Keep your account information secured.
- Buy products easily stored on server.
- View your transaction history with great details.
- Adjust your cart however you want to and checkout.

### Database design -
#### _Using MongoDB_
- 4 Collections - (**Auth, Profiles, Items, Transactions**)

- Auth contains the user authentication information.
> Data fields - email (string), pass (string)
- Profiles contains all of the User information.
> Data fields - fullname (string), email (string), address (string)
- Items contains the product information.
> Data fields - id (string), name (string), photo (string), price (number), region (string), discount (number)
- Transactions contains all of the transaction history information.
> Data fields - trans(array of order details linked to user id)

## The API Schema:

Base URL - > https://mysterious-beach-05426.herokuapp.com/

* **POST** - auth/login - Authenticates a User and returns JWT Token. `Parameters - email, pass. (urlencoded)`
* **POST** - auth/signup - Registers a User profile and return JWT Token. `Parameters - email, pass, fullname, address. (urlencoded)`
* **GET** - profile/view - View your User profile. `Headers - Your valid unexpired JWT Token.`
* **POST** - profile/update - Updates your User profile. `Headers - Your valid unexpired JWT Token. Parameters - email, fullname, address. (urlencoded)`
* **POST** - product/get - Get Product details by its ID. `Headers - Your valid unexpired JWT Token. Parameters - id. (urlencoded)`
* **GET** - product/getAll - Get all the Products on server. `Headers - Your valid unexpired JWT Token.`
* **POST** - product/checkout - Checkout Products using BrainTree payments. `Headers - Your valid unexpired JWT Token. Parameters - amount, timestamp, products array, paymentMethodNonce. (urlencoded)`
* **GET** - product/history - Get User transaction history. `Headers - Your valid unexpired JWT Token.`
