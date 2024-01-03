package com.toy.AssistCook.domain.email;

import lombok.Getter;

@Getter
public enum CodeStatus {
    ACTIVE,
    USED,
    EXPIRED,
    DISCARD
}
