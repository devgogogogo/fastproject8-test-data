package com.fastcampus.testdata.domain;


import com.fastcampus.testdata.domain.constant.MockDataType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 *특정 {@link MockDataType}에 대응하는 가짜데이터.
 *알고리즘으로 생성하지 않는 {@link MockDataType}의 경우, 이 가짜 데이터를 랜덤으로 뽑아 출력한다.
 * @author kwiHyeon
 */
@Getter
@ToString
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"mockDataType","mockDataValue"}) //동일한 타입에 대해서 자료는 하나만 있어야 한다.
})
@Entity
public class MockData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private MockDataType mockDataType;

    @Setter
    @Column(nullable = false)
    private String mockDataValue;

    public MockData(MockDataType mockDataType, String mockDataValue) {
        this.mockDataType = mockDataType;
        this.mockDataValue = mockDataValue;
    }

    public static MockData of(MockDataType mockDataType, String mockDataValue) {
        return new MockData(mockDataType, mockDataValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MockData mockData)) return false;

        if (getId() == null) {
           return Objects.equals(getMockDataType(), mockData.getMockDataType()) &&
                   Objects.equals(getMockDataValue(), mockData.getMockDataValue());
        }
        return Objects.equals(getId(), mockData.getId());
    }

    @Override
    public int hashCode() {
        if (getId() == null) {
            return Objects.hash(getMockDataType(), getMockDataValue());
        }
        return Objects.hash(getId());
    }
}
