package base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Folder implements Comparable<Folder>, Serializable {

	private ArrayList<Note> notes;
	private String name;

	public Folder(String name) {
		this.name = name;
		notes = new ArrayList<Note>();
	}

	public void addNote(Note note) {
		notes.add(note);
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<Note> getNotes() {
		return this.notes;
	}

	@Override
	public String toString() {
		int nText = 0;
		int nImage = 0;
		for (Note note : notes) {
			if (note instanceof TextNote)
				nText++;
			else if (note instanceof ImageNote)
				nImage++;
		}
		return this.name + ":" + nText + ":" + nImage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Folder other = (Folder) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Folder o) {
		return this.name.compareTo(o.name);
	}

	public void sortNotes() {
		Collections.sort(notes);
	}

	public List<Note> searchNotes(String keywords) {
		List<Note> sortedNotes = new ArrayList<>();
		String[] keys = keywords.split(" ");
		// check is the boolean that allows to add the current note in the
		// searching list
		boolean checkTitle = true;
		boolean checkContent = false;
		for (Note note : notes) {
			if (note instanceof TextNote)
				checkContent = true;
			for (int i = 0; i < keys.length; i++) {
				//we should never be in this case if the keywords are in the good format
				if (keys[i].toLowerCase().equals("or")) {
					System.err.println("\n or keyword problem 1 \n" + i);
					System.exit(-1);
				} else if (i + 2 < keys.length && keys[i + 1].toLowerCase().equals("or")) {
					//we should never be in this case if the keywords are in the good format
					if (keys[i + 2].equals("or")) {
						System.err.println("\n or keyword problem 2 \n");
						System.exit(-1);
					} 
					// case where we have key1 OR key2 ; variable "i" is currently on key1 at this step
					else {
						checkTitle = checkTitle && (note.getTitle().toLowerCase().contains(keys[i].toLowerCase())
								|| note.getTitle().toLowerCase().contains(keys[i + 2].toLowerCase()));
						if (note instanceof TextNote) 
							checkContent = checkContent && (((TextNote)note).getContent().toLowerCase().contains(keys[i].toLowerCase()) || ((TextNote)note).getContent().toLowerCase().contains(keys[i+2].toLowerCase()));						 
						i = i + 2;
					}
					// case where we have key1 key2 ; variable "i" is currently on key1 at this step
				} else {
					checkTitle = checkTitle && note.getTitle().toLowerCase().contains(keys[i].toLowerCase());
					if (note instanceof TextNote) 
						checkContent = checkContent && ((TextNote)note).getContent().toLowerCase().contains(keys[i].toLowerCase());
				}
			}
			if (checkTitle || checkContent)
				sortedNotes.add(note);
		}
		return sortedNotes;
	}
}
