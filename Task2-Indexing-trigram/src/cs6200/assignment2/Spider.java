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
	private ArrayList<Integer> tf = new ArrayList<Integer>();


	  public void computePR()
	  {
		  	String desktopPath = System.getProperty ("user.home") + "/Documents/EclipseWorkSpace/Task2-Indexing-trigram/corpus1/";

			File folder = new File(desktopPath);
			File[] listOfFiles = folder.listFiles();
			
			for (File file : listOfFiles)
			{
			    if (file.isFile())
			    {
			    	ArrayList<String> subterm = new ArrayList<String>();
			    	ArrayList<Integer> subtf = new ArrayList<Integer>();
			    	
			    	// Read each text file of the corpus ----------------------------------------------------------------
					  try
					  {
					      String line = null;
					      BufferedReader bufferedReader = new BufferedReader(new FileReader(desktopPath+file.getName()));
					      while ((line = bufferedReader.readLine()) != null)
					      {
					    	  String[] words = line.split(" ");
					    	  ArrayList<String> newWords = new ArrayList<String>();
					    	  for(int i=0;i<words.length-2;i++)
					    	  {
					    		  String trigram= words[i]+" "+words[i+1]+" "+words[i+2];
					    		  newWords.add(trigram);
					    	  }
					    	  for(String word : newWords)
							  {
					    		  if(subterm.contains(word))
					    		  {  int i= subterm.indexOf(word);
					    			 subtf.set(i, subtf.get(i)+1);
					    			 subterm.indexOf(word); 
					    		  }
					    		  
					    		  else
					    		  {
					    			  subterm.add(word);
					    			  subtf.add(1);
					    		  }
					    		  
							  }
					      }
					      bufferedReader.close();
					  }
					  catch (IOException ex) {
				    	    // reading error
				    	}
					  
					  
					  for(int i=0;i<subterm.size();i++)
					  {		  
						  term.add(subterm.get(i));
						  docId.add(file.getName());
						  tf.add(subtf.get(i));
					  }
					  
			    }
			}
			
			for(int i=0;i<term.size();i++)
			  {		
				  String temp=term.remove(i); 
				  
					if(term.contains(temp))
					{	
						int firstindex=term.indexOf(temp);		
						docId.set(firstindex, docId.get(firstindex)+" "+docId.remove(i));
						tf.set(firstindex, tf.get(firstindex)+tf.remove(i));
					}
					else
					{
						term.add(i, temp);
					}
					
					System.out.println(i+"/"+term.size()+"\n");
			  }
			
			
			 try
		      {FileWriter writer = new FileWriter("output.txt"); 
		      for(int i=0;i<term.size();i++) {
		        writer.write(term.get(i)+"\t\t"+docId.get(i)+"\t\t"+tf.get(i)+"\n");
		      }
		      writer.close();
		      }
		      catch (IOException ex) {
		    	    // complain to user
		    	}
		
	  }
	  
	  
}

