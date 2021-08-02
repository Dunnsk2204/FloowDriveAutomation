package com.floowgroup.floowdrive.model;

public class CustomerScoreData {

	private String totalScore;

	private String smoothDrivingScore;
	
	private String mobileUseScore;
	
	private String speedScore;
	
	private String fatigueScore;
	
	private String timeOfDayScore;
	
	private String totalJourneys;

	private String totalMiles;
	
	public CustomerScoreData(String totalScore, String smoothDrivingScore, String mobileUseScore, String speedScore,
			String fatigueScore, String timeOfDayScore, String totalJourneys, String totalMiles) {
		super();
		this.totalScore = totalScore;
		this.smoothDrivingScore = smoothDrivingScore;
		this.mobileUseScore = mobileUseScore;
		this.speedScore = speedScore;
		this.fatigueScore = fatigueScore;
		this.timeOfDayScore = timeOfDayScore;
		this.totalJourneys = totalJourneys;
		this.totalMiles = totalMiles;
	}
	
	public String getTotalJourneys() {
		return totalJourneys;
	}

	public void setTotalJourneys(String totalJourneys) {
		this.totalJourneys = totalJourneys;
	}

	public String getTotalMiles() {
		return totalMiles;
	}

	public void setTotalMiles(String totalMiles) {
		this.totalMiles = totalMiles;
	}
	
	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	public String getSmoothDrivingScore() {
		return smoothDrivingScore;
	}

	public void setSmoothDrivingScore(String smoothDrivingScore) {
		this.smoothDrivingScore = smoothDrivingScore;
	}

	public String getMobileUseScore() {
		return mobileUseScore;
	}

	public void setMobileUseScore(String mobileUseScore) {
		this.mobileUseScore = mobileUseScore;
	}

	public String getSpeedScore() {
		return speedScore;
	}

	public void setSpeedScore(String speedScore) {
		this.speedScore = speedScore;
	}

	public String getFatigueScore() {
		return fatigueScore;
	}

	public void setFatigueScore(String fatigueScore) {
		this.fatigueScore = fatigueScore;
	}

	public String getTimeOfDayScore() {
		return timeOfDayScore;
	}

	public void setTimeOfDayScore(String timeOfDayScore) {
		this.timeOfDayScore = timeOfDayScore;
	}

}
