package ressources;

import entities.Logement;
import metiers.LogementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Path("logements")
public class LogementRessources {
    public static LogementBusiness logementMetier = new LogementBusiness();

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addLogement(Logement l) {
     if(logementMetier.addLogement(l))
         return  Response.status(Status.CREATED).
                 entity(logementMetier.getLogements()).build();
     return  Response.status(Status.NOT_FOUND).build();
    }
    @GET
    @Produces (MediaType.APPLICATION_JSON)
    @Path("getall")
    public Response getAllLogement ()
    {
        if (logementMetier.getLogements().isEmpty()){
            return  Response.status(Status.NOT_FOUND).
                    entity("La liste est vide").build();
        }
        return  Response.status(200).
                entity(logementMetier.getLogements()).build();
    }

    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public Response getLogements(@QueryParam("delegation") String delegation) {
        List<Logement> liste=new ArrayList<Logement>();
        if(delegation != null) {
            liste = logementMetier.getLogementsByDeleguation(delegation);
            return  Response.status(Status.OK).entity(liste).build();
        }   return  Response.status(Status.NOT_FOUND).build();

    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Path("{ref}")
    public Response updateLogement(Logement updatedLogement, @PathParam("ref") int reference) {


        if (logementMetier.updateLogement(reference,updatedLogement)) {
            return Response.status(Status.OK).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{ref}")
    public Response deleteLogement(@PathParam("ref") int reference) {
        if (logementMetier.deleteLogement(reference)) {
            return Response.status(Status.OK).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

}
