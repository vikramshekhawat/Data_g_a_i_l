package com.gail.responseData;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TickerDataList {

	private String startDate;

	private String endDate;

	private String tickerText;
   @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<String> payerId;

	private String userId;

	private List<TickerData> dataList;

	private String tickerId;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTickerId() {
		return tickerId;
	}

	public void setTickerId(String tickerId) {
		this.tickerId = tickerId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTickerText() {
		return tickerText;
	}

	public void setTickerText(String tickerText) {
		this.tickerText = tickerText;
	}

	

	public List<String> getPayerId() {
		return payerId;
	}

	public void setPayerId(List<String> payerId) {
		this.payerId = payerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<TickerData> getDataList() {
		return dataList;
	}

	public void setDataList(List<TickerData> dataList) {
		this.dataList = dataList;
	}

	

	@Override
	public String toString() {
		return "TickerDataList [startDate=" + startDate + ", endDate=" + endDate + ", tickerText=" + tickerText
				+ ", payerId=" + payerId + ", userId=" + userId + ", dataList=" + dataList + ", tickerId=" + tickerId
				+ ", status=" + status + "]";
	}



	public interface SaveTicker {

	}

	public interface UpdateTicker {

	}

}