package org.runelive.client.graphics.rsinterface;

import org.runelive.client.Client;
import org.runelive.client.RSInterface;

/**
* Note Interface Handling
* @author Greg
*/
public class NotesTab {

	public final static int NOTE_INTERFACE_ID = 65230, NOTE_SELECT_ID = 65229, NOTE_TAB_ID = 65228, NOTE_COLOUR_ID = 65211;
	/*
	 * Maximum character length
	 */
	private final int NOTE_LENGTH = 30;
	/*
	 * Notes selection click
	 */
	private int[] notes = new int[90];
	
	/*
	 * Total notes counter
	 */
	private int numberOfNotes = 0;

	/*
	 * Maximum allowed notes
	 */
	public final static int maxNotes = 30;
	
	/*
	 * The note id which is currently selected
	 */
	private static int selectedNote = 0;

	/*
	 * The current note being editted
	 */
	private int editNote = 0;

	/*
	 * The current note being coloured
	 */
	private int colourNote = 0;
	
	/*
	 * wrapString
	 * Splits string into separate lines if over a set length.
	 * Splits based words wrapping
	 * TODO test and implement IF no deliminator.
	 */
	private String[] wrapString(String text, String deliminator, int maxLength) {
		int size = text.length()/maxLength + (text.length() % maxLength == 0 ? 0 : 1);
		int[] splitPosition = new int[size];
		String[] lines = new String[size];
		int prev = 0;
		for(int i = 0; i < size; i++) {
			String message = i == 0 ? text : text.substring(prev, text.length());
			int length = 0;
			for(String section: message.split(deliminator, -1)){
				if(section.length() > maxLength) {
					splitPosition[i] = maxLength;
					length = maxLength;
				} else if(length + section.length() > maxLength) {
					splitPosition[i] = length;
					break;
				} else {
					length += section.length() + 1;
				}
			}
			if(splitPosition[i] == 0) {
				splitPosition[i] = message.length();
			}
			lines[i] = text.substring(prev, prev + splitPosition[i]);
			prev += length;
		}
		boolean empty = true;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i] != null) {
				empty = false;
				break;
			}
		}
		return (empty) ? new String[] { text } : lines;
	}
	
	/*
	 * Counts the number of lines a note is displayed on
	 */
	private int countNoteLines(int noteId) {
		int size = 0;
		int note = notes[noteId-1];
		if(note == 0)return 0;
		for(int i = 0; i < notes.length; i++) {
			if(notes[i] == note) {
				size++;
			}
		}
		return size;
	}
	
	private int getNoteLine(int offsetId) {
		int note = notes[offsetId];
		if(note == 0)return 0;
		for(int i = 0; i < notes.length; i++) {
			if(notes[i] == note) {
				return i+1;
			}
		}
		return 0;
	}
	
	/*
	 * Returns id of a note
	 */
	private int getNoteId(int offsetId) {
		int count = 0;
		int prev = -1;
		for(int i = 0; i < notes.length; i++) {
			if(notes[i] == offsetId) {
				return count;
			}
			if(notes[i] != prev) {
				count++;
				prev = notes[i];
			}
		}
		return -1;
	}
	
	/*
	 * Returns the full text of a note
	 */
	private String getNoteText(int interfaceId) {
		
		StringBuilder response = new StringBuilder();
		int numb = getNoteLine(editNote - NOTE_INTERFACE_ID);
		if(numb == 0)return null;
		int note = notes[numb-1];
		if(note == 0)return null;
		for(int i = 0; i < notes.length; i++) {
			if(notes[i] == note) {
				response.append(RSInterface.interfaceCache[NOTE_INTERFACE_ID + i].message);
			}
		}
		return response.toString();
	}

	/*
	 * Returns the colour of a note
	 */
	private int getNoteColour(int interfaceId) {
		int numb = getNoteLine(editNote - NOTE_INTERFACE_ID);
		if(numb == 0)return 0;
		int note = notes[numb-1];
		return RSInterface.interfaceCache[NOTE_INTERFACE_ID + note].textColor;
	}
	/*
	 * Returns number of notes
	 */
	private int countNotes() {
		return numberOfNotes;
	}
	
	/*
	 * Updates the note counter
	 */
	public void updateNoteAmounts() {
		RSInterface.interfaceCache[65200].message = countNotes() >= 1 ? "" : "No notes";
		RSInterface.interfaceCache[65203].message = "Notes (" + countNotes() + "/" + maxNotes + ")";
	}
	
	/*
	 * Adds a note to the end of the list.
	 */
	public void addNote(Client client, String text) {
		addNote(client, text, 14064640, true);
	}
	
	public void addNote(Client client, String text, int colour, boolean save) {
		if(countNotes() == maxNotes) {
			//Not enough space.
			client.pushMessage("Your notes tab is full.", 0, "");
			return;
		}
		String[] lines = wrapString(text, " ", NOTE_LENGTH);
		int freeSpace = 0, spaces = 0;
		for(int i = 0; i < notes.length; i++) {
			if(notes[i] == 0) {
				if(freeSpace == 0)freeSpace = i+1;
				spaces++;
			}
		}
		if(lines.length > spaces || freeSpace == 0) {
			//Not enough space.
			client.pushMessage("Your notes tab is full, make more room by deleting a note.", 0, "");
			return;
		}
		if(lines.length > 1) {
			for(int i = 0; i < lines.length; i++) {
				RSInterface.interfaceCache[NOTE_INTERFACE_ID + (freeSpace-1) + i].message = lines[i];
				RSInterface.interfaceCache[NOTE_INTERFACE_ID + (freeSpace-1) + i].textColor = colour;
				notes[(freeSpace-1) + i] = freeSpace;
			}
		} else {
			RSInterface.interfaceCache[NOTE_INTERFACE_ID + (freeSpace-1)].message = text;
			RSInterface.interfaceCache[NOTE_INTERFACE_ID + (freeSpace-1)].textColor = colour;
			notes[(freeSpace-1)] = freeSpace;
		}
		numberOfNotes += 1;
		updateNoteAmounts();
		if(save)Client.saveNote(numberOfNotes-1, text);
	}
	
	/*
	 * Sets the select bar over a note
	 */
	public void selectNote(int interfaceId) {
		if(interfaceId == -1) {
			deselectNotes();
			return;
		}
		int numb = getNoteLine(interfaceId - NOTE_INTERFACE_ID);
		if(numb == 0)
			return;
		int size = countNoteLines(numb);
		if(size > 0) {
			int note = notes[numb-1];
			selectedNote = numb;
			RSInterface.interfaceCache[NOTE_TAB_ID].childY[0] = RSInterface.interfaceCache[NOTE_TAB_ID].childY[note] - (note == 0 ? 0 : 1);
			RSInterface.interfaceCache[NOTE_SELECT_ID].height = 15 * size;
		} else {
			return;
		}
	}
	
	/*
	 * Hides select bar
	 */
	public static void deselectNotes() {
		selectedNote = 0;
		RSInterface.interfaceCache[NOTE_SELECT_ID].height = 0;
	}
	
	/*
	 * Opens up note editor
	 */
	private void editNote(Client client, int interfaceId) {
		int numb = getNoteLine(interfaceId - NOTE_INTERFACE_ID);
		if(numb == 0)
			return;
		editNote = interfaceId;
		String str = getNoteText(interfaceId);
		client.editNote(str);
	}

	/*
	 * Opens up colour picker/Colours a note
	 */
	public void colourNote(int interfaceId) {
		if (interfaceId == NOTE_COLOUR_ID) {
			return;
		}
		if(interfaceId == -1) {
			colourNote = 0;
			RSInterface.interfaceCache[NotesTab.NOTE_COLOUR_ID].interfaceShown = true;
		} else if(colourNote == 0) {
			int numb = getNoteLine(interfaceId - NOTE_INTERFACE_ID);
			if(numb == 0)
				return;
			colourNote = interfaceId;
			RSInterface.interfaceCache[NotesTab.NOTE_COLOUR_ID].interfaceShown = false;
		} else {
			int numb = getNoteLine(colourNote - NOTE_INTERFACE_ID);
			if(numb == 0)
				return;
			int note = notes[numb-1];
			if(note == 0)return;
			int colour = RSInterface.interfaceCache[interfaceId - 1].textColor;
			for(int i = 0; i < notes.length; i++) {
				if(notes[i] == note) {
					RSInterface.interfaceCache[NOTE_INTERFACE_ID + i].textColor = colour;
				}
			}
			//Save
			Client.saveNoteColour(getNoteId(note), colour);
			//Close interface
			RSInterface.interfaceCache[NotesTab.NOTE_COLOUR_ID].interfaceShown = true;
			colourNote = 0;
		}
	}
	
	/*
	 * Inserts a line below
	 */
	private void insertBelowLine(int offsetId) {
		int spaces = 0;
		for(int i = 0; i < notes.length; i++) {
			if(notes[i] == 0) {
				spaces++;
			}
		}
		
		if(spaces <= 0) {
			//No spaces.
			return;
		}
		
		for(int i = notes.length-1; i > offsetId; i--) {
			notes[i] = notes[i-1] != 0 ? notes[i-1] + 1 : 0;
		}

		for(int i = notes.length-1; i > offsetId; i--) {
			RSInterface.interfaceCache[NOTE_INTERFACE_ID + i].message = RSInterface.interfaceCache[NOTE_INTERFACE_ID + (i-1)].message;
			RSInterface.interfaceCache[NOTE_INTERFACE_ID + i].textColor = RSInterface.interfaceCache[NOTE_INTERFACE_ID + (i-1)].textColor;
		}
		notes[offsetId] = notes[offsetId-1];
		RSInterface.interfaceCache[NOTE_INTERFACE_ID + offsetId].message = "";
		RSInterface.interfaceCache[NOTE_INTERFACE_ID + offsetId].textColor = RSInterface.interfaceCache[NOTE_INTERFACE_ID + offsetId - 1].textColor;
	}
	
	/*
	 * Deletes a line
	 */
	private void deleteLine(int offsetId) {
		if(offsetId == notes.length) {
			for(int i = offsetId-1; i < notes.length; i++) {
				notes[i] = 0;
			}
	
			for(int i = offsetId-1; i < notes.length; i++) {
				RSInterface.interfaceCache[NOTE_INTERFACE_ID + i].message = "";
				RSInterface.interfaceCache[NOTE_INTERFACE_ID + i].textColor = 14064640;
			}
		} else {
			for(int i = offsetId-1; i < notes.length-1; i++) {
				notes[i] = notes[i+1] != 0 ? notes[i+1] - 1 : 0;
			}
	
			for(int i = offsetId-1; i < notes.length-1; i++) {
				RSInterface.interfaceCache[NOTE_INTERFACE_ID + i].message = RSInterface.interfaceCache[NOTE_INTERFACE_ID + (i+1)].message;
				RSInterface.interfaceCache[NOTE_INTERFACE_ID + i].textColor = RSInterface.interfaceCache[NOTE_INTERFACE_ID + (i+1)].textColor;
			}
		}
	}

	/*
	 * Deletes an entire note
	 */
	private void deleteNote(int offsetId, int size) {
		int noteId = getNoteId(offsetId);
		deselectNotes();
		if(offsetId == (notes.length-2)) {
			for(int i = offsetId-1; i < notes.length; i++) {
				notes[i] = 0;
			}
			
			for(int i = offsetId-1; i < notes.length; i++) {
				RSInterface.interfaceCache[NOTE_INTERFACE_ID + i].message = "";
				RSInterface.interfaceCache[NOTE_INTERFACE_ID + i].textColor = 14064640;
			}
		} else {
			for(int i = offsetId-1; i < notes.length-size; i++) {
				notes[i] = notes[i+size] != 0 ? notes[i+size] - size : 0;
			}
	
			for(int i = offsetId-1; i < notes.length-size; i++) {
				RSInterface.interfaceCache[NOTE_INTERFACE_ID + i].message = RSInterface.interfaceCache[NOTE_INTERFACE_ID + (i + size)].message;
				RSInterface.interfaceCache[NOTE_INTERFACE_ID + i].textColor = RSInterface.interfaceCache[NOTE_INTERFACE_ID + (i + size)].textColor;
			}
		}
		//Save
		Client.saveNoteDelete(noteId);
		selectNote(NOTE_INTERFACE_ID + offsetId - (offsetId > 1 ? 2 : 1));
		numberOfNotes -= 1;
		updateNoteAmounts();
	}
	
	/*
	 * Handles note edits
	 */
	public void setEdittedNote(String text, int interfaceId) {
		editNote = interfaceId;
		setEdittedNote(text);
	}
	
	public void setEdittedNote(String text) {
		int numb = getNoteLine(editNote - NOTE_INTERFACE_ID);
		if(numb == 0)
			return;
		int currentSize = countNoteLines(numb);
		String[] lines = wrapString(text, " ", NOTE_LENGTH);
		int newSize = lines.length;
		if(currentSize != newSize) {
			if(newSize > currentSize) {
				int dif = newSize - currentSize;
				for(int i = 0; i < dif; i++) {
					insertBelowLine(numb + (newSize - (dif+1)) + i);
				}
			}
			if(currentSize > newSize) {
				int dif = currentSize - newSize;
				for(int i = 0; i < dif; i++) {
					deleteLine((numb + newSize + dif) - (i + 1));
				}
			}
		}
		deselectNotes();
		for(int i = 0; i < newSize; i++) {
			RSInterface.interfaceCache[NOTE_SELECT_ID + numb + i].message = lines[i];
		}
		Client.saveNote(getNoteId(numb), text);
	}
	
	public void deleteNote() {
		int offsetId = selectedNote;
		if(offsetId == 0)
			return;
		int lines = countNoteLines(offsetId);
		deleteNote(offsetId, lines);
	}

	public void deleteNotes() {
		deselectNotes();
		for(int i = 0; i < notes.length; i++) {
			notes[i] = 0;
		}

		for(int i = 0; i < notes.length; i++) {
			RSInterface.interfaceCache[NOTE_INTERFACE_ID + i].message = "";
			RSInterface.interfaceCache[NOTE_INTERFACE_ID + i].textColor = 14064640;
		}
		Client.saveNoteDeleteAll();
		numberOfNotes = 0;
		updateNoteAmounts();
	}
	
	public void deleteNote(int interfaceId) {
		int offsetId = getNoteLine(interfaceId - NOTE_INTERFACE_ID);
		if(offsetId == 0)
			return;
		int lines = countNoteLines(offsetId);
		deleteNote(offsetId, lines);
	}
	
	/*
	 * Handles note interaction
	 */
	public void handleButtons(Client client, int interfaceId, int actionId) {
		// TODO Auto-generated method stub
		switch(actionId) {
		case 4:
			selectNote(interfaceId);
			break;
		case 3:
			editNote(client, interfaceId);
			break;
		case 2:
			colourNote(interfaceId);
			break;
		case 1:
			deleteNote(interfaceId);
			break;
		}
	}

}
