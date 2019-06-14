package mods.skipsign;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiOptionSliderEx extends GuiButton
{
    /** The value of this slider control. */
    public float sliderValue = 1.0F;

    private float Hurihaba = 128;

    /** Is this slider control being dragged. */
    public boolean dragging;

    public Key key;

    private String text;

    public GuiOptionSliderEx(int par1, int par2, int par3, String par5Str, Key k, float h)
    {
        super(par1, par2, par3, 100, 20, par5Str);
        this.key = k;
        this.sliderValue = k.Float() / h;
        text = par5Str;
        this.displayString = String.format("%s:%dブロック", text, (int)(this.sliderValue * h));
        this.Hurihaba = h;
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
    protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.enabled)
        {
            if (this.dragging)
            {
                this.sliderValue = (float)(par2 - (this.x + 4)) / (float)(this.width - 8);

                if (this.sliderValue < 0.0F)
                {
                    this.sliderValue = 0.0F;
                }

                if (this.sliderValue > 1.0F)
                {
                    this.sliderValue = 1.0F;
                }

                key.Value = (int)(this.sliderValue * Hurihaba);
                this.displayString = String.format("%s:%dブロック", text, key.Int());
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.x + (int)(this.sliderValue * (float)(this.width - 8)), this.y, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.x + (int)(this.sliderValue * (float)(this.width - 8)) + 4, this.y, 196, 66, 4, 20);
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {
        if (super.mousePressed(par1Minecraft, par2, par3))
        {
            this.sliderValue = (float)(par2 - (this.x + 4)) / (float)(this.width - 8);

            if (this.sliderValue < 0.0F)
            {
                this.sliderValue = 0.0F;
            }

            if (this.sliderValue > 1.0F)
            {
                this.sliderValue = 1.0F;
            }

            key.Value = (int)(this.sliderValue * this.Hurihaba);
            this.displayString = String.format("%s:%dブロック", text, key.Int());
            this.dragging = true;
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int par1, int par2)
    {
        this.dragging = false;
    }
}
