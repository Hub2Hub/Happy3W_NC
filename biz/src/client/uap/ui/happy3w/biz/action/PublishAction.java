package uap.ui.happy3w.biz.action;

import java.awt.Container;

import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import uap.ui.happy3w.pub.action.AsynAction;
import uap.ui.happy3w.pub.action.SingleProcessor;

/**
 * @since 6.5
 * @version 2016��5��12�� ����2:48:59
 * @author caroline
 */

public class PublishAction extends AsynAction {
    private static final long serialVersionUID = 7902960506039090906L;

    public PublishAction() {
        super();
        this.setCode("publish");
        this.setBtnName("����");
        this.setTitle("������ѡ����");
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
     * ȷ�Ϸ����Ի���
     * 
     * @param parent �Ի�������������
     * @return ѡ��ť��
     */
    @Override
    public int showConfirmDialog(Container parent) {
        String TITLE = "ȷ�Ϸ���";
        String QUESTION = "���������޸ĺ�ɾ�������Ƿ�ȷ�϶���ѡ����ִ�з�����";
        return MessageDialog.showYesNoDlg(parent, TITLE, QUESTION, UIDialog.ID_NO);
    }
}
