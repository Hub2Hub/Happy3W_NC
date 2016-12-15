package uap.ui.happy3w.pub.view;

import nc.ui.pubapp.uif2app.FuncletDialog;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.uif2.TangramContainer;
import nc.ui.uif2.model.AbstractAppModel;

/**
 * 调度设置窗口容器。
 * 
 * @since 6.5
 * @version 2016年4月12日 上午10:41:32
 * @author chenjij
 */

public class CommonTangramContainer extends TangramContainer {

    private static final long serialVersionUID = 8166655660281196612L;

    private FuncletDialog dialog;

    private AbstractAppModel model;

    private ShowUpableBillForm editor;

    /**
     * 获得
     * AbstractAppModel
     */
    public AbstractAppModel getModel() {
        return this.model;
    }

    public void setModel(AbstractAppModel model) {
        this.model = model;
    }

    /**
     * 获得
     * FuncletDialog
     */
    public FuncletDialog getDialog() {
        return this.dialog;
    }

    public void setDialog(FuncletDialog dialog) {
        this.dialog = dialog;
    }

    /**
     * 获得
     * ShowUpableBillForm
     */
    public ShowUpableBillForm getEditor() {
        return this.editor;
    }

    public void setEditor(ShowUpableBillForm editor) {
        this.editor = editor;
    }

}
