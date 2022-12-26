
package ImagePackage;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.*;

public class FiltersClass {
    public BufferedImage blurImage(BufferedImage BI)
    {
        //The Kernel class defines a matrix that describes how a specified pixel and its surrounding pixels 
        // affect the value computed for the pixel's position in the output image of a filtering operation.
        //3x3 matrix as our kernel. For each pixel, the filter multiplies the current pixel value 
        //and the other 8 surrounding pixels by the kernel corresponding value.
        Kernel k = new Kernel(3, 3, new float[]{1f/(3*3),1f/(3*3),1f/(3*3),
                                                1f/(3*3),1f/(3*3),1f/(3*3),
                                                1f/(3*3),1f/(3*3),1f/(3*3)});
        
        //A convolution operation allows you to combine the colors of a source
        // pixel and its neighbors to determine the color of a destination pixel.
        //creates a ConvolveOp that combines equal amounts of each source pixel and its neighbors. 
        //This technique results in a blurring effect.
         ConvolveOp op = new ConvolveOp(k);
         return op.filter(BI, null);
    }

    public Image lightenImage(BufferedImage BI)
    {
        //This class performs a pixel-by-pixel rescaling of the data in the source image by 
        //multiplying the sample values for each pixel by a scale factor and then adding an offset.
        //The scaled sample values are clipped to the minimum/maximum representable in 
        //the destination image.

        RescaleOp op = new RescaleOp(2f, 0, null);
        return op.filter(BI, null);
    }
    public Image darkenImage(BufferedImage BI)
    {
        RescaleOp op = new RescaleOp(.5f, 0, null);
        return op.filter(BI, null);
    }
    public Image invertImage(BufferedImage BI)
    {
        //Traverses through the whole image (each pixel being an element in an array)
        for (int x = 0; x < BI.getWidth(); x++) {
            for (int y = 0; y < BI.getHeight(); y++) {
                int rgba = BI.getRGB(x, y);
                Color col = new Color(rgba, true);
                //Color component in the range 0-255 being true.
                col = new Color(255 - col.getRed(),
                                255 - col.getGreen(),
                                255 - col.getBlue());
                BI.setRGB(x, y, col.getRGB());
                //reverses the corresponding colors (by subtracting from 255 and sets it for inverse)
            }
        }
        return BI;
    }
}
