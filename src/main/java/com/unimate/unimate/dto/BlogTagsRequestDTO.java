package com.unimate.unimate.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Data
@Accessors(chain = true)
public class BlogTagsRequestDTO {
    ArrayList<String> tags;
}
