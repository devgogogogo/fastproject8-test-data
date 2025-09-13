package com.fastcampus.testdata.domain;


import com.fastcampus.testdata.domain.constant.MockDataType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MockData {

    private MockDataType mockDataType;
    private String mockDataValue;

}
