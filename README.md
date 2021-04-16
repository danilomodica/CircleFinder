# CircleFinder
A Java Program for detecting circles within a given image by using Circle Hough Transform (**CHT**).

It displays on screen the given image with detected circles highlighted in red.<br>
It also stores to the project */result* directory (automatically created): 
* the gray-scale image (*gray.png*)
* the blurred image (*blur.png*)
* the sobel image (*sobel.png*)
* the image with pixels above threshold (*aboveThreshold.png*)
* the image with detected circles (*detectedCircles.png*).

Based on:
* https://en.wikipedia.org/wiki/Circle_Hough_Transform
* https://github.com/lukemccl/CircleDetection

## How-To-Use

1) Open a terminal in the project directory <br>

2) Compile Java source code by running: <br>
``` javac -cp . *.java ``` <br>

3) Run the Main program by running: <br>
``` java -cp . Main imagePath [circles] [threshold] [minRadius] [maxRadius] ```

    Arguments:
    * imagePath (**required**): path of the image file.
    * circles (*optional*): number of circles to detect. **Default**: 1.
    * threshold (*optional*): threshold for Sobel Operator. **Default**: 150.
    * minRadius (*optional*): minimum radius of circles (in px). **Default**: 10.
    * maxRadius (*optional*): maximum radius of circles (in px). **Default**: 100.
