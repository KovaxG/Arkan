package Entities;

public class Result {
	String file;
	int score;
	long time;

	public Result(String file) {
		super();
		this.file = file;
	}

	public Result(String file, int score, long time) {
		super();
		this.time = time;
		this.score = score;
		this.file = file;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
}