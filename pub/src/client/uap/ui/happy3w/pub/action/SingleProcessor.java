package uap.ui.happy3w.pub.action;

import java.awt.event.ActionEvent;

import nc.ui.pub.beans.progress.NCProgresses;
import nc.ui.uif2.UIState;
import nc.vo.pubapp.pattern.model.entity.bill.IBill;

/**
 * �����첽ִ�в�����
 * 
 * @since 6.5
 * @version 2016��3��22�� ����10:46:17
 * @author caroline
 */

public class SingleProcessor extends ActionProcessor {

    @Override
    public boolean beforeStartDoAction(ActionEvent actionEvent) {
        if (null == this.model.getSelectedData()) {
            return false;
        }

        // ���õȴ�����
        // ����̫Ƶ��ֱ�ӷ���
        if (this.monitor != null && !this.monitor.isDone()) {
            return false;
        }
        if (null == this.monitor) {
            if (this.isTPAMonitor()) {
                this.monitor = this.getTpaProgressUtil().getTPAProgressMonitor();
            }
            else {
                this.monitor = NCProgresses.createDialogProgressMonitor(this.model.getContext().getEntranceUI());
            }
        }
        this.monitor.beginTask("�����У����Ժ�... ", 1);
        this.monitor.setProcessInfo("�����У����Ժ󡭡�");
        return true;
    }

    @Override
    public void doAction(ActionEvent e) throws Exception {
        if (null == this.model.getSelectedData()) {
            return;
        }
        IBill clientVO = (IBill) this.model.getSelectedData();
        IBill serverVO = (IBill) this.getService().operateBill(clientVO);
        this.directlyUpdate(new IBill[] {
            clientVO
        }, new IBill[] {
            serverVO
        });
    }

    @Override
    public boolean isActionEnable() {
        {
            // ѡ����������̬ʱ���ã����������á�
            if (this.model.getUiState() == UIState.NOT_EDIT) {
                if (null != this.getModel().getSelectedData()) {
                    return true;
                }
            }
            return false;
        }
    }
}
