package uap.ui.happy3w.pub.handler;

import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardHeadTailBeforeEditEvent;

/**
 * @since 6.5
 * @version 2016��3��18�� ����10:13:15
 * @author chenjij
 */

public class HeadTailBeforeEditHandler implements IAppEventHandler<CardHeadTailBeforeEditEvent> {

    @Override
    public void handleAppEvent(CardHeadTailBeforeEditEvent e) {
        e.setReturnValue(Boolean.TRUE);
    }

}
