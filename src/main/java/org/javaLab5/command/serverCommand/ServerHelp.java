package org.javaLab5.command.serverCommand;

import org.javaLab5.command.CommandArgumentList;

public class ServerHelp extends ServerCommand{
    @Override
    public ServerResponse execute(CommandArgumentList args){
        String Text = """
                help:\s
                \toutput help for available commands
                info:\s
                \toutput information about the collection (type, initialization date, number of elements, etc.) to the standard output stream
                show:\s
                \toutput all elements of the collection in a string representation to the standard output stream
                add {element}:\s
                \tadd a new element to the collection
                update id {element}:\s
                \tupdate the value of the element a collection whose id is equal to the specified
                remove_by_id id:\s
                \tdelete an item from the collection by its id
                clear:\s
                \tclear the collection
                save:\s
                \tsave the collection to a file
                execute_script file_name:\s
                \tread and execute the script from the specified file. The script contains commands in the same form as they are entered by the user interactively.
                exit:\s
                \tterminate the program (without saving to a file)
                add_if_max {element}:\s
                \tadd a new element to the collection if its value exceeds the value of the largest element in this collection
                add_if_min {element}:\s
                \tadd a new element to the collection if its value is less than that of the smallest element in this collection
                remove_last:\s
                \tremove the last element from the collection
                remove_lower {element}:\s
                \tremove all elements from collection that are less than the specified one
                group_counting_by_distance distance:\s
                \tgroup collection elements by distance field and show counts per group
                max_by_creation_date:\s
                \tdisplay the element with the latest creation date
                count_greater_than_distance distance:\s
                \toutput the number of elements whose value of the distance field is greater than the specified one
                """;

        return new ServerResponse(ResponseStatus.TEXT, Text);
    }
}
