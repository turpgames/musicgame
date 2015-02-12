package com.turpgames.musicgame.view;

import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.musicgame.components.GameLogo;
import com.turpgames.musicgame.components.Toolbar;
import com.turpgames.musicgame.components.IToolbarListener;
import com.turpgames.musicgame.utils.R;

public class AboutScreen extends Screen {
	@Override
	public void init() {
		super.init();

		// TODO: init buttons
		
		initVersionText();
		registerDrawable(new GameLogo("About"), Game.LAYER_SCREEN);
		registerDrawable(Toolbar.getInstance(), Game.LAYER_INFO);
	}

	private void initVersionText() {
		Text text = new Text();
		text.setText("v" + Game.getVersion());
		text.setFontScale(0.66f);
		text.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		text.setPadY(125f);
		registerDrawable(text, Game.LAYER_SCREEN);
	}

	protected static AboutButton createButton(ITexture texture, final int statAction, final String url, float size, float x, float y) {
		AboutButton btn = new AboutButton();
		btn.setTexture(texture);
		btn.setWidth(size);
		btn.setHeight(size);

		btn.getLocation().set(x, y);
		btn.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				Game.openUrl(url);
				TurpClient.sendStat(statAction);
			}
		});

		return btn;
	}

	@Override
	protected void onAfterActivate() {
		Toolbar.getInstance().enable();
		Toolbar.getInstance().setListener(new IToolbarListener() {
			@Override
			public void onToolbarBack() {
				onBack();
			}
		});
		
		// TODO: activate buttons
	}

	@Override
	protected boolean onBeforeDeactivate() {
		Toolbar.getInstance().disable();

		// TODO: deactivate buttons

		return super.onBeforeDeactivate();
	}

	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.screens.menu, true);
		return true;
	}

	private static class AboutButton extends ImageButton {
		@Override
		public boolean ignoreViewport() {
			return false;
		}
	}
}
