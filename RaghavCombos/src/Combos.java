import java.util.ArrayList;
import java.util.Collections;

/**
 * Answer to Raghav's question on Stack Overflow:
 * http://stackoverflow.com/questions/15827887/all-possible-combinations-from-non-uniform-table-with-only-one-value-from-each/15830656#15830656
 * 
 * @author shade
 *
 */
public class Combos {
	
	/**
	 * Just a filler.
	 * @return the initial columns with one subject per row
	 */
	private static ArrayList<Column> getColumns () {
		ArrayList<Column> columns = new ArrayList<Column> (5);
		Column col1 = new Column ();
		col1.insertSubject ("Economics");
		col1.insertSubject ("Hindi/Psychology");
		col1.insertSubject ("Maths");
		col1.insertSubject ("English");
		columns.add (col1);
		
		Column col2 = new Column ();
		col2.insertSubject ("Maths");
		col2.insertSubject ("History");
		col2.insertSubject ("Account");
		col2.insertSubject ("Physics");
		columns.add (col2);
		
		Column col3 = new Column ();
		col3.insertSubject ("Psychology/Politcal Science");
		col3.insertSubject ("Sociology");
		col3.insertSubject ("Commerce");
		col3.insertSubject ("Chemistry");
		columns.add (col3);
		
		Column col4 = new Column ();
		col4.insertSubject ("English");
		col4.insertSubject ("Art");
		col4.insertSubject ("Economics");
		col4.insertSubject ("Biology/Computer Applications/Mecahnical drawing");
		columns.add (col4);
		
		Column col5 = new Column ();
		col5.insertSubject ("Geography");
		col5.insertSubject ("Elective English");
		col5.insertSubject ("English");
		col5.insertSubject ("Maths");
		columns.add (col5);
		
		return columns;
	}
	
	/**
	 * Checks whether we still have English in any of the columns
	 * @param columns - the columns to check
	 * @return whether we still have English in any of the columns
	 */
	public static boolean stillHaveEnglish (ArrayList<Column> columns) {
		for (Column thisCol : columns)
			if (thisCol.hasEnglish())
				return true;
		
		return false;
	}
	
	/**
	 * Helper function. Prints all elements of a current selection.
	 * @param selection
	 */
	private static void printSelection (ArrayList<String> selection) {
		System.out.println ("START: English");
		
		for (String thisSubject : selection)
			System.out.println (thisSubject);
		
		System.out.println ("EOS\n\n");
	}
	
	/**
	 * Recursive function to print all combinations without duplication. Also, English is ignored. 
	 * @param columns - the pre-filled columns with subjects
	 * @param currentSelection - the current selection so far
	 * @param position - the column position we're using now
	 */
	private static void printCombos (ArrayList<Column> columns, ArrayList<String> currentSelection, int position) {
		// if we're at the end of the list, then we're iterating the last column
		// remove the last element and print with the next element of the last column 
		if (columns.size() <= position) {
			printSelection (currentSelection);
			currentSelection.remove (currentSelection.size () - 1);
			printCombos (columns, currentSelection, position - 1);
		}
		
		// we've gone back to print the next element, but we don't have a next element
		// we go one step back checking whether we're at the beginning of the list
		// if we're at the beginning, then we're done
		else if (!columns.get(position).hasNext ()) {
			int size = currentSelection.size();
			if (size < 1)
				return;
			
			currentSelection.remove(size - 1);
			
			printCombos (columns, currentSelection, position - 1);
			return;
		}
		
		// since we're here, then we are sure we have something at this position that hasNext
		// however, we have to perform deduplication and have to also avoid English
		// so, if we're getting more elements than there are in the column, we may need to go one step back,
		// or quit altogether if we're done
		else {
			String next = columns.get(position).next ();
			while (currentSelection.contains (next) || "English".equals (next))
				if (columns.get(position).hasNext ())
					next = columns.get(position).next ();
				else
					next = null;
			if (next != null) {
				currentSelection.add(next);
				printCombos (columns, currentSelection, position + 1);
			}
			else {
				int size = currentSelection.size();
				if (size < 1)
					return;
				
				currentSelection.remove(size - 1);
				
				printCombos (columns, currentSelection, position - 1);
				return;
			}
		}
	}
	
	public static void main(String[] args) {
		// get the pre-filled columns
		ArrayList<Column> columns = getColumns ();
		Collections.sort (columns);
		
		// if we no longer have English, then no more combos are possible
		while (stillHaveEnglish (columns)) {
			Column thisCol = columns.get(0);
			columns.remove (0);
			
			ArrayList<String> newSelection = new ArrayList<String> (10);
			
			printCombos (columns, newSelection, 0);
			
			// we have used the English in this col, so let's remove it
			thisCol.removeEnglish ();
			columns.add (thisCol);
			Collections.sort (columns);
		}
	}
}
