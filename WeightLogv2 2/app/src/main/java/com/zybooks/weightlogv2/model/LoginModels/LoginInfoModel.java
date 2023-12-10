package com.zybooks.weightlogv2.model.LoginModels;

import android.database.sqlite.SQLiteOpenHelper;

public class LoginInfoModel {
        private String username;
        private String password;

        public LoginInfoModel(String username, String password) {
            this.username = username;
            this.password = password;
        }
        
        public String getUsername() {
            return this.username;}

        public String getPassword() {
            return this.password;}
    }
