package Entities;

import org.omg.CORBA.ORBPackage.InconsistentTypeCode;

public class Request implements Comparable<Request> {

	private final int id;
	private final Video video;
	private final int demand;
	private int dataCenterLagOfParent;
	private int cacheLagOfParent;
	private int timeSaved;
	private Cache cache;
	//that it belongs to
	private final EndPoint endPoint; 

	public Request(Request request) {
		super();
		this.video = new Video(request.video);
		this.demand = request.demand;
		this.endPoint = request.endPoint;
		this.id = request.id;
	}

	/*public Request() {
		super();
	};*/

	public Request(Video video, int demand, EndPoint endPoint, int id) {
		super();
		this.video = video;
		this.demand = demand;
		this.endPoint = endPoint;
		this.id = id;
	}
	
	
	
	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public int getId() {
		return id;
	}
	
	public int getTimeSaved() {
		return timeSaved;
	}

	public void setTimeSaved(int timeSaved) {
		this.timeSaved = timeSaved;
	}

	public int getDataCenterLagOfParent() {
		return dataCenterLagOfParent;
	}

	public void setDataCenterLagOfParent(int dataCenterLagOfParent) {
		this.dataCenterLagOfParent = dataCenterLagOfParent;
	}

	public int getCacheLagOfParent() {
		return cacheLagOfParent;
	}

	public void setCacheLagOfParent(int cacheLagOfParent) {
		this.cacheLagOfParent = cacheLagOfParent;
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

	public EndPoint getEndPoint() {
		return endPoint;
	}

	@Override
	public int compareTo(Request r) {
		return -this.demand + r.getDemand();
	}

	public String toString() {
		return String.format("%d, %d", video.getId(), demand);
	}
} // End of Request