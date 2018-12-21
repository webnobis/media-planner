package com.webnobis.mediaplanner;

import com.webnobis.mediaplanner.sheet.PlannerFrame;
import com.webnobis.mediaplanner.sheet.util.Msg;

/**
 * The starter of the media planner
 * 
 * @author steffen
 * @version 1.00
 */
public class MediaPlanner {

	/**
	 * The title of the media planner window
	 */
	public static final String PLANNER_TITLE = Msg.getText("planner.header") + " (" + Msg.getText("planner.version") + ", programmed by Steffen Nobis)";

	/**
	 * Starts the media planner.
	 * 
	 * @param args
	 *            not needed
	 */
	public static void main(String[] args) {
		new PlannerFrame(PLANNER_TITLE);
	}

}
