package model.enums;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import view.FXStyles;

import java.util.function.Function;

public class EnumDropdown<T extends Enum<T>> extends ComboBox<T> {
    public EnumDropdown(T[] values, Function<T, String> displayMapper) {
        super(FXCollections.observableArrayList(values));
        setConverter(new StringConverter<>() {
            @Override
            public String toString(T item) {
                return item == null ? "" : displayMapper.apply(item);
            }

            @Override
            public T fromString(String string) {
                return null;
            }
        });
        if (values.length > 0)
            setValue(values[0]);
        setStyle(FXStyles.MODERN_DROPDOWN);
    }
}