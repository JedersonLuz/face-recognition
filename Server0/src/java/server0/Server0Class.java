/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server0;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author jederson_luz
 */
@Path("server0")
public class Server0Class {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Server0Class
     */
    public Server0Class() {
    }

    /**
     * Retrieves representation of an instance of server0.Server0Class
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cpu")
    public String getCPUJson() {
        //TODO return proper representation object
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return String.valueOf(operatingSystemMXBean.getSystemLoadAverage());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("facerec/{id}")
    public String getFaceRecJson() {
        //TODO return proper representation object
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return String.valueOf(operatingSystemMXBean.getSystemLoadAverage());
    }
    
    /**
     * PUT method for updating or creating an instance of Server0Class
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
