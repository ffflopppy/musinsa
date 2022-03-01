package com.categories.musinsa.config.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum MusinExceptionCodeEnum implements MusinCodeEnum {

    DATA_NOT_FOUND( "Data not found" ),
    DATA_FOUND( "Data not found" ),
    CAN_NOT_EDIT( "상위 카테고리를 현재 카테고리 하위로 수정 할수 없습니다. " ),
    NOT_FOUND_UPPER_CATEGORY( "상위 카테고리를 찾을수 없습니다." );

    private String message;


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getName() {
        return this.name();
    }

    public static MusinExceptionCodeEnum findByType (String type) {
        try {
            return MusinExceptionCodeEnum.valueOf(type);
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }
}
