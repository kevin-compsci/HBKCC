Kevin Nguyen
HBK Coding Challenge
2/21/2018
kevin_cs@utexas.edu


This Java program is my solution to the HBK Coding Challenge. The function called illuminate takes in a string (char array in other languages), and it'll put that data into a 2D array (grid). We then use the 2D map out the transformation on the grid where the size of the grid is changable since those are just constants (please note that the grid must be of the expected size). Key characters for the laser and mirrors are stored in a map and lists respectively because we want to keep track of all the key characters and their list of coordinates that they appear in. There are also helper functions such as initializer and handler function to handle case scenarios of reflection and laser activity; they are essential. This program was not created with efficiency and speed in mind, but to achieve a workable solution.

Alternatively, the code can still work without the main function. As long as the illuminate function has the correct formatted string.


-HOW TO RUN CODE (LINUX)- 

1.) Extract files to some location
2.) Open up terminal and navigate to the directory of the extracted files
3.) Type into the command line "javac *.java" to use the javac compiler and compile all files
4.) Next type in "java HBKCC_mySolution GridSmall#.txt" where # is some letter A-E.
5.) Enjoy the output on screen!


NOTE: To run the code on other systems or to use it in your own code, then it's at your own discretion.