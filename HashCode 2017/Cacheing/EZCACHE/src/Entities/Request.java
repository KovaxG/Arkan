package Entities;

public class Request implements Comparable<Request> {

	private final int id;
	private final Video video;
	private final int demand;
	//that it belongs to
	private final EndPoint endPoint; 

	public Request(Request request) {
		super();
		this.video = new Video(request.video);
		this.demand = request.demand;
		this.endPoint = request.endPoint;
		this.id=request.id;
	}

	/*public Request() {
		super();
	};*/

	public Request(int id,Video video, int demand, EndPoint endPoint) {
		super();
		this.id = id;
		this.video = video;
		this.demand = demand;
		this.endPoint = endPoint;
	}

	public int getId() {
		return id;
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

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Request))
			return false;

		Request r = (Request) o;
		return r.getVideo().equals(this.video) && r.getDemand() == this.demand && r.getId() == this.id;
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