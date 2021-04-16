import java.awt.Color;
import java.awt.image.BufferedImage;

public class Sobel {
	
	private BufferedImage gray;
	private int[][] sobelX;
    private int[][] sobelY;
    private double[][] sobel;
    
    public Sobel(BufferedImage grey) {
    	this.gray = grey;
    	this.sobelX = new int[this.gray.getWidth()][this.gray.getHeight()];
    	this.sobelY = new int[this.gray.getWidth()][this.gray.getHeight()];
    	this.sobel = new double[grey.getWidth()][grey.getHeight()];
    }
    
    /*
     * Computes the algorithm to combine the horizontal Sobel and vertical Sobel
     */
    public void combineSobel() {
    	calcSobelX();
    	calcSobelY();
        for(int i = 0; i < gray.getWidth(); i++){
            for(int j = 0; j < gray.getHeight(); j++){
                this.sobel[i][j] = Math.round(Math.sqrt(Math.pow((double)this.sobelX[i][j], 2) + Math.pow((double)this.sobelY[i][j], 2)));
            }
        }
    }
    
    /*
     * Generates the horizontal Sobel edge, defines the matrix using a 3x3 matrix and calculates every pixel Sobel
     */
    private void calcSobelX() {
        int[][] kernel = new int[3][3];
        kernel[0][0] = -1;
        kernel[1][0] = -2;
        kernel[2][0] = -1;
        kernel[0][1] = 0;
        kernel[1][1] = 0;
        kernel[2][1] = 0;
        kernel[0][2] = 1;
        kernel[1][2] = 2;
        kernel[2][2] = 1;

        for(int i = 0; i < this.gray.getWidth(); i++) {
            for(int j = 0; j < this.gray.getHeight(); j++) {
                this.sobelX[i][j] = getSobelResult(i, j, kernel);
            }
        }
    }

    /*
     * Generates the vertical Sobel edge, defines the matrix using a 3x3 matrix and calculates every pixel Sobel
     */
    private void calcSobelY() {
        int[][] kernel = new int[3][3];
        kernel[0][0] = -1;
        kernel[0][1] = -2;
        kernel[0][2] = -1;
        kernel[1][0] = 0;
        kernel[1][1] = 0;
        kernel[1][2] = 0;
        kernel[2][0] = 1;
        kernel[2][1] = 2;
        kernel[2][2] = 1;

        for(int i = 0; i < this.gray.getWidth(); i++) {
            for(int j = 0; j < this.gray.getHeight(); j++) {
                this.sobelY[i][j] = getSobelResult(i, j, kernel);
            }
        }
    }

    /* 
     * Checks all edges cases to ensure no errors and calculates the Sobel result for any pixel/kernel
     */
    private int getSobelResult(int x, int y, int[][] kernel) {
        int res = 0;
        
        if (x == 0 && y == 0) {
            for (int i = 0; i <= 1; i++) 
                for (int j = 0; j <= 1; j++) 
                    res += new Color(gray.getRGB(x+i, y+j)).getRed() * kernel[j+1][i+1];
        } 
        else if (x == 0 && y == gray.getHeight() - 1) {
            for (int i = 0; i <= 1; i++) 
                for (int j = -1; j <= 0; j++) 
                    res += new Color(gray.getRGB(x+i, y+j)).getRed() * kernel[j+1][i+1];
        } 
        else if (x == 0 && y != 0) {
            for (int i = 0; i <= 1; i++) 
                for (int j = -1; j <= 1; j++) 
                    res += new Color(gray.getRGB(x+i, y+j)).getRed() * kernel[j+1][i+1];
        } 
        else if (x == gray.getWidth() - 1 && y == 0) {
            for (int i = -1; i <= 0; i++)
                for (int j = 0; j <= 1; j++)
                    res += new Color(gray.getRGB(x+i, y+j)).getRed() * kernel[j+1][i+1];
        } 
        else if (x != 0 && y == 0) {
            for (int i = -1; i <= 1; i++)
                for (int j = 0; j <= 1; j++)
                    res += new Color(gray.getRGB(x+i, y+j)).getRed() * kernel[j+1][i+1];
        } 
        else if (x == gray.getWidth() - 1 && y == gray.getHeight() - 1){
            for (int i = -1; i <= 0; i++)
                for (int j = -1; j <= 0; j++)
                    res += new Color(gray.getRGB(x+i, y+j)).getRed() * kernel[j+1][i+1];
        } 
        else if (x == gray.getWidth() - 1 && y != gray.getHeight() - 1){
            for (int i = -1; i <= 0; i++)
                for (int j = -1; j <= 1; j++)
                    res += new Color(gray.getRGB(x+i, y+j)).getRed() * kernel[j+1][i+1];
        } 
        else if (x != gray.getWidth() - 1 && y == gray.getHeight() - 1) {
            for (int i = -1; i <= 1; i++)
                for (int j = -1; j <= 0; j++)
                    res += new Color(gray.getRGB(x+i, y+j)).getRed() * kernel[j+1][i+1];
        } 
        else {
            for (int i = -1; i <= 1; i++) 
                for (int j = -1; j <= 1; j++) 
                    res += new Color(gray.getRGB(x+i, y+j)).getRed() * kernel[j+1][i+1];
        }

        return res;
    }
    
    /*
     * Generates Sobel image
     */
    public BufferedImage getSobelImage() {
    	BufferedImage imgSobel = new BufferedImage(gray.getWidth(), gray.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        
    	// gets the max value of sobel array
    	double maxSobel = 0;
        for(int i = 0; i < gray.getWidth(); i++) {
            for(int j = 0; j < gray.getHeight(); j++) {
                if(sobel[i][j] > maxSobel) {
                    maxSobel = sobel[i][j];
                }
            }
        }
        for(int i = 0; i < gray.getWidth(); i++)
            for(int j = 0; j < gray.getHeight(); j++) {
                // maps pixels to a gray-scale value between 0 and 255 from a value between 0 and the max value of sobel array
                int rgb = new Color((int) Utils.map(sobel[i][j], 0, maxSobel, 0, 255),
                        			(int) Utils.map(sobel[i][j], 0, maxSobel, 0, 255),
                        			(int) Utils.map(sobel[i][j], 0, maxSobel, 0, 255), 255).getRGB();
                imgSobel.setRGB(i,j,rgb);
            }
        
        imgSobel = Utils.editBrightness(20.0f, imgSobel);
        return imgSobel;
    }
    
    /*
     * Generates the image with only pixels above the threshold
     */
    public BufferedImage getAboveThresholdImage(int threshold) {
    	BufferedImage imgAboveThreshold = new BufferedImage(gray.getWidth(), gray.getHeight(), BufferedImage.TYPE_INT_ARGB);
    	
    	for (int i = 0; i < gray.getWidth(); i++) 
        	for (int j = 0; j < gray.getHeight(); j++) {
        		Color c1 = new Color(255, 0, 0);
        		if (sobel[i][j] > threshold)
        			imgAboveThreshold.setRGB(i, j, c1.getRGB());
        	}
    	
    	return imgAboveThreshold;
    }
    
    public double[][] getSobel() {
    	return this.sobel;
    }
    
    public int[][] getSobelX() {
    	return this.sobelX;
    }
    
    public int[][] getSobelY() {
    	return this.sobelY;
    }
}
