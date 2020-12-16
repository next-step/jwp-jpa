package jpa.dto;

public class FavoriteDto {
	String departureName;
	String arrivalName;

	public FavoriteDto(String departureName, String arrivalName) {
		this.departureName = departureName;
		this.arrivalName = arrivalName;
	}

	public String getDepartureName() {
		return departureName;
	}

	public String getArrivalName() {
		return arrivalName;
	}
}
