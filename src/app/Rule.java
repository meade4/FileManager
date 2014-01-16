package app;

import java.io.File;

/**
 * Class for a Rule that is used by the File Manager. Has a Filter Type which
 * only supports file type at the moment and a destination where files will be
 * saved to.
 * 
 * @author meade4
 * 
 */
public class Rule {
	RuleFilter filterBy;
	File destination;

	/**
	 * Wrapper function for checking if a file should be moved
	 * File is moved if the condition is satisfied
	 * @param file The file to check
	 */
	void attemptMove(File file) {
		filterBy.move(file, destination);
	}

	/**
	 * Constructor for the rule object
	 * @param filter the condition that the rule will be based on
	 * @param dest the destination folder for this rule
	 * @throws Exception if the destination is null
	 */
	public Rule(RuleFilter filter, File dest) throws Exception {
		if (dest.equals(null))
			throw new Exception();
		filterBy = filter;
		destination = dest;
	}

	/**
	 * displays the rule in text for the gui
	 * @return the explanation of the rule
	 */
	public String display() {
		return "Move files by " + filterBy.display() + " to " + destination;

	}

	/*
	 *  GETTERS AND SETTERS
	 */
	public RuleFilter getFilter() {
		return this.filterBy;
	}

	public File getDest() {
		return this.destination;
	}

	void setDest(File newDest) {
		this.destination = newDest;
	}

	void setFilter(RuleFilter newFilter) {
		this.filterBy = newFilter;
	}
}
