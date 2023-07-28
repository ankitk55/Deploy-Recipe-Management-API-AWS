
# Recipe Management API



### Feature
```
- User
    1. User can create new Account.
    2. User can sign In existing Account.
    3. Signed In User can Add Recipe.
    4. Signed In User can get All Recipes added by him.
    5. Signed In User can update  Recipe by recipeId.
    6. Signed In User can delete Recipe by recipeId.
    7. Signed In User can create Post of Recipe.
    8. Signed In User can get All Posts of Recipes created by him.
    9. Signed In User can also get All Posts of Recipes of All Users.
    10.Signed In User can update  post by postId.
    11.Signed In User can delete Post by postId.
    12.Signed In User can comment on a other user's Post and can also comment on his post.
    13.Signed In User can get All comments by postId.
    14.Signed In User can delete his own comment from a Post by commentId.
    15.Signed In User can logout from Account.
    16.Signed In User can delete  his Account.
    
```

### Technologies Used
```
- Programming Language: Java
- Web Framework: SpringBoot
- Database : The project utilizes the MySQL database for data storage.
- Hibernate (for ORM)
- Maven (for dependency management)
- To test endPoints used Swagger
- Used AWS server for deploy this project.

```
### Data flow in Project
```
 Controller
    1.UserController
  Service
    1. UserService
    2. CommentService
    3. AuthenticationService
    4. RecipeService
    5. PostService
  Repository
    1. IUserRepo
    2. ICommentRpo
    3. IAuthenticationTokenRepo
    4. IRecipeRepo
    5. IPostRepo
  Model
    1. User
    2. Post 
    3. AuthenticationToken
    4. Recipe
    5. Comment
   

```

### Validation Rules
- User Email should be a normal Email like - ("something@something.com")
- User Name must not be Blank.
- RecipeName must not be Blank.
- Recipe Ingredients must not be Blank.
- Recipe Instructions must not be Blank.
- Comment Content must not be Blank.


    
## EndPoints Http Response Code
| Http Response Code | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `401` | `Unauthorized` | `Invalid Email or Token no.` |
| `404` | `not found`    | `whatever you trying to find that not found`  |
| `400` | `Bad Request` | `Input misMatch`  |
| `200`   |`OK`   | `All OK` |

## Entity Relationships
- **Recipe** and **User** have a **Many-to-One mapping**. Many Recipes can be added by a single User, but each Recipe belongs to only one User.
- **Authentication Token** and **User** have a **One-to-One** mapping. Each token can have  associated with one User, and each User can be linked to only one Token at a time.
- **Post** and **User** have a **Many-to-One mapping**. Many Posts can be added by a single User, but each Post belongs to only one User.
- **Post** and **Recipe** have a **Many-to-One mapping**. Many Posts can be linked with a single Recipe, but each Recipe belongs to only one Post.
- **Comment** and **User** have a **Many-to-One mapping**. Many Comments can be created by a single User, but each Comment belongs to only one User.
- **Comment** and **Post** have a **Many-to-One mapping**. Many Comments can be created on a single Post, but each Comment belongs to only one Post.
###  Conclusion
This is an Recipe Management API project that consists of many entities:  User, Post,Comment,Recipe .
To perform any operation on this project the User must be registered .From this project a Registered User can do the CRUD operations with the entities like add Recipes,delete recipes, Update recipes, add post of Recipes, update Post , get Post, Delete Post and can comments on the posts.
In this project I used passwordEncryptor which save the password in database in hashCode.
This Project provide a email Authentication when a user sign in his Account , a Token received that user mail.This Token will be valid when till user is sign in his Account after sign out the token will be expired.
With the help of token the valid user can do CRUD operations .
The project utilizes different types of mappings between these entities to establish relationships and functionality within the system.
And unregistered User can't do antthing.
This project workes very effecient and fast.
And this is User friendly any user can use easily.

### Author
 👨‍💼 **Ankit Kumar**
 + Github : [Ankit kumar](https://github.com/ankitk55?tab=repositories)
 + Linkdin : [Ankit Kumar](https://www.linkedin.com/in/ankit-kumar-7300581b3/)
 
### 🤝 Contributing
Contributions, issues and feature requests are Welcome!\
Feel free to check [issues Page](https://github.com/issues) 

### Show Your Support 
 Give a ⭐ if this project help you!