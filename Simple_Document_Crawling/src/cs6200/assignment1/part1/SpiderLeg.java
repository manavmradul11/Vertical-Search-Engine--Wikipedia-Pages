package cs6200.assignment1.part1;

import java.io.IOException;
import java.util.LinkedHashSet;
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
    private Set<String> storedLinks = new LinkedHashSet<String>();
    private Set<String> storedAbsLinks = new LinkedHashSet<String>();
    private Set<String> removeAbsLinks = new LinkedHashSet<String>();
    private Document htmlDocument;


  
    public boolean crawl(String url,Set<String> pagesStored,Set<String> pagesToVisit, int MAX_PAGES)
    {
    	
    	htmlDocument = getHTMLDocument(url);
        Elements linksOnPage = htmlDocument.select("a[href~=^/wiki/((?!:)(?!(Main_Page)).(?!#))*$]");
        storedLinks.clear();
        
        for(Element link : linksOnPage)
        {
        	if((pagesStored.size() + this.storedLinks.size()) >= MAX_PAGES)
        	{
	        	break;
        	}
        	
        	else if(pagesToVisit.contains(link.absUrl("href")))
        	{
        		int urlsize = link.absUrl("href").substring(30).length();
        		
        		for(String baseUrl : pagesStored)
        		{
        			if(baseUrl.length()<urlsize)
        			{
        				
        			}
        			
        			else if(baseUrl.substring(0, urlsize).equals(link.absUrl("href").substring(30)))
        			{   
        				System.out.println("\n ************** \n");
        				String newBaseUrl= baseUrl+" "+url.substring(30);
        				this.removeAbsLinks.add(baseUrl);
        				this.storedLinks.add(newBaseUrl);
        			}
        			
        		}
        		pagesStored.removeAll(getremoveAbsLinks());
        	}
        	
        	
        	else
        	{
        		this.storedAbsLinks.add(link.absUrl("href"));
        		this.storedLinks.add(link.absUrl("href"));
        	}
        	
        	
        }
        
        
        return true;
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
    
    
    public Set<String> getstoredLinks()
    {
        return this.storedLinks;
    }
    
    public Set<String> getstoredAbsLinks()
    {
        return this.storedAbsLinks;
    }
    
    public Set<String> getremoveAbsLinks()
    {
        return this.removeAbsLinks;
    }
    
   
}