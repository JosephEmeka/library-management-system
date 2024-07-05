package elibrary.enums;


import lombok.Getter;

@Getter
public enum Category {
    FICTION("Fiction"),
    NON_FICTION("Non-Fiction"),
    MYSTERY("Mystery"),
    SCIENCE_FICTION("Science Fiction"),
    FANTASY("Fantasy"),
    HORROR("Horror"),
    ROMANCE("Romance"),
    HISTORY("History"),
    BIOGRAPHY("Biography"),
    SELF_HELP("Self-Help"),
    REFERENCE("Reference"),
    COOKBOOK("Cookbook"),
    ART("Art"),
    TRAVEL("Travel"),
    POETRY("Poetry"),
    CHILDREN("Children's"),
    YOUNG_ADULT("Young Adult");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

}
