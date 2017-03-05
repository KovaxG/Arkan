package Entities;

public class Video {

	private int id;
	private int size;
	//should be used after inserting to a chace and evaluating swicharoo
	private int score;
	
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

	public int getSimpleScore(Cache cache){
		if (!cache.getVideos().contains(this)){
			return -1;
		}
		else{
			int score = 0;
			for (EndPoint endpoint: cache.getEndpoints()){
				Request request = endpoint.getRequestForVideo(this);
				if (request!=null){
					score += (endpoint.getDataCenterLatency() - endpoint.getLatency(cache))*request.getDemand();
				}
			}
			return score;
		}
		
	}
	
	
	public  int getGainedScore(Cache cache) {
		if (!cache.getVideos().contains(this)){
			return -1;
		}
		else{
			int score = 0;
			for (EndPoint endpoint: cache.getEndpoints()){
				Request request = endpoint.getRequestForVideo(this);
				if (request!=null){
					boolean alreadyPresent = false;
					for (Cache usedCache : endpoint.getCacheList()){
						/*if (usedCache.equals(cache))
							continue;*/
						if (usedCache.getVideos().contains(this)){
							alreadyPresent = true;
							break;
						}
					}
					if (!alreadyPresent){
						score += (endpoint.getDataCenterLatency() - endpoint.getLatency(cache))*request.getDemand();
					}
				}
			}
			return score;
		}
	}
	

	public void setScore(int score) {
		this.score = score;
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