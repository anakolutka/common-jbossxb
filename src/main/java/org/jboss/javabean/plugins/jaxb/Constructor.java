/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.javabean.plugins.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

import org.jboss.xb.annotations.JBossXmlConstants;
import org.jboss.xb.annotations.JBossXmlType;

/**
 * Constructor.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="constructorType")
@JBossXmlType(modelGroup = JBossXmlConstants.MODEL_GROUP_UNORDERED_SEQUENCE)
public class Constructor
{
   private String factoryClass;

   private String factoryMethod;

   private List<? extends AbstractParameter> parameters;

   public String getFactoryClass()
   {
      return factoryClass;
   }

   @XmlAttribute(name="factoryClass")
   public void setFactoryClass(String factoryClass)
   {
      this.factoryClass = factoryClass;
   }

   public String getFactoryMethod()
   {
      return factoryMethod;
   }

   @XmlAttribute(name="factoryMethod")
   public void setFactoryMethod(String factoryMethod)
   {
      this.factoryMethod = factoryMethod;
   }

   public List<? extends AbstractParameter> getParameters()
   {
      return parameters;
   }

   @XmlElements
   ({
      @XmlElement(name="parameter", type=Parameter.class),
      @XmlElement(name="property", type=Property.class)
   })
   public void setParameters(List<? extends AbstractParameter> parameters)
   {
      this.parameters = parameters;
   }
}
