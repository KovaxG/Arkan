import java.util.ArrayList;

public class Robert {
	public static ArrayList<Video> videos = new ArrayList<Video>();
	public static ArrayList<Cache> caches = new ArrayList<Cache>();
	public static ArrayList<Request> requests = new ArrayList<Request>();
	public static ArrayList<EndPoint> endpoints = new ArrayList<EndPoint>();
	public static DataCenter datacenter=null;
	
	public static void main(String[] args) {
		
		new Parser().Parse (videos, caches, requests,endpoints,datacenter);
	}
}
