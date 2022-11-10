
import java.util.List;

public class MathSign extends Shape{
	public MathSign(String keyWord) {
		this.keyWord = keyWord;
	}
	{
		this.point = 20;//total point
		this.type = TypeOfShape.MathSign; 
	}
	@Override
	public boolean isEqual(Shape s, boolean isWildCardCase) {
		if(s instanceof MathSign) return true;
		else return false;
	}
	public static void whatToDoBasedOnMathSign(int x ,int y
			,Shape s,List<Integer[]> coordinateList,
			List<Boolean> doesExist,Player p,Shape[][] grid){
		switch(s.keyWord) {
		case "/":
			Grid.explodeVerticalAndHorizantal
			(x, y, coordinateList.get(3), coordinateList.get(7), doesExist.get(3), doesExist.get(7), grid, false, p);
//			System.out.println("/");
			break;
		case "+":
			boolean match = Grid.explodeVerticalAndHorizantal
			(x, y, coordinateList.get(4), coordinateList.get(6), doesExist.get(4), doesExist.get(6), grid, false, p);
//			System.out.println("+");

			if (!match)
				Grid.explodeVerticalAndHorizantal
			(x, y, coordinateList.get(2), coordinateList.get(8), doesExist.get(2), doesExist.get(8), grid, false, p);
//			System.out.println("+");

			break;
		case "-":
			Grid.explodeVerticalAndHorizantal
			(x, y, coordinateList.get(4), coordinateList.get(6), doesExist.get(4), doesExist.get(6), grid, false, p);
//			System.out.println("-");

			break;
		case "\\":
			Grid.explodeVerticalAndHorizantal
			(x, y, coordinateList.get(1), coordinateList.get(9), doesExist.get(1), doesExist.get(9), grid, false, p);
//			System.out.println("\\");

			break;
		case "|":
			Grid.explodeVerticalAndHorizantal
			(x, y, coordinateList.get(2), coordinateList.get(8), doesExist.get(2), doesExist.get(8), grid, false, p);
//			System.out.println("|");

			break;
		}
	}
}
