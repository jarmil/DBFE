package advencedDatabase;

import java.io.Serializable;



final class SectorMap implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6063181429878614682L;
	private int size;
	private int capacity;

	private Entry[] list;	

	public SectorMap() {
		capacity = 10;
		size = 0;
		list = new Entry[capacity];

	}

	public void add(Long position, Long length, Long freeSpace) {
		if (size >= capacity) {
			ensureCapacity();
		}
		Entry entry = new Entry(position, length, freeSpace);
		list[size] = entry;
		size++;
	}

	public void change(int index, Entry e) {
		if (index >= size) {
			return;
		}
		list[index] = e;
	}

	public Entry get(int index) {
		if (index >= size) {
			return null;
		}
		return list[index].clone();
	}

	private void ensureCapacity() {
		list = java.util.Arrays.copyOf(list, capacity * 2);
		capacity *= capacity;
	}

	public class Entry implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2161999142074966797L;
		private Long position;
		private Long length;
		private Long freeSpace;
		private boolean lock;
		private int maxObject;

		public Entry(Long position, Long length, Long freeSpace) {
			super();
			this.position = position;
			this.length = length;
			this.freeSpace = freeSpace;
		}

		public Long getPosition() {
			return position;
		}

		public void setPosition(Long pos) {
			if (pos <= 0)
				return;
			this.position = pos;
		}

		public Long getLength() {
			return length;
		}

		public void setLength(Long length) {
			if (length < 0)
				return;
			this.length = length;
		}

		public Long getFreeSpace() {
			return freeSpace;
		}

		public void setFreeSpace(Long freeSpace) {
			if (freeSpace < 0)
				return;
			this.freeSpace = freeSpace;
		}

		public boolean isLock() {
			return lock;
		}

		public void setLock(boolean lock) {
			this.lock = lock;
		}

		public int getMaxObject() {
			return maxObject;
		}

		public void setMaxObject(int maxObject) {
			this.maxObject = maxObject;
		}
		
		protected Entry clone() {
			return new Entry(position, length, freeSpace);
		}
	}
}