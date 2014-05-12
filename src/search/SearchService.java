package search;

import java.util.ArrayList;

import account.Account;
import material.Notes;
import material.Question;
import material.Video;
import discussion_board.Comment;
import discussion_board.Post;

public class SearchService {
	public static ArrayList<SearchResult> searchPosts(String query){
		ArrayList<SearchResult> results = new ArrayList<SearchResult>();
		// Search all Posts
		for(Post p: Post.search(query)){
			SearchResult r = new SearchResult();
			r.setType("Post");
			r.setTitle(p.getBody());
			r.setDate(p.getDate());
			r.setAuthor(p.getAuthor().getDisplayNameOrEmail());
			results.add(r);
		}
		// Search all Comments
		for(Comment c: Comment.search(query)){
			SearchResult r = new SearchResult();
			r.setType("Comment");
			r.setTitle(c.getBody());
			r.setDate(c.getDate());
			r.setAuthor(c.getAuthor().getDisplayNameOrEmail());
			results.add(r);
		}
		// Search all Questions
		for(Question q: Question.search(query)){
			SearchResult r = new SearchResult();
			r.setType("Question");
			r.setTitle(q.getShortTitle());
			r.setDate(q.getDate());
			r.setAuthor(Account.getAccount(q.getAuthor()).getDisplayNameOrEmail());
			results.add(r);
		}
		// Search all Notes
		for(Notes n: Notes.search(query)){
			SearchResult r = new SearchResult();
			r.setType("Note");
			r.setTitle(n.getTitle());
			r.setDate(n.getDate());
			r.setAuthor(Account.getAccount(n.getAuthor()).getDisplayNameOrEmail());
			results.add(r);
		}
		// Search all Videos
		for(Video v: Video.search(query)){
			SearchResult r = new SearchResult();
			r.setType("Video");
			r.setTitle(v.getTitle());
			r.setDate(v.getDate());
			r.setAuthor(Account.getAccount(v.getAuthor()).getDisplayNameOrEmail());
			results.add(r);
		}
		return results;
	}
}
