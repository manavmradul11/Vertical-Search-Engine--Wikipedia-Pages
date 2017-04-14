package cs6200.assignment2;

import java.util.LinkedHashSet;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Spider
{
	private static final String USER_AGENT =
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private Set<String>  setOfDownloadedLinks = new LinkedHashSet<String>();
	


	  public void computePR(String fileName)
	  {
		  
		// Read Downloaded links from Assignment ----------------------------------------------------------------
		  try
		  {
		      String line = null;
		      BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
		      while ((line = bufferedReader.readLine()) != null)
		      {
		    	  setOfDownloadedLinks.add(line);
		      }
		      bufferedReader.close();
		  }
		  catch (IOException ex) {
	    	    // reading error
	    	}
		
		// Create corpus by parsing and tokenizing ----------------------------------------------------------------
		  for(String link : setOfDownloadedLinks)
		  {
			    Document html = getHTMLDocument(link);
			    Elements paragraphs = html.select(".mw-content-ltr p");
			    String title = html.select("h1").text();
			    Element firstParagraph = paragraphs.first();
			    Element lastParagraph = paragraphs.last();
			    try
		        {
			    	String desktopPath = System.getProperty ("user.home") + "/Documents/EclipseWorkSpace/Task1-GenerateCorpus/corpus/";
			
			    	
			    	Element p;
				    FileWriter writer = new FileWriter(desktopPath+title.replaceAll("\\s+", "")+".txt"); 
				    p = firstParagraph;
				    String regex="(\\[\\d*\\])|(,)|(\\.(?!\\d+))|(\\(|\\)|\\!|/|”|“|\"|;|\\:|^|\\*|\\[|\\]|`|~|=)";
				    writer.write(p.text().toLowerCase().replaceAll(regex,"")+"\n");
				    
				    int i = 1;
			        while (p != lastParagraph) {
				        p = paragraphs.get(i);
				        writer.write(p.text().toLowerCase().replaceAll(regex,"")+"\n");   
				        i++;
				    }
			        writer.close();
		        }
		        catch (IOException ex) {
		      	    // complain to user
		      	}
			    
		  }
         
	  }
	  
	  
  // Get HTML Document using JSOUP Library----------------------------------------------------------------
	  public Document getHTMLDocument(String url)
	  {
	  	try
	      {
	      	
	          Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
	          Document htmlDocument = connection.get();
	          
		        if(connection.response().statusCode() == 200)
				{
				System.out.println("\n** Received web page at " + url);
				}
				if(!connection.response().contentType().contains("text/html"))
				{
				System.out.println("**  Retrieved something other than HTML");
				
				}
	          
	          return htmlDocument;
	      }
	      catch(IOException ioe)
	      {
	        
	          return null;
	      }
	  }
}

