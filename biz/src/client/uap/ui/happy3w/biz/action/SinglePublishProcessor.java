package uap.ui.happy3w.biz.action;

import nc.ui.pubapp.pub.task.ISingleBillService;
import nc.ui.uif2.UIState;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import uap.ui.happy3w.pub.action.SingleProcessor;

/**
 * �����첽ִ�з�����
 * 
 * @since 6.5
 * @version 2016��3��22�� ����10:46:17
 * @author caroline
 */

public class SinglePublishProcessor extends SingleProcessor {

    /**
     * ����̬��
     */
    private static final String FREE = "1";

    private String dealStatusField = "dealstatus";

    /**
     * ���DealStatus�ֶ���,�����ж϶���״̬�Ͱ�ť����״̬��
     * String
     */
    public String getDealStatusField() {
        return this.dealStatusField;
    }

    /**
     * ����DealStatus�ֶ���,�����ж϶���״̬�Ͱ�ť����״̬��
     * 
     * @param dealStatusField
     */
    public void setDealStatusField(String dealStatusField) {
        this.dealStatusField = dealStatusField;
    }

    @Override
    public ISingleBillService<Object> getService() {
        return this.service;
    }

    /**
     * ѡ������Status����̬ʱ���ã�����������
     * 
     * @return
     */
    @Override
    public boolean isActionEnable() {
        // ѡ����������̬ʱ���ã����������á�
        if (this.model.getUiState() == UIState.NOT_EDIT) {
            if (this.getModel().getSelectedData() != null) {
                AbstractBill vo = (AbstractBill) this.getModel().getSelectedData();
                if (null == this.getDealStatusField() || this.getDealStatusField().isEmpty()) {
                    // û������״̬����
                    return true;
                }
                if (null != vo
                        && SinglePublishProcessor.FREE.equals(vo.getParentVO().getAttributeValue(
                                this.getDealStatusField()))) {
                    return true;
                }
            }
        }
        return false;
    }
}
