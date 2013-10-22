import java.util.ArrayList;


public class Scheduler {

	private int id = 0;
	private int coreNum;
	private int maxTimeRequired;
	private int maxTimePriority;
	private int maxSecurityPriority;
	private ArrayList<Task> queue = new ArrayList<Task>();
	
	public Scheduler(int coreNum, int maxTimeRequired, int maxTimePriority, int maxSecurityPriority) {
		this.coreNum = coreNum;
		this.maxTimeRequired = maxTimeRequired;
		this.maxTimePriority = maxTimePriority;
		this.maxSecurityPriority = maxSecurityPriority;
	}
	
	private Task getMaxTimeRequired(ArrayList<Task> cur) {
		int max = 0;
		Task maxTask = cur.get(0);
		for (Task t: cur) {
			if (t.getTimeRequired() > max) {
				max = t.getTimeRequired();
				maxTask = t;
			}
		}
		return maxTask;
	}
	
	private int getMaxTimeTotal(int[] cores) {
		int max = 0;
		for (int i = 0; i < cores.length; i++) {
			if (cores[i] > max) {
				max = cores[i];
			}
		}
		return max;
	}
	
	private void addToMin(int[] array, int num) {
		int minValue = maxTimeRequired*queue.size();
		int minPlace = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] < minValue) {
				minValue = array[i];
				minPlace = i;
			}
		}
		array[minPlace] += num;
	}
	
	private int getCompletionTime(ArrayList<Task> list) {
		ArrayList<Task>	copy = new ArrayList<Task>();
		int[] cores = new int[coreNum];
		Task cur;
		for (Task t: list) {
			copy.add(t);
		}
		for (int i = 0; i < coreNum; i++) {
			cores[i] = 0;
		}
		for (int i = 0; i < list.size(); i++) {
			cur = getMaxTimeRequired(copy);
			addToMin(cores, cur.getTimeRequired());
			copy.remove(copy.indexOf(cur));
		}
		return getMaxTimeTotal(cores);
	}
}
