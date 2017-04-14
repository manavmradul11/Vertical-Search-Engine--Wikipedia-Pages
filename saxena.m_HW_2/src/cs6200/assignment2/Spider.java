package cs6200.assignment2;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Spider
{ private Set<String>  setOfPages = new LinkedHashSet<String>();
  private List<String> pagesStored = new LinkedList<String>();
  private List<Double> perplexitydata = new LinkedList<Double>();
  private PageStats[] pg;
  private int N;


  public void computePR(String fileName)
  {
	  try
	  {
	      String line = null;
	      BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
	      while ((line = bufferedReader.readLine()) != null)
	      {
	    	  setOfPages.add(line);
	      }
	      bufferedReader.close();
	  }
	  catch (IOException ex) {
    	    // reading error
    	}
	  pagesStored.addAll(setOfPages);
	  // Compute Page Statistics *********************************************************
	  N = pagesStored.size();
	  pg = new PageStats[N];
	  int i=0;
	  System.out.println("\n **storing data** \n");
      do
      {   
    	  pg[i]= new PageStats();
          String nextUrl1 = this.pagesStored.get(i);
          
          String[] result1 = nextUrl1.split("\\s");
          pg[i].setPage(result1[0]);
          
          for(int j=1;j<result1.length;j++)
          {
        	  pg[i].addInlinks(result1[j]);
          }
          int count=0;
          int k=0;
          while(k<N)
          {
        	  String nextUrl2 = this.pagesStored.get(k);
              String result2[] = nextUrl2.split(" ");
              
              for(int m=1;m<result2.length;m++)
              {
            	  if(result2[m].equals(pg[i].getPage()))
            	  {
            		  count++;
            		  break;
            	  }
              }
              
              k++;
        	  
          }
          
          pg[i].setNoOfOutlinks(count);
          
          pg[i].setPageRank(1/N);
          
          i++;
      } while(i < N);
      
      System.out.println("\n **Data stored** \n");
      
      // Compute page rank ***********************************************************
      int convergenceContinuity = 0;
      double prevPerplexity=0;
      
      while(convergenceContinuity< 4)
      {
    	 
    	 double sinkPR=0;
    	 int k=0;
         while(k<N)
         { 
        	 if(pg[k].getNoOfOutlinks()==0)
        	 {
        		 sinkPR += pg[k].getPageRank(); 
        	 }
             k++;
       	  
         }
         
         k=0;
         double d= 0.95;
         double newPr[]=new double[N];
         while(k<N)
         {   
        	 newPr[k] = (1-d)/N; 
			 newPr[k] += d*sinkPR/N; 
			 for(String inlink : pg[k].getInlinks())
			 {
				 newPr[k] += d* pageRank(pg,inlink)/noOfOutLinks(pg,inlink);
			 }
        			 
             k++;
       	  
         }
         
         for(k=0;k<N;k++)
         {
        	 pg[k].setPageRank(newPr[k]);
         }
    	 
    	 
         if(perplexity(pg)-prevPerplexity<1)
    	 {
        	 perplexitydata.add(perplexity(pg));
    		 convergenceContinuity++;
    		 prevPerplexity=perplexity(pg);
    		 
    	 }
    	 else 
		 {
    		 convergenceContinuity = 0;
    		 prevPerplexity=perplexity(pg);
		 }
    	 
    	 
      }
      
      for(PageStats obj : pg )
	  {
		  System.out.println(obj.getPage()+":"+obj.getPageRank()+", outlinkcount:"+obj.getNoOfOutlinks()+"\n");
	  }
      
      
      try
      {FileWriter writer = new FileWriter("perpWG1.txt"); 
      for(double perp:perplexitydata)
      {
    	  writer.write(perp+"\n");
    	  
      }
      
      writer.close();
      }
      catch (IOException ex) {
    	    // complain to user
    	}
        
      Arrays.sort(pg,PageStats.pgComparator);
      
      try
      {FileWriter writer = new FileWriter("sortedtop50-by-inlink-WG1.txt"); 
      for(i=0;i<50;i++)
      {
    	  writer.write(pg[i].getPage()+":"+pg[i].getInlinks().size()+"\n");
    	  
      }
      
      writer.close();
      }
      catch (IOException ex) {
    	    // complain to user
    	}
      

      
  }
  
  
  private double pageRank(PageStats[] pg,String page)
  {
	  for(PageStats obj : pg )
	  {
		  if(obj.getPage().equals(page))
		  {
			  return obj.getPageRank();
		  }
	  }
	  
	  return -1;
  }
  
  private int noOfOutLinks(PageStats[] pg,String page)
  {
	  for(PageStats obj : pg )
	  {
		  if(obj.getPage().equals(page))
		  {
			  return obj.getNoOfOutlinks();
		  }
	  }
	  
	  return -1;
  }
  
  private double perplexity(PageStats[] pg)
  {
	  
	  double H= 0;
	  
	  for(int i=0;i<N;i++)
	  {
		  double pr=pg[i].getPageRank();
		  H = H - (pr*(Math.log10(pr)/Math.log10(2)));
	  }
	  
	  double P = Math.pow(2, H);
	  
	  return P;
	  
  }

}

