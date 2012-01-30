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
package com.stefanbrenner.droplet.ui.components;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

/**
 * JSpinner that registers a {@link MouseWheelListener} to navigate through the
 * values of the model.
 * 
 * @author Stefan Brenner
 */
public class MouseWheelSpinner extends JSpinner {

	private static final long serialVersionUID = 1L;

	public MouseWheelSpinner() {
		this(false);
	}

	public MouseWheelSpinner(boolean selectOnFocus) {
		super();
		registerMouseWheelListener();
		if (selectOnFocus) {
			registerFocusListener();
		}
	}

	// TODO brenner: not working as intended
	private void registerFocusListener() {
		final JFormattedTextField textField = ((JSpinner.DefaultEditor) getEditor()).getTextField();
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent event) {
				textField.selectAll();
				super.focusGained(event);
			}
		});
	}

	private void registerMouseWheelListener() {
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent event) {
				try {
					SpinnerModel model = getModel();
					int add = -(event.getWheelRotation());
					for (int i = 0; i < Math.abs(add); i++) {
						if (add > 0) {
							model.setValue(model.getNextValue());
						} else {
							model.setValue(model.getPreviousValue());
						}
					}
				} catch (IllegalArgumentException e) {
					// thrown if we try to set a value that is not allowed in
					// the model
				}
			}
		});
	}

}
