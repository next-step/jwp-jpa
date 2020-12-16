package jpa.dto;

public class FavoriteDto {
	String departureName;
	String arrivalName;

	public FavoriteDto(String departureName, String arrivalName) {
		checkDuplicateStation(departureName, arrivalName);
		this.departureName = departureName;
		this.arrivalName = arrivalName;
	}

	private void checkDuplicateStation(String departureName, String arrivalName) {
		if (departureName.equals(arrivalName)) {
			throw new IllegalArgumentException("같은 역은 즐겨찾기에 추가 할 수 없습니다.");
		}
	}

	public String getDepartureName() {
		return departureName;
	}

	public String getArrivalName() {
		return arrivalName;
	}
}
