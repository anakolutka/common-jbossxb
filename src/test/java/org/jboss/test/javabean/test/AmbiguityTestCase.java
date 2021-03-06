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
package org.jboss.test.javabean.test;

import junit.framework.Test;
import org.jboss.test.javabean.support.AmbiguityBean;

/**
 * PropertyTestCase.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 58996 $
 */
public class AmbiguityTestCase extends AbstractJavaBeanTest
{
   /**
    * Create a new AmbiguityTestCase.
    *
    * @param name the test name
    */
   public AmbiguityTestCase(String name)
   {
      super(name);
   }

   /**
    * Setup the test
    *
    * @return the test
    */
   public static Test suite()
   {
      return suite(AmbiguityTestCase.class);
   }

   public void testConfigureInt() throws Exception
   {
      AmbiguityBean ab = unmarshal("TestAmbiguityInt.xml", AmbiguityBean.class);
      Object something = ab.something();
      assertEquals(something.getClass(), Integer.class);
   }

   public void testConfigureString() throws Exception
   {
      AmbiguityBean ab = unmarshal("TestAmbiguityString.xml", AmbiguityBean.class);
      Object something = ab.something();
      assertEquals(something.getClass(), String.class);
   }

   public void testConstructorInt() throws Exception
   {
      AmbiguityBean ab = unmarshal("TestConstructorAmbiguityInt.xml", AmbiguityBean.class);
      Object something = ab.something();
      assertEquals(something.getClass(), Integer.class);
   }

   public void testConstrutorString() throws Exception
   {
      AmbiguityBean ab = unmarshal("TestConstructorAmbiguityString.xml", AmbiguityBean.class);
      Object something = ab.something();
      assertEquals(something.getClass(), String.class);
   }

}
