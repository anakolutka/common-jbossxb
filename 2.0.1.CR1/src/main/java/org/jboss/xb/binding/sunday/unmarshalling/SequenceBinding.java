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
package org.jboss.xb.binding.sunday.unmarshalling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collection;
import javax.xml.namespace.QName;
import org.jboss.xb.binding.JBossXBRuntimeException;
import org.xml.sax.Attributes;


/**
 * @author <a href="mailto:alex@jboss.org">Alexey Loubyansky</a>
 * @version <tt>$Revision$</tt>
 */
public class SequenceBinding
   extends ModelGroupBinding
{
   private List<ParticleBinding> sequence = Collections.emptyList();
   private ElementBinding arrayItem;

   public SequenceBinding(SchemaBinding schema)
   {
      super(schema);
   }

   public ElementBinding getArrayItem()
   {
      return arrayItem;
   }

   public void addParticle(ParticleBinding particle)
   {
      switch(sequence.size())
      {
         case 0:
            sequence = Collections.singletonList(particle);
            if(particle.isRepeatable() && particle.getTerm().isElement())
            {
               ElementBinding element = (ElementBinding)particle.getTerm();
               if(particle.isRepeatable())
               {
                  arrayItem = element;
               }
            }
            break;
         case 1:
            sequence = new ArrayList<ParticleBinding>(sequence);
            arrayItem = null;
         default:
            sequence.add(particle);
      }
      super.addParticle(particle);
   }

   public Collection<ParticleBinding> getParticles()
   {
      return Collections.unmodifiableCollection(sequence);
   }

   public Cursor newCursor(ParticleBinding particle)
   {
      return new Cursor(particle)
      {
         private int pos = -1;
         private ElementBinding element;
         private boolean wildcardContent;

         public ParticleBinding getCurrentParticle()
         {
            if(pos < 0)
            {
               throw new JBossXBRuntimeException(
                  "The cursor has not been positioned yet for " + SequenceBinding.this.toString()
               );
            }
            return (ParticleBinding)sequence.get(pos);
         }

         public ElementBinding getElement()
         {
            if(pos < 0)
            {
               throw new JBossXBRuntimeException(
                     "The cursor has not been positioned yet for " + SequenceBinding.this.toString()
               );
            }
            return element;
         }

         public boolean isPositioned()
         {
            return pos != -1;
         }

         public void endElement(QName qName)
         {
            if(element == null || !element.getQName().equals(qName))
            {
               throw new JBossXBRuntimeException("Failed to process endElement for " + qName +
                  " since the current element is " + (element == null ? "null" : element.getQName().toString())
               );
            }

            if(trace)
            {
               log.trace("endElement " + qName + " in " + getModelGroup());
            }
         }
         
         public boolean isWildcardContent()
         {
            return wildcardContent;
         }
         
         protected List<ModelGroupBinding.Cursor> startElement(QName qName, Attributes atts, Set<ModelGroupBinding.Cursor> passedGroups, List<ModelGroupBinding.Cursor> groupStack, boolean required)
         {
            if(trace)
            {
               StringBuffer sb = new StringBuffer();
               sb.append("startElement ").append(qName).append(" in ").append(SequenceBinding.this.toString());
               log.trace(sb.toString());
            }

            wildcardContent = false;
            int i = pos;
            if(pos >= 0)
            {
               ParticleBinding particle = getCurrentParticle();
               if(particle.getMaxOccursUnbounded() ||
                  occurence < particle.getMinOccurs() ||
                  occurence < particle.getMaxOccurs())
               {
                  --i;
               }
            }

            // i update pos only if the element has been found, though it seems to be irrelevant
            // since the cursor is going to be thrown away in case the element has not been found
            while(i < sequence.size() - 1)
            {
               ParticleBinding particle = (ParticleBinding)sequence.get(++i);
               Object item = particle.getTerm();
               if(item instanceof ElementBinding)
               {
                  ElementBinding element = (ElementBinding)item;
                  if(qName.equals(element.getQName()))
                  {
                     if(pos == i)
                     {
                        ++occurence;
                     }
                     else
                     {
                        pos = i;
                        occurence = 1;
                     }
                     groupStack = addItem(groupStack, this);
                     this.element = element;

                     if(trace)
                     {
                        log.trace("found " + qName + " in " + getModelGroup());
                     }
                     break;
                  }

                  if(i != pos && particle.getMinOccurs() > 0)
                  {
                     if(required)
                     {
                        StringBuffer sb = new StringBuffer(250);
                        sb.append(qName).append(" cannot appear in this position in group ")
                        .append(SequenceBinding.this.toString());
                        throw new JBossXBRuntimeException(sb.toString());
                     }
                     else
                     {
                        break;
                     }
                  }
               }
               else if(item instanceof ModelGroupBinding)
               {
                  ModelGroupBinding modelGroup = (ModelGroupBinding)item;
                  if(!passedGroups.contains(modelGroup))
                  {
                     switch(passedGroups.size())
                     {
                        case 0:
                           passedGroups = Collections.singleton((ModelGroupBinding.Cursor)this);
                           break;
                        case 1:
                           passedGroups = new HashSet<ModelGroupBinding.Cursor>(passedGroups);
                        default:
                           passedGroups.add(this);
                     }

                     int groupStackSize = groupStack.size();
                     groupStack = modelGroup.newCursor(particle).startElement(
                        qName, atts, passedGroups, groupStack, particle.isRequired(occurence)
                     );

                     if(groupStackSize != groupStack.size())
                     {
                        if(pos != i)
                        {
                           pos = i;
                           occurence = 1;
                        }
                        else
                        {
                           ++occurence;
                        }
                        groupStack = addItem(groupStack, this);
                        element = null;
                        break;
                     }

                     if(i != pos && particle.isRequired())
                     {
                        if(required)
                        {
                           throw new JBossXBRuntimeException("Requested element " + qName +
                              " is not allowed in this position in the sequence. A model group with minOccurs=" +
                              particle.getMinOccurs() + " that doesn't contain this element must follow."
                           );
                        }
                        else
                        {
                           break;
                        }
                     }
                  }
                  else if(i != pos && particle.isRequired())
                  {
                     if(required)
                     {
                        throw new JBossXBRuntimeException("Requested element " + qName +
                           " is not allowed in this position in the sequence. A model group with minOccurs=" +
                           particle.getMinOccurs() + " that doesn't contain this element must follow."
                        );
                     }
                     else
                     {
                        break;
                     }
                  }
               }
               else if(item instanceof WildcardBinding)
               {
                  WildcardBinding wildcard = (WildcardBinding)item;
                  element = wildcard.getElement(qName, atts);
                  if(element != null)
                  {
                     if(pos != i)
                     {
                        pos = i;
                        occurence = 1;
                     }
                     else
                     {
                        ++occurence;
                     }
                     groupStack = addItem(groupStack, this);
                     wildcardContent = true;
                     break;
                  }

                  if(i != pos && particle.getMinOccurs() > 0)
                  {
                     if(required)
                     {
                        throw new JBossXBRuntimeException("Requested element " + qName +
                           " is not allowed in this position in the sequence."
                        );
                     }
                     else
                     {
                        break;
                     }
                  }
               }
            }

            if(trace && i == sequence.size())
            {
               log.trace(qName + " not found in " + getModelGroup());
            }

            return groupStack;
         }

         protected ElementBinding getElement(QName qName, Attributes atts, Set<ModelGroupBinding.Cursor> passedGroups, boolean ignoreWildcards)
         {
            return getElement(sequence, qName, atts, passedGroups, ignoreWildcards);
         }
      };
   }

   protected boolean mayStartWith(QName qName, Set<ModelGroupBinding> set)
   {
      boolean result = false;
      for(int i = 0; i < sequence.size(); ++i)
      {
         ParticleBinding particle = (ParticleBinding)sequence.get(i);
         Object item = particle.getTerm();
         if(item instanceof ElementBinding)
         {
            ElementBinding element = (ElementBinding)item;
            if(qName.equals(element.getQName()))
            {
               result = true;
               break;
            }

            if(particle.getMinOccurs() > 0)
            {
               break;
            }
         }
         else if(item instanceof ModelGroupBinding)
         {
            ModelGroupBinding modelGroup = (ModelGroupBinding)item;
            if(!set.contains(modelGroup))
            {
               switch(set.size())
               {
                  case 0:
                     set = Collections.singleton((ModelGroupBinding)this);
                     break;
                  case 1:
                     set = new HashSet<ModelGroupBinding>(set);
                  default:
                     set.add(this);
               }

               result = modelGroup.mayStartWith(qName, set);

               if(result || particle.getMinOccurs() > 0)
               {
                  break;
               }
            }
            else if(particle.getMinOccurs() > 0)
            {
               break;
            }
         }
      }
      return result;
   }

   @Override
   public String getGroupType()
   {
      return "sequence";
   }
}
