package music.player;

// Java program to play an Audio
// file using Clip Object
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AudioPlayer
{

	// to store current position
	Long currentFrame;
	Clip clip;
	static int counter=0;
	// current status of clip
	String status;
	static LinkedList playlist;
	AudioInputStream audioInputStream;
	static String filePath;
       static File myfile = null;
        

	// constructor to initialize streams and clip
	public AudioPlayer()
		throws UnsupportedAudioFileException,
		IOException, LineUnavailableException
	{
		// create AudioInputStream object
		audioInputStream = AudioSystem.getAudioInputStream(myfile);
		
		// create clip reference
		clip = AudioSystem.getClip();
                
		
		// open audioInputStream to the clip
		clip.open(audioInputStream);
		
	}
        static File[]  arr;

	public static void main(String[] args)
	{
            JFileChooser chooser = new JFileChooser();
            
            chooser.setMultiSelectionEnabled(true);
            
            FileNameExtensionFilter filter = new FileNameExtensionFilter( "musicalbum","wav");
            chooser.setFileFilter(filter);
    
             int returnVal = chooser.showOpenDialog(null);
             if(returnVal == JFileChooser.APPROVE_OPTION) {
             arr =  chooser.getSelectedFiles();
             List<File> list=Arrays.asList(arr);
             playlist = new LinkedList(list);
             
             myfile=new File( playlist.getFirst().toString());
         
            }
	        try
		{
			AudioPlayer audioPlayer =new AudioPlayer();
			
			audioPlayer.play();
			Scanner sc = new Scanner(System.in);
			
			while (true)
			{
				System.out.println("1. pause");
				System.out.println("2. resume");
				System.out.println("3. restart");
				System.out.println("4. stop");
				System.out.println("5. Jump to specific time");
                                System.out.println("6. next");
                                System.out.println("7. prev");
                                System.out.println("8. delete");
				int c = sc.nextInt();
				audioPlayer.gotoChoice(c);
				if (c == 4)
				break;
			}
			sc.close();
		}
		
		catch (Exception ex)
		{
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		
		}
	}
	
	// Work as the user enters his choice
	
	private void gotoChoice(int c)
			throws IOException, LineUnavailableException, UnsupportedAudioFileException
	{
		switch (c)
		{
			case 1:
				pause();
				break;
			case 2:
				resumeAudio();
				break;
			case 3:
				restart();
				break;
			case 4:
				stop();
				break;
			case 5:
				System.out.println("Enter time (" + 0 +
				", " + clip.getMicrosecondLength() + ")");
				Scanner sc = new Scanner(System.in);
				long c1 = sc.nextLong();
				jump(c1);
				break;
                        case 6:
                                next();
                                break;
                                
                        case 7: prev();
                                break;
                                
                        case 8:  
                               
                                delete();
                                break;
	
		}
	
	}
	
	// Method to play the audio
	public void play()
	{
		//start the clip
		clip.start();
		
		status = "play";
                System.out.println("name song :" + myfile.getName() );
	}
	// Method to pause the audio
	public void pause()
	{
		if (status.equals("paused"))
		{
			System.out.println("audio is already paused");
			return;
		}
		this.currentFrame =
		this.clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}
        public void next() throws IOException, LineUnavailableException,UnsupportedAudioFileException
	{  clip.stop();
           clip.close();
            counter++;
        if(counter>=playlist.size()-1){
           myfile=new File( playlist.getLast().toString());
            
        }
        else{ myfile=new File( playlist.get(counter).toString());}
		resetAudioStream();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		this.play();
                status = "play";
	}
        public void prev()throws IOException, LineUnavailableException,UnsupportedAudioFileException
	{  clip.stop();
		clip.close();
            counter--;
        if(counter<0)
        {myfile=new File( playlist.getFirst().toString());}
        else{
         myfile=new File( playlist.get(counter).toString());
        }
		resetAudioStream();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		this.play();
                status = "play";
        }
	
	
	// Method to resume the audio
	public void resumeAudio() throws UnsupportedAudioFileException,IOException, LineUnavailableException
	{
		if (status.equals("play"))
		{
			System.out.println("Audio is already "+
			"being played");
			return;
		}
		clip.close();
		resetAudioStream();
		clip.setMicrosecondPosition(currentFrame);
		this.play();
	}
	
	// Method to restart the audio
	public void restart() throws IOException, LineUnavailableException,UnsupportedAudioFileException
	{
		clip.stop();
		clip.close();
		resetAudioStream();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		this.play();
	}
	
	// Method to stop the audio
	public void stop() throws UnsupportedAudioFileException,
	IOException, LineUnavailableException
	{
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}
	
	// Method to jump over a specific part
	public void jump(long c) throws UnsupportedAudioFileException, IOException,LineUnavailableException
	{
		if (c > 0 && c < clip.getMicrosecondLength())
		{
			clip.stop();
			clip.close();
			resetAudioStream();
			currentFrame = c;
			clip.setMicrosecondPosition(c);
			this.play();
		}
	}
	
	// Method to reset audio stream
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException,LineUnavailableException
	{
		audioInputStream = AudioSystem.getAudioInputStream(myfile);
		clip.open(audioInputStream);
		
                    
	}
        public void delete(){
            int i =0;
           while(i<=playlist.size()-1){
               System.out.println("Delete :" +i + arr[i].getName());
               i++;
           }
           
           
           Scanner sh = new Scanner(System.in);
           int z = sh.nextInt();
           
               
           playlist.remove(z);
        
        }

}