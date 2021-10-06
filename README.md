
POST /register?username&password - create user

POST /register?username&password with an 'admin' header - create admin

GET /user/{id} - get user by id

POST /location?address - call Nominatim API to search by address(to get Location objects with nested Address objects and store them in h2 db)

GET /address - get all the stored addresses

GET /address/coordinate?lat&lon - get the address by the coordinate