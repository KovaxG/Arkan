package Entities;

public class ProgressMeter {
	int currentIteration;
	int totalIterations;
	int currentPercentage;
	int oldPercentage;
	String title;
	
	public ProgressMeter (int totalIterations, String title){
		this.totalIterations = totalIterations;
		oldPercentage=0;
		currentPercentage=0;
		currentIteration=0;
		this.title = title;
	}
	
	public int increment(){
		currentIteration++;
		currentPercentage = currentIteration * 100 /totalIterations;
		if (oldPercentage!=currentPercentage){
			System.out.println(title + ": " + currentPercentage + "%");
		}
		oldPercentage = currentPercentage;
		return currentPercentage;
	}
	
	public int increment_noPrint() {
		currentIteration++;
		currentPercentage = currentIteration/totalIterations;
		oldPercentage = currentPercentage;
		return currentPercentage;
	}
	
	public void reset(){
		oldPercentage=0;
		currentPercentage=0;
		currentIteration=0;
	}
}
