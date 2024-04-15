package com.unimate.unimate.dto;

import com.unimate.unimate.entity.Blog;
import com.unimate.unimate.enums.BlogType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;

@Data
@Accessors(chain = true)
public class BlogResponseDTO {
    private long id;

    private String title;

    private String content;

    private String writer;

    //example : 5.Min Reading Time
    private String readingTime;

    private Date createdAt;

    //INFORMASI_BEASISWA, PEKERJAAN_LUAR_NEGERI, TIPS_TRIK
    private BlogType type;

    private ArrayList<String> blogTag;

    private String image;

//    private ArrayList<String> blogTag;
}
