/*******************************************************************************
 * Project: Droplet - Toolkit for Liquid Art Photographers
 * Copyright (C) 2012 Stefan Brenner
 *
 * This file is part of Droplet.
 *
 * Droplet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Droplet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Droplet. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.stefanbrenner.droplet.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.model.internal.Configuration;
import com.stefanbrenner.droplet.service.IDropletMessageProtocol;
import com.stefanbrenner.droplet.service.ISerialCommunicationService;

/**
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class StartAction extends AbstractSerialAction {

	public StartAction(JFrame frame, IDropletContext dropletContext) {
		super(frame, dropletContext, Messages.getString("StartAction.title")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("StartAction.description")); //$NON-NLS-1$
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		ISerialCommunicationService serialCommProvider = Configuration.getSerialCommProvider();
		IDropletMessageProtocol messageProtocolProvider = Configuration.getMessageProtocolProvider();

		String message = messageProtocolProvider.createStartMessage(1, 0);
		System.out.println(message);
		serialCommProvider.sendData(message);
	}

}
