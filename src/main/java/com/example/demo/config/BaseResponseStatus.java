package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    EXPIRED_JWT(false, 2002, "만료된 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),

    POST_USERS_EMPTY_PHONE(false, 2018, "전화번호를 입력해주세요."),
    POST_USERS_INVALID_PHONE(false, 2019, "전화번호 형식을 확인해주세요."),
    POST_USERS_EXISTS_PHONE(false,2020,"중복된 전화번호 입니다."),

    POST_USERS_EMPTY_NICK(false, 2021, "닉네임을 입력해주세요"),

    //business
    POST_BUSINESS_EMPTY_STORE(false, 2022, "비즈니스 명을 입력해주세요"),
    POST_BUSINESS_EMPTY_TYPE(false, 2023, "비즈니스 업종을 입력해주세요"),
    POST_BUSINESS_EMPTY_DETAIL_TYPE(false, 2024, "비즈니스 상세업종을 입력해주세요"),

    //product
    POST_PRODUCT_EMPTY_TITLE(false, 2025, "제목을 입력해주세요"),
    POST_PRODUCT_EMPTY_CONTENT(false, 2026, "내용을 입력해주세요"),
    POST_PRODUCT_EMPTY_PRICE(false, 2030, "가격을 입력해주세요"),
    POST_PRODUCT_EMPTY_CATEGORY(false, 2031, "카테고리를 입력해주세요"),
    POST_TOWNLIFE_EMPTY_TOPIC(false,2040,"토픽을 입력해주세요"),
    POST_TOWNLIFE_EMPTY_TOWNLIFE(false,2041,"없는 동네생활 게시글 입니다."),

    POST_USERS_EXISTS_KEYWORD(false, 2027, "중복된 키워드 입니다."),
    POST_USERS_EMPTY_PASSWORD(false,2028,"비밀번호를 입력해주세요"),
    POST_USERS_INVALID_PASSWORD(false,2029,"비밀번호 형식을 확인해주세요"),
    POST_KAKAO_INVALID_TOKEN(false,2050,"유효하지않은 카카오 토큰입니다."),
    POST_KAKAO_LOGIN_FAIL(false,2051,"카카오 로그인실패"),
    POST_KAKAO_LOGIN_EXISTS(false,2051,"중복된 카카오아이디입니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
