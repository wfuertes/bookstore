package com.wfuertes.books;

import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class PageQuery {

    int page;
    int limit;

    public int offset() {
        return limit * (page - 1);
    }
}
