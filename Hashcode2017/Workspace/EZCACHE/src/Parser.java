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
			

			videoCount = br.read();
			endpointCount  = br.read();
			requestDesctriptionCount  = br.read();
			cacheCount = br.read();
			cacheSize = br.read();
			
			System.out.println(videoCount + "; " + endpointCount + "; " + requestDesctriptionCount + "; " + cacheCount + "; " + cacheSize);
			
			for (int i = 0; i < videoCount; i++) {
				videos.add(new Video(i, br.read()));
				System.out.println(videos.get(videos.size()-1).getSize());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
