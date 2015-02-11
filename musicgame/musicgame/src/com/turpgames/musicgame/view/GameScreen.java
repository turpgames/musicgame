package com.turpgames.musicgame.view;

import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.musicgame.components.IToolbarListener;
import com.turpgames.musicgame.components.Toolbar;
import com.turpgames.musicgame.objects.Frame;
import com.turpgames.musicgame.objects.Music;
import com.turpgames.musicgame.utils.R;
import com.turpgames.utils.Util;

public class GameScreen extends Screen {
	private Music music;

	@Override
	public void init() {
		super.init();

		registerDrawable(Toolbar.getInstance(), Game.LAYER_INFO);
	}

	private void load() {
		try {
			music = Music.load("test", "music/test.mp3", 30);

			Frame[] frames = music.getFrames();

			Util.Random.shuffle(frames);

			float dx = 10f;
			float offsetX = 20f;
			float frameSize = (Game.getVirtualWidth() - 2 * offsetX - 90) / 10;
			float offsetY = frameSize + dx + offsetX;

			for (int i = 0; i < frames.length; i++) {
				Frame frame = frames[i];
				float x = offsetX + (i % 10) * (frameSize + dx);
				float y = offsetY + (i / 10) * (frameSize + dx);
				frame.setPosition(x, y);
				frame.setSize(frameSize, frameSize);

				registerInputListener(frame.getInputListener());
			}

			registerDrawable(music, Game.LAYER_GAME);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	private void unload() {
		music.dispose();
	}

	@Override
	protected boolean onBeforeActivate() {
		Toolbar.getInstance()
				.setListener(
						new IToolbarListener() {
							@Override
							public void onToolbarBack() {
								onBack();
							}
						});

		return super.onBeforeActivate();
	}

	@Override
	public void update() {
		if (music != null) {
			music.update();
		}
		super.update();
	}

	@Override
	protected void onAfterActivate() {
		Toolbar.getInstance().enable();
		load();
		super.onAfterActivate();
	}

	@Override
	protected boolean onBeforeDeactivate() {
		unload();
		Toolbar.getInstance().disable();
		
		for (Frame frame : music.getFrames()) {
			unregisterInputListener(frame.getInputListener());
		}

		unregisterDrawable(music);
		return super.onBeforeDeactivate();
	}

	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.screens.menu, true);
		return true;
	}
}
