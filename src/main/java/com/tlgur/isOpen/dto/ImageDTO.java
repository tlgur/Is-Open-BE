package com.tlgur.isOpen.dto;

import com.tlgur.isOpen.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private String savedName;
    private String originalName;

    public static ImageDTO fromEntity(Image image) {
        return ImageDTO.builder()
                .originalName(image.getOriginalName())
                .savedName(image.getSavedName())
                .build();
    }
}
