package com.p;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.p.pojo.FileProfile;
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
							// System.out.println("Files " + fp1.getFilePath()
							// + " and " + fp2.getFilePath()
							// + " are Equal :- " + same);

							// create instance of Random class

							// Generate random integers in range 0 to 999
							// String uniqueStr = System.currentTimeMillis() +
							// "_"
							// + rand.nextInt(1000);
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

			System.out.println("\n\n>>>>>>>>>>>>>>>>>");
			for (FileProfile fp1 : fileProfileList) {
				System.out.println(fp1);
			}

			// String[] files = {
			// "C:/tutorials/ebooks-home-pc/AI/Artificial Intelligence A Modern Approach  3rd Edition.pdf",
			// "C:/tutorials/ebooks-home-pc/AI/Artificial Intelligence A Modern Approach 3rd Edition.pdf"
			// };
			// System.out.println("Files "
			// + files[0]
			// + " and "
			// + files[1]
			// + " are Equal :- "
			// + FileUtils.contentEquals(new File(files[0]), new File(
			// files[1])));
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
