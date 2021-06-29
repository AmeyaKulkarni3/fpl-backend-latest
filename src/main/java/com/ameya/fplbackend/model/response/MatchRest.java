package com.ameya.fplbackend.model.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MatchRest {
	
	private int matchNumber;
	private String team1;
	private String team2;
	private LocalDate matchDate;
	private LocalTime matchTime;
	private String matchVenue;
	private String result;
	private List<MatchNominationRest> matchNominationRest;

	public int getMatchNumber() {
		return matchNumber;
	}
	public void setMatchNumber(int matchNumber) {
		this.matchNumber = matchNumber;
	}
	public String getTeam1() {
		return team1;
	}
	public void setTeam1(String team1) {
		this.team1 = team1;
	}
	public String getTeam2() {
		return team2;
	}
	public void setTeam2(String team2) {
		this.team2 = team2;
	}
	public LocalDate getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(LocalDate matchDate) {
		this.matchDate = matchDate;
	}
	public LocalTime getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(LocalTime matchTime) {
		this.matchTime = matchTime;
	}
	public String getMatchVenue() {
		return matchVenue;
	}
	public void setMatchVenue(String matchVenue) {
		this.matchVenue = matchVenue;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<MatchNominationRest> getMatchNominationRest() {
		return matchNominationRest;
	}
	public void setMatchNominationRest(List<MatchNominationRest> matchNominationRest) {
		this.matchNominationRest = matchNominationRest;
	}
}
