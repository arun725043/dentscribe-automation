package com.dentscribe.utils;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

import org.testng.annotations.Test;


public class Testing {

	@Test
	public void recording() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
//		calendarPage.clickVerifyStartRecording("click");
//		assertTrue(calendarPage.verifyStopPauseButton());
		runAudio(10000);
//		calendarPage.clickPauseStopButton("stop");
	}

	public void runAudio(long time) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		File file = new File(System.getProperty("user.dir") + "//audio_files//sample-15s.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		String response = "";
		while (!response.equals("Q")) {
			response = "p";
			response = response.toUpperCase();
			switch (response) {
			case ("P"):
				clip.start();
				Thread.sleep(time);
				response = "Q";
				break;
			case ("S"):
				clip.stop();
				break;
			case ("R"):
				clip.setMicrosecondPosition(0);
				break;
			case ("Q"):
				clip.close();
				break;
			default:
				System.out.println("Not a valid response");
			}

		}
		System.out.println("Byeeee!");
	}
}