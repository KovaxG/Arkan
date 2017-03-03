package Entities;

import java.util.ArrayList;

public class Cache {

	private int id;
	private int size;

	ArrayList<Video> videos;

	public Cache() {
		super();
		videos = new ArrayList<Video>();
	}

	public Cache(Cache cache) {
		super();
		this.id = cache.id;
		this.size = cache.size;
		this.videos = new ArrayList<Video>();
		for (Video video : cache.videos) {
			this.videos.add(new Video(video));
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

	public void setSize(int size) {
		this.size = size;
	}

	public ArrayList<Video> getVideos() {
		return videos;
	}

	public void setVideos(ArrayList<Video> videos) {
		this.videos = videos;
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