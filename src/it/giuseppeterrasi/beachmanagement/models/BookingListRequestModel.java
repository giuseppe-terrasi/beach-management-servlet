package it.giuseppeterrasi.beachmanagement.models;

import java.sql.Timestamp;

public class BookingListRequestModel {

	private Timestamp fromDate;
	
	private Timestamp toDate;

	public Timestamp getFromDate() {
		return fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public Timestamp getToDate() {
		return toDate;
	}

	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}
	
	
}
