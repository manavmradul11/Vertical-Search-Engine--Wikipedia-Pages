package cs6200.assignment1.part1;
import java.io.FileWriter;
import java.io.IOException;

public class Spider
{
  private static final int MAX_PAGES_TO_ADD = 1000;
  private  Links links = new Links();

  public void search(String url, String searchWord)
  {
	  String currentUrl;
      SpiderLeg leg = new SpiderLeg();
      currentUrl = url;
      
      this.links.getCheckedLinks().add(currentUrl);
      this.links.getStoredLinks().add(currentUrl);
      try
      {
          Thread.sleep(1000);

      } catch (InterruptedException ie) {
          ie.printStackTrace();
        }
      
      
      this.links = leg.crawl(currentUrl,links,1,MAX_PAGES_TO_ADD); 
      
      
      
      try
      {FileWriter writer = new FileWriter("output.txt"); 
      for(String str: this.links.getStoredLinks()) {
        writer.write(str+"\n");
      }
      writer.close();
      }
      catch (IOException ex) {
    	    // complain to user
    	}
      System.out.println("\n**Done** Added " + this.links.getStoredLinks().size() + " web page(s)");
      
  }

}

