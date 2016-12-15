package uap.ui.happy3w.biz.action;

import java.awt.Container;

import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import uap.ui.happy3w.pub.action.AsynAction;
import uap.ui.happy3w.pub.action.SingleProcessor;

/**
 * @since 6.5
 * @version 2016年5月12日 下午2:48:59
 * @author caroline
 */

public class PublishAction extends AsynAction {
    private static final long serialVersionUID = 7902960506039090906L;

    public PublishAction() {
        super();
        this.setCode("publish");
        this.setBtnName("发布");
        this.setTitle("发布所选数据");
    }

    @Override
    public void initAction() {
        super.initAction();
        SingleProcessor single = new SinglePublishProcessor();
        single.setModel(this.getModel());
        single.setService(this.getService());
        this.setSingleProcessor(single);
    }

    /**
     * 确认发布对话框。
     * 
     * @param parent 对话框所属父窗。
     * @return 选择按钮。
     */
    @Override
    public int showConfirmDialog(Container parent) {
        String TITLE = "确认发布";
        String QUESTION = "发布后不能修改和删除，您是否确认对所选数据执行发布？";
        return MessageDialog.showYesNoDlg(parent, TITLE, QUESTION, UIDialog.ID_NO);
    }
}
