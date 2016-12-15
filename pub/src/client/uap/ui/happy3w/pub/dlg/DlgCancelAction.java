package uap.ui.happy3w.pub.dlg;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * 框取消按钮。
 * 
 * @since 6.5
 * @version 2016年4月17日 上午8:13:54
 * @author caroline
 */

public class DlgCancelAction extends DlgAction {

    private static final long serialVersionUID = 8463684684793460923L;

    public DlgCancelAction() {
        super();
        this.setBtnCode("btnCancel");
        this.setBtnName("取消");
        this.setMnemonic(KeyEvent.VK_C);
        this.setToolTipText("关闭窗口，Alt+C");
    }

    @Override
    public void doAction(ActionEvent e) throws Exception {
        this.dialog.closeCancel();
    }

}
