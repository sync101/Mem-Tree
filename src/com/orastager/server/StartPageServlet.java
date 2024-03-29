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

package com.orastager.server;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet to check that the current user is authorized and to serve the
 * start page for DrEdit.
 *
 * @author vicfryzel@google.com (Vic Fryzel)
 */
public class StartPageServlet extends HttpServlet {
  /**
	 * 
	 */
	private static final long serialVersionUID = -3939028181537358492L;

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

    resp.sendRedirect("/index.html?fileId="+ids.get(0));
  }
}