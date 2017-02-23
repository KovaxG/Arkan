import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gyuri {
	public static void main(String args[]) {
		
		Robert.Parse();
		
		// Create an arrayList with all the requests
		List<Request> allRequests = new ArrayList<Request>();
		for (EndPoint ep : Robert.endpoints) {
			for (Request req : ep.getRequestList()) {
				allRequests.add(req);
			}
		}
		
		// Sort the list using the demand field
		Collections.sort(allRequests, (o1, o2) -> o1.compareTo(o2));
		
		// Go through all requests
		for (Request r : allRequests) {
			System.out.print(r.toString());
			EndPoint endP = findEndPoint(r);
			
			int minLag = 9999999;
			for (Pair<Cache, Integer> pair : endP.getChacheList()) {
				Cache c = pair.getFirst();
				int lag = pair.getSecond();
				if (c.getSize())
			}
		}
	}
} // End of Class Gyuri

public static EndPoint findEndPoint(Request r) {
	return null;
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
} // End of Pair

class Video {
	private int id;
	private int size;
	
	public Video() {}
	
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
	
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Video)) return false;
		
		Video v = (Video) o;
		return this.id == v.getId(); 
	}
} // End of Video

class Cache {
	private int id;
	private int size;
	
	ArrayList<Video> videos = new ArrayList<Video>();

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
	
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Request)) return false;
		
		Request r = (Request) o;
		return r.getVideo().equals(this.video) 
			   && r.getDemand() == this.demand;
	}
	
	public int compareTo(Request r) {
		return this.demand - r.getDemand();
	}
	
	public String toString() {
		return String.format("%d, %d", video.getId(), demand);
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
} // End of EndPoint

class DataCenter {
	
	private ArrayList<Video> videoList = new ArrayList<Video>();

	public ArrayList<Video> getVideoList() {
		return videoList;
	}

	public void setVideoList(ArrayList<Video> videoList) {
		this.videoList = videoList;
	}
} // End of DataCenter