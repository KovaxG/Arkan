import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Robert {
	public ArrayList<Video> videos = new ArrayList<Video>();
	public ArrayList<Cache> caches = new ArrayList<Cache>();
	public ArrayList<EndPoint> endpoints = new ArrayList<EndPoint>();

	public ArrayList<Video> videosOriginal = new ArrayList<Video>();
	public ArrayList<Cache> cachesOriginal  = new ArrayList<Cache>();
	public ArrayList<EndPoint> endpointsOriginal  = new ArrayList<EndPoint>();

	public String inputFile = "inputs/me_at_the_zoo.in";
	public String outputFile = "outputs/zoo_out.txt";

	public static void main(String[] args) {
		/*
		 * Robert robert = new Robert("me_at_the_zoo"); robert.Parse();
		 */
		Gyuri.main(args);

	}

	int videoCount;
	int endpointCount;
	int requestDesctriptionCount;
	int cacheCount;
	int cacheSize;

	String filename;

	public Robert(String fileNameWithoutExtension) {
		this.videos = new ArrayList<Video>();
		this.caches = new ArrayList<Cache>();
		this.endpoints = new ArrayList<EndPoint>();

		this.videoCount = 0;
		this.endpointCount = 0;
		this.requestDesctriptionCount = 0;
		this.cacheCount = 0;
		this.cacheSize = 0;

		this.filename = fileNameWithoutExtension;

		System.err.println("Starting " + fileNameWithoutExtension);

		this.inputFile = "inputs/" + fileNameWithoutExtension + ".in";
		this.outputFile = "outputs/" + fileNameWithoutExtension + ".txt";

	}

	public void Parse() {
		try {
			// br = new BufferedReader(new FileReader("me_at_the_zoo.in"));

			// String line = br.readLine();
			Scanner sc = new Scanner(new FileReader(inputFile));

			videoCount = sc.nextInt();
			endpointCount = sc.nextInt();
			requestDesctriptionCount = sc.nextInt();
			cacheCount = sc.nextInt();
			cacheSize = sc.nextInt();

			for (int i = 0; i < cacheCount; i++) {
				Cache c = new Cache();
				c.setSize(cacheSize);
				c.setId(i);
				caches.add(c);
			}
			// Cache.setSize(cacheSize);

			//// System.out.println(videoCount + "; " + endpointCount + "; " +
			//// requestDesctriptionCount + "; " + cacheCount + "; " +
			//// cacheSize);
			/// section 1 - vidoesizez
			for (int i = 0; i < videoCount; i++) {
				videos.add(new Video(i, sc.nextInt()));
				//// System.out.println(videos.get(videos.size()-1).getSize());
			}

			/// sectiion 2 - endpoint latencies
			for (int i = 0; i < endpointCount; i++) {
				EndPoint ep = new EndPoint();
				ep.setId(i);
				ep.setDataCenterLatency(sc.nextInt());
				//// System.out.println("Endpoint: " + i);
				//// System.out.println("DatacenterLatancy: " +
				//// ep.getDataCenterLatency());
				/// connected to n caches

				int m = sc.nextInt();
				// System.out.println("Connected to " + m + " caches");
				for (int j = 0; j < m; j++) {
					int toCacheNr = sc.nextInt();
					int toCacheLatency = sc.nextInt();

					// System.out.println("to Cache " + toCacheNr + " with
					// Latency: " + toCacheLatency);
					ep.getCacheAndLatencyList().add(new Pair<Cache, Integer>(caches.get(toCacheNr), toCacheLatency));
				}
				//Caches sorted by latency
				Collections.sort(ep.getCacheAndLatencyList(), (o1, o2) -> o1.getSecond() - o2.getSecond());
				
				endpoints.add(ep);			
					
			}

			/// section 3 - video requests

			for (int i = 0; i < requestDesctriptionCount; i++) {
				int videoID = sc.nextInt();
				int endpointID = sc.nextInt();
				int demand = sc.nextInt();

				Request request = new Request();
				request.setDemand(demand);
				request.setVideo(videos.get(videoID));
				endpoints.get(endpointID).getRequestList().add(request);

			}
			
			for (Video video : videos){
				videosOriginal.add(new Video(video));
			}
			for (Cache cache : caches) {
				cachesOriginal.add(new Cache(cache));
			}
			for (EndPoint endPoint : endpoints) {
				endpointsOriginal.add(new EndPoint(endPoint));
			}
			
			/// close file
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/// aux calss for sort

	class videosWithScore implements Comparable<videosWithScore> {
		Video video;
		int score;

		public videosWithScore(Video video, int score) {
			super();
			this.video = video;
			this.score = score;
		}

		public Video getVideo() {
			return video;
		}

		public int getScore() {
			return score;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((video == null) ? 0 : video.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			videosWithScore other = (videosWithScore) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (video == null) {
				if (other.video != null)
					return false;
			} else if (!video.equals(other.video))
				return false;
			return true;
		}

		private Robert getOuterType() {
			return Robert.this;
		}

		@Override
		/// inverted sort
		public int compareTo(videosWithScore o) {
			return o.getScore() - this.getScore();
		}

	}

	public void sort() {
		/// each cache is independent, so maybe this can be parallelized
		System.out.println(filename + ": Starting sort");
		int progress = 0;
		int progressPercentage = 0, progressPercentageOld = 0;
		
		long addedThisIteration = -1;
		while (addedThisIteration != 0){
			System.out.println(filename + ": Videos Added This iteration: " + addedThisIteration);
			addedThisIteration=0;
		for (Cache cache : caches) {
			
			/// keep track of progress;
			progressPercentage = progress * 100 / cacheCount;
			if (progressPercentage > progressPercentageOld)
				System.out.println(filename + ": Progress: " + progressPercentage + "%");
			progress++;
			progressPercentageOld = progressPercentage;

			// find end points which have this cache as lowest latency option
			ArrayList<Pair<EndPoint, Integer>> bestEndPoints = new ArrayList<Pair<EndPoint, Integer>>();
			for (EndPoint e : endpoints) {
				//endpoints sorted at the beginninng
				// im not sure if this is actually faster or slower(using a
				// temp
				// var)
				/// if it is first option, add it to selection and delete
				// it(this cache will not be visited again)
				ArrayList<Pair<Cache, Integer>> EPCacheList = e.getCacheAndLatencyList();
				if (!EPCacheList.isEmpty() && EPCacheList.get(0).getFirst().equals(cache)) {
					bestEndPoints.add(new Pair<EndPoint, Integer>(e, EPCacheList.get(0).getSecond()));
					EPCacheList.remove(0);
				}
			}

			/// construct a list with the videos and afferent scores from
			/// the
			/// endpoints selected
			ArrayList<videosWithScore> videosWithScores = new ArrayList<videosWithScore>();
			for (Pair<EndPoint, Integer> e : bestEndPoints) {
				for (Request request : e.getFirst().getRequestList()) {

					int DL = e.getFirst().getDataCenterLatency();
					int L = e.getSecond();
					int score = request.getDemand() * (DL - L);
					Video video = request.getVideo();

					if (videosWithScores.contains(video)) {
						int index = videosWithScores.indexOf(video);
						if (videosWithScores.get(index).getScore() < score) {
							videosWithScores.remove(index);
							videosWithScores.add(new videosWithScore(video, score));
						}
					} else {
						videosWithScores.add(new videosWithScore(video, score));
					}
				}
			}

			// sorted in descending order
			Collections.sort(videosWithScores);

			// Select the best videos to put in
			// TODO write code for replacing already inserted videos, for a
			// better combination
			for (videosWithScore vScore : videosWithScores) {
				if (vScore.video.getSize() < cache.getSize() && !cache.getVideos().contains(vScore.video)) {
					cache.getVideos().add(vScore.video);
					cache.setSize(cache.getSize() - vScore.video.getSize());
					addedThisIteration++;
					/// remove all requests that contain videos that have
					/// been
					/// inserted into the cache
					/*for (EndPoint e : endpoints) {
					
					e.getRequestList().removeIf(r -> r.getVideo().equals(vScore.video));
					}*/
					for (int i=0; i<endpointCount; i++){
						
						if (endpointsOriginal.get(i).containsCache(cache)){
							endpoints.get(i).getRequestList().removeIf(r -> r.getVideo().equals(vScore.video));
						}
					}
				} else {
					break;
				}
			}
		}
	}//addedthisoperation
	}
	

	public void WriteResults(ArrayList<Cache> caches) {

		BufferedWriter wr;
		try {
			wr = new BufferedWriter(new FileWriter(outputFile));
			int empty = 0;
			for (Cache c : caches) {
				if (c.getVideos().isEmpty())
					empty++;
			}

			wr.write("" + (caches.size() - empty));

			for (Cache c : caches) {
				if (!c.getVideos().isEmpty()) {
					wr.write("\r\n" + String.valueOf(c.getId()));
					for (Video v : c.getVideos()) {
						wr.write(" " + String.valueOf(v.getId()));
					}
				}
			}
			wr.flush();
			wr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int calculateScore() {
		try {
			/// read the values from the two files

			//ArrayList<Video> videosOriginal = new ArrayList<Video>();
			//ArrayList<Cache> caches = new ArrayList<Cache>();
			//ArrayList<EndPoint> endpointsOriginal = new ArrayList<EndPoint>();

			/// calculate score
			long totalTimeSaved = 0;
			long totalRequests = 0;
			for (EndPoint ep : endpointsOriginal) {
				for (Request req : ep.getRequestList()) {

					int minlag = ep.getDataCenterLatency();

					for (Pair<Cache, Integer> p : ep.getCacheAndLatencyList()) {
						Cache c = null;
						//int lag = -1;
						for (Cache cache : caches){
							if (cache.getId() == p.getFirst().getId()){
								c = cache;
							}
						}
						//Cache c = p.getFirst();
						int lag = p.getSecond();
						if (c.getVideos().contains(req.getVideo())) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
