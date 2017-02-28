import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class Robert {
	public static ArrayList<Video> videos = new ArrayList<Video>();
	public static ArrayList<Cache> caches = new ArrayList<Cache>();
	//public static ArrayList<Request> requests = new ArrayList<Request>();
	public static ArrayList<EndPoint> endpoints = new ArrayList<EndPoint>();
	DataCenter datacenter=null;
	
	public static String inputFile = "inputs/me_at_the_zoo.in";
	public static String outputFile = "outputs/zoo_out.txt";
	
	public static void main(String[] args) {
		
		Parse();
		
	}
	
	static int videoCount;
	static int endpointCount;
	static int requestDesctriptionCount;
	static int cacheCount;
	static int cacheSize;
	
	
	public static void init(String fileNameWithoutExtension){
		Robert.videos = new ArrayList<Video>();
		Robert.caches = new ArrayList<Cache>();
		Robert.endpoints = new ArrayList<EndPoint>();

		Robert.videoCount = 0;
		Robert.endpointCount = 0;
		Robert.requestDesctriptionCount = 0;
		Robert.cacheCount = 0;
		Robert.cacheSize = 0;

		System.err.println("Starting " + fileNameWithoutExtension);

		Robert.inputFile = "inputs/" + fileNameWithoutExtension + ".in";
		Robert.outputFile = "outputs/" + fileNameWithoutExtension + ".txt";
		
	}
	
	public static void Parse(){
		
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
	
	public static void WriteResults(ArrayList<Cache> caches){
		
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
