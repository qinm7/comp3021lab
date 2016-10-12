package base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class TextNote extends Note {

	private String content;

	public TextNote(String title) {
		super(title);
	}
	
	/**
	 * load a TextNote from File f
	 *
	 * the tile of the TextNote is the name of the file
	 * the content of the TextNote is the content of the file
	 *
	 * @param File f
	 */
	public TextNote(File f) {
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());
	}
	
	public TextNote(String title, String content) {
		this(title);
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}
	
	/**
	 * get the content of a file
	 *
	 * @param absolutePath of the file
	 * @return the content of the file
	 */
	private String getTextFromFile(String absolutePath) {
		String result = "";
		try {
			InputStream in = new FileInputStream(new File(absolutePath));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) 
				result = result + line;
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * export text note to file
	 *
	 *
	 * @param pathFolder path of the folder where to export the note
	 * the file has to be named as the title of the note with extension ".txt"
	 *
	 * if the tile contains white spaces " " they has to be replaced with underscores "_"
	 *
	 *
	 */
	public void exportTextToFile(String pathFolder) {
		try {
			String title = this.getTitle().replaceAll(" ","_");		
			File file = new File( pathFolder + File.separator + title + ".txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter out = new BufferedWriter(fw);
			out.write(this.content);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Character countLetters(){
		HashMap<Character,Integer> count = new HashMap<Character,Integer>();
		String a = this.getTitle() + this.getContent();
		int b = 0;
		Character r = ' ';
		for (int i = 0; i < a.length(); i++) {
			Character c = a.charAt(i);
			if (c <= 'Z' && c >= 'A' || c <= 'z' && c >= 'a') {
				if (!count.containsKey(c)) {
					count.put(c, 1);
				} else {
					count.put(c, count.get(c) + 1);
					if (count.get(c) > b) {
						b = count.get(c);
						r = c;
					}
				}
			}
		}
		return r;
	}
}	
