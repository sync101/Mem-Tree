/*
 * Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.drive.dredit;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import com.google.drive.dredit.model.State;

/**
 * Servlet to check that the current user is authorized and to serve the
 * start page for DrEdit.
 *
 * @author vicfryzel@google.com (Vic Fryzel)
 * @author nivco@google.com (Nicolas Garnier)
 * @author jbd@google.com (Burcu Dogan)
 */
@SuppressWarnings("serial")
public class StartPageServlet extends DrEditServlet {
  /**
   * Ensure that the user is authorized, and setup the required values for
   * index.jsp.
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    // Deserialize the state in order to specify some values to the DrEdit
    // JavaScript client below.
    ArrayList<String> ids = new ArrayList<String>();
    // Assume an empty ID in the list if no IDs were set.

    ids.add("");
    if (req.getParameter("state") != null) {
      State state = new State(req.getParameter("state"));
      if (state.ids != null && state.ids.size() > 0) {
        ids = state.ids;
      }

     if (state.action.equalsIgnoreCase("create"))
    	  ids.set(0, "new_dma_file");
     }
    
    if (req.getParameter("error") != null) {
    	resp.sendRedirect("/index.html?fileId="+ids.get(0));
    	return;
    }

    // handle OAuth2 callback
    String code = req.getParameter("code");
    String userId = "";
    if (code != null) {
     //handleCallbackIfRequired(req, resp);
      // retrieve new credentials with code
      Credential credential = getCredentaialManager().retrieve(code);
      // request userinfo
      Oauth2 service = getOauth2Service(credential);
      try {
        Userinfo about = service.userinfo().get().execute();
        userId = about.getId();
        //credentialManager.save(id, credential);
      } catch (IOException e) {
        //throw new RuntimeException
    	PrintWriter out = resp.getWriter();
		out.println("Can't handle the OAuth2 callback, make sure that chosen user is valid.");
		return;
      }
    }else{     
     // Making sure that we have user credentials
     //loginIfRequired(req, resp);
        Credential credential = getCredential(req, resp);
        if (credential == null) {
          // redirect to authorization url
          try {
            resp.sendRedirect(getCredentaialManager().getAuthorizationUrl(req.getParameter("state")));
            return;
          } catch (IOException e) {
          	PrintWriter out = resp.getWriter();
    		out.println("Can't redirect to authorize page.");
    		return;
          }
        }
    }

    //resp.sendRedirect("/index.html?fileId="+ids.get(0)+"&userId="+userId);
    resp.sendRedirect("/index.html?fileId="+ids.get(0));
  }
}
