package cn.feng.skin.manager.listener;

public interface ISkinSubject {
	void attach(ISkinUpdateObserver observer);
	void detach(ISkinUpdateObserver observer);
	void notifySkinUpdate();
//	void notifySkinDefault();
}
