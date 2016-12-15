package uap.ui.happy3w.pub.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import nc.bs.logging.Logger;
import nc.funcnode.ui.FuncletInitData;
import nc.ui.pub.beans.UIButton;
import nc.ui.pubapp.uif2app.FuncletDialog;
import nc.ui.pubapp.uif2app.model.BillManageModel;
import nc.uitheme.ui.ThemeResourceCenter;
import nc.vo.uif2.LoginContext;
import uap.ui.happy3w.pub.dlg.DlgAction;

/**
 * 归档任务调度方式设置对话框。
 * 
 * @since 6.5
 * @version 2016年4月9日 下午2:36:59
 * @author Caroline
 */
public class CommonDialog extends FuncletDialog {

    private static final long serialVersionUID = -3528142298978784088L;

    /**
     * 构造函数。
     * 
     * @param context 登录上下文。
     * @param reset 窗口最大化最小化。
     */
    public CommonDialog(LoginContext context, boolean reset) {
        super(context.getEntranceUI(), reset);
        this.context = context;
        this.setMaximumSize(new Dimension(800, 600));
        this.setMinimumSize(new Dimension(100, 50));
    }

    private BillManageModel model;

    private LoginContext context;

    private String configPath;

    private int dlgWidth;

    private int dlgHeight;

    private CommonTangramContainer dlgContainer;

    private FuncletInitData data;

    private ProgressBar progressBar;

    /**
     * 进度完成标识。
     */
    private boolean progressover;

    /**
     * 获取进度完成标识。
     * 
     * @return
     */
    public boolean isProgressover() {
        return this.progressover;
    }

    /**
     * 设置进度完成标识。
     * 
     * @param progressover
     */
    public void setProgressover(boolean progressover) {
        this.progressover = progressover;
    }

    /**
     * 是否显示进度条。
     */
    private boolean showProgressBar;

    /**
     * 获取是否显示进度条。
     * 
     * @return
     */
    public boolean isShowProgressBar() {
        return this.showProgressBar;
    }

    /**
     * 设置是否显示进度条。
     * 
     * @param showProgressBar
     */
    public void setShowProgressBar(boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
    }

    private static Color msgLblErrorColor = ThemeResourceCenter.getInstance().getColor("themeres/index/indexResConf",
            "funcletStatusBar.msgLblErrorColor");

    /**
     * 获得
     * BillManageModel
     */
    public BillManageModel getModel() {
        return this.model;
    }

    public void setModel(BillManageModel model) {
        this.model = model;
    }

    /**
     * 获得
     * LoginContext
     */
    public LoginContext getContext() {
        return this.context;
    }

    public void setContext(LoginContext context) {
        this.context = context;
    }

