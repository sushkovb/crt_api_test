package pojo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookInfo {
    private String author;
    private int id;
    private boolean isElectronicBook;
    private String name;
    private int year;

    public boolean getIsElectronicBook() {

        return isElectronicBook;
    }
}