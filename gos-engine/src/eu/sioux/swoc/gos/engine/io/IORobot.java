// Copyright 2014 theaigames.com (developers@theaigames.com)

//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at

//        http://www.apache.org/licenses/LICENSE-2.0

//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//	
//    For the full copyright and license information, please view the LICENSE
//    file that was distributed with this source code.

package eu.sioux.swoc.gos.engine.io;

import java.io.IOException;

import org.json.simple.JSONObject;

import eu.sioux.swoc.gos.engine.Board;

public class IORobot implements AutoCloseable
{
	IOHandler handler;
	StringBuilder dump;
	int errorCounter;
	final int maxErrors = 2;

	public IORobot(String command) throws IOException
	{
		handler = new IOHandler(command);
		dump = new StringBuilder();
		errorCounter = 0;
	}

	public void setup(long timeOut)
	{
	}

	@SuppressWarnings("unchecked")
	public String doMove(Board board, long timeOut)
	{
		JSONObject obj = new JSONObject();
		obj.put("message", "init");
		obj.put("boardState", board.Serialize());
		
		String output = obj.toString();
		handler.writeLine(output);
		
		String line = handler.readLine(timeOut);
		
		dump.append("output: " + output + "\n");
		dump.append("line: " + line + "\n");
		return line;
	}

	@Override
	public void close()
	{
		handler.close();
	}

	public String getStdin()
	{
		return handler.getStdin();
	}

	public String getStdout()
	{
		return handler.getStdout();
	}

	public String getStderr()
	{
		return handler.getStderr();
	}

	public String getDump()
	{
		return dump.toString();
	}
}
