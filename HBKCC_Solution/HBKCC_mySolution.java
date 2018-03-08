/*
Kevin Nguyen
HBK Coding Challenge
2/21/2018

This coding challenge is from HBK where you have a grid of characters and a laser. Lasers illuminates a path, but it can also be reflected.


How to run (linux):
	1.) open command line and cd into directory folder of where this code is
	2.) type in "javac *.java" to compile java files
	3.) type in "java HBKCC_mySolution"
*/

//imports
import java.io.*;
import java.util.*;

//class
class HBKCC_mySolution {
	//Global Declarations --REQUIRED--
	List<List<Integer>> posList;
	HashMap<Character, List<List<Integer>>> trackerList = new HashMap<Character, List<List<Integer>>>();
	final static int ROW_SIZE = 4, COL_SIZE = 5;

	//main driver for the program; reads an input file and calls illuminate function which returns a string
	public static void main(String[] args) {
		//local Declarations
		String contents = "";
		BufferedReader buffr = null; //initial buffer
		int x = 0, y = 0;
		char t;
		//Error check: args must not be null, must have file, and must be txt; otherwise fail
		try {
			if (args[0] != null && args.length == 1 && args[0].endsWith(".txt")) {
            	buffr = new BufferedReader(new FileReader(args[0])); //set buffer to read file from args
            	//loop through file contents and read them; build grid as char[][]
            	while(buffr.ready()) {
            		contents = contents + buffr.readLine(); //build string
            	}
			}

		}
		catch (IOException e) { //intercept any ioexception
			System.out.println("Error: something is horribly wrong with the file!");
		}
		catch (ArrayIndexOutOfBoundsException a) { //intercept any "out of bounds" exceptions
			System.out.println("Error: Not enough args!");
		}
        HBKCC_mySolution my = new HBKCC_mySolution();
		String result = my.illuminate(contents);
		System.out.println("Result: " + result);
	}



	//ALL FUNCTIONS BELOW THIS LINE IS REQUIRED
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//TODO: implement a illuminate function that returns an output with illuminated path given the grid data
	public String illuminate(String gridData) {
		//Local Declaratoins
		String result = "";
		char[][] grid = new char[ROW_SIZE][COL_SIZE]; //initial grid
		List<List<Integer>> value;
		Character key;
		//populate grid
		grid = initializeGrid(grid, gridData);
		//iterate over mapping of key characters
		for (Map.Entry<Character, List<List<Integer>>> entry : trackerList.entrySet()) {
		    key = entry.getKey();
		    value = entry.getValue();
		    if(key == '<') {
		    	grid = handleLeftLaser(grid, value);
		    }
		    else if(key == '>') {
		    	grid = handleRightLaser(grid, value);
		    }
		    else if(key == '^') {
		    	grid = handleUpLaser(grid, value);
		    }
		}

		//print out the grid by looping through rows and columns
		for(int row = 0; row < grid.length; row++) {
			for(int col = 0; col < grid[row].length; col++) {
				System.out.print(grid[row][col]);
				result += grid[row][col];
			}
			System.out.println("");
		}
		return result;
	}

	//handle leftarrow
	public char[][] handleLeftLaser(char[][] grid, List<List<Integer>> value) {
		//Local Declarations
		List<Integer> coords;
		int i = 0, row = 0;
		//loop through poslist and get coords, then update grid
		while(i < value.size()) {
			//obtain coord data
			coords = value.get(i);
			row = coords.get(0);
			//go through columns since left and right arrows don't change rows
			for(int col = coords.get(1)-1; col > -1 && col < grid[row].length; col--) {
				//laser doesn't destroy other lasers mirrors? Also handle reflected scenarios
				if(grid[row][col] == 'x') {
					grid[row][col] = ' ';
				}
				else if (grid[row][col] == '/') { //down scenario
					List<Integer> tempCoordList = new ArrayList<Integer>();
					List<List<Integer>> tempPosList = new ArrayList<List<Integer>>();
					tempCoordList.add(row); tempCoordList.add(col);
					tempPosList.add(tempCoordList);
					grid = handleDownLaser(grid, tempPosList);
					break;
				}
				else if (grid[row][col] == '\\') { //up scenario
					List<Integer> tempCoordList = new ArrayList<Integer>();
					List<List<Integer>> tempPosList = new ArrayList<List<Integer>>();
					tempCoordList.add(row); tempCoordList.add(col);
					tempPosList.add(tempCoordList);
					grid = handleUpLaser(grid, tempPosList);
					break;
				}
			}
			i++; //increment
		}
		return grid; //return grid
	}

