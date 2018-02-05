/**
 * Copyright (C) 2018 huangzheng3 (1044859452@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.butor.netty.handler.codec.ftp.user;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager
{
    private volatile Map<String,User> users=new ConcurrentHashMap<String, User>();
    private volatile String currentUserName="";
    private volatile boolean noAuth=false;

    public boolean authUserName(String userName)
    {
        if(noAuth)
        {
            return true;
        }
        boolean isUserNameExists=(users.get(userName)!=null);
        if(isUserNameExists)
        {
            currentUserName=userName;
        }
        return isUserNameExists;
    }

    public boolean authPassword(String passwod)
    {
        return noAuth || (passwod != null && passwod.equals(users.get(currentUserName).getPassword()));
    }

    public void addUser(User user)
    {
        users.put(user.getUserName(),user);
    }

    public void clearUsers()
    {
        users.clear();
    }

    public boolean isNoAuth()
    {
        return noAuth;
    }

    public void setNoAuth(boolean noAuth)
    {
        this.noAuth = noAuth;
    }

    public static UserManager noAuthUserManager()
    {
        UserManager userManager=new UserManager();
        userManager.setNoAuth(true);
        return userManager;
    }
}
