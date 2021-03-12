package io.github.ultimateboomer.resolutioncontrol.util;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.lwjgl.opengl.GL11;

public enum ScalingAlgorithm {
    NEAREST(new TranslatableText("resolutioncontrol.settings.main.nearest"),
            GL11.GL_NEAREST, GL11.GL_NEAREST_MIPMAP_NEAREST),
    LINEAR(new TranslatableText("resolutioncontrol.settings.main.linear"),
            GL11.GL_LINEAR, GL11.GL_LINEAR_MIPMAP_NEAREST);

    private final Text text;
    private final int id;
    private final int idMipped;

    ScalingAlgorithm(Text text, int id, int idMipped) {
        this.text = text;
        this.id = id;
        this.idMipped = idMipped;
    }

    public Text getText() {
        return text;
    }

    public int getId(boolean mipped) {
        return mipped ? idMipped : id;
    }
}
