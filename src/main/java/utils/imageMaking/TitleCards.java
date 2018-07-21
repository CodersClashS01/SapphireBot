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

import static utils.imageMaking.PixelUtils.toPixels;

class TitleCards {

    public static File img_createProfileImage(String title, int size, File source, String type, File destination) throws IOException {

        BufferedImage image;
        image = ImageIO.read(source);

        // determine image type and handle correct transparency
        int imageType = "png".equalsIgnoreCase(type) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage watermarked = new BufferedImage(image.getWidth(), image.getHeight(), imageType);

        // initializes necessary graphic properties
        Graphics2D w = (Graphics2D) watermarked.getGraphics();

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        w.setRenderingHints(rh);

        w.drawImage(image, 0, 0, null);

        /* Set Alpha
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
        w.setComposite(alphaChannel);
        */

        Font roboto_bold = new Font("Roboto", Font.BOLD, toPixels(size));

        w.setColor(Color.white);
        w.setFont(roboto_bold);

        //FontMetrics fontMetrics = w.getFontMetrics();
        //Rectangle2D rect = fontMetrics.getStringBounds(name, w);

        // add text overlay to the image

        Rectangle r = new Rectangle();

        r.height = image.getHeight();
        r.width = image.getWidth();

        String[] xy = PixelUtils.drawCenteredString(w, title, r, roboto_bold);

        w.drawString(title, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));

        ImageIO.write(watermarked, type, destination);

        w.dispose();
        return destination;
    }


}
