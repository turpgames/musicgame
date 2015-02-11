package com.turpgames.musicgame.objects;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.ShapeDrawer;

class Signal implements IDrawable {
	private float[] data;
	private float x;
	private float y;
	private float width;
	private float height;

	private float max;
	private float min;
	private float range;
	private float pulseWidth;

	private float progress;

	public float[] getData() {
		return data;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public void setData(float[] data) {
		this.data = data;
		this.max = data[0];
		this.min = data[0];

		for (float f : data) {
			if (f > max)
				max = f;
			else if (f < min)
				min = f;
		}

		range = 255f;// max - min;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
		this.pulseWidth = width / (float) data.length;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	private final Color notPlayed = Color.white();
	private final Color played = Color.green();

	@Override
	public void draw() {
		float midY = y + height / 2;

		float playLimit = progress * data.length;

		for (int i = 0; i < data.length; i++) {
			float xx = x + pulseWidth * i;
			float yy = (data[i] / range) * height;

			ShapeDrawer.drawLine(
					xx,
					midY - yy,
					xx,
					midY + yy,
					i < playLimit ? played : notPlayed,
					false);
		}
	}
}
