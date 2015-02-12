package com.turpgames.musicgame.mp3;

import java.io.InputStream;

import com.turpgames.utils.Util;

public class Mp3Decoder {
	private final InputStream is;
	private float sampleRate;

	public Mp3Decoder(InputStream is) {
		this.is = is;
	}
	
	public float getSampleRate() {
		return sampleRate;
	}

	public byte[] decode() throws Exception {
//		AudioInputStream mp3Stream = AudioSystem.getAudioInputStream(is);
//		AudioInputStream pcmStream = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, mp3Stream);
//
//		AudioFormat format = mp3Stream.getFormat();
//		this.sampleRate = format.getSampleRate();
//		// System.out.println("Frame rate:  " + format.getFrameRate());
//		// System.out.println("Frame size:  " + format.getFrameSize());
//		// System.out.println("Sample rate: " + format.getSampleRate());
//		// System.out.println("Sample size: " + format.getSampleSizeInBits());
//
//		return Util.IO.readStream(pcmStream);
		
		byte[] res = new byte[37513728];
		for (int i = 0; i < res.length; i++) {
			res[i] = (byte)Util.Random.randInt(-127, 128);
		}
		this.sampleRate = 44100;
		return res;
	}
}
