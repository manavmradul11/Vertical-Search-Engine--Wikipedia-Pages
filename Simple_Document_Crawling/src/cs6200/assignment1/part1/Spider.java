package cs6200.assignment1.part1;

import java.util.LinkedHashSet;
import java.util.Set;
import java.io.FileWriter;
import java.io.IOException;

public class Spider
{
  private static final int MAX_PAGES_TO_ADD = 1003;
  private Set<String> pagesVisited = new LinkedHashSet<String>();
  private Set<String> pagesStored = new LinkedHashSet<String>();
  private Set<String> pagesToVisit = new LinkedHashSet<String>();


  public void search(String url, String searchWord)
  {
      while(this.pagesStored.size() < MAX_PAGES_TO_ADD)
      {
          String currentUrl;
          SpiderLeg leg = new SpiderLeg();
          if(this.pagesToVisit.isEmpty())
          {
              currentUrl = url;
              this.pagesVisited.add(currentUrl);
              this.pagesStored.add(currentUrl.substring(30));
              
              
          }
          else
          {
              currentUrl = this.nextUrl();
          }
          
          
	      try
	      {
	          Thread.sleep(1);
	
          } catch (InterruptedException ie) {
	          ie.printStackTrace();
	        }
	      
	      
          leg.crawl(currentUrl,this.pagesStored,this.pagesToVisit,MAX_PAGES_TO_ADD); 
          
          this.pagesToVisit.addAll(leg.getstoredAbsLinks());
          this.pagesStored.removeAll(leg.getremoveAbsLinks());
          this.pagesStored.addAll(leg.getstoredLinks());
          System.out.println("\nAdded till now : " + this.pagesStored.size() + " web page(s)");
          
      }
      
      
      try
      {FileWriter writer = new FileWriter("output.txt"); 
      for(String str: this.pagesStored) {
        writer.write(str+"\n");
      }
      writer.close();
      }
      catch (IOException ex) {
    	    // complain to user
    	}
      System.out.println("\n**Done** Added " + this.pagesStored.size() + " web page(s)");
      
  }



  private String nextUrl()
  {
      String nextUrl;
      do
      {
          nextUrl = this.pagesToVisit.iterator().next();
          pagesToVisit.remove(pagesToVisit.iterator().next());
      } while(this.pagesVisited.contains(nextUrl));
      this.pagesVisited.add(nextUrl);
      return nextUrl;
  }
}

