/*
 *  Copyright 2007 Institute of Computing, UNICAMP, Brazil
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov;

import java.util.Hashtable;

/**
 * This class represents an instance of a component and is the access point to
 * the component's interfaces.
 *
 * @author Leonel Aguilar Gayard (leonel.gayard@gmail.com)
 *
 */
public interface IManager {
	/**
	 * Connects a required interface to its implementation
	 *
	 * @param name
	 *            The name of the required interface
	 * @param facade
	 *            An object that implements the required interface
	 */
	public void setRequiredInterface(String name, Object facade);

	/**
	 * Returns an implementation of the interface by this component.
	 *
	 * @param name
	 *            The name of the desired provided interface.
	 * @return The implementation of the desired provided interface.
	 * @throws NoSuchInterfaceException
	 */
	public Object getProvidedInterface(String name);
			

	/**
	 * Returns the object connected to the required interface of this
	 * component with the requested name.
	 *
	 * @param name
	 *            The name of the required interface in this component.
	 *
	 * @return The façade object connected to the required interface
	 * in this component.
	 * @throws NoSuchInterfaceException if this component does not
	 * present a required interface with that name
	 */
	public Object getRequiredInterface(String name);

	/**
	 * Sets a facade to implement an interface of this component.
	 *
	 * @param name
	 *            The name of the interface the facade is supposed to implement
	 * @param facade
	 *            The facade that implements this interface
	 */
	public void setProvidedInterface(String name, Object facade);

	/**
	 * Returns an array with the names of the provided interfaces in this
	 * component
	 *
	 * @return An array with the names of the provided interfaces in this
	 *         component
	 */
	public String[] getProvidedInterfaces();

	/**
	 * Returns an array with the names of the required interfaces in this
	 * component
	 *
	 * @return An array with the names of the required interfaces in this
	 *         component
	 */
	public String[] getRequiredInterfaces();

	/**
	 * Returns a Map String -> Class that maps the name of each provided
	 * interface in this component to its class. This map in unmodifiable.
	 *
	 * Returns a Map String->Class that maps the name of the provided interfaces
	 * to the Class of each interface. This map is unmodifiable.
	 *
	 * @return A mapping of each interface name to the Class object of the
	 *         interface it implements.
	 */
	//public Hashtable getProvidedInterfaceTypes();

	/**
	 * Returns a Hashtable String -> Class that maps the name of each required
	 * interface in this component to its class. This map in unmodifiable.
	 *
	 * Returns a Hashtable String->Class that maps the name of the required interfaces
	 * to the Class of each interface. This map is unmodifiable.
	 *
	 * @return A mapping of each interface name to the Class object of the
	 *         interface it implements.
	 */
	//public Hashtable getRequiredInterfaceTypes();

	

	

	
}
