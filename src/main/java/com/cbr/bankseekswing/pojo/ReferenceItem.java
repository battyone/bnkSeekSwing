package com.cbr.bankseekswing.pojo;

/**
 * Created by e.vassaev on 11/13/17.
 */
public interface ReferenceItem {

    String getCode();

    String getName();

    default String asString(){
        return getCode()+" / "+getName();
    }
}
