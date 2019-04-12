package com.p;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.p.pojo.SimilarFiles;

public class GsonExample {

	public static void main(String[] args) {

		try {
			List<SimilarFiles> staff = createDummyObject();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			PrintStream ps2 = new PrintStream(new File(
					"C:/tutorials/diff2.json"));

			String json = gson.toJson(staff).replaceAll("\\\\", "/");
			ps2.println(json);

			ps2.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

	private static List<SimilarFiles> createDummyObject() {

		List<SimilarFiles> staff = null;
		try {
			Gson gson = new Gson();

			// 1. JSON to Java object, read it from a file.

			staff = gson.fromJson(new FileReader("C:/tutorials/diff2.json"),
					new TypeToken<List<SimilarFiles>>() {
					}.getType());

		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {

			e.printStackTrace();
		}

		return staff;

	}

}