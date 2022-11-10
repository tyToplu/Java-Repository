
public enum TypeOfShape {
	Diamond,MathSign,Square,Triangle,Wildcard,Error,Empty;
	public static TypeOfShape getTypeOfShape(String keyWord) {
		switch (keyWord) {
			case "D":
				return Diamond;
			case "S":
				return Square;
			case "T":
				return Triangle;
			case "W":
				return Wildcard;
			case "+":
				return MathSign;
			case "-":
				return MathSign;
			case "/":
				return MathSign;
			case "\\":
				return MathSign;
			case "|":
				return MathSign;
		}
		return Error;
	}	
}// bu class muhtemelen silinecek
