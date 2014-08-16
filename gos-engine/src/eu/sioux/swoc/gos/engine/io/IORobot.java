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

import com.google.gson.Gson;

public class IORobot implements AutoCloseable
{
	private final IOHandler handler;
	private final StringBuilder dump;
	private final Gson gson;

	public IORobot(String command) throws IOException
	{
		handler = new IOHandler(command);
		dump = new StringBuilder();
		gson = new Gson();
	}

	public void writeMessage(Object message)
	{
		// Serialize
		String messageStr = gson.toJson(message);

		// Write
		dump.append(">" + messageStr + "\n");
		handler.writeLine(messageStr);
	}

	public <T> T readMessage(Class<T> classOfT)
	{
		return readMessage(classOfT, 2000);
	}

	public <T> T readMessage(Class<T> classOfT, long timeOut)
	{
		// Read
		String message = handler.readLine(timeOut);
		dump.append("<" + message + "\n");
		
		// Deserialize
		return gson.fromJson(message, classOfT);
	}

	public <T> T writeAndReadMessage(Object message, Class<T> classOfT)
	{
		return writeAndReadMessage(message, classOfT, 2000);
	}
	
	public <T> T writeAndReadMessage(Object message, Class<T> classOfT, long timeOut)
	{
		writeMessage(message);
		return readMessage(classOfT, timeOut);
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
