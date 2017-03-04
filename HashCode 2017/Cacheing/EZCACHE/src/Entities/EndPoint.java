package Entities;

import java.util.ArrayList;

public class EndPoint {

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
	
	public boolean containsVideo(Video video){
		for (Request request : requestList){
			if (request.getVideo().equals(video)){
				return true;
			}
		}
		return false;
	}
	
	public int getLatency(Cache cache){
		for (Pair<Cache, Integer> cacheAndLag : cacheAndLatencyList){
			if (cacheAndLag.getFirst().equals(cache)){
				return cacheAndLag.getSecond();
			}
		}
		return -1;
	}
	
	public Request getRequestForVideo(Video video){
		for (Request request : requestList){
			if (request.getVideo().equals(video)){
				return request;
			}
		}
		
		return null;
	}

	public Cache getFastestCache(){
		if (cacheAndLatencyList.isEmpty())
			return null;
		else
			return cacheAndLatencyList.get(0).getFirst();
	}
	
	public ArrayList<Cache> getCacheList(){
		ArrayList<Cache> result = new ArrayList<Cache>();
		for (Pair<Cache, Integer> cachePair : cacheAndLatencyList){
			result.add(cachePair.getFirst());
		}
		return result;
	}
	
	public Cache getCache(int i){
		if (cacheAndLatencyList.isEmpty()){
			return null;
		}
		else{
			return cacheAndLatencyList.get(i).getFirst();
		}
	}
	
	public int getCacheCount(){
		return cacheAndLatencyList.size();
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