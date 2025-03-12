package com.mytemple.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mytemple.managers.FontManager;

public class GameUI {

    private Stage stage;
    private Skin skin;
    private Window messageWindow;
    private Label messageLabel;
    private float minWidth = 500; // Tamaño mínimo de la caja.
    private float minHeight = 220;

    public GameUI(Stage stage, Skin skin) {
        this.stage = stage;
        this.skin = skin;
    }

    public void showStandardDialog(String message) {
        createDialog(null, message, false, false, 0);
    }

    public void showTimedDialog(String message, float duration) {
        createDialog(null, message, true, false, duration);
    }

    public void showButtonDialog(String message) {
        createDialog(null, message, false, true, 0);
    }

    public void showEventDialog(String title, String message) {
        createDialog(title, message, false, true, 0);
    }

    private void createDialog(String title, String message, boolean autoClose, boolean hasCloseButton, float duration) {
        if(messageWindow != null) {
            messageWindow.remove();
        }

        messageWindow = new Window("", skin);
        messageWindow.setColor(0, 0, 0, 0.7f); // Fondo semitransparente
        messageWindow.setModal(true); // Bloquea clics en el resto de la pantalla

        Table contentTable = new Table();

        if(title != null) {
            Label titleLabel = new Label(title, new Label.LabelStyle(FontManager.getFont("title"), Color.WHITE));
            contentTable.add(titleLabel).padBottom(10).center().row();
        }

        messageLabel = new Label(message, new Label.LabelStyle(FontManager.getFont("body"), Color.WHITE));
        messageLabel.setAlignment(Align.center);
        messageLabel.setWrap(true); // Salto de línea automático.
        contentTable.add(messageLabel).width(minWidth - 40).pad(15).center().row();

        if(hasCloseButton) {
            TextButton closeButton = new TextButton("Entendido", skin, "default");
            closeButton.getLabel().setStyle(new Label.LabelStyle(FontManager.getFont("button"), Color.WHITE));
            closeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    hideDialog();
                }
            });
            contentTable.add(closeButton).padTop(10).width(150).height(50);
        }

        messageWindow.add(contentTable).pad(10);
        stage.addActor(messageWindow);

        adjustSize(); // Ajustar tamaño dinámico

        if(autoClose) {
            messageWindow.addAction(Actions.sequence(
                    Actions.delay(duration),
                    Actions.fadeOut(0.5f),
                    Actions.run(this::hideDialog)
            ));
        }
    }

    private void adjustSize() {
        float estimatedHeight = messageLabel.getPrefHeight() + 100;
        messageWindow.setSize(minWidth, Math.max(minHeight, estimatedHeight));
        messageWindow.setPosition(
                (stage.getWidth() - messageWindow.getWidth()) / 2,
                (stage.getHeight() - messageWindow.getHeight()) / 2
        );
    }

    public void hideDialog() {
        if (messageWindow != null) {
            messageWindow.remove();
            messageWindow = null;
        }
    }
}
