package cs6200.assignment1.part1;

import java.util.LinkedHashSet;
import java.util.Set;

public class Links {
	
	private Set<String> checkedLinks = new LinkedHashSet<String>();
	private Set<String> storedLinks = new LinkedHashSet<String>();
	private int depth;
	
    public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public Set<String> getCheckedLinks() {
		return checkedLinks;
	}
	public void setCheckedLinks(Set<String> checkedLinks) {
		this.checkedLinks = checkedLinks;
	}
	public Set<String> getStoredLinks() {
		return storedLinks;
	}
	public void setStoredLinks(Set<String> storedLinks) {
		this.storedLinks = storedLinks;
	}
	
}
