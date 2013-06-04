package advencedDatabase;

public class Pair {
	Long position;
	SectorHead head;
	
	
	public Pair(Long position, SectorHead head) {
		this.position = position;
		this.head = head;
	}


	public Long getPosition() {
		return position;
	}


	public void setPosition(Long position) {
		this.position = position;
	}


	public SectorHead getHead() {
		return head;
	}


	public void setHead(SectorHead head) {
		this.head = head;
	}
}
