package pdp.nazarevych.concurency;

public class Result implements Comparable<Result> {
	private String fileName;
	private Integer number;
	
	public Result(String fileName, Integer number) {
		this.fileName = fileName;
		this.number = number;
	}

	@Override
	public int compareTo(Result result) {
		//return number.compareTo(result.number);
		if (number.equals(result.number)){
			return result.fileName.compareTo(fileName);
		} else {
			return result.number.compareTo(number);
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "fileName=" + fileName + ", number=" + number ;
	}
	
	

}
