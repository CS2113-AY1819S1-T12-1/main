//@@author cqinkai
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.EditEventDescriptor;
import static seedu.address.logic.commands.EditCommand.createEditedEvent;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.Status;

/**
 * Updates status of events listed in {@code lastShownList} based on new {@code Date}.
 * New status identified by {@code setStatus}.
 */
public class UpdateStatusCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_SUCCESS = "Updated all event statuses.";

    public static final String MESSAGE_MISSING_EVENTS = "No events to update.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (lastShownList.isEmpty()) {
            return new CommandResult(MESSAGE_MISSING_EVENTS);
        }

        for (Event updatingEvent : lastShownList) {
            EditEventDescriptor updatedStatusDescriptor = new EditEventDescriptor();

            Status updatedStatus = new Status(Status.setStatus(updatingEvent.getDateTime()));
            updatedStatusDescriptor.setStatus(updatedStatus);
            Event updatedEvent = createEditedEvent(updatingEvent, updatedStatusDescriptor);

            model.updateEvent(updatingEvent, updatedEvent);
        }
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
