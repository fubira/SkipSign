package mods.skipsign.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

public class GuiOptionSliderEx extends GuiButton
{
    private float sliderValue = 1.0f;
    private float maxValue = 128;
    public boolean dragging;
    private String text;

    public GuiOptionSliderEx(int id, int x, int y, String text, float startValue, float maxValue)
    {
        super(id, x, y, 100, 20, text);

        this.sliderValue = startValue / maxValue;
        this.maxValue = maxValue;
        this.text = text;

        this.updateText();
    }

    public int getValue() {
        return (int)(this.sliderValue * this.maxValue);
    }

    public void updateText() {
        this.displayString = String.format("%s:%dブロック", this.text, this.getValue());
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    public int getHoverState(boolean par1)
    {
        return 0;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    @Override
    public boolean mouseDragged(double par1, double par2, int par3, double par4, double par5)
    {
        if (this.enabled) {
            if (this.dragging) {
                this.sliderValue = (float)(par1 - (this.x + 4)) / (float)(this.width - 8);

                if (this.sliderValue < 0.0f) {
                    this.sliderValue = 0.0f;
                }

                if (this.sliderValue > 1.0f) {
                    this.sliderValue = 1.0f;
                }

                updateText();
            }

            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.x + (int)(this.sliderValue * (float)(this.width - 8)), this.y, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.x + (int)(this.sliderValue * (float)(this.width - 8)) + 4, this.y, 196, 66, 4, 20);
        }
        return super.mouseDragged(par1, par2, par3, par4, par5);
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    @Override
    public boolean mouseClicked(double par1, double par2, int par3)
    {
        if (super.mouseClicked(par1, par2, par3)) {
            this.sliderValue = (float)(par1 - (this.x + 4)) / (float)(this.width - 8);

            if (this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            }

            if (this.sliderValue > 1.0f) {
                this.sliderValue = 1.0f;
            }

            updateText();
            this.dragging = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    @Override
    public boolean mouseReleased(double par1, double par2, int par3)
    {
        this.dragging = false;
        return super.mouseReleased(par1, par2, par3);
    }
}
