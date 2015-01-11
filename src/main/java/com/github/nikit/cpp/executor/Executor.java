package com.github.nikit.cpp.executor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Executor {
	public static ExecutorResult execute(String... command) throws ExecuteException {
		try{
			Process processList = Runtime.getRuntime().exec(command);
			BufferedReader inReader = new BufferedReader(new InputStreamReader(processList.getInputStream()));
			BufferedReader errReader = new BufferedReader(new InputStreamReader(processList.getErrorStream()));
			int exitCode = processList.waitFor();
			
			String tmp;
			
			List<String> procOut = new ArrayList<String>();
			while ((tmp=inReader.readLine())!=null) {
				procOut.add(tmp);
			}
			
			List<String> procErr = new ArrayList<String>();
			while ((tmp=errReader.readLine())!=null) {
				procErr.add(tmp);
			}
			
			inReader.close();
			errReader.close();
			return new ExecutorResult(procOut, procErr, exitCode);
		}catch (InterruptedException e){
			throw new ExecuteException("Interrupted", e);
		}catch (IOException e){
			throw new ExecuteException("IOException", e);
		}
	}
}
