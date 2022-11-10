
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Main {
	public static void main(String[] args) {
		
		Shape[][] grid = Grid.createGrid(args[0]);
		Player player = new Player(0,"unkwon player");
		
		
		List<String[]> commands = ReadAndWrite.readCommand(args[1]);
		Iterator<String[]> it = commands.iterator();
		List<String> willBeWrittenList = new ArrayList<String>();
		Grid.process(willBeWrittenList, grid, player, it,"leaderboard.txt");
		
	}
}
