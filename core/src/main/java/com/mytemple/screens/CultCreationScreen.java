package com.mytemple.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mytemple.CultIdleGame;

public class CultCreationScreen implements Screen {

    private CultIdleGame game;
    private Stage stage;
    private Skin skin;
    private TextField cultNameField;
    private SelectBox<String> countrySelectBox;
    private TextButton startButton;

    public CultCreationScreen(CultIdleGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Usar el Skin predeterminado.
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label nameLabel = new Label("Nombre del Culto:", skin);
        cultNameField = new TextField("", skin);

        Label countryLabel = new Label("País de inicio:", skin);
        countrySelectBox = new SelectBox<>(skin);
        countrySelectBox.setItems("Aleatorio", "Estados Unidos", "España", "Brasil", "Rusia", "China", "India");

        startButton = new TextButton("Empezar", skin);
        startButton.addListener(event -> {
            if(event.toString().equals("touchDown")) {
                String selectedCountry = countrySelectBox.getSelected();
                String cultName = cultNameField.getText();

                if (cultName.isEmpty()) {
                    cultName = "Culto Anónimo";
                }

                if (selectedCountry.equals("Aleatorio")) {
                    String[] countries = {"Estados Unidos", "España", "Brasil", "Rusia", "China", "India"};
                    selectedCountry = countries[(int) (Math.random() * countries.length)];
                }

                game.setScreen(new GameScreen(game));
            }
            return true;
        });

        table.add(nameLabel).padBottom(10);
        table.row();
        table.add(cultNameField).width(300).padBottom(20);
        table.row();
        table.add(countryLabel).padBottom(10);
        table.row();
        table.add(countrySelectBox).width(300).padBottom(20);
        table.row();
        table.add(startButton).width(200).height(50);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
