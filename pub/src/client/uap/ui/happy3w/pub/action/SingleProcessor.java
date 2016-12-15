package uap.ui.happy3w.pub.action;

import java.awt.event.ActionEvent;

import nc.ui.pub.beans.progress.NCProgresses;
import nc.ui.uif2.UIState;
import nc.vo.pubapp.pattern.model.entity.bill.IBill;

/**
 * 单条异步执行操作。
 * 
 * @since 6.5
 * @version 2016年3月22日 下午10:46:17
 * @author caroline
 */

public class SingleProcessor extends ActionProcessor {

    @Override
    public boolean beforeStartDoAction(ActionEvent actionEvent) {
        if (null == this.model.getSelectedData()) {
            return false;
        }

        // 设置等待界面
        // 操作太频繁直接返回
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
        this.monitor.beginTask("操作中，请稍候... ", 1);
        this.monitor.setProcessInfo("操作中，请稍后……");
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
            // 选中数据自由态时可用，其他不可用。
            if (this.model.getUiState() == UIState.NOT_EDIT) {
                if (null != this.getModel().getSelectedData()) {
                    return true;
                }
            }
            return false;
        }
    }
}
