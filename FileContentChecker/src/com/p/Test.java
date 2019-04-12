package com.p;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.p.pojo.FileProfile;
import com.p.pojo.SimilarFiles;
import com.p.util.FileUtils;

public class Test {

	public static void main(String[] args) {
		
		
		try {
			List<File> fileList = FileUtils.getFileList(new File(
					"C:/tutorials/ebooks-home-pc"));
			Random rand = new Random();
			List<FileProfile> fileProfileList = new ArrayList<FileProfile>();
			for (File f : fileList) {

				// System.out.println(f.getAbsolutePath());
				if (FileUtils.hasExtension(f.getName(), ".pdf")) {
					fileProfileList.add(new FileProfile(f.getAbsolutePath()));
				}

			}

			PrintStream ps = new PrintStream(new File("C:/tutorials/diff.txt"));

			for (FileProfile fp1 : fileProfileList) {
				for (FileProfile fp2 : fileProfileList) {

					boolean same = false;
					if (!fp1.getFilePath().equals(fp2.getFilePath())) {
						if (fp1.getFileEqualityProfile() != null
								&& fp2.getFileEqualityProfile() != null
								&& fp1.getFileEqualityProfile().equals(
										fp2.getFileEqualityProfile())) {
							same = true;
							continue;

						}

						same = same
								|| FileUtils.contentEquals(
										new File(fp1.getFilePath()), new File(
												fp2.getFilePath()));
						if (same) {

							String uniqueStr = generateUniqueString(rand,
									fp1.getFileEqualityProfile(),
									fp2.getFileEqualityProfile());
							fp1.setFileEqualityProfile(uniqueStr);
							fp2.setFileEqualityProfile(uniqueStr);
							System.out.println(fp1.toString() + " == "
									+ fp2.toString());
							ps.println(fp1.toString() + " == " + fp2.toString());
						}
					}

				}
			}

			Map<String, List<FileProfile>> map=new HashMap<String, List<FileProfile>>();
			System.out.println("\n\n>>>>>>>>>>>>>>>>>");
			for (FileProfile fp1 : fileProfileList) {
				if(fp1.getFileEqualityProfile()!=null&&!fp1.getFileEqualityProfile().equals("")){
					List<FileProfile> list=map.get(fp1.getFileEqualityProfile());
					if(list==null||list.size()==0){
						list=new ArrayList<FileProfile>();
					}
					list.add(fp1);
					map.put(fp1.getFileEqualityProfile(), list);
				}
				
				//System.out.println(fp1);
			}
			
			PrintStream ps1 = new PrintStream(new File("C:/tutorials/diff1.txt"));
			List<SimilarFiles> similarFiles=new ArrayList<SimilarFiles>();
			for(String key:map.keySet()){
				List<FileProfile> list=map.get(key);
				ps1.println("===================================="
						+ "\n"+key);
				for(FileProfile fp1 : list){
					ps1.println(fp1);
				}
				similarFiles.add(new SimilarFiles(key, list));
			}
			
			PrintStream ps2 = new PrintStream(new File("C:/tutorials/diff2.json"));
			Gson gson = new Gson(); 
			String json = gson.toJson(similarFiles);
			ps2.println(json);

			ps.close();
			ps1.close();
			ps2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String generateUniqueString(Random rand, String a, String b) {

		System.out.println("a == " + a + " , b== " + b);
		String uniqueStr = System.currentTimeMillis() + "_"
				+ rand.nextInt(1000);

		if (a == null && b == null) {
			return uniqueStr;
		} else if (a == null && b != null) {
			return b;
		} else if (a != null && b == null) {
			return a;
		}

		return uniqueStr;
	}
}
