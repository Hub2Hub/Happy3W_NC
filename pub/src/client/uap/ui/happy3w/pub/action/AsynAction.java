package uap.ui.happy3w.pub.action;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.text.MessageFormat;

import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pubapp.pub.task.ISingleBillService;
import nc.ui.uif2.NCAsynAction;
import nc.ui.uif2.editor.BillListView;
import nc.ui.uif2.model.AbstractAppModel;
import nc.ui.uif2.model.IMultiRowSelectModel;

/**
 * �����첽ִ�в�����
 * 
 * @since 6.5
 * @version 2016��3��22�� ����10:46:17
 * @author caroline
 */

public class AsynAction extends NCAsynAction {
    private static final long serialVersionUID = 5982428667771237828L;

    private ISingleBillService<Object> service;

    private ActionProcessor processor;

    private BillListView listView;

    private AbstractAppModel model;

    private ActionProcessor batchProcessor;

    private ActionProcessor singleProcessor;

    private String title;

    /**
     * ���
     * ISingleBillService<Object>
     */
    public ISingleBillService<Object> getService() {
        return this.service;
    }

    public void setService(ISingleBillService<Object> service) {
        this.service = service;
    }

    /**
     * ���
     * BillListView
     */
    public BillListView getListView() {
        return this.listView;
    }

    public void setListView(BillListView listView) {
        this.listView = listView;
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
     * ActionProcessor
     */
    public ActionProcessor getBatchProcessor() {
        return this.batchProcessor;
    }

    public void setBatchProcessor(ActionProcessor batchProcessor) {
        this.batchProcessor = batchProcessor;
        if (null == this.processor) {
            this.processor = this.batchProcessor;
        }
    }

    /**
     * ���
     * ActionProcessor
     */
    public ActionProcessor getSingleProcessor() {
        return this.singleProcessor;
    }

    public void setSingleProcessor(ActionProcessor singleProcessor) {
        this.singleProcessor = singleProcessor;
        if (null == this.processor) {
            this.processor = singleProcessor;
        }
    }

    /**
     * ���
     * String
     */
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * ��ť���캯����
     */
    public AsynAction() {
        super();
    }

    public void initAction() {
        SingleProcessor single = new SingleProcessor();
        single.setModel(this.getModel());
        single.setService(this.getService());
        this.setSingleProcessor(single);

        BatchProcessor batch = new BatchProcessor();
        batch.setModel(this.getModel());
        batch.setService(this.getService());
        batch.setTitle(this.getTitle());
        this.setBatchProcessor(batch);
    }

    @Override
    public boolean beforeStartDoAction(ActionEvent actionEvent) throws Exception {
        boolean result = UIDialog.ID_YES == this.showConfirmDialog(this.model.getContext().getEntranceUI());
        if (result) {
            return this.processor.beforeStartDoAction(actionEvent);
        }
        return false;
    }

    /**
     * ȷ�ϲ����Ի���
     * 
     * @param parent �Ի�������������
     * @return ѡ��ť��
     */
    public int showConfirmDialog(Container parent) {
        String caption = "ȷ�ϴ���";
        String message = MessageFormat.format("ȷ�϶���ѡ����ִ��{0}��", this.getBtnName());
        return MessageDialog.showYesNoDlg(parent, caption, message, UIDialog.ID_NO);
    }

    private ActionProcessor getProcessor() {
        ActionProcessor actionProcessor = null;
        if (null != this.listView && this.listView.isVisible()) {
            Object[] selectDatas = null;
            if (this.model instanceof IMultiRowSelectModel) {
                selectDatas = ((IMultiRowSelectModel) this.model).getSelectedOperaDatas();
            }
            if (null != selectDatas && selectDatas.length > 1) {
                actionProcessor = this.batchProcessor;
            }
            else {
                actionProcessor = this.singleProcessor;
            }
        }
        else {
            actionProcessor = this.singleProcessor;
        }
        return actionProcessor;
    }

    @Override
    public void doAction(ActionEvent e) throws Exception {
        this.processor.doAction(e);
    }

    @Override
    public void doAfterSuccess(ActionEvent actionEvent) {
        this.processor.doAfterSuccess();
    }

    @Override
    public boolean doAfterFailure(ActionEvent actionEvent, Throwable ex) {
        return this.processor.doAfterFailure(ex);
    }

    @Override
    protected boolean isActionEnable() {
        this.processor = this.getProcessor();
        if (null == this.processor) {
            return false;
        }
        return this.processor.isActionEnable();
    }
}
