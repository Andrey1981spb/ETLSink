package ru.spb.voskhod.file_read_maven.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadFile {

  //  private LocalDate date;
    private String operation;
    private Integer amount;
    private String itemOfExpenditure;
    private Integer oldBalance;

}
