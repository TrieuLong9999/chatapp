package com.chatapp.project.form.request.user;

import java.util.List;

public class FormUserIds {
    private List<Long> userIds;

    public FormUserIds() {
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
