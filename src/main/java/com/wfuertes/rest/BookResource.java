package com.wfuertes.rest;

import com.wfuertes.books.Book;
import com.wfuertes.books.BookApi;
import com.wfuertes.books.Page;
import com.wfuertes.books.PageQuery;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("books")
public class BookResource {

    private final BookApi bookApi;

    @Inject
    public BookResource(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Response listBooks(@QueryParam("page") @DefaultValue("1") int page,
                              @QueryParam("limit") @DefaultValue("10") int limit) {

        final PageQuery pageQuery = new PageQuery(page, limit);
        final Page<Book> bookPage = bookApi.findAll(pageQuery);

        return Response.ok(bookPage).build();
    }

    @GET
    @Produces("application/csv")
    public Response downloadCSV() {
        final String booksCSV = new String(bookApi.downloadCSV().toByteArray());
        return Response.ok(booksCSV)
                       .header("Content-Disposition", "attachment; filename=\"books.csv\"")
                       .build();
    }
}
