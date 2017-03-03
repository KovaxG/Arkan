
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Gyuri {
	public static void main(String args[]) {

		String[] files = { "example", "me_at_the_zoo", "trending_today", "videos_worth_spreading", "kittens" };
		String logFile = "log.txt";

		ArrayList<Result> results = new ArrayList<Result>();

		for (String file : files) {
			Robert robert = new Robert(file);

			long t0 = System.currentTimeMillis();

			robert.Parse();

			// printCacheList();

			System.out.println(file + ": Parsing Finished");

			Thread sorthread = new Thread(new Runnable() {
				public void run() {
					// Gyuri.sort(robert, file);
					robert.sort();
				}
			});

			sorthread.start();

			/// Ez nincs sehol használva?
			/*
			 * Create a CacheList
			 * 
			 * System.out.println("Creating cachelist.")
			 * 
			 * ArrayList<Cache> cachelist = new ArrayList<Cache>(); for
			 * (EndPoint endp : Robert.endpoints) { for (Pair<Cache, Integer> p
			 * : endp.getChacheList()) { Cache c = p.getFirst(); if
			 * (!cachelist.contains(c)) { cachelist.add(c); } } }
			 */

			new Thread(new Runnable() {
				public void run() {
					try {
						sorthread.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println(file + ": Writing results.");

					robert.WriteResults(robert.caches);

					System.out.println(file + ": Program Finished.");

					long t1 = System.currentTimeMillis();

					int score = robert.calculateScore();
					/// due to java optimizing code in the background this is
					/// not really accurate
					long time = t1 - t0;

					synchronized (results) {
						results.add(new Result(file, score, time));
					}

					System.err.println(file + ": Completed in: " + time + " ms");

					System.err.println(file + ": Score: " + score);
				}
			}).start();
		}

		/// TODO maybe this should have its separate method of methods.
		boolean equal = false;

		while (!equal) {
			synchronized (results) {
				if (results.size() == files.length)
					equal = true;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (results.size() == files.length) {
			RandomAccessFile raf;
			BufferedWriter wr;
			Scanner sc;

			try {
				/// update number of entries in file;
				raf = new RandomAccessFile(logFile, "rw");
				raf.seek(0);
				int n = raf.read();
				raf.close();

				/// read last entry
				ArrayList<Result> resultsLast = new ArrayList<Result>();
				if (n >= 1) {
					sc = new Scanner(new FileReader(logFile));

					// skip the size line
					String s = sc.nextLine();

					int i = 0;
					while (i < n - 1) {
						s = sc.nextLine();
						if (s.getBytes()[0] == 14)
							i++;
					}

					String line = "::";
					String[] elements;

					for (int j = 0; j < files.length; j++) {
						if (sc.hasNextLine()) {
							line = sc.nextLine();

							if (line.getBytes()[0] == 14) {
								break;
							} else {
								elements = line.split(":");
								resultsLast.add(new Result(elements[0], Integer.parseInt(elements[1]),
										Long.parseLong(elements[2])));
							}
						}
					}
					sc.close();
				}

				/// append new results
				wr = new BufferedWriter(new FileWriter(logFile, true));
				wr.write(14);// 'new page'
				wr.newLine();

				for (int j = 0; j < files.length; j++) {
					Result result = null;
					for (Result r : results) {
						if (r.getFile().equals(files[j])) {
							result = r;
							break;
						}
					}

					if (result != null) {
						wr.write(result.getFile() + ":" + result.getScore() + ":" + result.getTime());
						wr.newLine();
					}
				}

				wr.flush();
				wr.close();

				// update entries value
				raf = new RandomAccessFile(logFile, "rw");
				raf.seek(0);
				if (n == -1) {
					raf.write(1);
					raf.write('\r');
					raf.write('\n');
					System.out.println("Created log file. No former results to compare to");
				} else
					raf.write(n + 1);
				raf.close();

				/// compare last and current results
				if (n >= 1) {
					System.out.println("");
					System.out.println("Improvements (e.g. (150) means current=last+150):");
					for (Result currentResult : results) {
						for (Result lastResult : resultsLast) {
							if (currentResult.getFile().equals(lastResult.getFile())) {
								System.out.println(currentResult.getFile() + ":");
								int scoreDiff = currentResult.getScore() - lastResult.getScore();
								System.out.println("     Score(+ => better): " + currentResult.getScore() + " ("
										+ (scoreDiff > 0 ? "+" : "") + scoreDiff + ")");

								long timeDiff = currentResult.getTime() - lastResult.getTime();
								System.out.println("     Time(- => better): " + currentResult.getTime() + " ("
										+ (timeDiff > 0 ? "+" : "") + timeDiff + ")");

								break;
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.err.println("Error occured, data corrupted, no logging done");
		}

	} // End of Main

	public void sort(Robert robert, String file) {

		System.out.println(file + ": Creating List of Requests");

		// Create an arrayList with all the requests
		List<Request> allRequests = new ArrayList<Request>();
		for (EndPoint ep : robert.endpoints) {
			for (Request req : ep.getRequestList()) {
				allRequests.add(req);
			}
		}

		System.out.println(file + ": Starting Sort.");

		// Sort the list using the demand field
		Collections.sort(allRequests, (o1, o2) -> o1.compareTo(o2));

		System.out.println(file + ": This will take forever");

		// Go through all requests
		for (Request r : allRequests) {
			EndPoint endP = findEndPoint(robert, r);

			if (endP == null) {
				System.err.println(file + ":endPoint == null");
				return;
			}

			// Get the cache that has the lowest lag and
			// the video can fit in it
			// and the video is not in it already
			int minLag = 99999;
			Cache temp = null;
			for (Pair<Cache, Integer> pair : endP.getCacheAndLatencyList()) {
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

	public void printCacheList(Robert robert) {
		System.out.println("Size: " + robert.caches.size());
		for (Cache c : robert.caches) {
			System.out.println(c.toString());
		}
		System.out.println("\n\n");
	}

	public EndPoint findEndPoint(Robert robert, Request r) {
		for (EndPoint ep : robert.endpoints) {
			if (ep.getRequestList().contains(r))
				return ep;
		}
		return null;
	} // End of findEndPoint

	/**
	 * @param endPoints
	 * @return score
	 */
	public static int calculateScore(ArrayList<EndPoint> endPoints) {
		long totalTimeSaved = 0;
		long totalRequests = 0;

		// ArrayList<Pair<Video, Integer>> vidLatency = new
		// ArrayList<Pair<Video, Integer>>();

		for (EndPoint ep : endPoints) {
			for (Request req : ep.getRequestList()) {
				int minlag = ep.getDataCenterLatency();

				for (Pair<Cache, Integer> p : ep.getCacheAndLatencyList()) {
					Cache c = p.getFirst();
					int lag = p.getSecond();

					if (c.getVideos().contains(req.getVideo())) {
						// System.out.println("" + (ep.getDataCenterLatency() -
						// lag) + " " + req.getDemand());
						if (lag < minlag) {
							minlag = lag;
						}

					}
				}
				totalTimeSaved += ((ep.getDataCenterLatency() - minlag) * req.getDemand());
				totalRequests = totalRequests + req.getDemand();
			}
		}
		return (int) (totalTimeSaved * 1000.0 / totalRequests);
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

	public Video(Video video) {
		super();
		this.id = video.id;
		this.size = video.size;
	}

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

	ArrayList<Video> videos;

	public Cache() {
		super();
		videos = new ArrayList<Video>();
	}

	public Cache(Cache cache) {
		super();
		this.id = cache.id;
		this.size = cache.size;
		this.videos = new ArrayList<Video>();
		for (Video video : cache.videos) {
			this.videos.add(new Video(video));
		}
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

class Request implements Comparable<Request> {

	private final Video video;
	private final int demand;
	//that it belongs to
	private final EndPoint endPoint; 

	public Request(Request request) {
		super();
		this.video = new Video(request.video);
		this.demand = request.demand;
		this.endPoint = request.endPoint;
	}

	/*public Request() {
		super();
	};*/

	public Request(Video video, int demand, EndPoint endPoint) {
		super();
		this.video = video;
		this.demand = demand;
		this.endPoint = endPoint;
	}

	public Video getVideo() {
		return video;
	}
/*
	public void setVideo(Video video) {
		this.video = video;
	}*/

	public int getDemand() {
		return demand;
	}
/*
	public void setDemand(int demand) {
		this.demand = demand;
	}*/

	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Request))
			return false;

		Request r = (Request) o;
		return r.getVideo().equals(this.video) && r.getDemand() == this.demand;
	}

	@Override
	public int compareTo(Request r) {
		return -this.demand + r.getDemand();
	}

	public String toString() {
		return String.format("%d, %d", video.getId(), demand);
	}
} // End of Request

class EndPoint {

	private int id;
	private ArrayList<Request> requestList;
	
	private ArrayList<Pair<Cache, Integer>> cacheAndLatencyList;
	private int dataCenterLatency;

	public EndPoint() {
		super();
		requestList = new ArrayList<Request>();
		cacheAndLatencyList = new ArrayList<Pair<Cache, Integer>>();
	}

	public EndPoint(EndPoint endpoint) {
		super();
		this.id = endpoint.id;
		this.requestList = new ArrayList<Request>();
		for (Request request : endpoint.requestList) {
			this.requestList.add(new Request(request));
		}
		this.cacheAndLatencyList = new ArrayList<Pair<Cache, Integer>>();
		for (Pair<Cache, Integer> cacheElement : endpoint.cacheAndLatencyList) {
			this.cacheAndLatencyList.add(new Pair<Cache, Integer>(new Cache(cacheElement.getFirst()),
					new Integer(cacheElement.getSecond().intValue())));
		}
		this.dataCenterLatency = endpoint.dataCenterLatency;
	}
	
	public boolean containsCache(Cache cache){
		for (Pair<Cache,Integer> cachePair : cacheAndLatencyList){
			if (cachePair.getFirst().equals(cache))
				return true;
		}
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Request> getRequestList() {
		return requestList;
	}

	// see setChachelist
	/*public void setRequestList(ArrayList<Request> requestList) {
		this.requestList = requestList;
	}*/

	public ArrayList<Pair<Cache, Integer>> getCacheAndLatencyList() {
		return cacheAndLatencyList;
	}

	//since they are initialized in constructor
	/*public void setChacheList(ArrayList<Pair<Cache, Integer>> chacheList) {
		this.chacheList = chacheList;
	}*/

	public int getDataCenterLatency() {
		return dataCenterLatency;
	}

	public void setDataCenterLatency(int dataCenterLatency) {
		this.dataCenterLatency = dataCenterLatency;
	}
} // End of EndPoint

class Result {
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
/*
 * class DataCenter {
 * 
 * private ArrayList<Video> videoList = new ArrayList<Video>();
 * 
 * public ArrayList<Video> getVideoList() { return videoList; }
 * 
 * public void setVideoList(ArrayList<Video> videoList) { this.videoList =
 * videoList; } }
 */// End of DataCenter