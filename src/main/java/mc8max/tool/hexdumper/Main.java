package mc8max.tool.hexdumper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main 
{
	private static final int RETURN_FAILURE = -1; 
	private static final int DEFAULT_LINE_LENGTH = 16;

	public static void main(String [] args)
	{
		if (args.length < 1)
		{
			fail("Missing input file.");
		}
		
		File file = new File(args[0]);
		try (BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			boolean isFirst = true;
			int len = 0;
			char[] buffer = getBuffer();
			while ((len = br.read(buffer)) > 0)
			{
				for (int i = 0; i < len; i++)
				{
					if (!isFirst && i % DEFAULT_LINE_LENGTH == 0)
					{
						System.out.println();
					}
					isFirst = false;
					System.out.print(toHexString(buffer[i]));
					System.out.print(" ");
				}
			}
		} 
		catch (FileNotFoundException e) 
		{
			error(e.getMessage());
			fail("File not found.");
		} 
		catch (IOException e) 
		{
			error(e.getMessage());
			fail("Unable to read file.");
		}
	}
	
	private static String toHexString(char b)
	{
		String str = Integer.toHexString(b).toUpperCase();
		if (str.length() > 2)
		{
			return str.substring(str.length() - 2);
		}
		else if (str.length() < 2)
		{
			return "0" + str;
		}
		return str;
	}
	
	private static char[] getBuffer()
	{
		return new char[2048];
	}
	
	private static void error(String msg)
	{
		System.err.println(msg);
	}
	
	private static void fail(String msg)
	{
		System.err.println(msg);
		System.exit(RETURN_FAILURE);
	}
}
