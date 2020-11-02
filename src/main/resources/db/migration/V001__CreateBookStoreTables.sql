-- Default Schema: bookstore must be created
CREATE TABLE books (
    id          VARCHAR(40)  NOT NULL,
    title       VARCHAR(256) NOT NULL,
    author      VARCHAR(256) NOT NULL,
    isbn        VARCHAR(13)  NOT NULL UNIQUE,
    rating      INT          NOT NULL,
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB charset=UTF8;

CREATE INDEX idx_books_title ON books (title);
CREATE INDEX idx_books_isbn ON books (isbn);
CREATE INDEX idx_books_author ON books (author);
CREATE INDEX idx_books_created_at ON books (created_at DESC);