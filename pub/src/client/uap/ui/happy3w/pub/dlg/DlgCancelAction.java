package uap.ui.happy3w.pub.dlg;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * ��ȡ����ť��
 * 
 * @since 6.5
 * @version 2016��4��17�� ����8:13:54
 * @author caroline
 */

public class DlgCancelAction extends DlgAction {

    private static final long serialVersionUID = 8463684684793460923L;

    public DlgCancelAction() {
        super();
        this.setBtnCode("btnCancel");
        this.setBtnName("ȡ��");
        this.setMnemonic(KeyEvent.VK_C);
        this.setToolTipText("�رմ��ڣ�Alt+C");
    }

    @Override
    public void doAction(ActionEvent e) throws Exception {
        this.dialog.closeCancel();
    }

}
