package com.wfuertes.rest;

import com.wfuertes.books.Book;
import com.wfuertes.books.BookApi;
import com.wfuertes.books.BookPrice;
import com.wfuertes.books.PageQuery;
import com.wfuertes.books.PageSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("books")
public class BookResource {

    private static final Logger LOG = LoggerFactory.getLogger(BookResource.class);

    private final BookApi bookApi;

    @Inject
    public BookResource(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Response listBooks(@BeanParam PageQuery pageQuery) {
        final PageSummary<Book> pageSummary = bookApi.findAll(pageQuery);
        return Response.ok(pageSummary).build();
    }

    @GET
    @Path("/csv")
    @Produces("application/csv")
    public Response downloadCSV() {
        final String booksCSV = new String(bookApi.downloadCSV().toByteArray());
        return Response.ok(booksCSV)
                       .header("Content-Disposition", "attachment; filename=\"books.csv\"")
                       .build();
    }

    @POST
    @Path("/prices")
    public Response processPrices(@BeanParam PageQuery pageQuery) {
        bookApi.processPrices(pageQuery)
               .thenAcceptAsync(unused -> LOG.info("Book prices processed successfully"));

        return Response.accepted().build();
    }

    @GET
    @Path("/prices")
    @Produces(APPLICATION_JSON)
    public Response listPrices(@BeanParam PageQuery pageQuery) {
        final PageSummary<BookPrice> pageSummary = bookApi.findPrices(pageQuery);
        return Response.ok(pageSummary).build();
    }
}
