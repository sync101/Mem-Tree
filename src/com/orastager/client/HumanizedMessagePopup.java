/*
 * Copyright 2011 Nanda Emani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.orastager.client;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.PopupPanel;

public class HumanizedMessagePopup extends PopupPanel
{
	private static InlineHTML html = new InlineHTML("");
	private static HumanizedMessagePopup humanizedMessagePopup = new HumanizedMessagePopup();

	public static enum HIDE_BEHAVIOR
	{
		ON_CLICK, ON_CLICK_FADE, SHOW_AND_FADE, FADE_AFTER_MOUSE_MOVE
	}

	public static enum MESSAGE_TYPE
	{
		INFO, WARNING, ERROR
	}

	private static final String BASE_CSS_CLASS = "humanizedMessage";

	private static final String ERROR_CSS_CLASS = "humanizedMessageError";

	private static final String WARNING_CSS_CLASS = "humanizedMessageWarning";

	private static final String INFO_CSS_CLASS = "humanizedMessageInfo";

	private static final float DELTA = 0.05f;

	private static final int HIDE_TIMER_DELAY = 50;

	private static final int SHOW_MESSAGE_DELAY = 2400;

	private static final float INITAL_OPACITY = 0.8f;

	private static HIDE_BEHAVIOR hideBehavior;

	private static HandlerRegistration handlerRegistration;

	private static Timer hideTimer = new Timer()
	{
		@Override
		public void run()
		{

			float opacity = getOpacity();
			opacity -= DELTA;

			if (opacity > DELTA)
			{
				setOpacity(opacity);
			}
			else
			{
				humanizedMessagePopup.hide();
				cancel();
			}
		}
	};

	public static void showMessage(String message, MESSAGE_TYPE messageType, HIDE_BEHAVIOR hideBehavior)
	{
		HumanizedMessagePopup.hideBehavior = hideBehavior;

		boolean autoHide = false;
		boolean animate = false;

		switch (hideBehavior)
		{
			case ON_CLICK:
				autoHide = true;
				break;
			case SHOW_AND_FADE:
				animate = false;
				break;

			default:
				break;
		}

		HumanizedMessagePopup.humanizedMessagePopup.setAutoHideEnabled(autoHide);
		HumanizedMessagePopup.humanizedMessagePopup.setAnimationEnabled(animate);
		setOpacity(INITAL_OPACITY);

		html.setText(message);
		html.setStyleName("gwt-MessageHTML");
		HumanizedMessagePopup.humanizedMessagePopup.setStylePrimaryName(BASE_CSS_CLASS);

		switch (messageType)
		{
			case ERROR:
				html.addStyleName(ERROR_CSS_CLASS);
				HumanizedMessagePopup.humanizedMessagePopup.addStyleName(ERROR_CSS_CLASS);
				break;
			case WARNING:
				html.addStyleName(WARNING_CSS_CLASS);
				HumanizedMessagePopup.humanizedMessagePopup.addStyleName(WARNING_CSS_CLASS);
				break;

			default:
				html.addStyleName(INFO_CSS_CLASS);
				HumanizedMessagePopup.humanizedMessagePopup.addStyleName(INFO_CSS_CLASS);
				break;
		}

		HumanizedMessagePopup.humanizedMessagePopup.setWidget(html);
	}

	private Timer getHideTimer()
	{
		if (handlerRegistration != null)
		{
			handlerRegistration.removeHandler();
			handlerRegistration = null;
		}

		return hideTimer;
	}

	@Override
	public void show()
	{
		super.show();

		Timer waitTimer = null;

		switch (hideBehavior)
		{
			case SHOW_AND_FADE:
				waitTimer = new Timer()
				{
					@Override
					public void run()
					{
						getHideTimer().scheduleRepeating(HIDE_TIMER_DELAY);
					}
				};
				break;
			case ON_CLICK_FADE:
				waitTimer = new Timer()
				{
					@Override
					public void run()
					{
						addNativePreviewHandler(Event.ONCLICK);
					}
				};
				break;
			case FADE_AFTER_MOUSE_MOVE:
				waitTimer = new Timer()
				{
					@Override
					public void run()
					{
						addNativePreviewHandler(Event.ONMOUSEMOVE);
					}
				};
				break;
			default:
				break;
		}

		if (waitTimer != null)
		{
			waitTimer.schedule(SHOW_MESSAGE_DELAY);
		}
	}

	private void addNativePreviewHandler(final int eventType)
	{
		if (handlerRegistration == null)
		{
			handlerRegistration = Event.addNativePreviewHandler(new NativePreviewHandler()
			{
				@Override
				public void onPreviewNativeEvent(NativePreviewEvent event)
				{
					int type = event.getTypeInt();

					if (type == eventType)
						getHideTimer().scheduleRepeating(HIDE_TIMER_DELAY);
				}
			});
		}
	}

	private static void setOpacity(double opacity)
	{
		if (opacity < 0)
		{
			opacity = 0;
		}

		HumanizedMessagePopup.humanizedMessagePopup.getElement().getStyle().setOpacity(opacity);
	}

	private static float getOpacity()
	{
		String opacityString = HumanizedMessagePopup.humanizedMessagePopup.getElement().getStyle().getOpacity();

		try
		{
			float opacity = Float.parseFloat(opacityString);
			return opacity;
		}
		catch (NumberFormatException nfe)
		{
			throw new RuntimeException("could not parse opacity, expected float but got '" + opacityString + "'");
		}

	}

	/**
	 * Show a message for {@link SHOW_MESSAGE_DELAY} milliseconds and hide it
	 * when the user clicks outside of it
	 * 
	 * @param message
	 *            the message
	 * @param messageType
	 *            the message type
	 */
	public static void showMessageUntilClick(String message, MESSAGE_TYPE messageType)
	{
		showMessage(message, messageType, HIDE_BEHAVIOR.ON_CLICK);
		humanizedMessagePopup.center();
	}

	/**
	 * Show a message for {@link SHOW_MESSAGE_DELAY} milliseconds and fade it
	 * out when the user clicks outside of it
	 * 
	 * @param message
	 *            the message
	 * @param messageType
	 *            the message type
	 */
	public static void showMessageAndFadeAfterClick(String message, MESSAGE_TYPE messageType)
	{
		showMessage(message, messageType, HIDE_BEHAVIOR.ON_CLICK_FADE);
		humanizedMessagePopup.center();
	}

	/**
	 * Show a message for {@link SHOW_MESSAGE_DELAY} milliseconds and fade it
	 * after first mouse move
	 * 
	 * @param message
	 *            the message
	 * @param messageType
	 *            the message type
	 */
	public static void showMessageAndFadeAfterMouseMove(String message, MESSAGE_TYPE messageType)
	{
		showMessage(message, messageType, HIDE_BEHAVIOR.FADE_AFTER_MOUSE_MOVE);
		humanizedMessagePopup.center();
	}

	/**
	 * Show a message for {@link SHOW_MESSAGE_DELAY} milliseconds and fade it
	 * out afterwards
	 * 
	 * @param message
	 *            the message
	 * @param messageType
	 *            the message type
	 */
	public static void showMessageAndFade(String message, MESSAGE_TYPE messageType)
	{
		showMessage(message, messageType, HIDE_BEHAVIOR.SHOW_AND_FADE);
		humanizedMessagePopup.center();
	}

}
