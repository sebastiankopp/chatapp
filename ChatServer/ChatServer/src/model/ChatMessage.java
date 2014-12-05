package model;

import java.time.LocalDateTime;

public class ChatMessage {
	private String message;
	private Conversation conv;
	private User from;
	private LocalDateTime tstamp;
	public ChatMessage(String msg, User user, Conversation conv) {
		// TODO Auto-generated constructor stub
		tstamp = LocalDateTime.now();
		from = user;
		message = msg;
		this.conv = conv;
	}
	public ChatMessage(String msg, User user, Conversation conv, LocalDateTime dt){
		this (msg, user, conv);
		this.tstamp = dt;
	}
	/**
	 * @return the from
	 */
	public User getFrom() {
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(User from) {
		this.from = from;
	}
	/**
	 * @return the conv
	 */
	public Conversation getConv() {
		return conv;
	}
	/**
	 * @param conv the conv to set
	 */
	public void setConv(Conversation conv) {
		this.conv = conv;
	}
	/**
	 * @return the tstamp
	 */
	public LocalDateTime getTstamp() {
		return tstamp;
	}
	/**
	 * @param tstamp the tstamp to set
	 */
	public void setTstamp(LocalDateTime tstamp) {
		this.tstamp = tstamp;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	public String toString(){
		return String.format("{%s}: \"%s\" to:{%s}(%s)", this.from, this.message, this.conv, this.tstamp);
	}
	
}
