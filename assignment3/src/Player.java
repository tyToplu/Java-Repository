
public class Player implements Comparable<Player>{
	int totalPoint;
	String name;
	public int pointInHand;
	
	public Player(int totalPoint, String name) {
		this.totalPoint = totalPoint;
		this.name = name;
	}
	@Override
	public int compareTo(Player o) {
		return -this.totalPoint+o.totalPoint;
	}
	@Override
	public String toString() {
		return this.name + " "+ this.totalPoint+"\n";
	}
}
