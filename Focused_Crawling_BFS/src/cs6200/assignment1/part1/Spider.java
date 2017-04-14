package cs6200.assignment1.part1;

import java.util.LinkedHashSet;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Spider
{
  private static final int MAX_PAGES_TO_ADD = 1000;
  private Set<String> pagesVisited = new LinkedHashSet<String>();
  private Set<String> pagesStored = new LinkedHashSet<String>();
  private Set<String> pagesChecked = new LinkedHashSet<String>();
  private List<String> pagesToVisit = new LinkedList<String>();


  public void search(String url, String searchWord)
  {
      while(this.pagesStored.size() < MAX_PAGES_TO_ADD)
      {
          String currentUrl;
          SpiderLeg leg = new SpiderLeg();
          if(this.pagesToVisit.isEmpty())
          {
              currentUrl = url;
              this.pagesVisited.add(url);
              this.pagesStored.add(url);
              
              
          }
          else
          {
              currentUrl = this.nextUrl();
          }
          
          
	      try
	      {
	          Thread.sleep(1000);
	
          } catch (InterruptedException ie) {
	          ie.printStackTrace();
	        }
	      
	      
          leg.crawl(currentUrl,this.pagesStored,this.pagesChecked,MAX_PAGES_TO_ADD); 
          
          this.pagesChecked.addAll(leg.getLinks());
          this.pagesToVisit.addAll(leg.getstoredLinks());
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
          nextUrl = this.pagesToVisit.remove(0);
      } while(this.pagesVisited.contains(nextUrl));
      this.pagesVisited.add(nextUrl);
      return nextUrl;
  }
}

