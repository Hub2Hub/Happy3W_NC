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
 * 支持批量异步操作。
 * 
 * @since 6.5
 * @version 2016年5月12日 上午9:54:41
 * @author caroline
 */
public class BatchProcessor extends ActionProcessor implements IMultiReturnObjProcessor {

    /**
     * 进度对话框表体。（需注入）
     */
    private String title;

    // 是否显示登录。
    private boolean showLogInDialog = true;

    // 多选执行器。
    private MultiBillTaskRunner<Object> multiBillTaskRunner;

    private Object[] selectDatas;

    /**
     * 获得对话框表体。
     * String
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * 对话框表体。
     * 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获得
     * boolean
     */
    public boolean isShowLogInDialog() {
        return this.showLogInDialog;
    }

    /**
     * 设置显示登录窗口，默认显示。
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
     * 获得多选任务执行器。
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
