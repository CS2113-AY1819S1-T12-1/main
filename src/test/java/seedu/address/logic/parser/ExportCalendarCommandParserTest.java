package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ExportCalendarCommand;

public class ExportCalendarCommandParserTest {

    private ExportCalendarCommandParser parser = new ExportCalendarCommandParser();

    @Test
    public void parse_validArgument_success() {
        String filename = "mycal";
        assertParseSuccess(parser, "  mycal ", new ExportCalendarCommand(filename));

        filename = "@#!$%@";
        assertParseSuccess(parser, "   @#!$%@   ", new ExportCalendarCommand(filename));
    }

    @Test
    public void parse_invalidArgument_failure() {
        //empty file name
        assertParseFailure(parser, "      ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCalendarCommand.MESSAGE_USAGE));

        //name longer than 255 characters
        assertParseFailure(parser, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCalendarCommand.MESSAGE_USAGE));

        //invalid special characters
        assertParseFailure(parser, "C/::*?", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCalendarCommand.MESSAGE_USAGE));
    }

}
