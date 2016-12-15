package uap.ui.happy3w.pub.dlg;

import nc.bs.uif2.validation.IValidationService;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.model.AbstractAppModel;
import uap.ui.happy3w.pub.view.CommonDialog;

/**
 * �Ի���ť��
 * 
 * @since 6.5
 * @version 2016��4��12�� ����11:31:16
 * @author caroline
 */

public abstract class DlgAction extends NCAction {
    private static final long serialVersionUID = -8360741013720781808L;

    public DlgAction() {
        super();
        this.setCode(this.getBtnCode());
    }

    protected CommonDialog dialog;

    protected AbstractAppModel model;

    protected ShowUpableBillForm editor;

    protected IValidationService validationService;

    protected String btnCode;

    protected String toolTipText;

    protected int Mnemonic;

    /**
     * ���
     * String
     */
    public String getBtnCode() {
        return this.btnCode;
    }

    public void setBtnCode(String btnCode) {
        this.btnCode = btnCode;
        this.setCode(this.btnCode);
    }

    /**
     * ���
     * FuncletDialog
     */
    public CommonDialog getDialog() {
        return this.dialog;
    }

    public void setDialog(CommonDialog dialog) {
        this.dialog = dialog;
    }

    /**
     * ���
     * AbstractAppModel
     */
    public AbstractAppModel getModel() {
        return this.model;
    }

    public void setModel(AbstractAppModel model) {
        this.model = model;
        model.addAppEventListener(this);
    }

    /**
     * ���
     * ShowUpableBillForm
     */
    public ShowUpableBillForm getEditor() {
        return this.editor;
    }

    public void setEditor(ShowUpableBillForm editor) {
        this.editor = editor;
    }

    /**
     * ���
     * IValidationService
     */
    public IValidationService getValidationService() {
        return this.validationService;
    }

    public void setValidationService(IValidationService validationService) {
        this.validationService = validationService;
    }

    /**
     * ���
     * String
     */
    public String getToolTipText() {
        return this.toolTipText;
    }

    public void setToolTipText(String toolTipText) {
        this.toolTipText = toolTipText;
    }

    /**
     * ���
     * String
     */
    public int getMnemonic() {
        return this.Mnemonic;
    }

    public void setMnemonic(int mnemonic) {
        this.Mnemonic = mnemonic;
    }

}
