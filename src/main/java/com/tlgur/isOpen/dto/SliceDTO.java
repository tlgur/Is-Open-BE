package com.tlgur.isOpen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.util.backoff.BackOff;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SliceDTO<T> {
    private List<T> content;
    private Boolean hasNext;
    private Integer size;
    private Integer number;


    public SliceDTO(Slice<T> slice) {
        this.content = slice.getContent();
        this.hasNext = slice.hasNext();
        this.size = slice.getNumberOfElements();
        this.number = slice.getNumber();
    }
}
