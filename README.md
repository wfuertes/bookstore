# bookstore [![Build Status](https://travis-ci.com/wfuertes/bookstore.svg?branch=main)](https://travis-ci.com/wfuertes/bookstore)

### Pre-requirements:
* Java 11
* Maven 3

Sample project using Jersey 2.29, guice 4.2.2.
* Migration Flyway;
* SQL Jooq;
* Local Database H2
* Production Database MySQL
* HikariCP

#### Running BookStore

Just compile and run the main class: `com.wfuertes.BookStore.main`

#### BookRecourse

The pagination I implemented using PageIterator, with a supplier to the next page. See `com.wfuertes.books.Page` implementation. 

```
GET http://localhost:8088/books?page=1&limit=10

Response: 
{
    "items": [
        {
            "id": "c0581b70b41e8d6320eee3590f81775a",
            "title": "Lesquerella S. Watson",
            "author": "Giuditta Mill",
            "isbn": "882677038-7",
            "rating": 1,
            "createdAt": "2020-11-03 21:10:15",
            "updatedAt": "2020-11-03 21:10:15"
        },
        {
            "id": "4414d576b154924ed6ff68898d7a3b81",
            "title": "Oonopsis multicaulis (Nutt.) Greene",
            "author": "Sharyl Fells",
            "isbn": "107475651-7",
            "rating": 2,
            "createdAt": "2020-11-03 21:10:15",
            "updatedAt": "2020-11-03 21:10:15"
        },
        {
            "id": "06376f07ff3679156544bc55a41e2923",
            "title": "Oncostema Raf.",
            "author": "Aloysia McArtan",
            "isbn": "915634830-4",
            "rating": 4,
            "createdAt": "2020-11-03 21:10:15",
            "updatedAt": "2020-11-03 21:10:15"
        },
        {
            "id": "a909092b9555c5ac907ba277e8b5f764",
            "title": "Cynoglossum glochidiatum Wall. ex Benth.",
            "author": "Lewiss Dmiterko",
            "isbn": "727010295-3",
            "rating": 2,
            "createdAt": "2020-11-03 21:10:15",
            "updatedAt": "2020-11-03 21:10:15"
        }
    ],
    "nextPage": "/books?page=2&limit=4"
}
```

```
GET http://localhost:8088/books/csv

Response: e.g.
id,title,author,isbn,rating,createdAt,updatedAt
c0581b70b41e8d6320eee3590f81775a,Lesquerella S. Watson,Giuditta Mill,882677038-7,1,2020-11-03T21:10:15.528622Z,2020-11-03T21:10:15.528622Z
4414d576b154924ed6ff68898d7a3b81,Oonopsis multicaulis (Nutt.) Greene,Sharyl Fells,107475651-7,2,2020-11-03T21:10:15.528622Z,2020-11-03T21:10:15.528622Z
06376f07ff3679156544bc55a41e2923,Oncostema Raf.,Aloysia McArtan,915634830-4,4,2020-11-03T21:10:15.528622Z,2020-11-03T21:10:15.528622Z
a909092b9555c5ac907ba277e8b5f764,Cynoglossum glochidiatum Wall. ex Benth.,Lewiss Dmiterko,727010295-3,2,2020-11-03T21:10:15.528622Z,2020-11-03T21:10:15.528622Z
071b0cbb17c83a469ea61676bed93ce2,Ribes lobbii A. Gray,Dunc Ramsbotham,329467015-X,4,2020-11-03T21:10:15.528622Z,2020-11-03T21:10:15.528622Z
d57aac42167f1c037967260b30f0f72d,Gelsemium Juss.,Mildrid Clemensen,814993969-5,5,2020-11-03T21:10:15.528622Z,2020-11-03T21:10:15.528622Z
a75894bce1e4a12ee2372df8a0c2155e,Panicum hirticaule J. Presl var. verrucosum F. Zuloaga & O. Morrone,Fanni Worsnop,060397812-6,7,2020-11-03T21:10:15.528622Z,2020-11-03T21:10:15.528622Z
baf8d37b237c0664059ba30c1146a1dc,Chamaesyce dioica (Kunth) Millsp.,Elliott Cripps,294558986-9,5,2020-11-03T21:10:15.528622Z,2020-11-03T21:10:15.528622Z
...
```
