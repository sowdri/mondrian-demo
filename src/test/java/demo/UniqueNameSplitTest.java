package demo;

import static org.junit.Assert.*;

import org.junit.Test;

public class UniqueNameSplitTest {

	String uniqueName = "[Geo].[state].[TN]";

	@Test
	public void test() {
		String[] split = uniqueName.split("\\]\\.\\[");
		for (String s : split)
			System.out.println(s);

		System.out.println(uniqueName.substring(1, uniqueName.length() - 1));
	}

}
