package com.company.command;

import com.company.ConsoleHelper;

/**
 * Вспомогательная команда.
 * Команда для выхода из приложения.
 */
public class ExitCommand implements Command {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("До встречи!");
    }
}
