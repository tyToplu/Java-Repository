
public abstract class Shape {
	protected int point;
	protected String keyWord;
	protected TypeOfShape type;
	
	
	public int getPoint() {
		return point;
	}
	public static Shape setShapeType(String keyWord) {
		switch (keyWord) {
		case "D":
			return new Diamond();
		case "S":
			return new Square();
		case "T":
			return new Triangle();
		case "W":
			return new Wildcard();
		case "+":
			return new MathSign(keyWord);
		case "-":
			return new MathSign(keyWord);
		case "/":
			return new MathSign(keyWord);
		case "\\":
			return new MathSign(keyWord);
		case "|":
			return new MathSign(keyWord);
		}
		return null;// sonra duzelt burayi
	}
	@Override
	public String toString() {
		return this.keyWord;
	}
	public boolean isEqual(Shape s,boolean isWildCardCase) {
		if(s.keyWord.equals(" ")||this.keyWord.equals(" ")) return false;	
		if(isWildCardCase) {
				if(this.keyWord.equals(s.keyWord)) return true;
				else if(s.keyWord.equals("W")||(this.keyWord.equals("W"))) return true;
				else return false;
			}
			else {
				if(this.keyWord.equals(s.keyWord)) return true;
				else return false;
			}
	}
	
}
	

