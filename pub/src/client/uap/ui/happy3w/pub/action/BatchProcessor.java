package uap.ui.happy3w.pub.action;

import java.awt.event.ActionEvent;
import java.util.Arrays;

import nc.ui.pubapp.pub.task.IMultiReturnObjProcessor;
import nc.ui.pubapp.pub.task.MultiBillTaskRunner;
import nc.ui.pubapp.uif2app.model.BillManageModel;
import nc.ui.uif2.UIState;
import nc.ui.uif2.model.IMultiRowSelectModel;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.pattern.model.entity.bill.IBill;

/**
 * ֧�������첽������
 * 
 * @since 6.5
 * @version 2016��5��12�� ����9:54:41
 * @author caroline
 */
public class BatchProcessor extends ActionProcessor implements IMultiReturnObjProcessor {

    /**
     * ���ȶԻ�����塣����ע�룩
     */
    private String title;

    // �Ƿ���ʾ��¼��
    private boolean showLogInDialog = true;

    // ��ѡִ������
    private MultiBillTaskRunner<Object> multiBillTaskRunner;

    private Object[] selectDatas;

    /**
     * ��öԻ�����塣
     * String
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * �Ի�����塣
     * 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * ���
     * boolean
     */
    public boolean isShowLogInDialog() {
        return this.showLogInDialog;
    }

    /**
     * ������ʾ��¼���ڣ�Ĭ����ʾ��
     * 
     * @param showLogInDialog
     */
    public void setShowLogInDialog(boolean showLogInDialog) {
        if (this.multiBillTaskRunner != null) {
            this.multiBillTaskRunner.setShowLogInDialog(showLogInDialog);
        }
        this.showLogInDialog = showLogInDialog;
    }

    /**
     * ��ö�ѡ����ִ������
     * MultiBillTaskRunner<Object>
     */
    public MultiBillTaskRunner<Object> getMultiBillTaskRunner() {
        if (this.multiBillTaskRunner == null) {
            this.multiBillTaskRunner = new MultiBillTaskRunner<Object>(this.getService());
            if (this.isTPAMonitor()) {
                this.multiBillTaskRunner.setTpaProgressUtil(this.getTpaProgressUtil());
            }
            this.multiBillTaskRunner.setTPAMonitor(this.isTPAMonitor());
            this.multiBillTaskRunner.setShowLogInDialog(this.isShowLogInDialog());
        }
        return this.multiBillTaskRunner;
    }

    @Override
    public boolean beforeStartDoAction(ActionEvent actionEvent) {
        return true;
    }

    @Override
    public void doAction(ActionEvent e) throws Exception {
        if (this.model instanceof IMultiRowSelectModel) {
            this.selectDatas = ((IMultiRowSelectModel) this.model).getSelectedOperaDatas();
        }
        if (null == this.selectDatas) {
            return;
        }
        this.getMultiBillTaskRunner().setOperateObjs(this.selectDatas);
        this.getMultiBillTaskRunner().setTitle(this.title);
        this.getMultiBillTaskRunner().setMultiReturnObjProcessor(this);
        this.getMultiBillTaskRunner().runTask();
    }

    @Override
    public void processReturnObjs(Object[] returnObj) {
        try {
            IBill[] arySelectDatas = Arrays.copyOf(this.selectDatas, this.selectDatas.length, IBill[].class);
            IBill[] aryReturnObj = Arrays.copyOf(returnObj, returnObj.length, IBill[].class);

            this.directlyUpdate(arySelectDatas, aryReturnObj);
            if (this.getService() != null && this.getMultiBillTaskRunner().isTaskSuccessful()) {
                this.showSuccessInfo();
            }
            else {
                this.showFailedInfo(this.getMultiBillTaskRunner().getFailedMessage());
            }
        }
        catch (Exception e) {
            ExceptionUtils.wrappBusinessException(e.getMessage(), e.getLocalizedMessage());
        }
    }

    @Override
    public void doAfterSuccess() {
    }

    @Override
    public boolean doAfterFailure(Throwable ex) {
        return true;
    }

    @Override
    public boolean isActionEnable() {
        if (this.getModel().getUiState() == UIState.NOT_EDIT) {
            Object[] objs = ((BillManageModel) this.getModel()).getSelectedOperaDatas();
            if (null != objs && objs.length > 1) {
                return true;
            }
        }
        return false;
    }
}
