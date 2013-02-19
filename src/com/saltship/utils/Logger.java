package com.saltship.utils;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * A basic logger: it keeps the messages
 * in memory only.
 */
public class Logger
{
	public static final Level DEBUG = Level.DEBUG;
	public static final Level INFO = Level.INFO;
	public static final Level WARN = Level.WARN;
	public static final Level ERROR = Level.ERROR;
	public static final Level FATAL = Level.FATAL;
	
	enum Level
	{
		DEBUG (1),
		INFO (2),
		WARN (3),
		ERROR (4),
		FATAL (5);

		private int level;
		Level (int level)
		{
			this.level = level;
		}

		public int getLevel ()
		{
			return this.level;
		}
	};

	protected static final int INIT_SIZE = 10;

	protected Level logLevel;
	protected Map<Level, List<String>> messages;

	public Logger ()
	{
		messages = new HashMap<>();
		logLevel = INFO;
	}
	
	public void cond (boolean cond, String message)
	{
		if (cond)
			log (INFO, message);
	}

	public void cond (boolean cond, String ifMessage, String elseMessage)
	{
		log (INFO, cond ? ifMessage : elseMessage);
	}

	/**
	 * Logs a message: defaults to info
	 */
	public void log (String message)
	{
		this.log (INFO, message);
	}
	
	public void log (Level level, String message)
	{
		if ( level.getLevel() < logLevel.getLevel() )
			return;
		
		List<String> list = messages.get (level);

		if ( list == null )
			list = new ArrayList<>(INIT_SIZE);
		
		list.add (message);
		messages.put (level, list);
	}

	public Map<Level, List<String>> getMessages ()
	{
		return messages;
	}

	public List<String> getMessages (Level level)
	{
		return messages.get(level);
	}

	public void setLevel (Level level) throws IllegalArgumentException
	{
		if ( level.getLevel() < DEBUG.getLevel() || level.getLevel() > FATAL.getLevel() )
			throw new IllegalArgumentException ("Invalid argument");
		logLevel = level;
	}

	public Thread getShutdownHook ()
	{
		return new ShutdownHook();
	}

	protected class ShutdownHook extends Thread
	{
		public void run ()
		{
			log (INFO, "Program exited");
		}
	}
}
