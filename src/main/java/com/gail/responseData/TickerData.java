package com.gail.responseData;

public class TickerData {
	private String tickerId;
	private String tickerText;
	private String start;
	private String end;
	public String getTickerId() {
		return tickerId;
	}
	public void setTickerId(String tickerId) {
		this.tickerId = tickerId;
	}
	public String getTickerText() {
		return tickerText;
	}
	public void setTickerText(String tickerText) {
		this.tickerText = tickerText;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	@Override
	public String toString() {
		return "TickerData [tickerId=" + tickerId + ", tickerText=" + tickerText + ", start=" + start + ", end=" + end
				+ "]";
	}
	

}
