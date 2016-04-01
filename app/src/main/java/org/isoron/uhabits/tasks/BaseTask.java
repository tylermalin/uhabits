/*
 * Copyright (C) 2016 Álinson Santos Xavier <isoron@gmail.com>
 *
 * This file is part of Loop Habit Tracker.
 *
 * Loop Habit Tracker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Loop Habit Tracker is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.isoron.uhabits.tasks;

import android.os.AsyncTask;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BaseTask extends AsyncTask<Void, Void, Void>
{
    private static CountDownLatch latch;
    private static int activeTaskCount;

    @Override
    protected Void doInBackground(Void... params)
    {
        return null;
    }

    @Override
    protected void onPreExecute()
    {
        activeTaskCount++;
        latch = new CountDownLatch(activeTaskCount);
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        activeTaskCount--;
        latch.countDown();
    }

    public static void waitForTasks(long timeout, TimeUnit unit)
            throws TimeoutException, InterruptedException
    {
        if(activeTaskCount == 0) return;

        boolean successful = latch.await(timeout, unit);
        if(!successful) throw new TimeoutException();
    }
}