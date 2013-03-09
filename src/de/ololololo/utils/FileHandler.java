package de.ololololo.utils;

import de.ololololo.*;
import java.util.ArrayList; 

public class FileHandler {
	
	//Public Methods
	public ArrayList<Task> getData() {
		return new ArrayList<Task>();
	}
	
	public String writeData(ArrayList<Task> data) {
		if (true) { //write successful
			return null;
		} else {
			//On error
			return "Error Message";
		}
	}
}
