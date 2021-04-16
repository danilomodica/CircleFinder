import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		/* Default Variables/Arguments */
		final String path = System.getProperty("user.dir") + "\\result";
	    int threshold = 150;
	    int minRadius = 10;
	    int maxRadius = 100;
	    int numberOfCircles = 1;
		
        /* Checks argument, files and system folder */
	    if (args.length < 1) {
	    	System.out.println("You must pass, as argument, at least the image file's path !");
	    	System.err.println("Program finished");
	    	System.exit(1);
	    }
	    /* Checking arguments: [Image Path] [Number of circles to draw] [Sobel Threshold] [Minimum radius of circles] [Maximum radius of circles] */
         try {
        	if (args.length > 1) {
        	    if (args.length > 2) {
        	 	    if (args.length > 3) {
        	 	    	if (args.length > 4) {
        	 	    		maxRadius = Integer.parseInt(args[4]);
        			    }
        			    minRadius = Integer.parseInt(args[3]);
                    }
                    threshold = Integer.parseInt(args[2]);
                }
                numberOfCircles = Integer.parseInt(args[1]);
        	}
		} catch (NumberFormatException e) {
			System.out.println("All optional arguments must be integers");
			System.err.println("Program finished");
	    	System.exit(1);
		}
       
        if (maxRadius - minRadius <= 0) {
    	    System.out.println("Invalid values for minimum and maximum radius!");
    	    System.err.println("Program finished");
    	    System.exit(1);
        }
	    
        File originalFile = new File(args[0]);
        File originalFolder = new File(path);
        if (!(originalFolder.exists() && originalFolder.isDirectory())) {
            try {
                originalFolder.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        /* -------------------------------------------------------------------------------------------------------------*/

        /* Reads the original image */
        BufferedImage originalImage = null;
        try {
        	originalImage = ImageIO.read(originalFile);
        } catch (IOException e) {
			System.err.println("Image doesn't exist");
			System.err.println("Program finished");
			System.exit(1);
		}
        /* Utils.displayImage(args[0], "Original Image"); */
        
        /* -------------------------------------------------------------------------------------------------------------*/
        
        /* Converts image into gray-scale and outputs the gray-scaled image */
        BufferedImage gray = Utils.toGrayScale(originalImage);
        Utils.writeImage(gray, path + "\\gray.png");
        /* Utils.displayImage(path + "\\gray.png", "Gray-Scale Image"); */
        
        /* -------------------------------------------------------------------------------------------------------------*/
        
        /* Applies Gaussian Blur and outputs the blurred image */
        BufferedImage blurred = Blur.blur(gray);
        blurred = Blur.blur(blurred);
        blurred = Blur.blur(blurred);
        Utils.writeImage(blurred, path + "\\blur.png");
        /* Utils.displayImage(path + "\\blur.png", "Blur Image"); */
        
        /* -------------------------------------------------------------------------------------------------------------*/
        
        /* Applies Sobel edge detection and outputs the Sobel image */
        Sobel sob = new Sobel(blurred);
        sob.combineSobel();
        double[][] sobel = sob.getSobel();
        BufferedImage imgSobel = sob.getSobelImage();
        Utils.writeImage(imgSobel, path + "\\sobel.png");
        /* Utils.displayImage(path + "\\sobel.png", "Sobel Image"); */
        
        /* -------------------------------------------------------------------------------------------------------------*/
        
        /* Outputs an image with only pixels above the threshold */
        BufferedImage imgAboveThreshold = sob.getAboveThresholdImage(threshold);
        Utils.writeImage(imgAboveThreshold, path + "\\aboveThreshold.png");
        /* Utils.displayImage(path + "\\aboveThreshold.png", "Above Threshold Image"); */
        
        /* -------------------------------------------------------------------------------------------------------------*/
        
        /* Performs the circle detection and outputs the original image with the detected circles in red */
        BufferedImage imgCircles = new BufferedImage(gray.getWidth(), gray.getHeight(), BufferedImage.TYPE_INT_ARGB);
        CircleFinder circleFinder = new CircleFinder(threshold, numberOfCircles, minRadius, maxRadius);
        List<Circle> detectedCircles = circleFinder.circleDetection(imgSobel, sobel);
        Collections.sort(detectedCircles, Collections.reverseOrder());
        
        imgCircles.getGraphics().drawImage(originalImage, 0, 0, null);
        Graphics2D g = imgCircles.createGraphics();
        g.setColor(Color.RED);
        for (int i = 0; i < numberOfCircles; i++) {
            Circle circleToDraw = detectedCircles.get(i);
            double x =  circleToDraw.getX() - circleToDraw.getR() * Math.cos(0 * Math.PI / 180);
            double y =  circleToDraw.getY() - circleToDraw.getR() * Math.sin(90 * Math.PI / 180);
            g.drawOval((int) x, (int) y, 2 * circleToDraw.getR(), 2 * circleToDraw.getR());
        }
    	
        Utils.writeImage(imgCircles, path + "\\detectedCircles.png");
        Utils.displayImage(path + "\\detectedCircles.png", "Detected Circles");
    }
}
