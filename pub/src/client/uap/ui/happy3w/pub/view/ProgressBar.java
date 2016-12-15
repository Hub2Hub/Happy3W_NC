package uap.ui.happy3w.pub.view;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.dialog.Constants;

/**
 * 执行操作过程中显示的进度条。
 * 
 * @see StandarUIDialog.ProgressBar.
 * @since 6.5
 * @version 2016年6月23日 下午3:45:23
 * @author chenjij
 */
public class ProgressBar extends UIPanel {

    private static final long serialVersionUID = 224861020901176656L;

    private int gradientDepth = 0;

    private int width = 100;

    private int height = 20;

    private boolean progressOver = false;

    public boolean isProgressOver() {
        return this.progressOver;
    }

    public void setProgressOver(boolean progressOver) {
        this.progressOver = progressOver;
    }

    public ProgressBar(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint blueGradientPaint =
                new GradientPaint(2, 2, Constants.TOOLBAR_BACKGROUND_COLOR, this.gradientDepth, 2, Color.white);
        g2d.setPaint(blueGradientPaint);
        g2d.fill(new RoundRectangle2D.Float(1, 1, this.width - 2, this.height - 2, 2, 2));

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setPaint(Color.black);
        if (this.isCloseWhileComplete()) {
            g2d.drawString(nc.ui.ml.NCLangRes.getInstance().getStrByID("beansv5", "UPPbeansv5-000015")/*
                                                                                                       * @res
                                                                                                       * "操作正在进行中,请稍等...操作成功后将自动关闭对话框"
                                                                                                       */, 15, 45);
        }
        else {
            g2d.drawString(nc.ui.ml.NCLangRes.getInstance().getStrByID("beansv5", "UPPbeansv5-000016")/*
                                                                                                       * @res
                                                                                                       * "操作正在进行中,请稍等..."
                                                                                                       */, 15, 45);
        }
    }

    private boolean isCloseWhileComplete() {
        // TODO 未实现。
        return false;
    }

    public int getGradientDepth() {
        return this.gradientDepth;
    }

    public void setGradientDepth(int gradientDepth) {
        this.gradientDepth = gradientDepth;
    }
}
