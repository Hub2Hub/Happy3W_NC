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
 * @version 2016��5��12�� ����11:07:57
 * @author caroline
 */

public abstract class ActionProcessor implements ProgressActionInterface {
    // ���ȼ������
    protected IProgressMonitor monitor = null;

    // ��̨�����������ע�룩
    protected ISingleBillService<Object> service;

    // Ӧ��ģ��(����ע��)��
    protected AbstractAppModel model;

    // �Ƿ��ɰ档
    protected boolean isTPAMonitor = true;

    // ��������
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
     * ��ȡ����������������û��ע�룬ϵͳ�����Ƿ��ɰ����Ĭ��ֵ��
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
        ShowStatusBarMsgUtil.showErrorMsg("����ʧ�ܡ�", detailMsg, this.getModel().getContext());
    }

    public void doAfterSuccess() {
        if (this.monitor != null) {
            this.monitor.done();
            this.monitor = null;
        }
        this.showSuccessInfo();
    }

    protected void showSuccessInfo() {
        ShowStatusBarMsgUtil.showStatusBarMsg("�����ɹ���", this.getModel().getContext());
    }

    /**
     * �ϲ���̨�������ݸ���ǰ̨��ֻ���º�̨���񷵻ص����ݡ�
     * 
     * @param clientVOs ԭ�ͻ������ݡ�
     * @param serverVOs ���·�������ݡ�
     */
    protected void directlyUpdate(IBill[] clientVOs, IBill[] serverVOs) {
        Map<String, IBill> clientMap = new HashMap<String, IBill>();
        for (IBill vo : clientVOs) {
            clientMap.put(vo.getPrimaryKey(), vo);
        }
        // �Ƴ������˷��ز�һ�µ����ݡ�
        List<IBill> fromClientList = new ArrayList<IBill>();
        for (IBill vo : serverVOs) {
            if (clientMap.containsKey(vo.getPrimaryKey())) {
                fromClientList.add(clientMap.get(vo.getPrimaryKey()));
            }
        }
        IBill[] fromClientVOs = fromClientList.toArray(new IBill[0]);
        // ��̨�仯VO��ǰ̨�ϲ�
        new ClientBillCombinServer<IBill>().combine(fromClientVOs, serverVOs);
        this.getModel().directlyUpdate(clientVOs);
    }

    public abstract boolean isActionEnable();

}
