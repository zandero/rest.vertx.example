package com.zandero.rest.example.rest;

import com.zandero.rest.annotation.ResponseWriter;
import com.zandero.rest.annotation.RouteOrder;
import com.zandero.rest.example.rest.writers.FileResourceWriter;
import com.zandero.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Handles all static pages ... simply by loading resources ...
 */
public class PagesRest {

    private final static Logger log = LoggerFactory.getLogger(PagesRest.class);

    @RouteOrder(1000) // last
    @GET
    @Path("/{path:.*}") // take any path
    @ResponseWriter(FileResourceWriter.class)
    public String serveDocFile(@PathParam("path") String path) {

        if (StringUtils.isNullOrEmptyTrimmed(path)) {
            path = "index.html"; // go to index.html
        }

        // check if path is "correct" ... otherwise revert to index.html
        log.info("Serving resource file: 'html/" + path + "'");
        return "html/" + path;
    }
}
