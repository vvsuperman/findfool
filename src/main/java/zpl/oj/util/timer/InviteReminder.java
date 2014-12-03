package zpl.oj.util.timer;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.InviteDao;
import zpl.oj.model.common.Invite;

/*
 * 
用于更新invite的计时器类
由于inviteDao无法被注入，而且inviteDao是个接口，无法new，因此直接使用参数传递了，会不会有问题
*/
public class InviteReminder {
	
	private Timer timer;
	private int inviteId;
	
	private InviteDao inviteDao;
	
    public InviteReminder(int minutes,int inviteId,InviteDao invDao) {
        timer = new Timer();
        timer.schedule(new RemindTask(), minutes*60000);
        this.inviteId = inviteId;
        this.inviteDao = invDao;
    }

    class RemindTask extends TimerTask {
        public void run() {
            Invite invite = inviteDao.getInviteById(inviteId);
            invite.setState(0);
            inviteDao.updateInvite(invite);
            timer.cancel(); //Terminate the timer thread
        }
    }

}
