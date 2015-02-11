package com.turpgames.musicgame.view;

import com.turpgames.musicgame.components.GameLogo;
import com.turpgames.musicgame.utils.GameAuth;
import com.turpgames.musicgame.utils.R;
import com.turpgames.musicgame.utils.StatActions;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;

public class AuthScreen extends Screen {

	private TextButton facebookButton;
	private TextButton anonymousButton;
	private TextButton offlineButton;
	
	private boolean isFirstActivate;
	
	@Override
	public void init() {
		super.init();

		facebookButton = initButton("Login With Facebook", 250, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				loginWithFacebook();
			}
		});
		
		anonymousButton = initButton("Play As Guest", 175, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				playAsGuest();
			}
		});
		
		offlineButton = initButton("Play Offline", 100, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				ScreenManager.instance.switchTo(R.screens.menu, false);
			}
		});
		
		registerDrawable(new GameLogo("Auth Screen"), Game.LAYER_GAME);
		
		if (Settings.getInteger("game-installed", 0) == 0) {
			TurpClient.sendStat(StatActions.GameInstalled, Game.getPhysicalScreenSize().toString());
			Settings.putInteger("game-installed", 1);
		}
		
		TurpClient.sendStat(StatActions.StartGame);
		
		isFirstActivate = true;
	}
	
	private void initAuth() {
		GameAuth.init();
	}
	
	private void loginWithFacebook() {
		GameAuth.doFacebookLogin();
	}
	
	private void playAsGuest() {
		GameAuth.doGuestLogin();
	}
	
	private TextButton initButton(String text, float y, IButtonListener listener) {
		TextButton btn = new TextButton(Color.white(), R.colors.yellow);
		
		btn.setText(text);
		btn.getLocation().set((Game.getVirtualWidth() - btn.getWidth()) / 2, y);
		btn.setListener(listener);
		
		registerDrawable(btn, Game.LAYER_GAME);
		
		return btn;
	}
	
	@Override
	protected void onAfterActivate() {
		if (isFirstActivate) {
			isFirstActivate = false;
			initAuth();
		}
		facebookButton.activate();
		anonymousButton.activate();
		offlineButton.activate();
	}
	
	@Override
	protected boolean onBeforeDeactivate() {
		facebookButton.deactivate();
		anonymousButton.deactivate();
		offlineButton.deactivate();
		return super.onBeforeDeactivate();
	}
	
	@Override
	protected boolean onBack() {
		TurpClient.sendStat(StatActions.ExitGame);
		Game.exit();
		return true;
	}
}
