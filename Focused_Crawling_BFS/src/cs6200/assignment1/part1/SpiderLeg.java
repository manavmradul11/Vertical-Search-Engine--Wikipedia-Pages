package cs6200.assignment1.part1;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg
{
   
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private Set<String> links = new LinkedHashSet<String>();
    private List<String> storedLinks = new LinkedList<String>();
    private Document htmlDocument;


  
    public boolean crawl(String url,Set<String> pagesStored,Set<String> pagesChecked, int MAX_PAGES)
    {
    	
    	htmlDocument = getHTMLDocument(url);
        Elements linksOnPage = htmlDocument.select("a[href~=^/wiki/((?!:).)*$]");
        
        
        for(Element link : linksOnPage)
        {
        	if((pagesStored.size()+ this.storedLinks.size()) >= MAX_PAGES)
        	{
	        	break;
        	} 
        	
        	else if(pagesChecked.contains(link.absUrl("href")))
        	{}
        	
        	else if(storedLinks.contains(link.absUrl("href")))
        	{}
        	
        	else if((link.ownText().toLowerCase().contains("solar"))||(link.absUrl("href").toLowerCase().contains("solar")))
        	{   
        		
        		this.storedLinks.add(link.absUrl("href"));
        		System.out.println("\n**Visiting** Received web page at " + link.absUrl("href"));
        		System.out.println("found keyword in anchor text or Url");
        		
        	}
        	
        	else
        	{  
        		try
      	        {
      	          Thread.sleep(10);
      	
                } catch (InterruptedException ie) {
      	          ie.printStackTrace();
      	        }
        		
        		Document htmlDocument = getHTMLDocument(link.absUrl("href"));
        	   
		    	if(searchForWord("solar" , htmlDocument))
	    		{
    	    		this.storedLinks.add(link.absUrl("href"));
    	    		System.out.println("found keyword in text");
	    		}
        		
        		
        	}
        	
        	this.links.add(link.absUrl("href"));
        }
        
        
        return true;
    }


 
    public boolean searchForWord(String searchWord , Document htmlDocument)
    {
        // Defensive coding. This method should only be used after a successful crawl.
        if(htmlDocument == null)
        {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return false;
        }
        System.out.println("Searching for the word " + searchWord + "...");
        String bodyText = htmlDocument.body().text();
        return bodyText.toLowerCase().contains(searchWord.toLowerCase());
    }

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
    
    public Set<String> getLinks()
    {
        return this.links;
    }
    
    public List<String> getstoredLinks()
    {
        return this.storedLinks;
    }
    
   
}