package Model.Entity.Skills;

import Model.Entity.Character.CharacterEntity;

import static Model.Enums.ItemSlot.ONEHANDED;

public class OneHandedWeapon extends VariableEffectSkill {

    public OneHandedWeapon() {}

    @Override
    public void activateSkill(CharacterEntity player) {
        player.useItemSlot(ONEHANDED);
    }
}
