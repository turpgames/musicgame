package com.turpgames.musicgame.objects;

import java.io.InputStream;
import java.util.Arrays;

import com.turpgames.framework.v0.IDisposable;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.IMusic;
import com.turpgames.framework.v0.IResourceManager;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.musicgame.mp3.Mp3Decoder;

public class Music implements IDrawable, IDisposable {
	private final static IResourceManager res = Game.getResourceManager();

	private final Frame[] frames;
	private final IMusic music;
	private final float duration;

	private final Timer stopTimer;
	private int lastFrame = -1;

	private Music(String id, float[] bytes, float duration, int frameCount) {
		this.music = res.getMusic(id);
		this.duration = duration;
		this.frames = new Frame[frameCount];
		this.stopTimer = new Timer();

		int frameSize = bytes.length / frameCount;
		float frameLength = duration / frameCount;

		for (int i = 0; i < frameCount; i++) {
			float[] frameData = Arrays.copyOfRange(bytes, i * frameSize, (i + 1) * frameSize);
			frames[i] = new Frame(this, frameData, frameLength, i);
		}
		
		this.stopTimer.setInterval(frameLength);
		this.stopTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				stop();
			}
		});
	}
	
	public Frame[] getFrames() {
		return frames;
	}

	float getDuration() {
		return duration;
	}
	
	public void update() {
		if (!stopTimer.isRunning()) {
			return;
		}
		for (Frame frame : frames) {
			frame.update();
		}
	}

	void play(int frameIndex) {
		stopTimer.stop();
		music.setPosition(0);
		music.stop();
		if (lastFrame == frameIndex) {
			lastFrame = -1;
			return;
		}
		
		lastFrame = frameIndex;
		float progress = (float)frameIndex / (float)frames.length;
		music.play();
		music.setPosition(progress * duration);
		stopTimer.start();
	}
	
	float getPosition() {
		return music.getPosition();
	}
	
	public void stop() {
		music.stop();
		music.setPosition(0);
	}

	public static Music load(String id, String path, int frameCount) throws Exception {
		InputStream is = res.readFile(path);
		Mp3Decoder decoder = new Mp3Decoder(is);

		byte[] bytes = decoder.decode();
		float sampleRate = decoder.getSampleRate();
		float duration = bytes.length / (sampleRate * 4);

		is.close();

		float[] data = new float[(int) (Game.getVirtualWidth() - 20f)];
		for (int i = 0; i < data.length; i++) {
			int index = (int) (bytes.length * ((float) i / (float) data.length));
			data[i] = bytes[index];
		}

		bytes = null;

		Music music = new Music(id, data, duration, frameCount);

		data = null;

		return music;
	}

	@Override
	public void dispose() {
		if (music != null) {
			music.dispose();
		}
	}

	@Override
	public void draw() {
		for (Frame frame : frames) {
			frame.draw();
		}
	}
}
