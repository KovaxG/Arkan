import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;



public class Robert {
	public ArrayList<Video> videos = new ArrayList<Video>();
	public ArrayList<Cache> caches = new ArrayList<Cache>();
	//public static ArrayList<Request> requests = new ArrayList<Request>();
	public ArrayList<EndPoint> endpoints = new ArrayList<EndPoint>();
	DataCenter datacenter=null;
	
	public String inputFile = "inputs/me_at_the_zoo.in";
	public String outputFile = "outputs/zoo_out.txt";
	
	public static void main(String[] args) {
		/*Robert robert = new Robert("me_at_the_zoo");
		robert.Parse();*/
		Gyuri.main(args);
		
	}
	
	int videoCount;
	int endpointCount;
	int requestDesctriptionCount;
	int cacheCount;
	int cacheSize;
	
	String filename;
	
	public Robert(String fileNameWithoutExtension){
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
	
	public void Parse(){
		
		BufferedReader br;
		try {
			//br = new BufferedReader(new FileReader("me_at_the_zoo.in"));
			
			//String line = br.readLine();
			Scanner sc = new Scanner(new FileReader(inputFile));
			
			videoCount = sc.nextInt();
			endpointCount  = sc.nextInt();
			requestDesctriptionCount  = sc.nextInt();
			cacheCount = sc.nextInt();
			cacheSize = sc.nextInt();
			
			for (int i = 0; i < cacheCount; i++) {
				Cache c = new Cache();
				c.setSize(cacheSize);
				c.setId(i);
				caches.add(c);
			}
			//Cache.setSize(cacheSize);
			
			////System.out.println(videoCount + "; " + endpointCount + "; " + requestDesctriptionCount + "; " + cacheCount + "; " + cacheSize);
			///section 1 - vidoesizez
			for (int i = 0; i < videoCount; i++) {
				videos.add(new Video(i, sc.nextInt()));
				////System.out.println(videos.get(videos.size()-1).getSize());
			}
			
			///sectiion 2 - endpoint latencies
			for (int i = 0; i < endpointCount; i++) {
				EndPoint ep = new EndPoint();
				ep.setId(i);
				ep.setDataCenterLatency(sc.nextInt());
				////System.out.println("Endpoint: " + i);
				////System.out.println("DatacenterLatancy: " + ep.getDataCenterLatency());
				///connected to n caches
				
				int m = sc.nextInt();
				//System.out.println("Connected to " + m + " caches");
				for (int j=0; j < m; j++){
					int toCacheNr = sc.nextInt();
					int toCacheLatency = sc.nextInt();
					
					//System.out.println("to Cache " + toCacheNr + " with Latency: " + toCacheLatency);
					ep.getChacheList().add(new Pair<Cache,Integer>(caches.get(toCacheNr),toCacheLatency));
				}
				endpoints.add(ep);
			}
			
			///section 3 - video requests
			
			for (int i = 0; i < requestDesctriptionCount; i++) {
				int videoID = sc.nextInt();
				int endpointID = sc.nextInt();
				int demand = sc.nextInt();
				
				Request request = new Request();
				request.setDemand(demand);
				request.setVideo(videos.get(videoID));
				endpoints.get(endpointID).getRequestList().add(request);
				
				
			}
			
			///close file
			sc.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	///aux calss for sort
	
	class videosWithScore implements Comparable<videosWithScore>{
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
		///inverted sort
		public int compareTo(videosWithScore o) {
			return o.getScore()-this.getScore();
		}
		
		
		
	}
	
	public void sort(){
		///each cache is independent, so maybe this can be parallelized
		System.out.println(filename + ": Starting sort");
		int progress = 0;
		int progressPercentage = 0, progressPercentageOld = 0;
		for (Cache cache : caches){
			///keep track of progress;
			progressPercentage = progress * 100  / cacheCount; 
			if (progressPercentage> progressPercentageOld)
			System.out.println(filename + ": Progress: " +progressPercentage + "%");
			progress++;
			progressPercentageOld = progressPercentage;
			
			//find endpoints wich have this cache as lowest latency option
			ArrayList<Pair<EndPoint,Integer>> bestEndPoints = new ArrayList<Pair<EndPoint,Integer>>();
			for (EndPoint e : endpoints){
				/// sort to ascending order, by cache latency
				Collections.sort(e.getChacheList(),(o1, o2) ->  o1.getSecond()-o2.getSecond());
				
				//im not sure if this is actually faster or slower(using a temp var)
				///if it is first option, add it to selection and delete it(this cache will not be visited again)
				ArrayList<Pair<Cache,Integer>> EPCacheList = e.getChacheList();
				if (!EPCacheList.isEmpty() && EPCacheList.get(0).getFirst().equals(cache)){
					bestEndPoints.add(new Pair<EndPoint, Integer>(e,EPCacheList.get(0).getSecond()));
					EPCacheList.remove(0);
				}
			}
			
			///construct a list with the videos and afferent scores from the endpoints selected 
			ArrayList<videosWithScore> videosWithScores  = new ArrayList<videosWithScore>();
			for (Pair<EndPoint,Integer> e: bestEndPoints){
				for (Request request : e.getFirst().getRequestList()){

					int DL = e.getFirst().getDataCenterLatency();
					int L = e.getSecond();
					int score = request.getDemand()*(DL - L);
					Video video = request.getVideo();
					
					if (videosWithScores.contains(video)){
						int index = videosWithScores.indexOf(video);
						if (videosWithScores.get(index).getScore()<score){
							videosWithScores.remove(index);
							videosWithScores.add(new videosWithScore(video, score));
						}
					}
					else{
						videosWithScores.add(new videosWithScore(video, score));
					}
				}
			}
			
			//sorted in descending order
			Collections.sort(videosWithScores);
			
			// Select the best videos to put in
			// TODO write code for replaceing already insered videos, for a better combination
			for (videosWithScore vScore : videosWithScores){
				if (vScore.video.getSize() < cache.getSize()){
					cache.getVideos().add(vScore.video);
					cache.setSize(cache.getSize()-vScore.video.getSize());

					///remove all requests that contain videos that have been inserted into the cache
					for (EndPoint e : endpoints){
						//while(e.getRequestList().remove(new Request(vScore.video,0)));
						for (Request r: e.getRequestList()){
							if (r.getVideo().equals(vScore.video)){
								//TODO fix this
								//e.getRequestList().remove(r);
							}
						}
					}
				}
				else{
					break;
				}
			}
			
			
			
		}
	}
	/*Pair<ArrayList<Video>,ArrayList<Integer>> videosWithScores = new Pair<ArrayList<Video>,ArrayList<Integer>>(new ArrayList<Video>(),new ArrayList<Integer>()); //ArrayList<Pair<Video,Integer>>();
			for (Pair<EndPoint,Integer >e: bestEndPoints){
				for (Request request : e.getFirst().getRequestList()){
					if (videosWithScores.getFirst().contains(request)){
						
					}
					else{
						videosWithScores.getFirst().add(request.getVideo());
						int DL = e.getFirst().getDataCenterLatency();
						int L = e.getSecond();
						videosWithScores.getSecond().add(request.getDemand()*(DL - L));
					}
				}
			}*/
	public void WriteResults(ArrayList<Cache> caches){
		
		BufferedWriter wr;
		try {
			wr = new BufferedWriter(new FileWriter(outputFile));
			int empty = 0;
			for (Cache c:caches){
				if (c.getVideos().isEmpty()) empty++;
			}
			
			wr.write("" + (caches.size() - empty));
			
			for (Cache c:caches){
				if (!c.getVideos().isEmpty()){
					wr.write("\r\n" + String.valueOf(c.getId()));
					for (Video v:c.getVideos()){
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
}