	//handle rightarrow
	public char[][] handleRightLaser(char[][] grid, List<List<Integer>> value) {
		//Local Declarations
		List<Integer> coords;
		int i = 0, row = 0;
		//loop through poslist and get coords, then update grid
		while(i < value.size()) {
			//obtain coord data
			coords = value.get(i);
			row = coords.get(0);
			//go through columns since left and right arrows don't change rows
			for(int col = coords.get(1)+1; col > -1 && col < grid[row].length; col++) {
				//laser doesn't destroy other lasers mirrors?
				if(grid[row][col] == 'x') {
					grid[row][col] = ' ';
				}
				else if (grid[row][col] == '/') { //down scenario
					List<Integer> tempCoordList = new ArrayList<Integer>();
					List<List<Integer>> tempPosList = new ArrayList<List<Integer>>();
					tempCoordList.add(row); tempCoordList.add(col);
					tempPosList.add(tempCoordList);
					grid = handleUpLaser(grid, tempPosList);
					break;
				}
				else if (grid[row][col] == '\\') { //up scenario
					List<Integer> tempCoordList = new ArrayList<Integer>();
					List<List<Integer>> tempPosList = new ArrayList<List<Integer>>();
					tempCoordList.add(row); tempCoordList.add(col);
					tempPosList.add(tempCoordList);
					grid = handleDownLaser(grid, tempPosList);
					break;
				}
			}
			i++; //increment
		}
		return grid; //return grid
	}

	// //handle uparrow
	public char[][] handleUpLaser(char[][] grid, List<List<Integer>> value) {
		//Local Declarations
		List<Integer> coords;
		int i = 0, col = 0;
		//loop through poslist and get coords, then update grid
		while(i < value.size()) {
			//obtain coord data
			coords = value.get(i);
			col = coords.get(1);
			//go through rows since columns are not affected
			for(int row = coords.get(0)-1; row > -1 && row < grid.length; row--) {
				//laser doesn't destroy other lasers mirrors?
				if(grid[row][col] == 'x') {
					grid[row][col] = ' ';
				}
				else if (grid[row][col] == '/') { //right scenario
					List<Integer> tempCoordList = new ArrayList<Integer>();
					List<List<Integer>> tempPosList = new ArrayList<List<Integer>>();
					tempCoordList.add(row); tempCoordList.add(col);
					tempPosList.add(tempCoordList);
					grid = handleRightLaser(grid, tempPosList);
					break;
				}
				else if (grid[row][col] == '\\') { //left scenario
					List<Integer> tempCoordList = new ArrayList<Integer>();
					List<List<Integer>> tempPosList = new ArrayList<List<Integer>>();
					tempCoordList.add(row); tempCoordList.add(col);
					tempPosList.add(tempCoordList);
					grid = handleLeftLaser(grid, tempPosList);
					break;
				}				
			}
			i++; //increment
		}
		return grid; //return grid	
	}

	//handle down arrow
	public char[][] handleDownLaser(char[][] grid, List<List<Integer>> value) {
		//Local Declarations
		List<Integer> coords;
		int i = 0, col = 0;
		//loop through poslist and get coords, then update grid
		while(i < value.size()) {
			//obtain coord data
			coords = value.get(i);
			col = coords.get(1);
			//go through rows since columns are not affected
			for(int row = coords.get(0)+1; row > -1 && row < grid.length; row++) {
				//laser doesn't destroy other lasers mirrors?
				if(grid[row][col] == 'x') {
					grid[row][col] = ' ';
				}
				else if (grid[row][col] == '/') { //right scenario
					List<Integer> tempCoordList = new ArrayList<Integer>();
					List<List<Integer>> tempPosList = new ArrayList<List<Integer>>();
					tempCoordList.add(row); tempCoordList.add(col);
					tempPosList.add(tempCoordList);
					grid = handleLeftLaser(grid, tempPosList);
					break;
				}
				else if (grid[row][col] == '\\') { //left scenario
					List<Integer> tempCoordList = new ArrayList<Integer>();
					List<List<Integer>> tempPosList = new ArrayList<List<Integer>>();
					tempCoordList.add(row); tempCoordList.add(col);
					tempPosList.add(tempCoordList);
					grid = handleRightLaser(grid, tempPosList);
					break;
				}	
			}
			i++; //increment
		}
		return grid; //return grid	
	}

	//generate initial grid
	public char[][] initializeGrid(char[][] grid, String gridData) {
		// local declarations
		int x = 0, y = 0, t = 0;
		char c;
		//loop through string and insert characters into grid
		while(t < gridData.length()) {
			if(y > 4) { //reset column y count if greater than 4 (column size limit)
				y=0;
				x++;
			}
			c = gridData.charAt(t);
			grid[x][y] = c; //modify array with contents
			//identify laser/mirror, pos
			if(c == '<' || c == '>' || c == '^' || c == '/' || c == '\\') {
				//if already in map then add it to list; else insert it into map
				if(trackerList.get(c) != null) {
					posList = trackerList.get(c);
				}
				else {
					posList = new ArrayList<List<Integer>>();
				}
				//declare new list to hold coordinates
				List<Integer> coord = new ArrayList<Integer>();
				coord.add(x); coord.add(y); //add values to coord
				posList.add(coord); //add coord list to pos list
				trackerList.put(c, posList); //add character and poslist to map
			} 
			y++; //increment column
			t++; //increment character position
		}
		return grid;
	}
}
