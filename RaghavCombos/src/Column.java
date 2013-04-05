import java.util.ArrayList;

/**
 * Column with subjects that can have English in it. Has an internal pointer to the current element
 * and allows the query of all elements sequentially (wrapping). Can also remove English when requested.
 * 
 * @author shade
 *
 */
public class Column implements Comparable<Column> {
	public ArrayList<String> mSubjects;
	public boolean mHasEnglish = false;
	private int mEnglishPos = -1;
	private int mOffset = 0;
	
	public Column () {
		// at max 10 subjects initially
		this.mSubjects = new ArrayList<String> (10);
	}
	
	public void insertSubject (String subject) {
		// so that we get only single subjects
		String [] subjects = subject.split ("\\/");
		
		for (String singleSubject : subjects) {
			this.mSubjects.add(singleSubject);
			
			if (singleSubject.equals ("English")) {
				this.mHasEnglish = true;
				this.mEnglishPos = this.mSubjects.size () - 1;
			}
		}
	}
	
	public boolean hasNext () {
		if (this.mOffset >= mSubjects.size ()) {
			this.mOffset = 0;
			return false;
		}
		
		return true;
	}
	
	public String next () {
		if (mSubjects.size() <= mOffset)
			return "NO MOAR";
		
		mOffset += 1;
		return this.mSubjects.get (mOffset - 1);
	}
	
	public boolean hasEnglish  () {
		return mHasEnglish;
	}
	
	public boolean removeEnglish () {
		if (this.mHasEnglish) {
			this.mSubjects.remove (mEnglishPos);
			this.mHasEnglish = false;
			return true;
		}
		
		return false;
	}

	@Override
	public int compareTo (Column otherColumn) {
		// this should be a forward sort. looks to me like a reverse one, but
		// don't really care - works
		if (this.mHasEnglish && otherColumn.hasEnglish ())
			return 0;
		else if (this.mHasEnglish && !otherColumn.hasEnglish())
			return -1;
		else
			return 1;
			
	}
}
