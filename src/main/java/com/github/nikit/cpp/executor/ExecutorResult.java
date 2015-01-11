package com.github.nikit.cpp.executor;
import java.util.ArrayList;
import java.util.List;


public class ExecutorResult {
	private List<String> stdOut;
	private List<String> stdErr;
	private int exitCode;
	
	ExecutorResult(List<String> stdOut, List<String> stdErr, int exitCode){
		this.stdOut=stdOut;
		this.stdErr=stdErr;
		this.exitCode=exitCode;
	}
	
	public List<String> getStdOutList() {
		return new ArrayList<String>(stdOut);
	}
	public List<String> getStdErrList() {
		return new ArrayList<String>(stdErr);
	}
	
	public String getStdOut() {
		StringBuilder sb = new StringBuilder();
		for(String s: stdOut){
			sb.append(s);
		}
		return sb.toString();
	}
	public String getStdErr() {
		StringBuilder sb = new StringBuilder();
		for(String s: stdErr){
			sb.append(s);
		}
		return sb.toString();
	}
	public int getExitCode() {
		return exitCode;
	}
}
