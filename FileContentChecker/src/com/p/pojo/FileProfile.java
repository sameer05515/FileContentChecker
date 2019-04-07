package com.p.pojo;

public class FileProfile {
	
	private String filePath;
	private String fileEqualityProfile;
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileEqualityProfile() {
		return fileEqualityProfile;
	}
	public void setFileEqualityProfile(String fileEqualityProfile) {
		this.fileEqualityProfile = fileEqualityProfile;
	}
	public FileProfile(String filePath) {
		super();
		this.filePath = filePath;
	}
	public FileProfile(String filePath, String fileEqualityProfile) {
		super();
		this.filePath = filePath;
		this.fileEqualityProfile = fileEqualityProfile;
	}
	@Override
	public String toString() {
		return "FileProfile [filePath=" + filePath + ", fileEqualityProfile="
				+ fileEqualityProfile + "]";
	}

}
