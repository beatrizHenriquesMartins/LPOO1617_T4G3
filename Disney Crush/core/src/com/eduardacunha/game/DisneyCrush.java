package com.eduardacunha.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eduardacunha.game.Screens.MainMenu;
import com.sun.org.apache.xpath.internal.operations.String;

public class DisneyCrush extends Game {
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 200;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
