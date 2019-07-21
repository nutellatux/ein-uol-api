package com.ein.uol.exception;

import lombok.Data;

public @Data class DetailsError {

    private String title;

    private Long status;

    private Long timestamp;

    private String messageDev;
}

