package Entities;


public class VideosWithScore implements Comparable<VideosWithScore> {
	Video video;
	int score;

	public VideosWithScore(Video video, int score) {
		super();
		this.video = video;
		this.score = score;
	}

	public Video getVideo() {
		return video;
	}

	public int getScore() {
		return score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((video == null) ? 0 : video.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VideosWithScore other = (VideosWithScore) obj;
		if (video == null) {
			if (other.video != null)
				return false;
		} else if (!video.equals(other.video))
			return false;
		return true;
	}


	@Override
	/// inverted sort
	public int compareTo(VideosWithScore o) {
		return o.getScore() - this.getScore();
	}

}