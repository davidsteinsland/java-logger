package com.saltship.utils;
import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class StreamLogger extends Logger
{
	private OutputStream outputStream;
	private Writer writer;
	
	private static final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

	public StreamLogger(OutputStream s)
	{
		super();
		outputStream = s;
		writer = new PrintWriter (
					new BufferedWriter (new OutputStreamWriter (s))
				);
	}

	public void log (Level level, String message)
	{
		super.log(level, message);

		try
		{
			String dateString = dateFormat.format (new Date (System.currentTimeMillis()));

			writer.write("[" + dateString + "][" + level.toString() + "] " + message +"\n");
			writer.flush();
		}
		catch (IOException e)
		{
			System.err.println ( e.getMessage() );
		}
	}
}
