package com.fastcampus.testdata.domain.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Domain] 테스트 데이터 자료형 테스트")
class MockDataTypeTest {


    @DisplayName("쟈료형이 주어지면, 해당 원소의 이름을 리턴한다.")
    @Test
    void givenMockDataType_whenReading_thenReturnElementName() {

        //given
        MockDataType mockDataType = MockDataType.STRING;
        //when
        String elementName = mockDataType.toString();
        //then
        System.out.println(elementName);
        assertThat(elementName).isEqualTo(MockDataType.STRING.name());

    }

    @DisplayName("자료형이 주어지면, 해당 원소의 데이터를 리턴한다.")
    @Test
    void givenMockDataType_whenReading_thenReturnsEnumElementObject() {
        // Given
        MockDataType mockDataType = MockDataType.STRING;

        // When
        MockDataType.MockDataTypeObject result = mockDataType.toObject();

        // Then
        assertThat(result.toString())
                .contains("name", "requiredOptions", "baseType");
    }
}