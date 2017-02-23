import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;

public class Parser {
	
	int videoCount;
	int endpointCount;
	int requestDesctriptionCount;
	int cacheCount;
	int cacheSize;
	
	public void Parse(ArrayList<Video> videos, ArrayList<Cache> caches, ArrayList<Request> requests, ArrayList<EndPoint> endpoints, 
			DataCenter datacenter){
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("me_at_the_zoo.in"));
			
			String line = br.readLine();
			Scanner sc = new Scanner(new FileReader("me_at_the_zoo.in"));
			
			videoCount = sc.nextInt();
			endpointCount  = sc.nextInt();
			requestDesctriptionCount  = sc.nextInt();
			cacheCount = sc.nextInt();
			for (int i = 0; i < cacheCount; i++) {
				Cache c =new Cache();
				c.setId(i);
				caches.add(c);
			}
			cacheSize = sc.nextInt();
			Cache.setSize(cacheSize);
			
			//System.out.println(videoCount + "; " + endpointCount + "; " + requestDesctriptionCount + "; " + cacheCount + "; " + cacheSize);
			///section 1 - vidoesizez
			for (int i = 0; i < videoCount; i++) {
				videos.add(new Video(i, sc.nextInt()));
				//System.out.println(videos.get(videos.size()-1).getSize());
			}
			
			///sectiion 2 - endpoint latencies
			for (int i = 0; i < endpointCount; i++) {
				EndPoint ep = new EndPoint();
				ep.setId(i);
				ep.setDataCenterLatency(sc.nextInt());
				System.out.println("Endpoint: " + i);
				System.out.println("DatacenterLatancy: " + ep.getDataCenterLatency());
				///connected to n caches
				
				int m = sc.nextInt();
				System.out.println("Connected to " + m + " caches");
				for (int j=0; j < m; j++){
					int toCacheNr = sc.nextInt();
					int toCacheLatency = sc.nextInt();
					
					System.out.println("to Cache " + toCacheNr + " with Latency: " + toCacheLatency);
					ep.getChacheList().add(new Pair<Cache,Integer>(caches.get(toCacheNr),toCacheLatency));
				}
			}
			
			sc.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
