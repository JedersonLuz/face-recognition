import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.regex.Pattern;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Negativa {
    
    private static final long serialVersionUID = 1L;
    private static int red, green, blue, newRGB;
    
    public Negativa() {}
    
    public static byte[] negativa(byte[] img) throws IOException {
        BufferedImage originalImage = javax.imageio.ImageIO.read(new ByteArrayInputStream(img));
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

        ImageIO.write(image, "jpg", new File("img2.jpg"));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        javax.imageio.ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
    }

    public static void main(String[] args) {
        try {
            File fileImg = new File("/home/vitoria/Imagens/fotoo.jpg");
            BufferedImage image = ImageIO.read(fileImg);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(image, "jpg", baos);
            image = javax.imageio.ImageIO.read(new ByteArrayInputStream(negativa(baos.toByteArray())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
