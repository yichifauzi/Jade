package snownee.jade.gui.config.value;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.EditBox;

public class InputOptionValue<T> extends OptionValue<T> {

	public static final Predicate<String> INTEGER = s -> s.matches("[-+]?[0-9]+");
	public static final Predicate<String> FLOAT = s -> s.matches("[-+]?([0-9]*[.,][0-9]+|[0-9]+)");

	private final EditBox textField;
	private final Predicate<String> validator;

	public InputOptionValue(Runnable responder, String optionName, Supplier<T> getter, Consumer<T> setter, Predicate<String> validator) {
		super(optionName, getter, setter);
		this.validator = validator;
		textField = new EditBox(client.font, 0, 0, 98, 18, getTitle());
		updateValue();
		textField.setResponder(s -> {
			if (this.validator.test(s)) {
				textField.setTextColor(Objects.requireNonNull(ChatFormatting.WHITE.getColor()));
			} else {
				textField.setTextColor(Objects.requireNonNull(ChatFormatting.RED.getColor()));
			}
			responder.run();
		});
		addWidget(textField, 0);
	}

	@Override
	public boolean isValidValue() {
		return validator.test(textField.getValue());
	}

	@Override
	public void setValue(T value) {
		textField.setValue(String.valueOf(value));
	}

	@Override
	public void updateValue() {
		value = getter.get();
		textField.setValue(String.valueOf(value));
	}

}
