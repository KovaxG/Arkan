package Entities;

import java.util.ArrayList;


public class Cache {

	private int id;
	private int size;

	ArrayList<Video> videos;
	ArrayList<EndPoint> endPoints;

	public Cache(int id, int size) {
		super();
		videos = new ArrayList<Video>();
		this.id = id;
		this.size = size;
		this.endPoints = new ArrayList<EndPoint>();
	}

	public Cache(Cache cache) {
		super();
		this.id = cache.id;
		this.size = cache.size;
		this.videos = new ArrayList<Video>();
		for (Video video : cache.videos) {
			this.videos.add(new Video(video));
		}
		this.endPoints = new ArrayList<EndPoint>();
		for (EndPoint endPoint : cache.endPoints) {
			this.endPoints.add(endPoint);
		}
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
	

	public ArrayList<EndPoint> getEndpoints() {
		return endPoints;
	}

	///This is done now through add
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public ArrayList<Video> getVideos() {
		return videos;
	}
/*
	public void setVideos(ArrayList<Video> videos) {
		this.videos = videos;
	}*/
	
	public boolean addVideo(Video video){
		if (size-video.getSize()>=0 && !videos.contains(video)){
			size -= video.getSize();
			videos.add(video);
			return true;
		}
		else{
			return false;
		}
	}
	public boolean removeVideo(Video video) {
		boolean result = videos.remove(video);
		if (result){
			size += video.getSize();
		}
		return result;
		
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Cache))
			return false;

		Cache c = (Cache) o;
		return this.id == c.getId();
	}
	public String toString() {
		return String.format("id: %d, # of vids: %d", id, videos.size());
	}
} // End of Cache