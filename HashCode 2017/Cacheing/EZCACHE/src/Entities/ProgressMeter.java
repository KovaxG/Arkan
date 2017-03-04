package Entities;

public class ProgressMeter {
	int currentIteration;
	int totalIterations;
	int currentPercentage;
	int oldPercentage;
	int tenByTen;
	String title;
	
	public ProgressMeter (int totalIterations, String title, boolean goTenByTen){
		this(totalIterations,title);
		if(goTenByTen){
			tenByTen = 10;
		}
		else{
			tenByTen = 1;
		}
	}
	
	public ProgressMeter (int totalIterations, String title){
		this.totalIterations = totalIterations;
		oldPercentage=0;
		currentPercentage=0;
		currentIteration=0;
		this.title = title;
		tenByTen = 1;
	}
	
	public int increment(){
		currentIteration++;
		currentPercentage = currentIteration * 100/tenByTen /totalIterations;
		if (oldPercentage!=currentPercentage){
			System.out.println(title + ": " + currentPercentage*tenByTen + "%");
		}
		oldPercentage = currentPercentage;
		return (currentPercentage * tenByTen);
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
