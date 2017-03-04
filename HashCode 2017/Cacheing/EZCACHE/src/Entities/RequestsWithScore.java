package Entities;

public class RequestsWithScore implements Comparable<RequestsWithScore> {
	Request request;
	int score;

	public RequestsWithScore(Request request, int score) {
		super();
		this.request = request;
		this.score = score;
	}

	public Request getRequest() {
		return request;
	}

	public Video getVideo(){
		return request.getVideo();
	}
	
	public int getScore() {
		return score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((request == null) ? 0 : request.hashCode());
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
		RequestsWithScore other = (RequestsWithScore) obj;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		return true;
	}


	@Override
	/// inverted sort
	public int compareTo(RequestsWithScore o) {
		return o.getScore() - this.getScore();
	}

}