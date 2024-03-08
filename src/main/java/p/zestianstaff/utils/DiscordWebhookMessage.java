package p.zestianstaff.utils;
import java.util.ArrayList;
import java.util.List;

import java.util.List;

public class DiscordWebhookMessage {

    private String content;
    private List<Embed> embeds;

    public DiscordWebhookMessage(String content, List<Embed> embeds) {
        this.content = content;
        this.embeds = embeds;
    }

    public String getContent() {
        return content;
    }

    public List<Embed> getEmbeds() {
        return embeds;
    }

    public static class Embed {
        private String title;
        private int color;
        private List<Field> fields;
        private Author author;
        private Footer footer;
        private Thumbnail thumbnail;

        public Embed(String title, int color, List<Field> fields, Author author, Footer footer, Thumbnail thumbnail) {
            this.title = title;
            this.color = color;
            this.fields = fields;
            this.author = author;
            this.footer = footer;
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public int getColor() {
            return color;
        }

        public List<Field> getFields() {
            return fields;
        }

        public Author getAuthor() {
            return author;
        }

        public Footer getFooter() {
            return footer;
        }

        public Thumbnail getThumbnail() {
            return thumbnail;
        }

        public static class Field {
            private String name;
            private String value;
            private boolean inline;

            public Field(String name, String value, boolean inline) {
                this.name = name;
                this.value = value;
                this.inline = inline;
            }

            public String getName() {
                return name;
            }

            public String getValue() {
                return value;
            }

            public boolean isInline() {
                return inline;
            }
        }

        public static class Author {
            private String name;
            private String iconUrl;

            public Author(String name, String iconUrl) {
                this.name = name;
                this.iconUrl = iconUrl;
            }

            public String getName() {
                return name;
            }

            public String getIconUrl() {
                return iconUrl;
            }
        }

        public static class Footer {
            private String text;
            private String iconUrl;

            public Footer(String text, String iconUrl) {
                this.text = text;
                this.iconUrl = iconUrl;
            }

            public String getText() {
                return text;
            }

            public String getIconUrl() {
                return iconUrl;
            }
        }

        public static class Thumbnail {
            private String url;

            public Thumbnail(String url) {
                this.url = url;
            }

            public String getUrl() {
                return url;
            }
        }
    }
}



