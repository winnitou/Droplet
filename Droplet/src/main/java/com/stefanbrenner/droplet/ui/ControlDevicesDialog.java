/*****************************************************************************
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
 *****************************************************************************/
package com.stefanbrenner.droplet.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.BeanAdapter;
import com.stefanbrenner.droplet.model.IDevice;
import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.ui.actions.DeviceOffAction;
import com.stefanbrenner.droplet.ui.actions.DeviceOnAction;
import com.stefanbrenner.droplet.utils.DropletColors;
import com.stefanbrenner.droplet.utils.DropletFonts;
import com.stefanbrenner.droplet.utils.UiUtils;

/**
 * Simple dialog to send HIGH and LOW commands to devices and set device
 * numbers.
 * <p>
 * This is very useful i.e. for valve cleaning.
 * 
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class ControlDevicesDialog extends AbstractDropletDialog {
	
	public ControlDevicesDialog(final JFrame frame, final IDropletContext dropletContext) {
		super(frame, dropletContext, Messages.getString("ControlDevicesDialog.deviceControl")); //$NON-NLS-1$
		
		setLayout(new BorderLayout());
		
		JPanel devicesPanel = new JPanel();
		devicesPanel.setLayout(new GridLayout(0, 1));
		add(devicesPanel, BorderLayout.CENTER);
		
		// add panel for each device
		for (IDevice device : dropletContext.getDroplet().getDevices()) {
			devicesPanel.add(new DevicePanel(frame, device));
		}
		
		JTextArea txtHelp = new JTextArea();
		txtHelp.setText(Messages.getString("DeviceSetupPanel.help"));
		txtHelp.setFont(DropletFonts.FONT_STANDARD_MINI);
		txtHelp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		txtHelp.setEditable(false);
		txtHelp.setLineWrap(true);
		txtHelp.setWrapStyleWord(true);
		add(txtHelp, BorderLayout.SOUTH);
		
		setModal(true);
		setAlwaysOnTop(true);
		// setResizable(false);
		
		pack();
		setLocationRelativeTo(frame);
		setMinimumSize(getPreferredSize());
	}
	
	class DevicePanel extends JPanel {
		
		private final JTextField txtDeviceNumber;
		private final JLabel lbDeviceName;
		private final JButton btnHigh;
		private final JButton btnLow;
		
		public DevicePanel(final JFrame frame, final IDevice device) {
			
			setLayout(new GridBagLayout());
			setBackground(DropletColors.getBackgroundColor(device));
			setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
			
			GridBagConstraints gbc = UiUtils.createGridBagConstraints(0, 0, 0, 0);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			
			BeanAdapter<IDevice> adapter = new BeanAdapter<IDevice>(device, true);
			
			txtDeviceNumber = BasicComponentFactory.createTextField(adapter.getValueModel(IDevice.PROPERTY_NUMBER),
					false);
			txtDeviceNumber.setColumns(3);
			txtDeviceNumber.setHorizontalAlignment(SwingConstants.CENTER);
			add(txtDeviceNumber, gbc);
			
			lbDeviceName = new JLabel(device.getName());
			lbDeviceName.setPreferredSize(new Dimension(200, 30));
			lbDeviceName.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
			UiUtils.editGridBagConstraints(gbc, 1, 0, 1, 0);
			add(lbDeviceName, gbc);
			
			btnHigh = new JButton(new DeviceOnAction(frame, getDropletContext(), device));
			UiUtils.editGridBagConstraints(gbc, 2, 0, 0, 0);
			add(btnHigh, gbc);
			
			// TODO brenner: add input for offset and duration
			
			btnLow = new JButton(new DeviceOffAction(frame, getDropletContext(), device));
			UiUtils.editGridBagConstraints(gbc, 3, 0, 0, 0);
			add(btnLow, gbc);
			
		}
		
	}
	
}
