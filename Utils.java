import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Utils {
	
	private Utils() {}
	
	/*
	 * Converts given file image into a gray-scale image
	 */
    public static BufferedImage toGrayScale(BufferedImage img) throws Exception{
    	BufferedImage gray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        gray.getGraphics().drawImage(img, 0 , 0, null);
        return gray;
    }

	/*
	 * Changes the brightness of an image by the given factor
	 */
    public static BufferedImage editBrightness(float brightenFactor, BufferedImage image){
        RescaleOp rOp = new RescaleOp(brightenFactor, 0, null);
        image = rOp.filter(image, image);
        return image;
    }
    
    /*
     * Maps the value between start1 and end1 to a value between start2 and end2
     */
    public static double map(double value, double start1, double end1, double start2, double end2) {
        double ratio = (end2 - start2) / (end1 - start1);
        return ratio * (value - start1) + start2;
    }
    
    /*
     * Writes on disk the given image
     */
    public static void writeImage(BufferedImage img, String path) throws IOException {
    	 File out = new File(path);
         ImageIO.write(img, "png", out);
    }
    
    /*
     * Displays an image given its path
     */
    public static void displayImage(String imagePath, String title) throws IOException {
    	File file = new File(imagePath);
    	BufferedImage image = ImageIO.read(file);
    	
    	JFrame f = new JFrame(title);
    	ImageIcon pic = new ImageIcon(imagePath);
    	JLabel label;
    	JPanel panel = new JPanel();
    	panel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    	panel.setBackground(Color.WHITE);
    	label = new JLabel ("", pic, SwingConstants.CENTER);
    	panel.add(label);
    	f.setVisible(true);
    	f.setResizable(false);
    	f.add(panel);
    	f.pack();
    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
