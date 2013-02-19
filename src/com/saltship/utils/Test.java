package com.saltship.utils;

public class Test
{
	public static void main (String[] args)
	{
		Logger log = new StreamLogger (System.out);
		log.setLevel (Logger.INFO);

		Runtime runtime = Runtime.getRuntime();
		runtime.addShutdownHook (log.getShutdownHook());
		
		log.log ("Program started!");
		log.cond (args.length == 1, "There's 1 arg!");
		log.cond (args.length == 0, "There's no args", "There are some args");
	}
}
