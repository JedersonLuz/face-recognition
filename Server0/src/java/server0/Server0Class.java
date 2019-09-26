/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server0;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Base64;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author jederson_luz
 */
@Path("server0")
public class Server0Class {

    private static int red, green, blue, newRGB;
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Server0Class
     */
    public Server0Class() {}

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
    @Path("upload/{file}")
    public String getMatrix(@PathParam("file") String file) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(file);

        //Convert byte[] to String
        String s = Base64.getEncoder().encodeToString(negativa(bytes));
        
        return s;
    }
    
    public static byte[] negativa(byte[] img) throws IOException {
        BufferedImage image = javax.imageio.ImageIO.read(new ByteArrayInputStream(img));
        
        int len = image.getWidth();
        
        for (int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {
                // System.out.println("x = " + x + ", y = " + y);
                // Get current pixel color.
                Color color = new Color(image.getRGB(y, x));

                // Turn red, green, blue into grayscale.
                red = (int) (color.getRed() * 0.299);
                green = (int) (color.getGreen() * 0.587);
                blue = (int) (color.getBlue() * 0.114);

                // Save grayscale color as a new Color.
                newRGB = red + green + blue;
                Color gray = new Color(newRGB, newRGB, newRGB);

                // Set new grayscale color.
                image.setRGB(y, x, gray.getRGB());
            }
        }
        
        for (int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {
                Color color = new Color(image.getRGB(y, x));
                red = (int) color.getRed();
                int neg = (255-1-red);
                Color black = new Color(neg, neg, neg);
                image.setRGB(y, x, black.getRGB());
            }
        }
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        javax.imageio.ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
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
