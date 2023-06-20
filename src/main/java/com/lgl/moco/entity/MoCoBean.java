package com.lgl.moco.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoCoBean {

    private MoCoRequest request;

    private MoCoResponse response;

    @Override
    public String toString() {
        return "resquest==>\n"+request.toString()+"\nresponse==>\n"+response.toString();
    }
}
