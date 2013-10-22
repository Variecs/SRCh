
public class Task {

	private int id;
	private int timePriority;
	private int timeRequired;
	private int securityPriority;
	
	public Task(int id, int timePriority, int timeRequired, int securityPriority) {
		this.id = id;	
		this.timePriority = timePriority;
		this.timeRequired = timeRequired;
		this.securityPriority = securityPriority;
	}
	
	public void tick() {
		timePriority--;
	}

	public int getId() {
		return id;
	}

	public int getTimePriority() {
		return timePriority;
	}

	public int getTimeRequired() {
		return timeRequired;
	}

	public int getSecurityPriority() {
		return securityPriority;
	}
}
