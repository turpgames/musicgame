package com.turpgames.musicgame.objects;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.IInputListener;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.framework.v0.util.Vector;

public class Frame implements IDrawable {
	private final FrameObj obj;
	private final float start;
	private final float length;
	private final Music music;
	private final int index;

	Frame(Music music, float[] data, float length, int index) {
		this.length = length;
		this.music = music;
		this.index = index;
		this.obj = new FrameObj(data);
		this.start = index * length;
	}

	public void setPosition(float x, float y) {
		obj.setPosition(x, y);
	}

	public void setSize(float width, float height) {
		obj.setSize(width, height);
	}

	public void play() {
		obj.setProgress(0);
		music.play(index);
	}

	public void stop() {
		music.stop();
	}

	void update() {
		float pos = music.getPosition();
		if (start < pos && pos <= start + length) {
			obj.setProgress((pos - start) / length);
		} else {
			obj.setProgress(0);
		}
	}

	@Override
	public void draw() {
		obj.draw();
	}

	private class FrameObj extends GameObject {
		private final Color draggingColor = Color.red();
		private final Color canDragColor = Color.fromHex("#ff0");
		private final Color defaultColor = Color.fromHex("#0080ff");

		private Signal signal;
		private float dragDx;
		private float dragDy;
		private boolean canDrag;
		private boolean isDragging;

		FrameObj(float[] data) {
			this.signal = new Signal();
			this.signal.setData(data);
			getColor().set(defaultColor);
		}

		void setSize(float width, float height) {
			signal.setHeight(height);
			signal.setWidth(width);
			super.setHeight(height);
			super.setWidth(width);
		}

		void setPosition(float x, float y) {
			signal.setX(x);
			signal.setY(y);
			super.getLocation().set(x, y);
		}

		void setProgress(float progress) {
			signal.setProgress(progress);
		}
		@Override
		protected boolean onTouchDown(float x, float y) {
			canDrag = true;
			getColor().set(canDragColor);

			x = Game.screenToViewportX(x);
			y = Game.screenToViewportY(y);
			
			Vector loc = getLocation();
			
			dragDx = loc.x - x;
			dragDy = loc.y - y;
			
			return super.onTouchDown(x, y);
		}
		
		@Override
		protected boolean onTouchDragged(float x, float y) {
			if (!canDrag) {
				return false;
			}
			
			isDragging = true;
			getColor().set(draggingColor);
			
			return true;
		}

		@Override
		public boolean touchDragged(float x, float y, int pointer) {
			if (!isDragging) {
				return super.touchDragged(x, y, pointer);
			}
			x = Game.screenToViewportX(x);
			y = Game.screenToViewportY(y);
			setPosition(x + dragDx, y + dragDy);
			return false;
		}

		@Override
		protected boolean onTouchUp(float x, float y) {
			if (isDragging) {
				isDragging = false;
			} else {
				play();
			}
			canDrag = false;
			getColor().set(defaultColor);
			return true;
		}

//		@Override
//		protected boolean onLongPress(float x, float y) {
//			if (canDrag) {
//				return false;
//			}
//			x = Game.screenToViewportX(x);
//			y = Game.screenToViewportY(y);
//			canDrag = true;
//			getColor().set(selectedColor);
//			Vector loc = getLocation();
//			dragDx = loc.x - x;
//			dragDy = loc.y - y;
//			return super.onLongPress(x, y);
//		}

		@Override
		public void draw() {
			signal.draw();
			ShapeDrawer.drawRect(this, false);
		}
	}

	public IInputListener getInputListener() {
		return obj;
	}
}
