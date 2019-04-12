package com.p.pojo;

import java.util.List;

public class SimilarFiles {

	private String fileEqualityProfile;
	private List<FileProfile> similarFileList;
	public String getFileEqualityProfile() {
		return fileEqualityProfile;
	}
	public void setFileEqualityProfile(String fileEqualityProfile) {
		this.fileEqualityProfile = fileEqualityProfile;
	}
	public List<FileProfile> getSimilarFileList() {
		return similarFileList;
	}
	public void setSimilarFileList(List<FileProfile> similarFileList) {
		this.similarFileList = similarFileList;
	}
	public SimilarFiles(String fileEqualityProfile,
			List<FileProfile> similarFileList) {
		super();
		this.fileEqualityProfile = fileEqualityProfile;
		this.similarFileList = similarFileList;
	}
	
}
