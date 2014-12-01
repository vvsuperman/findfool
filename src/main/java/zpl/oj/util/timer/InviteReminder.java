package zpl.oj.util.timer;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.stereotype.Service;

import zpl.oj.dao.InviteDao;
import zpl.oj.model.common.Invite;

@Service
public class InviteReminder {
	
	private Timer timer;
	private int inviteId;
	private InviteDao inviteDao;
	
    public InviteReminder(int minutes,int inviteId) {
        timer = new Timer();
        timer.schedule(new RemindTask(), minutes*60000);
    }

    class RemindTask extends TimerTask {
        public void run() {
            Invite invite = new Invite();
            invite.setIid(inviteId);
            invite.setState(0);
            inviteDao.updateInvite(invite);
            timer.cancel(); //Terminate the timer thread
        }
    }

}
