package search;

import java.io.Serializable;
import java.util.Date;

public class SearchResult implements Serializable{
	
	private static final long serialVersionUID = 6796684142637406192L;
	private String type; // ie, Post, Comment, Question, Note, Video
	private String title;
	private String author;
	private Date date;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
