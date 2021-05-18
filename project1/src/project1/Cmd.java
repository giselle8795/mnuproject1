package project1;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cmd {
	
	private StringBuffer buffer;
	private Process process;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferWriter;
	private StringBuffer readBuffer;
	private File file;
	
	
	public String FILENAME = null;
	
	public String inputSource(String source) {
		
		buffer = new StringBuffer();
		
		buffer.append("cmd.exe ");	
		buffer.append("/c  ");
		buffer.append("javac "+FILENAME);
		
		createFileAsSource(source);

		return buffer.toString();
	}
	
	private void createFileAsSource(String source) {
		try {
			file = new File(FILENAME);
			bufferWriter = new BufferedWriter(new FileWriter(file, false));
			
			bufferWriter.write(source);
			bufferWriter.flush();
			
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}finally {
			try {
				bufferWriter.close();
				file = null;
			}catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	public String execCommand(String cmd) {
		try {
			process = Runtime.getRuntime().exec(cmd);
			return "컴파일 완료\n";
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public String Run() {
		try {
		process = Runtime.getRuntime().exec(runClass());
		
		bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String line = null;
		readBuffer = new StringBuffer();
		
		while((line = bufferedReader.readLine()) != null) {
			readBuffer.append(line);
			readBuffer.append("\n");
		}
		
		return readBuffer.toString();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String runClass() {
		buffer = new StringBuffer();
		buffer.append("cmd.exe ");	
		buffer.append("/c ");
		buffer.append("java "+FILENAME.replace(".java", ""));
		return buffer.toString();
	}
	
}

