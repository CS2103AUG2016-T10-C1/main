package guitests;

import guitests.guihandles.PersonCardHandle;
import org.junit.Test;

import seedu.simply.commons.core.Messages;
import seedu.simply.logic.commands.AddCommand;
import seedu.simply.testutil.TestTask;
import seedu.simply.testutil.TestUtil;
import seedu.simply.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class AddEventCommandTest extends SimplyGuiTest {

    @Test
    public void add() {
        //add one person
        TestTask[] currentList = td.getTypicalPersons();
        TestTask personToAdd = TypicalTestTasks.hoon;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

        //add another person
        personToAdd = TypicalTestTasks.ida;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

        //add duplicate person
        commandBox.runCommand(TypicalTestTasks.hoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(personListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTasks.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        PersonCardHandle addedCard = personListPanel.navigateToPerson(personToAdd.getName().toString());
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addPersonsToList(currentList, personToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
