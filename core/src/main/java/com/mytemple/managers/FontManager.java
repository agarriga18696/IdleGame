package com.mytemple.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

import java.util.HashMap;
import java.util.Map;

public class FontManager {

	private static Map<String, BitmapFont> fonts = new HashMap<>();
	private static FreeTypeFontGenerator regularGenerator;
	private static FreeTypeFontGenerator boldGenerator;
	private static FreeTypeFontGenerator italicGenerator;

	/**
	 * Inicializa el generador de fuentes y las registra en el Skin.
	 */
	public static void initialize(Skin skin) {
		regularGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/GentiumPlus-Regular.ttf"));
		boldGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/GentiumPlus-Bold.ttf"));
		italicGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/GentiumPlus-Italic.ttf"));

		loadFonts();

		// Registrar fuentes en el Skin
		skin.add("mainTitleFont", getFont("mainTitle"), BitmapFont.class);
		skin.add("titleFont", getFont("title"), BitmapFont.class);
		skin.add("bodyFont", getFont("body"), BitmapFont.class);
		skin.add("buttonFont", getFont("button"), BitmapFont.class);
		skin.add("newsFont", getFont("news"), BitmapFont.class);

		// Registrar estilos en Skin
		if(!skin.has("mainTitle", Label.LabelStyle.class)) {
			Label.LabelStyle mainTitleStyle = new Label.LabelStyle();
			mainTitleStyle.font = skin.getFont("mainTitleFont");
			skin.add("mainTitle", mainTitleStyle);
		}

		if(!skin.has("title", Label.LabelStyle.class)) {
			Label.LabelStyle titleStyle = new Label.LabelStyle();
			titleStyle.font = skin.getFont("titleFont");
			titleStyle.font.getData().markupEnabled = true;
			skin.add("title", titleStyle);
		}

		if(!skin.has("body", Label.LabelStyle.class)) {
			Label.LabelStyle bodyStyle = new Label.LabelStyle();
			bodyStyle.font = skin.getFont("bodyFont");
			bodyStyle.font.getData().markupEnabled = true;
			skin.add("body", bodyStyle);
		}

		if(!skin.has("button", Label.LabelStyle.class)) {
			Label.LabelStyle buttonStyle = new Label.LabelStyle();
			buttonStyle.font = skin.getFont("buttonFont");
			buttonStyle.font.getData().markupEnabled = true;
			skin.add("button", buttonStyle);
		}

		if(!skin.has("news", Label.LabelStyle.class)) {
			Label.LabelStyle newsStyle = new Label.LabelStyle();
			newsStyle.font = skin.getFont("newsFont");
			newsStyle.font.getData().markupEnabled = true;
			skin.add("news", newsStyle);
		}

		if(!skin.has("textField", TextField.TextFieldStyle.class)) {
		    TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
		    textFieldStyle.font = skin.getFont("bodyFont"); // Usa la fuente "body"
		    textFieldStyle.fontColor = Color.WHITE;

		    // Crear un Drawable con padding interno.
		    textFieldStyle.background = skin.newDrawable("white", new Color(1, 1, 1, 0.15f));
		    textFieldStyle.background.setLeftWidth(10f);  // Padding izquierdo.
		    textFieldStyle.background.setRightWidth(10f); // Padding derecho.
		    textFieldStyle.background.setTopHeight(5f);   // Padding superior.
		    textFieldStyle.background.setBottomHeight(5f);// Padding inferior.

		    // Configurar el cursor y selección.
		    textFieldStyle.cursor = skin.newDrawable("white", Color.WHITE);
		    textFieldStyle.selection = skin.newDrawable("white", new Color(0.3f, 0.3f, 1, 0.75f));
		    
		    // Centrar el texto horizontalmente.
		    textFieldStyle.font.getData().markupEnabled = true;

		    skin.add("textField", textFieldStyle);
		}


	}

	/**
	 * Carga todas las fuentes con distintos tamaños y estilos.
	 */
	private static void loadFonts() {
		fonts.put("mainTitle", createFont(boldGenerator, 64, Color.WHITE, 2, Color.BLACK));
		fonts.put("title", createFont(boldGenerator, 24, Color.WHITE, 2, Color.BLACK));
		fonts.put("body", createFont(regularGenerator, 24, Color.WHITE, 1, Color.BLACK));
		fonts.put("button", createFont(boldGenerator, 24, Color.WHITE, 1, Color.BLACK));
		fonts.put("news", createFont(italicGenerator, 22, Color.WHITE, 1, Color.BLACK));
	}

	/**
	 * Crea una fuente con las características especificadas.
	 */
	private static BitmapFont createFont(FreeTypeFontGenerator generator, int size, Color color, int borderWidth, Color borderColor) {
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
		params.size = size;
		params.color = color;
		params.borderWidth = borderWidth;
		params.borderColor = borderColor;
		return generator.generateFont(params);
	}

	/**
	 * Obtiene una fuente específica.
	 */
	public static BitmapFont getFont(String key) {
		return fonts.getOrDefault(key, fonts.get("body"));
	}

	/**
	 * Libera los recursos cuando ya no se necesitan.
	 */
	public static void dispose() {
		if(regularGenerator != null) {
			regularGenerator.dispose();
			regularGenerator = null;
		}

		if(boldGenerator != null) {
			boldGenerator.dispose();
			boldGenerator = null;
		}

		if(italicGenerator != null) {
			italicGenerator.dispose();
			italicGenerator = null;
		}

		fonts.clear();
	}
}
