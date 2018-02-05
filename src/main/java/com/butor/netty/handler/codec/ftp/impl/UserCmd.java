/**
 * Copyright (C) 2013 codingtony (t.bussieres@gmail.com)
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
package com.butor.netty.handler.codec.ftp.impl;

import com.butor.netty.handler.codec.ftp.user.UserManager;
import io.netty.channel.ChannelHandlerContext;

import com.butor.netty.handler.codec.ftp.cmd.AbstractFTPCommand;
import com.butor.netty.handler.codec.ftp.cmd.FTPAttrKeys;
import com.butor.netty.handler.codec.ftp.cmd.LogonCommand;

public class UserCmd extends AbstractFTPCommand  implements LogonCommand {
	private UserManager userManager;

	public UserCmd(UserManager userManager) {
		super("USER");
		this.userManager=userManager;
	}
	
	@Override
	public void execute(ChannelHandlerContext ctx, String args) {
		if(userManager.isNoAuth())
		{
			send("230 USER LOGGED IN", ctx, args);
			ctx.attr(FTPAttrKeys.LOGGED_IN).set(true);
			return;
		}
		if(userManager.authUserName(args))
		{
			send("311 INPUT PASSWORD", ctx, args);
			return;
		}
		send("500 username is wrong", ctx, args);
	}
}
