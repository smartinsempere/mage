package mage.view;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AbilityPickerView implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<UUID, String> choices = new LinkedHashMap<>();
    private String message = null;

    public AbilityPickerView(String objectName, List<? extends Ability> abilities, String message) {
        this.message = message;

        int num = 0;
        for (Ability ability : abilities) {
            num++;
            String rule;
            if (objectName == null) {
                rule = ability.getRule(true);
            } else {
                // spell abilities must start with "Cast name" (split cards have different names for each spell part)
                if (ability instanceof SpellAbility) {
                    SpellAbility spell = (SpellAbility) ability;
                    rule = getAbilityRules(spell, spell.getCardName());
                    if (!rule.startsWith("Cast ")) {
                        rule = spell.toString() + ": " + rule; // spell.toString() must return this.name (example: Cast Armed)
                    }
                } else {
                    rule = getAbilityRules(ability, objectName);
                }
            }
            choices.put(ability.getId(), num + ". " + rule);
        }
    }

    private String getAbilityRules(Ability ability, String objectName) {
        String rule = ability.getRule(objectName);
        if (rule.isEmpty()) {
            rule = ability.toString();
        }
        rule = Character.toUpperCase(rule.charAt(0)) + rule.substring(1);
        return rule;
    }

    public AbilityPickerView(Map<UUID, String> modes, String message) {
        this.choices = modes;
        this.message = message;
    }

    public Map<UUID, String> getChoices() {
        return choices;
    }

    public String getMessage() {
        return message;
    }
}
