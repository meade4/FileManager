package app;

import java.io.IOException;
/**
 * Separate thread to continuously run the actual file manager.
 * TODO fix the bug causing problems with interrupting.
 * @author meade4
 *
 */
public class AppThread extends Thread{
	FileManager fileManager;
	public boolean runThread;
	
	/**
	 * stops the thread
	 */
	public void terminate(){
		this.runThread = false;
	}
	
	/**
	 * continuously runs the filemanager over a folder.
	 */
	public void run() {
		try{
			while(true){
				sleep(1000);
				fileManager.run();
			}
		}
		catch(InterruptedException e){
				//silently ignore otherwise we get "SLEEP INTERRUPTED BLAH!"
		}
		
	}
	public AppThread(FileManager manager){
		runThread = true;
		this.fileManager = manager;
	}
	
}
