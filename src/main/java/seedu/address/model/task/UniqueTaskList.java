package seedu.address.model.task;

import java.util.Collection;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.EditCommand;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicatePersonException extends DuplicateDataException {
        protected DuplicatePersonException() {
            super("Operation would result in duplicate persons");
        }
    }

    /**
     * Signals that an operation targeting a specified person in the list would fail because
     * there is no such matching person in the list.
     */
    public static class PersonNotFoundException extends Exception {}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PersonList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Task toAdd) throws DuplicatePersonException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws PersonNotFoundException {
        assert toRemove != null;
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return personFoundAndDeleted;
    }

    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    public boolean edit(ReadOnlyTask key, String args) throws IllegalValueException {
        // TODO Auto-generated method stub
        String keyword = args.substring(0, args.indexOf(' '));
        args = args.substring(args.indexOf(' ') + 1);
        
        int editIndex = internalList.indexOf(key);
        //System.out.println(keyword);
        //System.out.println(args);
        //System.out.println(keyword.equals(EditCommand.DESCRIPTION_WORD));
        if (keyword.equals(EditCommand.DESCRIPTION_WORD)) {
            //internalList.get(editIndex).setName(new Name(args));
            Task toEdit = new Task(internalList.get(editIndex));
            toEdit.setName(new Name(args));
            internalList.set(editIndex, toEdit);
            System.out.println("dummy2");
            return true;
        } else if (keyword.equals(EditCommand.DATE_WORD)) {
            //internalList.get(editIndex).setDate(new Date(args));
            Task toEdit = new Task(internalList.get(editIndex));
            toEdit.setDate(new Date(args));
            internalList.set(editIndex, toEdit);
            return true;
        } else if (keyword.equals(EditCommand.START_WORD)) {
            //internalList.get(editIndex).setStart(new Start(args));
            Task toEdit = new Task(internalList.get(editIndex));
            toEdit.setStart(new Start(args));
            internalList.set(editIndex, toEdit);
            return true;
        } else if (keyword.equals(EditCommand.END_WORD)) {
            //internalList.get(editIndex).setEnd(new End(args));
            Task toEdit = new Task(internalList.get(editIndex));
            toEdit.setEnd(new End(args));
            internalList.set(editIndex, toEdit);
            return true;
        } /*else if (keyword.equals(EditCommand.TAG_WORD)) {
            internalList.get(editIndex).setTag(new Tag(args));*/
        else {
            return false;
        }
    }

}
