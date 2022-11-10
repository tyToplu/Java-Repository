
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grid {
	public static Shape[][] createGrid(String path) {
		List<String> grids =ReadAndWrite.read(path);
		Pattern whiteSpaceSplitter = Pattern.compile(" ");
		Shape[][] grid = new Shape[10][10];
		for (int i = 0; i < grids.size(); i++) {
			String[] line = whiteSpaceSplitter.split(grids.get(i));
			for (int j = 0; j < line.length; j++) {
				grid[i][j] = Shape.setShapeType(line[j]);
			}
		}
		return grid;
	}
	public static void printer(Object[][] matrix) {
		for (int i = 0; i < matrix.length; i++) { //this equals to the row in our matrix.
	         for (int j = 0; j < matrix[i].length; j++) { //this equals to the column in each row.
	            System.out.print(matrix[i][j] + " ");
	         }
	         System.out.println(); //change line on console as row comes to end in the matrix.
	      }
	}
	public static Shape[][] transposeMatrix(Shape[][] matrix){
	    int m = matrix.length;
	    int n = matrix[0].length;
	    //In order to remove space transpose the matrix	    
	    Shape[][] transposedMatrix = new Shape[n][m];
	    
	    for(int x = 0; x < n; x++) {
	        for(int y = 0; y < m; y++) {
	            transposedMatrix[x][y] = matrix[y][x];
	        }
	    }

	    return transposedMatrix;
	}
	public static void removeWhiteSpacesInColumns(Shape[][] matrix) {
		Shape[] emptyRow = new Shape[matrix[0].length];
		for (int j = 0; j <matrix[0].length; j++) {
			emptyRow[j] = new Empty();
		}
		for (int i = 0; i < matrix.length; i++) {
			if(matrix[i].equals(emptyRow)) {continue;}
			else {
				List<Shape> column = new ArrayList<Shape>();
				List<Shape> empties = new ArrayList<Shape>();
				for (int j = 0; j < matrix[i].length; j++) {
					if (matrix[i][j] instanceof Empty) {
						empties.add(new Empty());
					}else {
						column.add(matrix[i][j]);
					}
				}
				empties.addAll(column);
				Object[] array = empties.toArray();
				Shape[] newColumn = new Shape[matrix[0].length];
				for (int j = 0; j < array.length; j++) {
					newColumn[j] = (Shape)array[j];
				}
				matrix[i] = newColumn;
//				Shape[] newColumn = (Shape[])column.toArray();
//				bu niye hata veriyor ki???
//				demek ki array'lerin normal nesnelerden
//				farkli bir calisma mekanizmasi var
			}
		}
	}
	public static void explode(Shape[][] grid,int x ,int y,Player player){
		Integer[] right = new Integer[2];
		Integer[] left = new Integer[2];
		Integer[] top = new Integer[2];
		Integer[] bottom = new Integer[2];
		Integer[] rightUpperDiagnol = new Integer[2];
		Integer[] leftUpperDiagnol = new Integer[2];
		Integer[] rightLowerDiagnol = new Integer[2];
		Integer[] leftLowerDiagnol = new Integer[2];
		
		List<Boolean> doesExist = new ArrayList<Boolean>();
		
		doesExist.add(null);
		if(x-1>0&&y-1>0) {
			leftUpperDiagnol[0] = y-1;
			leftUpperDiagnol[1] = x-1;
			doesExist.add(true);
		}else doesExist.add(false);
		
		if(y-1>0) {
			top[0] = y-1;
			top[1] = x;
			doesExist.add(true);
		}else doesExist.add(false);
		
		if((y-1>0)&&x+1<10) {
			rightUpperDiagnol[0] = y-1;
			rightUpperDiagnol[1] = x+1;
			doesExist.add(true);
		}else doesExist.add(false);
		
		if(x-1>0) {
			left[0] = y;
			left[1] = x-1;
			doesExist.add(true);
		}else doesExist.add(false);
		
		doesExist.add(null);

		if(x+1<10) {
			right[0] = y;
			right[1] = x+1;
			doesExist.add(true);
		}else doesExist.add(false);
		
		if(y+1<10&&x-1>0) {
			leftLowerDiagnol[0] = y+1;
			leftLowerDiagnol[1] = x-1;
			doesExist.add(true);
		}else doesExist.add(false);
		
		if(y+1<10) {
			bottom[0] = y+1;
			bottom[1] = x;
			doesExist.add(true);
		}else doesExist.add(false);
		
		if(x+1<10&& y+1<10) {
			rightLowerDiagnol[0] = y+1;
			rightLowerDiagnol[1] = x+1;
			doesExist.add(true);
		}else doesExist.add(false);
		switch(grid[y][x].keyWord) {
		case "D":
			explodeDiagnol(x, y, leftUpperDiagnol, rightLowerDiagnol, rightUpperDiagnol,
					leftLowerDiagnol, doesExist.get(1), doesExist.get(9), doesExist.get(3),
					doesExist.get(7), grid,false, player);
			break;
		case "T":
			explodeVerticalAndHorizantal(x, y, top, bottom, doesExist.get(2),
					doesExist.get(8) , grid, false,player);
			break;
		case "S":
			explodeVerticalAndHorizantal(x, y, left, right, doesExist.get(4), doesExist.get(6),
					grid, false,player);
			break;
		case "W":
			boolean match;
			match = explodeVerticalAndHorizantal(x, y, top, bottom, doesExist.get(2), doesExist.get(8), 
					grid,true,player);
			if(!match) match= explodeVerticalAndHorizantal(x, y, left, right,doesExist.get(4), doesExist.get(6), grid,
					true,player);
			if(!match) match = explodeDiagnol(x, y, leftUpperDiagnol, rightLowerDiagnol, rightUpperDiagnol,
					leftLowerDiagnol, doesExist.get(1), doesExist.get(9), doesExist.get(3),
					doesExist.get(7), grid,true,player);
			break;
		}
		if(grid[y][x].type.equals(TypeOfShape.MathSign)) {
			List<Integer[]> listOfCoordintes = new ArrayList<>();
			Integer[][] coordinates = {new Integer[2],leftUpperDiagnol,top,rightUpperDiagnol,
					left,new Integer[2],right,leftLowerDiagnol,bottom,rightLowerDiagnol};
			listOfCoordintes = Arrays.asList(coordinates);
			MathSign.whatToDoBasedOnMathSign(x, y, grid[y][x], listOfCoordintes, doesExist,player, grid);}
	}
	public static boolean explodeVerticalAndHorizantal(int x, int y,
			Integer[] coordinatesFirstYthenX,Integer[]
					coordinatesFirstYthenX2,
			boolean doesExist,boolean doesExist2,Shape[][] grid,boolean isWildCardCase,Player player) {
		//doesExist and doesExist2 stores whether there is a match or not in the direction we move
		boolean match = false;
		if(doesExist) {		
			int x1 = coordinatesFirstYthenX[1]; 
			int y1 = coordinatesFirstYthenX[0];

			if(grid[y][x].isEqual(grid[y1]
					[x1],isWildCardCase)&&(!match)) {
				int dx = x1 - x;// aralarindaki farki tutuyor
				int dy = y1 - y;
				if((y1 + dy<0)||(y1+dy>9)||(x1 + dx<0)||(x1+dx>9)) {}
				else {
					goesInOneDirection(grid, x, y, x1, y1, x1 + dx, y1 + dy,match, player,isWildCardCase);}
				}
		}
		if(doesExist2) {
			int y2 = coordinatesFirstYthenX2[0];
			int x2 = coordinatesFirstYthenX2[1];
			if(grid[y][x].isEqual(grid[y2][x2],isWildCardCase)&&(!match)) {
					int dx = x2 - x;
					int dy = y2 - y;
					if((y2 + dy<0)||(y2+dy>9)||(x2 + dx<0)||(x2+dx>9)) {}
					else {
						goesInOneDirection(grid, x, y, x2, y2, x2 + dx, y2 + dy,match, player,isWildCardCase);}
			}
		}
		return match;
	}
	public static boolean explodeDiagnol(int x, int y,
			Integer[] coordinatesFirstYthenX,Integer[]
					coordinatesFirstYthenX2,Integer[] coordinatesFirstYthenX3,
					Integer[] coordinatesFirstYthenX4,boolean doesExist
					,boolean doesExist2, boolean doesExist3,boolean doesExist4,
			Shape[][] grid,boolean isWildCardCase,Player player) {
		boolean match = false;
		match = explodeVerticalAndHorizantal(x, y, coordinatesFirstYthenX, coordinatesFirstYthenX2, doesExist,
				doesExist2, grid,isWildCardCase,player);
		if(!match) explodeVerticalAndHorizantal(x, y, coordinatesFirstYthenX3, coordinatesFirstYthenX4, doesExist3,
				doesExist4, grid, isWildCardCase,player);
		return match;
	}
	public static void goesInOneDirection(Shape[][] grid,int x,int y,int x1,int y1,int x2, int y2,boolean match, Player p, boolean isWildCardCase) {
		if(grid[y1][x1].isEqual(grid[y2][x2],isWildCardCase)) {
			match = true;
			p.totalPoint += grid[y][x].point + grid[y1][x1].point+ grid[y2][x2].point; 
			grid[y][x] = new Empty();
			grid[y1][x1] = new Empty();
			grid[y2][x2] = new Empty();
//			System.out.println(p.name + " has obtained "+ p.totalPoint);
		}
	}
	public static void process(List<String> willBeWrittenList,Shape[][] grid
			,Player player,Iterator<String[]> it,String fileNameLeaderBoard) {
		willBeWrittenList.add("Game grid : "+"\n\n");
		willBeWrittenList = Stream.concat(willBeWrittenList.
				stream(), ReadAndWrite.printGrid(grid).stream())
                .collect(Collectors.toList());
		willBeWrittenList.add("\n");
		
		while(it.hasNext()) {
			String[] nextCommand = it.next();
			if(nextCommand[0].equals("E")) {

				willBeWrittenList.add("Select coordinate or enter E to end the game: E"+"\n\n");
				willBeWrittenList.add("Total score: "+ player.totalPoint+" points"+"\n\n");
				willBeWrittenList.add("Enter name: "+nextCommand[1]+"\n\n");
				player.name = nextCommand[1];
			}
			else {
				willBeWrittenList.add("Select coordinate o"
						+ "r enter E to end the game: "+
						nextCommand[0]+ " "+nextCommand[1]+"\n\n");
				player.pointInHand = 0;
				int store = player.totalPoint;
				if(grid[Integer.parseInt(nextCommand[0])]
						[Integer.parseInt(nextCommand[1])] instanceof
						Empty) {willBeWrittenList.add("Please enter a valid coordinate\n\n");
						}
				else{
					Grid.explode(grid, Integer.parseInt(nextCommand[1]), 
							 Integer.parseInt(nextCommand[0]),player);
					player.pointInHand = player.totalPoint - store;
					
					Shape[][] transposedOne = Grid.transposeMatrix(grid);
					Grid.removeWhiteSpacesInColumns(transposedOne);
					grid = Grid.transposeMatrix(transposedOne);
					
					willBeWrittenList.addAll(ReadAndWrite.printGrid(grid));
					willBeWrittenList.add("\n");
					willBeWrittenList.add("Score: "+player.pointInHand+"\n\n");
					}
				}
		}
		willBeWrittenList.add(ReadAndWrite.printRank(player, fileNameLeaderBoard));
		willBeWrittenList.add("\n\n");
		willBeWrittenList.add("Good bye!\n\n");

		ReadAndWrite.write("monitoring.txt", willBeWrittenList);
	}
	
}