    /**
     * 获得
     * String
     */
    public String getConfigPath() {
        return this.configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    /**
     * 获得
     * int
     */
    public int getDlgWidth() {
        return this.dlgWidth;
    }

    public void setDlgWidth(int dlgWidth) {
        this.dlgWidth = dlgWidth;
    }

    /**
     * 获得
     * int
     */
    public int getDlgHeight() {
        return this.dlgHeight;
    }

    public void setDlgHeight(int dlgHeight) {
        this.dlgHeight = dlgHeight;
    }

    /**
     * 获得
     * FuncletInitData
     */
    public FuncletInitData getData() {
        return this.data;
    }

    public void setData(FuncletInitData data) {
        this.data = data;
    }

    /**
     * 获得
     * ScheduleTangramContainer
     */
    public CommonTangramContainer getDlgContainer() {
        return this.dlgContainer;
    }

    public void setDlgContainer(CommonTangramContainer dlgContainer) {
        this.dlgContainer = dlgContainer;
    }

    @Override
    public void initUI() {
        Dimension dimension = new Dimension(this.dlgWidth, this.dlgHeight);
        super.initUI(this.context, this.configPath, this.data, dimension);
        this.dlgContainer = (CommonTangramContainer) this.getDialogContainer();
        this.dlgContainer.setDialog(this);
        this.getTopPanel().setVisible(false);
        for (Component com : this.getBottomPanel().getComponents()) {
            if (com instanceof JPanel) {
                if (com.equals(this.btnPanel)) {
                    // 原来按钮面板显示不出来，设置为200
                    com.setPreferredSize(new Dimension(200, 30));
                }
                else {
                    com.setPreferredSize(new Dimension(this.dlgWidth - 200, 30));
                    // 将显示错误信息的Label变成红色。
                    ((JPanel) com).getComponent(0).setForeground(CommonDialog.msgLblErrorColor);
                }
            }
        }
        this.getBottomPanel().add(this.getBtnPanel(), BorderLayout.EAST);
        Border border = BorderFactory.createLineBorder(Color.gray);
        this.getBottomPanel().setBorder(border);

        // 显示进度条。
        if (this.showProgressBar) {
            this.progressBar = new ProgressBar(this.dlgWidth, 20);

            this.progressBar.setPreferredSize(new Dimension(200, 60));
            this.progressBar.setVisible(false);
        }
    }

    @Override
    protected void close() {
        super.close();
        // 清除面板提示信息。
        this.setContentText(null);
        // 隐藏进度条。
        if (this.isShowProgressBar()) {
            this.hideProgressBar();
        }
    }

    @Override
    protected JPanel getBtnPanel() {
        if (this.btnPanel == null) {
            this.btnPanel = new JPanel();
            this.btnPanel.setPreferredSize(new Dimension(200, 30));
            this.btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            for (Action action : this.getDialogContainer().getActions()) {
                if (action instanceof DlgAction) {
                    DlgAction dlgAction = (DlgAction) action;
                    dlgAction.setDialog(this);
                    UIButton button = new UIButton();
                    this.btnPanel.add(button);
                    button.setAction(dlgAction);
                    button.setMnemonic(dlgAction.getMnemonic());
                    button.setToolTipText(dlgAction.getToolTipText());
                }
            }
        }
        return this.btnPanel;
    }

    /**
     * 显示进度条。
     */
    private void showProgressBar() {
        this.progressBar.setVisible(true);
        this.contentPanel.add(this.progressBar, BorderLayout.NORTH);
        this.contentPanel.validate();
        this.contentPanel.updateUI();
    }

    /**
     * 隐藏进度条。
     */
    private void hideProgressBar() {
        this.progressBar.setVisible(false);
        this.contentPanel.validate();
        this.contentPanel.updateUI();
    }

    /**
     * 启动进度显示。
     */
    public void startProgress() {
        this.progressover = false;
        // 开始显示进度条
        this.showProgressBar();
        ProgressShowThread thread = new ProgressShowThread();
        thread.start();
    }

    public class ProgressShowThread extends Thread {

        private int width;

        public ProgressShowThread() {
        }

        @Override
        public void run() {
            this.width = CommonDialog.this.progressBar.getWidth();
            int bardepth = this.width / 4;
            boolean positive = true;
            while (!CommonDialog.this.progressover) {
                if (positive) {
                    CommonDialog.this.progressBar.setGradientDepth(bardepth);
                    CommonDialog.this.progressBar.repaint();
                    bardepth = bardepth + this.width / 4;
                    if (bardepth >= this.width * 5 / 4) {
                        positive = false;
                        bardepth = bardepth * 3 / 4;
                    }
                }
                else {
                    CommonDialog.this.progressBar.setGradientDepth(bardepth);
                    CommonDialog.this.progressBar.repaint();
                    bardepth = bardepth - this.width / 4;
                    if (bardepth <= 0) {
                        positive = true;
                        bardepth = this.width / 4;
                    }
                }
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {
                    Logger.debug(e);
                }
            }
            // 关闭进度显示。
            CommonDialog.this.hideProgressBar();
        }
    }
}
