import java.util.ArrayList;

public class Gyuri {
	public static void main(String args[]) {
		
	}
}

class Pair <T1,T2> {
	private T1 first;
	private T2 second;
	
	public Pair(T1 f, T2 s) {
		first = f;
		second = s;
	}
	
	public T1 getFirst() {
		return first;
	}
	public void setFirst(T1 first) {
		this.first = first;
	}
	public T2 getSecond() {
		return second;
	}
	public void setSecond(T2 second) {
		this.second = second;
	}
}

class Video {
	private int id;
	private int size;
	
	public Video(int id, int size) {
		this.id = id;
		this.size = size;
	}
} // End of Video

class Cache {
	private int id;
	private static int size;
	
	ArrayList<Video> videos = new ArrayList<Video>();
} // End of Cache

class Request {
	private Video video;
	private int demand;
	
} // End of Request

class EndPoint {
	private int id;
	private ArrayList<Request> requestList = new ArrayList<Request>();
	private ArrayList<Pair<Cache, Integer>> chacheList = new ArrayList<Pair<Cache, Integer>>();
	private int dataCenterLatency;
}

class DataCenter {
	private ArrayList<Video> videoList = new ArrayList<Video>();
}