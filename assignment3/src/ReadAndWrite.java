
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;


public class ReadAndWrite {
	public static List<String> read(String path) {
		try(FileReader fr = new FileReader(path);
				BufferedReader br = new BufferedReader(fr);) {
			String line = "";
			List<String> store = new ArrayList<String>();
			while((line=br.readLine())!=null) {
				store.add(line);
			}
			return store;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
	public static <T extends Collection<String>>void write(String fileName,T collection) {
		try(FileWriter fw = new FileWriter(fileName);
				BufferedWriter bw = new BufferedWriter(fw);) {
			Iterator<? extends String> iter = collection.iterator();
			while (iter.hasNext()) {
				bw.write(iter.next().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static List<Player> pullAndPushLeaderBoard(String fileName,Player presentPlayer) {
		List<String> namesAndScores= read(fileName);
		List<Player> namesAndScoresInOrder = new ArrayList<>();
		String[] namesAndScoresArray = namesAndScores.toArray(new String[0]);
		
		Pattern pattern = Pattern.compile(" ");
		Iterator<String> iter = namesAndScores.iterator(); 
		for (int i = 0; i < namesAndScores.size(); i++) {
			String store = namesAndScores.get(i) + "\n";
			namesAndScoresArray[i] = store;
		}
		namesAndScores = Arrays.asList(namesAndScoresArray);
		while(iter.hasNext()) {
			String[] scoreAndName = pattern.split(iter.next());
			namesAndScoresInOrder.add(new 
					Player(Integer.parseInt
							(scoreAndName[1].trim()),scoreAndName[0]));
		}
		List<String> namesAndScoresNew = new ArrayList<>(namesAndScores);
		namesAndScoresNew.add(presentPlayer.toString());
		namesAndScoresInOrder.add(presentPlayer);
		
		Collections.sort(namesAndScoresInOrder);
		write("leaderboard.txt",namesAndScoresNew);
		
		return namesAndScoresInOrder;
	}
	public static List<String[]> readCommand(String path) {
		List<String> commands = read(path);
		Iterator<String> iter = commands.iterator();
		Pattern pattern = Pattern.compile(" ");
		List<String[]> list = new ArrayList<>();
		
		while(iter.hasNext()) { 
			String next = iter.next(); 
			if(next.equals("E")) {
				String name = iter.next();
				String[] nameAndEnd = {"E",name,"bosluk"};
				list.add(nameAndEnd);
				break;
			}else {
				list.add(pattern.split(next));
			}
		}
		return list;
	}
	public static List<String> printGrid(Shape[][] matrix) {
		List<String> willBeWritten = new ArrayList<>();
		for (int i = 0; i < matrix.length; i++) { //this equals to the row in our matrix.
	         for (int j = 0; j < matrix[i].length; j++) { //this equals to the column in each row.
	            willBeWritten.add(matrix[i][j] + " ");
	         }
	         willBeWritten.add("\n"); //change line on console as row comes to end in the matrix.
	      }
		return willBeWritten;
	}
	public static String printRank(Player p,String fileName){
		List<Player> playerList = ReadAndWrite.pullAndPushLeaderBoard(fileName,p);
		String written = "Your rank is ";
		
		int orderOfPresentPlayer = Collections.binarySearch(playerList, p)+1;
		int numberOfPlayers = playerList.size();

		if(orderOfPresentPlayer == numberOfPlayers) {
			Player higherScore = playerList.get(orderOfPresentPlayer-2);
			written += "" + orderOfPresentPlayer+"/"+numberOfPlayers+
					" your score is "+ (higherScore.totalPoint-p.totalPoint)+" points " +" lower "
							+ "than "+higherScore.name;
		}else if(orderOfPresentPlayer<numberOfPlayers&&orderOfPresentPlayer!=1) {
			Player higherScore = playerList.get(orderOfPresentPlayer-2);
			Player lowerScore = playerList.get(orderOfPresentPlayer);
			written += ""+orderOfPresentPlayer+"/"+numberOfPlayers+
					" your score is " +(higherScore.totalPoint-p.totalPoint)+" points"+ " lower "
							+ "than "+ higherScore.name + " and "+
							(p.totalPoint-lowerScore.totalPoint)+" points "+" higher than "
							+" "+lowerScore.name;
		}else if (orderOfPresentPlayer == 1){
			Player lowerScore = playerList.get(orderOfPresentPlayer);
			written += "1/"+numberOfPlayers+" your score is "+(p.totalPoint-lowerScore.totalPoint)
			+" points "	+" higher than "+lowerScore.name;
		}
		return written;
	}
}
