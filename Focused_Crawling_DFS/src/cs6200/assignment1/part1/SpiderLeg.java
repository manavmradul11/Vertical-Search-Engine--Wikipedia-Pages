package cs6200.assignment1.part1;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg
{
    // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private  Links links = new Links();
    private Document htmlDocument;

    public Links crawl(String url,Links links,int depth, int MAX_PAGES)
    {
    	
    	
    	System.out.println("\n *****  Depth :" + (depth+1));
    	
    	if((depth+1) >= 5)
    	{
    		
    		return links;
    	}
    	
    	this.links.setCheckedLinks(links.getCheckedLinks());
        this.links.setStoredLinks(links.getStoredLinks());
    	htmlDocument = getHTMLDocument(url);
        Elements linksOnPage = htmlDocument.select("a[href~=^/wiki/((?!:).)*$]");
        
        
        for(Element link : linksOnPage)
        {
        	if((this.links.getStoredLinks().size()+ 1) > MAX_PAGES)
        	{
	        	break;
        	} 
        	
        	else if(this.links.getCheckedLinks().contains(link.absUrl("href")))
        	{
        	}
        	
        	
        	else if((link.ownText().toLowerCase().contains("solar"))||(link.absUrl("href").toLowerCase().contains("solar")))
        	{   
        		
        		this.links.getStoredLinks().add(link.absUrl("href"));
        		System.out.println("\n** Added web page at " + link.absUrl("href"));
        		System.out.println("found keyword in anchor text or Url");
        		System.out.println("Added:" + this.links.getStoredLinks().size() + " web pages");
        		this.links.getCheckedLinks().add(url);
        		this.links= crawl(link.absUrl("href"),this.links,depth,MAX_PAGES);
        		
        		
        	}
        	
        	else
        	{  
        		try
      	        {
      	          Thread.sleep(1);
      	
                } catch (InterruptedException ie) {
      	          ie.printStackTrace();
      	        }
        		
        		Document htmlDocument = getHTMLDocument(link.absUrl("href"));
        		System.out.println("\n** Added web page at " + link.absUrl("href"));
		    	if(searchForWord("solar" , htmlDocument))
	    		{
		    		this.links.getStoredLinks().add(link.absUrl("href"));
    	    		System.out.println("found keyword in text");
    	    		System.out.println("Added:" + this.links.getStoredLinks().size() + " web pages");
    	    		this.links.getCheckedLinks().add(url);
            		this.links= crawl(link.absUrl("href"),this.links,depth,MAX_PAGES);
            		
	    		}
		    	
		    	this.links.getCheckedLinks().add(link.absUrl("href"));
        	}
        	
        	
        }
        
        
        return this.links;
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
            
	        if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code
			                // indicating that everything is great.
			{
			}
			if(!connection.response().contentType().contains("text/html"))
			{
			System.out.println("**Failure** Retrieved something other than HTML");
			
			}
            
            return htmlDocument;
        }
        catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            return null;
        }
    }
    
   
}