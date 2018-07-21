package utils.imageMaking;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static utils.imageMaking.PixelUtils.img_resize;
import static utils.imageMaking.PixelUtils.toPixels;

public class ProfileCard {

    public static File img_createProfileImage(String name, String id, String state, String rep, Color color, String bio, String warns, int ep, int lvl, BufferedImage pb, File on_off, String type, File source, File destination) throws IOException {

        BufferedImage image;
        image = ImageIO.read(source);
        BufferedImage on_off_Switch;
        on_off_Switch = ImageIO.read(on_off);

        BufferedImage PB_Img = img_resize(pb, 90, 90);

        int imageType = "png".equalsIgnoreCase(type) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage watermarked = new BufferedImage(image.getWidth(), image.getHeight(), imageType);

        Graphics2D w = (Graphics2D) watermarked.getGraphics();

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        w.setRenderingHints(rh);

        w.drawImage(image, 0, 0, null);

        w.setColor(color);
        w.setFont(new Font("Roboto", Font.BOLD, toPixels(21)));

        if (name.length() <= 11) {
            w.drawString(name, (float) 122.50, (float) 53.32); // Printing the Name if smaller than 11 Chars
        } else {

            String smallName = name.substring(0, 11) + "...";

            w.drawString(smallName, (float) 122.50, (float) 53.32); // Printing the Name if longer than 11 Chars
        }

        w.setColor(new Color(109, 207, 246 , 155));


        w.setFont(new Font("Roboto", Font.PLAIN, toPixels(15)));
        w.drawString(state, (float) 123.83, (float) 72); // Priting the Online/Offline/DnD/... Status

        w.setColor(new Color(177, 177, 177 , 155));
        w.setFont(new Font("Roboto", Font.PLAIN, toPixels(12)));
        w.drawString(warns, (float) 241, (float) 234); // Priting the warns

        w.setColor(new Color(177, 177, 177 , 155));
        w.setFont(new Font("Roboto", Font.PLAIN, toPixels(12)));
        w.drawString(rep, (float) 67.36, (float) 234); // Priting the REP

        w.setColor(new Color(177, 177, 177 , 255));
        w.setFont(new Font("Roboto", Font.PLAIN, toPixels(15)));
        w.drawString(bio, (float) 67.36, (float) 181.21); // Priting the Bio

        w.setColor(new Color(177, 177, 177 , 155));
        w.setFont(new Font("Roboto", Font.PLAIN, toPixels(12)));
        w.drawString(ep + " EP", (float) 67.36, (float) 138.12); // Priting the EP


        w.setColor(new Color(177, 177, 177 , 155));
        w.setFont(new Font("Roboto", Font.PLAIN, toPixels(12)));
        w.drawString("LEVEL " + lvl, (float) 206, (float) 138.12); // Priting the Level

        w.setColor(Color.gray);
        w.setFont(new Font("Roboto", Font.PLAIN, toPixels(40)));
        w.setColor(Color.white);
        w.setFont(new Font("Roboto", Font.PLAIN, toPixels(30)));

        w.drawImage(on_off_Switch, 16, 12, null);

        w.drawImage(PB_Img, 16, 12, null);

            ImageIO.write(watermarked, type, destination);

        w.dispose();
        return destination;
    }

}
