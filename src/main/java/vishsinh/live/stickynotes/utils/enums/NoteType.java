package vishsinh.live.stickynotes.utils.enums;

public enum NoteType {
    ALL(0),
    PUBLIC(1),
    PRIVATE(2);

    public final int value;

    NoteType(int i) {
        this.value = i;
    }
}
