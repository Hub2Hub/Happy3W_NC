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
 * �鵵������ȷ�ʽ���öԻ���
 * 
 * @since 6.5
 * @version 2016��4��9�� ����2:36:59
 * @author Caroline
 */
public class CommonDialog extends FuncletDialog {

    private static final long serialVersionUID = -3528142298978784088L;

    /**
     * ���캯����
     * 
     * @param context ��¼�����ġ�
     * @param reset ���������С����
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
     * ������ɱ�ʶ��
     */
    private boolean progressover;

    /**
     * ��ȡ������ɱ�ʶ��
     * 
     * @return
     */
    public boolean isProgressover() {
        return this.progressover;
    }

    /**
     * ���ý�����ɱ�ʶ��
     * 
     * @param progressover
     */
    public void setProgressover(boolean progressover) {
        this.progressover = progressover;
    }

    /**
     * �Ƿ���ʾ��������
     */
    private boolean showProgressBar;

    /**
     * ��ȡ�Ƿ���ʾ��������
     * 
     * @return
     */
    public boolean isShowProgressBar() {
        return this.showProgressBar;
    }

    /**
     * �����Ƿ���ʾ��������
     * 
     * @param showProgressBar
     */
    public void setShowProgressBar(boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
    }

    private static Color msgLblErrorColor = ThemeResourceCenter.getInstance().getColor("themeres/index/indexResConf",
            "funcletStatusBar.msgLblErrorColor");

    /**
     * ���
     * BillManageModel
     */
    public BillManageModel getModel() {
        return this.model;
    }

    public void setModel(BillManageModel model) {
        this.model = model;
    }

    /**
     * ���
     * LoginContext
     */
    public LoginContext getContext() {
        return this.context;
    }

    public void setContext(LoginContext context) {
        this.context = context;
    }

    /**
     * ���
     * String
     */
    public String getConfigPath() {
        return this.configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    /**
     * ���
     * int
     */
    public int getDlgWidth() {
        return this.dlgWidth;
    }

    public void setDlgWidth(int dlgWidth) {
        this.dlgWidth = dlgWidth;
    }

    /**
     * ���
     * int
     */
    public int getDlgHeight() {
        return this.dlgHeight;
    }

    public void setDlgHeight(int dlgHeight) {
        this.dlgHeight = dlgHeight;
    }

    /**
     * ���
     * FuncletInitData
     */
    public FuncletInitData getData() {
        return this.data;
    }

    public void setData(FuncletInitData data) {
        this.data = data;
    }

    /**
     * ���
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
                    // ԭ����ť�����ʾ������������Ϊ200
                    com.setPreferredSize(new Dimension(200, 30));
                }
                else {
                    com.setPreferredSize(new Dimension(this.dlgWidth - 200, 30));
                    // ����ʾ������Ϣ��Label��ɺ�ɫ��
                    ((JPanel) com).getComponent(0).setForeground(CommonDialog.msgLblErrorColor);
                }
            }
        }
        this.getBottomPanel().add(this.getBtnPanel(), BorderLayout.EAST);
        Border border = BorderFactory.createLineBorder(Color.gray);
        this.getBottomPanel().setBorder(border);

        // ��ʾ��������
        if (this.showProgressBar) {
            this.progressBar = new ProgressBar(this.dlgWidth, 20);

            this.progressBar.setPreferredSize(new Dimension(200, 60));
            this.progressBar.setVisible(false);
        }
    }

    @Override
    protected void close() {
        super.close();
        // ��������ʾ��Ϣ��
        this.setContentText(null);
        // ���ؽ�������
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
     * ��ʾ��������
     */
    private void showProgressBar() {
        this.progressBar.setVisible(true);
        this.contentPanel.add(this.progressBar, BorderLayout.NORTH);
        this.contentPanel.validate();
        this.contentPanel.updateUI();
    }

    /**
     * ���ؽ�������
     */
    private void hideProgressBar() {
        this.progressBar.setVisible(false);
        this.contentPanel.validate();
        this.contentPanel.updateUI();
    }

    /**
     * ����������ʾ��
     */
    public void startProgress() {
        this.progressover = false;
        // ��ʼ��ʾ������
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
            // �رս�����ʾ��
            CommonDialog.this.hideProgressBar();
        }
    }
}
