# DynamicalSystem
Iterating a R2->R2 Function to get a Attractor/Fractal

CC-NC-BY


This will output a sequence of png files that can be put together to a video
To get started create a java project with the three classes included.

The main class is DynamicalSystem.
There one can change various things:
-f : The method is the function to be iterated. One can use complex operations on z, or real operations on z.x and z.y
-func & tion: The string to be printed on the top left with the current parameter value of "d" in between
-colorsymmetry: what kind of slice of the rgb cube is used in the color map. Further changes can be made directly in the method
  -colormap: change the sign the definition of x,y,w & swap them out in the color definition to turn/mirror the color cube
- zoom, loc: zoom factor and center of image
- colorwidth: onto what size of a square is the color slice mapped. Outside of it the color is radially constant
- name0 : The name of the png files to be printed
-main : in the for loop with index b one can specify what to change from frame to frame: zoom, location, the parameter d, the iteration number...

The ComplexNumber class gives various methods on the complex numbers, mostly self explaining I hope

The Writing class is used to print a String onto the picture in my handwriting from the bmp files included. I only wrote the signs I need.
It can also write powers, for this the exponent must be in curvet brackets: "a^{b}" 
In the method write one can specify the String to be written, the positioning of the upper left corner and the width of a letter.

Feel free to ask if something is unclear, e.g. on my Discord: https://discord.gg/7ABsUP6dG
