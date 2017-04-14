package cs6200.assignment2;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Spider
{
	private ArrayList<String> term = new ArrayList<String>();
	private ArrayList<String> docId = new ArrayList<String>();
	private ArrayList<String> tf = new ArrayList<String>();
	private ArrayList<Integer> df = new ArrayList<Integer>();


	  public void computePR()
	  {
		  	String desktopPath = System.getProperty ("user.home") + "/Documents/EclipseWorkSpace/Task3-Generate Tables/";

		 // Read each text file of the corpus ----------------------------------------------------------------
			  try
			  {
			      String line = null;
			      BufferedReader bufferedReader = new BufferedReader(new FileReader(desktopPath+"term-{docs}-tf.txt"));
			      while ((line = bufferedReader.readLine()) != null)
			      {
			    	  String[] words = line.split("\t\t");
			    	  
			    	  
			    	  
			    	  term.add(words[0]);
			    	  docId.add(words[1]);
			    	  tf.add(words[3]);
			    	  df.add(words[2].length());
			    	  
			    	  
			    	  
			      }
			      bufferedReader.close();
			  }
			  catch (IOException ex) {
		    	    // reading error
		    	}
		
	  }
	  
	  
	  
	  
	  
	  
	  
}

