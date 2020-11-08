-- Default Schema: bookstore must be created
CREATE TABLE book_prices (
    book_id          VARCHAR(40)  NOT NULL,
    price            INT          NOT NULL,
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (book_id)
) ENGINE=InnoDB charset=UTF8;

CREATE INDEX idx_book_prices_created_at ON book_prices (created_at DESC);