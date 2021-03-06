package constants;

/*
 * Keys.Java contains all of the different keys used for session
 */
public class Keys {
	// Used for storing user's account in session
	public static final String ACCOUNT = "account"; // Used for holding an account
	public static final String SUBJECT = "subject"; // Used for holding a subject
	public static final String SUBTOPIC = "subtopic"; // Used for holding a subtopic
	public static final String DISPLAY_NAME = "displayName"; // Used for storing users display name in request
	public static final String EMAIL = "email"; // Used for storing users email in request
	public static final String SUBJECT_KEY = "sid"; // Used for holding the key of a subject
	public static final String SUBJECT_TOPIC_KEY = "stid"; // Used for holding the key of a subtopic
	public static final String SUBTOPICS_LIST = "stlist"; // Used for holding an array of subtopics
	public static final String SUBJECT_NAME = "subjectName"; // Used for holding name of subject
	public static final String SUBTOPIC_NAME = "subtopicName"; // Used for holding name of subtopic 
	public static final String NOTES_LIST = "notesList"; // Used for holding list of notes
	public static final String QUESTIONS_LIST = "questionsList"; // Used for holding list of questions
	public static final String VIDEOS_LIST = "videosList"; // Used for holding list of videos
	public static final String SUBJECT_LIST = "subjectList"; // Used for holding onto a list of subjects
	public static final String CATEGORY_LIST = "categoryList";//Used for holding onto the categories
	public static final String CATEGORY_KEY = "catid";//Used to hold the selected Category
	public static final String SUBJECT_REQUEST_LIST = "subjectRequestList"; 
	public static final String SUBJECT_REQUEST = "subjectRequest";
	public static final String SUBJECT_SUBTOPICS_REQUEST_LIST = "subjectSubtopicRequests";
	public static final String QUESTION_KEY = "qid"; // Used for holding the key of a question
	public static final String VIDEO_KEY = "vid"; // Used for holding the key of a video
	public static final String VIDEO = "video"; // Used for holding onto video
	public static final String NOTES = "notes";//Used for holding onto the notes
	public static final String QUESTION = "question"; // Used for holding onto question
	public static final String ALL_SUBJECTS_LIST = "allsubjectslist"; // Used for holding onto subjects
	public static final String POST_KEY = "pid"; // Used for holding post key
	public static final String POST = "post"; // Used for holding onto post
	public static final String META_DATA = "metadata"; // Used for holding meta data
	public static final String CONTENT_KEY = "cid";
	public static final String TEST = "test"; // Used for storing a test in the session
	public static final String FLAGGED_QUESTIONS = "flaggedQuestions"; // Used for holding flagged
	public static final String FLAGGED_VIDEOS = "flaggedVideos";
	public static final String FLAGGED_NOTES = "flaggedNotes";
	public static final String CONTENT_TYPE = "ctype"; // Used for holding content type
	public static final String TOP_QUESTIONS = "topQuestions";
	public static final String TOP_VIDEOS = "topVideos";
	public static final String TOP_NOTES = "topNotes";
	public static final String USERS_QUESTIONS = "userQuestions";
	public static final String USERS_VIDEOS = "userVideos";
	public static final String USERS_NOTES =  "userNotes";
	
	public static final String ORDER = "order";
	public static final String REQUESTED_SUBTOPIC_KEY = "rstid";
	public static final String FROM_INDEX = "from";
	public static final String TO_INDEX = "to";
	
			
	// Used in SubjectServlet for statistics
	public static final String NUM_Q_SUB = "numQuestionsInSubject";
	public static final String NUM_TOPS = "numTopicsInSubject";
	public static final String NUM_Q_COMPLETED = "numQuestionsCompleted";
	public static final String NUM_Q_CORRECT = "numQuestionsCorrect";
	public static final String PERCENT_Q_CORRECT = "percentCorrect";
	public static final String NUM_TOP_COMPLETED = "numTopicsCompleted";
	public static final String NUM_TOP_NOT_STARTED = "numSubtopicsNotStarted";
	
}
