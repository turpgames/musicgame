package com.turpgames.musicgame.updates;

import com.turpgames.framework.v0.impl.UpdateProcessor;

public class GameUpdateManager {
	public static void runUpdates() {
		// UpdateProcessor.instance.addProcess(process);
		UpdateProcessor.instance.execute();
	}
}
