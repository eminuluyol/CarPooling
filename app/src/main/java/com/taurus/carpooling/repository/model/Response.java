package com.taurus.carpooling.repository.model;

public class Response {

    private boolean response;

    public Response(boolean response) {
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}
