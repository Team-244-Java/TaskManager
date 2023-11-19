package edu.cs244.taskpulse.utils;

public class ChatMessage {
	 private final String content;
	    private final boolean fromUser;

	    public ChatMessage(String content, boolean fromUser) {
	        this.content = content;
	        this.fromUser = fromUser;
	    }

	    public String getContent() {
	        return content;
	    }

	    public boolean isFromUser() {
	        return fromUser;
	    }
	    @Override
	    public String toString() {
	        return content; // Override toString to return the content for display
	    }
}
