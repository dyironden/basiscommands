package org.to2mbn.basiscommands.util.command;

import org.to2mbn.basiscommands.i18n.I18n;

import java.util.function.Function;

public class CommandArgumentTemplet<T> {
    private final Class<T> type;
    private final boolean isRequired;
    private final T defaultValue;
    private final Function<T, Boolean> isValid;
    private final String info;

    public CommandArgumentTemplet(Class<T> type, boolean isRequired, T defaultValue, Function<T, Boolean> isValid, String info) {
        this.type = type;
        this.isRequired = isRequired;
        this.defaultValue = defaultValue;
        this.isValid = isValid;
        this.info = info;
    }

    public CommandArgumentTemplet(Class<T> type, boolean isRequired, Function<T, Boolean> isValid, String info) {
        this(type, isRequired, null, isValid, info);
    }

    public CommandArgumentTemplet(Class<T> type, boolean isRequired, Function<T, Boolean> isValid) {
        this(type, isRequired, null, isValid, I18n.translate("command.invalid_arguments_msg"));
    }

    public CommandArgumentTemplet(Class<T> type, boolean isRequired) {
        this(type, isRequired, null, arg -> true, I18n.translate("command.invalid_arguments_msg"));
    }

    public Class<T> getType() {
        return type;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public Function<T, Boolean> getIsValid() {
        return isValid;
    }

    public String getInfo() {
        return info;
    }
}
