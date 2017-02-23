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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
} // End of Video

class Cache {
	private int id;
	private static int size;
	
	ArrayList<Video> videos = new ArrayList<Video>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static int getSize() {
		return size;
	}

	public static void setSize(int size) {
		Cache.size = size;
	}

	public ArrayList<Video> getVideos() {
		return videos;
	}

	public void setVideos(ArrayList<Video> videos) {
		this.videos = videos;
	}
	
	
} // End of Cache

class Request {
	private Video video;
	private int demand;
	
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public int getDemand() {
		return demand;
	}
	public void setDemand(int demand) {
		this.demand = demand;
	}
	
	
} // End of Request

class EndPoint {
	private int id;
	private ArrayList<Request> requestList = new ArrayList<Request>();
	private ArrayList<Pair<Cache, Integer>> chacheList = new ArrayList<Pair<Cache, Integer>>();
	private int dataCenterLatency;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<Request> getRequestList() {
		return requestList;
	}
	public void setRequestList(ArrayList<Request> requestList) {
		this.requestList = requestList;
	}
	public ArrayList<Pair<Cache, Integer>> getChacheList() {
		return chacheList;
	}
	public void setChacheList(ArrayList<Pair<Cache, Integer>> chacheList) {
		this.chacheList = chacheList;
	}
	public int getDataCenterLatency() {
		return dataCenterLatency;
	}
	public void setDataCenterLatency(int dataCenterLatency) {
		this.dataCenterLatency = dataCenterLatency;
	}
	
	
}

class DataCenter {
	private ArrayList<Video> videoList = new ArrayList<Video>();

	public ArrayList<Video> getVideoList() {
		return videoList;
	}

	public void setVideoList(ArrayList<Video> videoList) {
		this.videoList = videoList;
	}
	
	
}