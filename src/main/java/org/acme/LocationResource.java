package org.acme;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.List;

@Path("/location")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationResource {

    @GET
    public Uni<List<Location>> getAll() {
        return Location.listAll();
    }

    @POST
    @WithTransaction
    public Uni<Location> post() {
        Location l = new Location();

        var gf = new GeometryFactory();
        l.geom = gf.createPoint(new Coordinate(-10, 10));

        return l.persist();
    }
}
