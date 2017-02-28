import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.naming.ldap.SortControl;

public class Gyuri {
	public static void main(String args[]) {

		String[] file = { "example", "me_at_the_zoo", "trending_today", "videos_worth_spreading", "kittens" };

		for (int i = 0; i < file.length; i++) {

			Robert.init(file[i]);

			long t0 = System.currentTimeMillis();

			Robert.Parse();

			// printCacheList();

			System.out.println("Parsing Finished");

			Gyuri.sort();

			System.out.println("Creating cachelist.");

			
			///Ez nincs sehol használva?
			// Create a CacheList
			/*ArrayList<Cache> cachelist = new ArrayList<Cache>();
			for (EndPoint endp : Robert.endpoints) {
				for (Pair<Cache, Integer> p : endp.getChacheList()) {
					Cache c = p.getFirst();
					if (!cachelist.contains(c)) {
						cachelist.add(c);
					}
				}
			}*/

			System.out.println("Writing results.");

			Robert.WriteResults(Robert.caches);

			System.out.println("Program Finished.");

			long t1 = System.currentTimeMillis();

			System.out.println("Completed in: " + (t1 - t0) + " ms");

			System.out.println("Score: " + calculateScore(Robert.endpoints));
		}
	} // End of Main

	public static void sort() {

		System.out.println("Creating List of Requests");

		// Create an arrayList with all the requests
		List<Request> allRequests = new ArrayList<Request>();
		for (EndPoint ep : Robert.endpoints) {
			for (Request req : ep.getRequestList()) {
				allRequests.add(req);
			}
		}

		System.out.println("Starting Sort.");

		// Sort the list using the demand field
		Collections.sort(allRequests, (o1, o2) -> o1.compareTo(o2));

		System.out.println("This will take forever");

		// Go through all requests
		for (Request r : allRequests) {
			EndPoint endP = findEndPoint(r);

			if (endP == null) {
				System.err.println("endPoint == null");
				return;
			}

			// Get the cache that has the lowest lag and
			// the video can fit in it
			// and the video is not in it already
			int minLag = 99999;
			Cache temp = null;
			for (Pair<Cache, Integer> pair : endP.getChacheList()) {
				Cache c = pair.getFirst();
				int lag = pair.getSecond();
				if (c.getSize() >= r.getVideo().getSize() && !c.getVideos().contains(r.getVideo()) && lag < minLag) {

					minLag = lag;
					temp = c;

				}
			}

			if (temp != null) {
				temp.setSize(temp.getSize() - r.getVideo().getSize());
				temp.getVideos().add(r.getVideo());

				// printCacheList();
			}
		}
	}

	public static void printCacheList() {
		System.out.println("Size: " + Robert.caches.size());
		for (Cache c : Robert.caches) {
			System.out.println(c.toString());
		}
		System.out.println("\n\n");
	}

	public static EndPoint findEndPoint(Request r) {
		for (EndPoint ep : Robert.endpoints) {
			if (ep.getRequestList().contains(r))
				return ep;
		}
		return null;
	} // End of findEndPoint

	/**
	 * TODO this doesn't work. Fix.
	 * 
	 * @param endPoints
	 * @return score
	 */
	public static int calculateScore(ArrayList<EndPoint> endPoints) {
		int totalTimeSaved = 0;
		int totalRequests = 0;

		for (EndPoint ep : endPoints) {
			for (Request req : ep.getRequestList()) {
				for (Pair<Cache, Integer> p : ep.getChacheList()) {
					Cache c = p.getFirst();
					int lag = p.getSecond();

					if (c.getVideos().contains(req.getVideo())) {
						// System.out.println("" + (ep.getDataCenterLatency() -
						// lag) + " " + req.getDemand());
						totalTimeSaved += ((ep.getDataCenterLatency() - lag) * req.getDemand());
						totalRequests = totalRequests + req.getDemand();
					}
				}
			}
		}

		return totalTimeSaved / totalRequests * 1000;
	} // End of calculateScore

} // End of Class Gyuri

// <=== === === === === === Classes === === === === === ===>

class Pair<T1, T2> {

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

	public Video() {
	}

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
		if (o == null)
			return false;
		if (!(o instanceof Video))
			return false;

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

	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Cache))
			return false;

		Cache c = (Cache) o;
		return this.id == c.getId();
	}

	public String toString() {
		return String.format("id: %d, # of vids: %d", id, videos.size());
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
		if (o == null)
			return false;
		if (!(o instanceof Request))
			return false;

		Request r = (Request) o;
		return r.getVideo().equals(this.video) && r.getDemand() == this.demand;
	}

	public int compareTo(Request r) {
		return -this.demand + r.getDemand();
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