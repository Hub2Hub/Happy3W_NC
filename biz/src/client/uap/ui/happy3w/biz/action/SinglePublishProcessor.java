package uap.ui.happy3w.biz.action;

import nc.ui.pubapp.pub.task.ISingleBillService;
import nc.ui.uif2.UIState;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import uap.ui.happy3w.pub.action.SingleProcessor;

/**
 * 单条异步执行发布。
 * 
 * @since 6.5
 * @version 2016年3月22日 下午10:46:17
 * @author caroline
 */

public class SinglePublishProcessor extends SingleProcessor {

    /**
     * 自由态。
     */
    private static final String FREE = "1";

    private String dealStatusField = "dealstatus";

    /**
     * 获得DealStatus字段名,用于判断对象状态和按钮可用状态。
     * String
     */
    public String getDealStatusField() {
        return this.dealStatusField;
    }

    /**
     * 设置DealStatus字段名,用于判断对象状态和按钮可用状态。
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
     * 选中数据Status自由态时可用，其他不可用
     * 
     * @return
     */
    @Override
    public boolean isActionEnable() {
        // 选中数据自由态时可用，其他不可用。
        if (this.model.getUiState() == UIState.NOT_EDIT) {
            if (this.getModel().getSelectedData() != null) {
                AbstractBill vo = (AbstractBill) this.getModel().getSelectedData();
                if (null == this.getDealStatusField() || this.getDealStatusField().isEmpty()) {
                    // 没有设置状态，则
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
