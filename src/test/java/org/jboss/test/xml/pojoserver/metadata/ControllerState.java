/*
  * JBoss, Home of Professional Open Source
  * Copyright 2005, JBoss Inc., and individual contributors as indicated
  * by the @authors tag. See the copyright.txt in the distribution for a
  * full listing of individual contributors.
  *
  * This is free software; you can redistribute it and/or modify it
  * under the terms of the GNU Lesser General Public License as
  * published by the Free Software Foundation; either version 2.1 of
  * the License, or (at your option) any later version.
  *
  * This software is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * Lesser General Public License for more details.
  *
  * You should have received a copy of the GNU Lesser General Public
  * License along with this software; if not, write to the Free
  * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
  * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
  */
package org.jboss.test.xml.pojoserver.metadata;

/**
 * Description of state.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 37406 $
 */
public class ControllerState
{
   /** Error */
   public static final ControllerState ERROR = new ControllerState("**ERROR**");

   /** Not installed state */
   public static final ControllerState NOT_INSTALLED = new ControllerState("Not Installed");

   /** Described state */
   public static final ControllerState DESCRIBED = new ControllerState("Described");

   /** Instantiated state */
   public static final ControllerState INSTANTIATED = new ControllerState("Instantiated");

   /** Configured state */
   public static final ControllerState CONFIGURED = new ControllerState("Configured");

   /** Create state */
   public static final ControllerState CREATE = new ControllerState("Create");

   /** Start state */
   public static final ControllerState START = new ControllerState("Start");

   /** Installed state */
   public static final ControllerState INSTALLED = new ControllerState("Installed");

   /** The state string */
   protected final String stateString;
   
   /**
    * Create a new state
    * 
    * @param stateString the string representation
    */
   public ControllerState(String stateString)
   {
      if (stateString == null)
         throw new IllegalArgumentException("Null state string");
      this.stateString = stateString;
   }
   
   /**
    * Get the state string
    * 
    * @return the state string
    */
   public String getStateString()
   {
      return stateString;
   }
   
   public boolean equals(Object object)
   {
      if (object == null || object instanceof ControllerState == false)
         return false;
      ControllerState other = (ControllerState) object;
      return stateString.equals(other.stateString);
   }

   public int hashCode()
   {
      return stateString.hashCode();
   }
}
