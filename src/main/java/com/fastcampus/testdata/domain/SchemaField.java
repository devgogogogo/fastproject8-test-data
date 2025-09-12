package com.fastcampus.testdata.domain;

import com.fastcampus.testdata.domain.constant.MockDataType;
import lombok.*;

@Getter
@Setter
@ToString
public class SchemaField {

    private String fieldName;
    private MockDataType mockDataType;
    private Integer fieldOrder;
    private Integer blankPercent;
    private String typeOptionJson;
    private String forceValue;

}
