package cs6200.assignment2;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class PageStats{
	
	private String page;
	private List<String> inlinks = new LinkedList<String>();
	private int noOfOutlinks;
	private double pageRank;
	
	public double getPageRank() {
		return pageRank;
	}
	public void setPageRank(double pageRank) {
		this.pageRank = pageRank;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public List<String> getInlinks() {
		return inlinks;
	}
	public void addInlinks(String inlink) {
		this.inlinks.add(inlink);
	}
	public int getNoOfOutlinks() {
		return noOfOutlinks;
	}
	public void setNoOfOutlinks(int noOfOutlinks) {
		this.noOfOutlinks = noOfOutlinks;
	}
	
	
	public static Comparator<PageStats> pgComparator = new Comparator<PageStats>() 
	{

		public int compare(PageStats pg1, PageStats pg2)
		{
		
			double pr1 = pg1.getInlinks().size();
			double pr2 = pg2.getInlinks().size();
			
			return 	   pr2 > pr1 ? 1
			         : pr2 < pr1 ? -1
			         : 0;
		}
		
	};

}
