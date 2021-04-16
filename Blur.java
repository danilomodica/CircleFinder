import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class Blur {
	
	private static int[] kernel = {1, 2, 1, 2, 4, 2, 1, 2, 1};
    /* kernel = {1 2 1
    			 2 4 2
    			 1 2 1
    			}; 
    */
    private static int kernelWidth = 3;

	private Blur() {}
	
	/*
	 * Applies Gaussian Blur to the given image
	 */
	public static BufferedImage blur(BufferedImage image) {
	    if (kernel.length % kernelWidth != 0) {
	        throw new IllegalArgumentException("Filter contains an incomplete row");
	    }

	    final int width = image.getWidth();
	    final int height = image.getHeight();
	    final int sum = IntStream.of(kernel).sum();

	    int[] input = image.getRGB(0, 0, width, height, null, 0, width);

	    int[] out = new int[input.length];

	    final int pxIdxOffset = width - kernelWidth; 				// pixel index offset
	    final int ctrOffsetX = kernelWidth / 2;		 				// center offset X
	    final int ctrOffsetY = kernel.length / kernelWidth / 2;		// center offset Y

	    // applies filter
	    for (int h = height - kernel.length / kernelWidth + 1, w = width - kernelWidth + 1, y = 0; y < h; y++) {
	        for (int x = 0; x < w; x++) {
	            int r = 0;
	            int g = 0;
	            int b = 0;
	            for (int kernelIndex = 0, pixelIndex = y * width + x; kernelIndex < kernel.length; pixelIndex += pxIdxOffset) {
	                for (int fx = 0; fx < kernelWidth; fx++, pixelIndex++, kernelIndex++) {
	                    int col = input[pixelIndex];
	                    int base = kernel[kernelIndex];

	                    // computes each RGB channel
	                    r += ((col >>> 16) & 0xFF) * base;
	                    g += ((col >>> 8) & 0xFF) * base;
	                    b += (col & 0xFF) * base;
	                }
	            }
	            
	            r /= sum;
	            g /= sum;
	            b /= sum;
	            
	            // combine RGB channels with full opacity (alpha channel)
	            out[x + ctrOffsetX + (y + ctrOffsetY) * width] = (r << 16) | (g << 8) | b | 0xFF000000;
	        }
	    }

	    BufferedImage blurredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    blurredImage.setRGB(0, 0, width, height, out, 0, width);
	    return blurredImage;
	}
}
