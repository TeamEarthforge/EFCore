package com.earthforge.efcore.gui.dialog.data;

import com.github.bsideup.jabel.Desugar;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Desugar
public record DialogData(Map<String,CharacterData> characters, List<DialogPart> parts) {
    @Desugar
    public record DialogPart(String characterid, String content, @Nullable String emotion, String position){
        public String defaultPosition(DialogData root) {
            return position != null ? position :
                root.characters().get(characterid).defaultPosition();
        }
    }
    @Desugar
    public record CharacterData(String name, Map<String, String> images, String defaultPosition) {
        public String getImage(String emotion) {
            return images.getOrDefault(emotion, images.get("default"));
        }
    }
    public CharacterData getCharacter(String id) {
        return characters.get(id);
    }

}
