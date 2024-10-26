package dev.matheuscruz.rest;

import dev.matheuscruz.model.Database;
import dev.matheuscruz.model.DrawingModel;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api/drawings/{drawingCode}/")
public class DrawingResource {

    @GET
    @Path("/result")
    public Response result(@PathParam("drawingCode") String drawingCode) {
        DrawingModel drawingModel = Database.DRAWINGS.get(drawingCode);
        return Response.ok(drawingModel).build();
    }

    @GET
    public Response get(@PathParam("drawingCode") String drawingCode) {
        DrawingModel drawingModel = Database.DRAWINGS.get(drawingCode);
        return Response.ok(drawingModel).build();
    }
}
