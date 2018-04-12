package paket;

import java.io.Serializable;

public class SessionCounter implements Serializable {

	private static final long serialVersionUID = -5878154353222857662L;
	private int count = 0;

	public void inc() {
		count++;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int c) {
		count = c;
	}

}
