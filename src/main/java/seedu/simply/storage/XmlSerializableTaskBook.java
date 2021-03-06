package seedu.simply.storage;

import seedu.simply.commons.exceptions.IllegalValueException;
import seedu.simply.model.ReadOnlyTaskBook;
import seedu.simply.model.tag.Tag;
import seedu.simply.model.tag.UniqueTagList;
import seedu.simply.model.task.ReadOnlyTask;
import seedu.simply.model.task.UniqueTaskList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable TaskBook that is serializable to XML format
 */
@XmlRootElement(name = "taskbook")
public class XmlSerializableTaskBook implements ReadOnlyTaskBook {

    @XmlElement
    private List<XmlAdaptedTask> events;
    @XmlElement
    private List<XmlAdaptedTask> deadlines;
    @XmlElement
    private List<XmlAdaptedTask> todos;
    @XmlElement
    private List<Tag> tags;

    {
        events = new ArrayList<>();
        deadlines = new ArrayList<>();
        todos = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTaskBook() {}

    /**
     * Conversion
     */
    public XmlSerializableTaskBook(ReadOnlyTaskBook src) {
        events.addAll(src.getEventList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        deadlines.addAll(src.getDeadlineList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        todos.addAll(src.getTodoList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags = src.getTagList();
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        try {
            return new UniqueTagList(tags);
        } catch (UniqueTagList.DuplicateTagException e) {
            //TODO: better error handling
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UniqueTaskList getUniqueEventList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : events) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public UniqueTaskList getUniqueDeadlineList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : deadlines) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public UniqueTaskList getUniqueTodoList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : todos) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> getEventList() {
        return events.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<ReadOnlyTask> getDeadlineList() {
        return deadlines.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<ReadOnlyTask> getTodoList() {
        return todos.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

}
