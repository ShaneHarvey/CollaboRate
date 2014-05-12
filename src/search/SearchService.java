package search;

import java.util.ArrayList;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import account.Account;
import material.Notes;
import material.Question;
import material.Subtopic;
import material.Video;
import discussion_board.Comment;
import discussion_board.Post;

public class SearchService {
	public static ArrayList<SearchResult> searchPosts(String query){
		ArrayList<SearchResult> results = new ArrayList<SearchResult>();
		// Search all Posts
		for(Post p: Post.search(query)){
			SearchResult r = new SearchResult();
			Key pid = p.getKey();
			Key stid = Post.getPost(pid).getSubtopic();
			Key sid = Subtopic.getSubtopic(stid).getSubjectKey();
			r.setType("<a href=\"/discuss?sid="+
					KeyFactory.keyToString(sid)+"&stid="+
					KeyFactory.keyToString(stid)+"&pid="+
					KeyFactory.keyToString(pid)+"\">Post</a>");
			r.setTitle(p.getBody());
			r.setDate(p.getDate());
			r.setAuthor(p.getAuthor().getDisplayNameOrEmail());
			results.add(r);
		}
		// Search all Comments
		for(Comment c: Comment.search(query)){
			SearchResult r = new SearchResult();
			Key pid = c.getPostID();
			Key stid = Post.getPost(pid).getSubtopic();
			Key sid = Subtopic.getSubtopic(stid).getSubjectKey();
			r.setType("<a href=\"/discuss?sid="+KeyFactory.keyToString(sid)+
					"&stid="+KeyFactory.keyToString(stid)+
					"&pid="+KeyFactory.keyToString(pid)+"\">Comment</a>");
			r.setTitle(c.getBody());
			r.setDate(c.getDate());
			r.setAuthor(c.getAuthor().getDisplayNameOrEmail());
			results.add(r);
		}
		// Search all Questions
		for(Question q: Question.search(query)){
			SearchResult r = new SearchResult();
			r.setType("<a href=\"/question?qid="+KeyFactory.keyToString(q.getKey())+"\">Question</a>");
			r.setTitle(q.getShortTitle());
			r.setDate(q.getDate());
			r.setAuthor(Account.getAccount(q.getAuthor()).getDisplayNameOrEmail());
			results.add(r);
		}
		// Search all Notes
		for(Notes n: Notes.search(query)){
			SearchResult r = new SearchResult();
			r.setType("<a href=\""+n.getURL()+"\">Note</a>");
			r.setTitle(n.getTitle());
			r.setDate(n.getDate());
			r.setAuthor(Account.getAccount(n.getAuthor()).getDisplayNameOrEmail());
			results.add(r);
		}
		// Search all Videos
		for(Video v: Video.search(query)){
			SearchResult r = new SearchResult();
			r.setType("<a href=\"/video?vid="+KeyFactory.keyToString(v.getKey())+"\">Video</a>");
			r.setTitle(v.getTitle());
			r.setDate(v.getDate());
			r.setAuthor(Account.getAccount(v.getAuthor()).getDisplayNameOrEmail());
			results.add(r);
		}
		return results;
	}
}
