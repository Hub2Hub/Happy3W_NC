package uap.ui.happy3w.pub.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.pub.beans.progress.IProgressMonitor;
import nc.ui.pubapp.pub.task.ISingleBillService;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.components.progress.ProgressActionInterface;
import nc.ui.uif2.components.progress.TPAProgressUtil;
import nc.ui.uif2.model.AbstractAppModel;
import nc.vo.pubapp.pattern.model.entity.bill.IBill;
import nc.vo.pubapp.pattern.model.transfer.bill.ClientBillCombinServer;

/**
 * @since 6.5
 * @version 2016年5月12日 上午11:07:57
 * @author caroline
 */

public abstract class ActionProcessor implements ProgressActionInterface {
    // 进度监测器。
    protected IProgressMonitor monitor = null;

    // 后台服务代理（必须注入）
    protected ISingleBillService<Object> service;

    // 应用模型(必须注入)。
    protected AbstractAppModel model;

    // 是否蒙版。
    protected boolean isTPAMonitor = true;

    // 进度条。
    protected TPAProgressUtil tpaProgressUtil;

    public ISingleBillService<Object> getService() {
        return this.service;
    }

    public void setService(ISingleBillService<Object> service) {
        this.service = service;
    }

    public AbstractAppModel getModel() {
        return this.model;
    }

    public void setModel(AbstractAppModel model) {
        this.model = model;
    }

    public boolean isTPAMonitor() {
        return this.isTPAMonitor;
    }

    public void setTPAMonitor(boolean isTPAMonitor) {
        this.isTPAMonitor = isTPAMonitor;
    }

    /**
     * 获取进度条监测器，如果没有注入，系统根据是否蒙版给出默认值。
     * 
     * @return
     */
    public IProgressMonitor getMonitor() {
        return this.monitor;
    }

    public void setMonitor(IProgressMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public TPAProgressUtil getTpaProgressUtil() {
        if (null == this.tpaProgressUtil) {
            this.tpaProgressUtil = new TPAProgressUtil();
            this.tpaProgressUtil.setContext(this.getModel().getContext());
        }
        return this.tpaProgressUtil;
    }

    @Override
    public void setTpaProgressUtil(TPAProgressUtil tpaProgressUtil) {
        this.tpaProgressUtil = tpaProgressUtil;
    }

    public abstract boolean beforeStartDoAction(ActionEvent actionEvent);

    public abstract void doAction(ActionEvent e) throws Exception;

    public boolean doAfterFailure(Throwable ex) {
        if (this.monitor != null) {
            this.monitor.done();
            this.monitor = null;
        }
        this.showFailedInfo(ex.getMessage());
        return true;
    }

    protected void showFailedInfo(String detailMsg) {
        ShowStatusBarMsgUtil.showErrorMsg("操作失败。", detailMsg, this.getModel().getContext());
    }

    public void doAfterSuccess() {
        if (this.monitor != null) {
            this.monitor.done();
            this.monitor = null;
        }
        this.showSuccessInfo();
    }

    protected void showSuccessInfo() {
        ShowStatusBarMsgUtil.showStatusBarMsg("操作成功。", this.getModel().getContext());
    }

    /**
     * 合并后台最新数据更新前台，只更新后台服务返回的数据。
     * 
     * @param clientVOs 原客户端数据。
     * @param serverVOs 最新服务端数据。
     */
    protected void directlyUpdate(IBill[] clientVOs, IBill[] serverVOs) {
        Map<String, IBill> clientMap = new HashMap<String, IBill>();
        for (IBill vo : clientVOs) {
            clientMap.put(vo.getPrimaryKey(), vo);
        }
        // 移除与服务端返回不一致的数据。
        List<IBill> fromClientList = new ArrayList<IBill>();
        for (IBill vo : serverVOs) {
            if (clientMap.containsKey(vo.getPrimaryKey())) {
                fromClientList.add(clientMap.get(vo.getPrimaryKey()));
            }
        }
        IBill[] fromClientVOs = fromClientList.toArray(new IBill[0]);
        // 后台变化VO与前台合并
        new ClientBillCombinServer<IBill>().combine(fromClientVOs, serverVOs);
        this.getModel().directlyUpdate(clientVOs);
    }

    public abstract boolean isActionEnable();

}
