package com.turpgames.musicgame.components;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.Text;

public class GameLogo implements IDrawable {
	private final Text demoPlaceHolder;

	public GameLogo(String titleText) {
		demoPlaceHolder = new Text();
		demoPlaceHolder.setFontScale(1.25f);
		demoPlaceHolder.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		demoPlaceHolder.setPadY(50f);
		demoPlaceHolder.setText(titleText);
	}

	@Override
	public void draw() {
		demoPlaceHolder.draw();
	}
}
