package zpl.oj.util.timer;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.InviteDao;
import zpl.oj.model.common.Invite;

public class InviteReminder {
	
	private Timer timer;
	private int inviteId;
	
	private InviteDao inviteDao;
	
    public InviteReminder(int minutes,int inviteId,InviteDao invDao) {
        timer = new Timer();
      //  timer.schedule(new RemindTask(), minutes*60000);
        timer.schedule(new RemindTask(), minutes*3000);
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
