package com.mytemple.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class WaterShader {
    private ShaderProgram shader;
    private float time = 0f;

    public WaterShader() {
        shader = new ShaderProgram(
                Gdx.files.internal("shaders/water.vert"),
                Gdx.files.internal("shaders/water.frag")
        );

        if (!shader.isCompiled()) {
            Gdx.app.error("Shader", "Error en compilación: " + shader.getLog());
        }
    }

    public void update(float delta) {
        time += delta;
    }

    public void apply() {
        shader.bind();
        shader.setUniformf("u_time", time);
    }

    public ShaderProgram getShader() {
        return shader;
    }

    public void dispose() {
        shader.dispose();
    }
}
