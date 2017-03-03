package Entities;

public class Video {

	private int id;
	private int size;

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

	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Video))
			return false;

		Video v = (Video) o;
		return this.id == v.getId();
	}
} // End of Video