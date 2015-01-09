/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 * 	2012 Michael Mosmann <michael@mosmann.de>
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.pageserializer.common.listener;

import org.junit.Assert;
import org.junit.Test;

public class TestSerializationListeners
{
	@Test
	public void listenerDelegation()
	{
		CountingListener first = new CountingListener();
		CountingListener second = new CountingListener();

		ISerializationListener proxy = SerializationListeners.listOf(first, second);

		proxy.begin(null);
		proxy.begin("HA");
		proxy.begin("HE");

		proxy.before(1, null);
		proxy.before(2, "HAaaa");
		proxy.before(3, "HEeee");
		proxy.before(4, "HEeeee");

		proxy.after(6, "HEeee");
		proxy.after(7, "HAaaa");
		proxy.after(8, null);
		proxy.after(9, null);

		proxy.end("HE", null);
		proxy.end(null, null);

		proxy.end("HE", new RuntimeException());
		proxy.end("HA", new RuntimeException());
		proxy.end(null, new RuntimeException());

		Assert.assertEquals(first, second);
		checkCounter("begin",first.beginCounter, 1, 2);
		checkCounter("before",first.beforeCounter, 1, 3);
		checkCounter("after",first.afterCounter, 2, 2);
		checkCounter("end",first.endCounter, 2, 3);
		checkCounter("exception",first.exceptionCounter, 2, 3);

		Assert.assertEquals("pos",new Counter(40), first.positionCounter);
	}

	private void checkCounter(String label,CounterPair counterPair, int withNullCount, int notNullCount)
	{
		Assert.assertEquals(label,new Counter(withNullCount), counterPair.withNull);
		Assert.assertEquals(label,new Counter(notNullCount), counterPair.notNull);
	}

	static class CountingListener implements ISerializationListener
	{

		CounterPair beginCounter = new CounterPair();
		CounterPair beforeCounter = new CounterPair();
		CounterPair afterCounter = new CounterPair();
		CounterPair endCounter = new CounterPair();
		CounterPair exceptionCounter = new CounterPair();

		Counter positionCounter = new Counter();

		@Override
		public void begin(Object object)
		{
			increment(beginCounter, object);
		}

		@Override
		public void before(int position, Object object)
		{
			increment(beforeCounter, object);
			positionCounter.add(position);
		}

		@Override
		public void after(int position, Object object)
		{
			increment(afterCounter, object);
			positionCounter.add(position);
		}

		@Override
		public void end(Object object, Exception exceptionIfAny)
		{
			increment(endCounter, object);
			increment(exceptionCounter, exceptionIfAny);
		}

		private static void increment(CounterPair counterPair, Object object)
		{
			(object != null ? counterPair.notNull : counterPair.withNull).increment();
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((afterCounter == null) ? 0 : afterCounter.hashCode());
			result = prime * result + ((beforeCounter == null) ? 0 : beforeCounter.hashCode());
			result = prime * result + ((beginCounter == null) ? 0 : beginCounter.hashCode());
			result = prime * result + ((endCounter == null) ? 0 : endCounter.hashCode());
			result = prime * result +
				((exceptionCounter == null) ? 0 : exceptionCounter.hashCode());
			result = prime * result + ((positionCounter == null) ? 0 : positionCounter.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CountingListener other = (CountingListener)obj;
			if (afterCounter == null)
			{
				if (other.afterCounter != null)
					return false;
			}
			else if (!afterCounter.equals(other.afterCounter))
				return false;
			if (beforeCounter == null)
			{
				if (other.beforeCounter != null)
					return false;
			}
			else if (!beforeCounter.equals(other.beforeCounter))
				return false;
			if (beginCounter == null)
			{
				if (other.beginCounter != null)
					return false;
			}
			else if (!beginCounter.equals(other.beginCounter))
				return false;
			if (endCounter == null)
			{
				if (other.endCounter != null)
					return false;
			}
			else if (!endCounter.equals(other.endCounter))
				return false;
			if (exceptionCounter == null)
			{
				if (other.exceptionCounter != null)
					return false;
			}
			else if (!exceptionCounter.equals(other.exceptionCounter))
				return false;
			if (positionCounter == null)
			{
				if (other.positionCounter != null)
					return false;
			}
			else if (!positionCounter.equals(other.positionCounter))
				return false;
			return true;
		}

		@Override
		public String toString()
		{
			return "Counter(begin:"+beginCounter+",before:"+beforeCounter+",after:"+afterCounter+",end:"+endCounter+",ex:"+exceptionCounter+",pos:"+positionCounter+")";
		}

	}

	static class Counter
	{
		int count = 0;

		public Counter()
		{

		}

		public Counter(int count)
		{
			this.count = count;
		}

		void increment()
		{
			count++;
		}

		public void add(int increment)
		{
			count = count + increment;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + count;
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Counter other = (Counter)obj;
			if (count != other.count)
				return false;
			return true;
		}
		
		@Override
		public String toString()
		{
			return ""+count;
		}
	}

	static class CounterPair
	{
		final Counter notNull = new Counter();
		final Counter withNull = new Counter();

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((notNull == null) ? 0 : notNull.hashCode());
			result = prime * result + ((withNull == null) ? 0 : withNull.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CounterPair other = (CounterPair)obj;
			if (notNull == null)
			{
				if (other.notNull != null)
					return false;
			}
			else if (!notNull.equals(other.notNull))
				return false;
			if (withNull == null)
			{
				if (other.withNull != null)
					return false;
			}
			else if (!withNull.equals(other.withNull))
				return false;
			return true;
		}
		
		@Override
		public String toString()
		{
			return "(null:"+withNull+",notNull:"+notNull+")";
		}

		static CounterPair with(int notNull, int withNull)
		{
			CounterPair ret = new CounterPair();
			ret.notNull.count = notNull;
			ret.withNull.count = withNull;
			return ret;
		}

	}
}
