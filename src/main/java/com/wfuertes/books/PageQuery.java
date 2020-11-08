package com.wfuertes.books;

import lombok.Value;
import lombok.experimental.Accessors;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

@Value
@Accessors(fluent = true)
public class PageQuery {

    int page;
    int limit;

    public PageQuery(@QueryParam("page") @DefaultValue("1") int page,
                     @QueryParam("limit") @DefaultValue("10") int limit) {
        this.page = page;
        this.limit = limit;
    }

    public int offset() {
        return limit * (page - 1);
    }
}
