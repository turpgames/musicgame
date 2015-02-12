package com.turpgames.musicgame.view;

import com.turpgames.framework.v0.impl.FormScreen;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.musicgame.components.FacebookLoginButton;
import com.turpgames.musicgame.components.GameLogo;

public class MenuScreen extends FormScreen {
	private FacebookLoginButton loginButton;
	
	@Override
	public void init() {
		super.init();	
		
		loginButton = FacebookLoginButton.getInstance();

		registerDrawable(loginButton, Game.LAYER_SCREEN);

		registerDrawable(new GameLogo("Menu Screen"), Game.LAYER_SCREEN);
		setForm("mainMenu", false);
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();
		loginButton.activate();
	}
	
	@Override
	protected boolean onBeforeDeactivate() {
		loginButton.deactivate();
		return super.onBeforeDeactivate();
	}

	protected boolean onBack() {
		Game.exit();
		return true;
	}
}
