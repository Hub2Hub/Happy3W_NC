package uap.ui.happy3w.pub.view;

import nc.ui.pubapp.uif2app.FuncletDialog;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.uif2.TangramContainer;
import nc.ui.uif2.model.AbstractAppModel;

/**
 * �������ô���������
 * 
 * @since 6.5
 * @version 2016��4��12�� ����10:41:32
 * @author chenjij
 */

public class CommonTangramContainer extends TangramContainer {

    private static final long serialVersionUID = 8166655660281196612L;

    private FuncletDialog dialog;

    private AbstractAppModel model;

    private ShowUpableBillForm editor;

    /**
     * ���
     * AbstractAppModel
     */
    public AbstractAppModel getModel() {
        return this.model;
    }

    public void setModel(AbstractAppModel model) {
        this.model = model;
    }

    /**
     * ���
     * FuncletDialog
     */
    public FuncletDialog getDialog() {
        return this.dialog;
    }

    public void setDialog(FuncletDialog dialog) {
        this.dialog = dialog;
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

}
