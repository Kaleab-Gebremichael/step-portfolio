// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

    //General Outline:
    //  1. Go through each person's events and add it to a list that holds all 
    //      times when people can't meet
    //  2. Sort that list by start time
    //  3. Merge overlapping intervals if any so that start/end times are clear
    //  4. Start from beginning of day to end and the result will be all the times in this 
    //      merged timeranges that are atleast the requested meeting's duration

    if (events.empty()){
      return Arrays.asList(TimeRange.WHOLE_DAY);
    } 

    ArrayList<TimeRange> allUnavailableTimes = new ArrayList<>();

    for (Event event : events){
      allUnavailableTimes.add(event.getWhen());
    }

    Collections.sort(allUnavailableTimes, TimeRange.ORDER_BY_START);

    ArrayList<TimeRange> mergedUnavailableTimes = mergeTimeRanges(allUnavailableTimes);

    ArrayList<TimeRange> availableTimes = new ArrayList<>();
    int prevEndTime;
    
    for (TimeRange curTime: mergedUnavailableTimes){

      if (availableTimes.isEmpty()) {
        TimeRange possibleTime = TimeRange.fromStartEnd(TimeRange.START_OF_DAY, curTime.start(), false);
      } else {
        TimeRange possibleTime = TimeRange.fromStartEnd(prevEndTime, curTime.start(), false);
      }

      if (possibleTime.duration() > request.getDuration()){
          availableTimes.add(possibleTime);
      }
      prevEndTime = curTime.end();
    }

    return availableTimes;

  }

  public ArrayList<TimeRange> mergeTimeRanges(ArrayList<TimeRange> allUnavailableTimes){

    ArrayList<TimeRange> result = new ArrayList<>();

    for (TimeRange curTime: allUnavailableTimes){
      TimeRange lastTimeRangeInResult = result.get(result.size() - 1);

      if (result.isEmpty() || !curTime.overlaps(lastTimeRangeInResult) {
        result.add(curTime);
      
      } else if (curTime.overlaps(lastTimeRangeInResult){
        int newStart = Math.min(lastTimeRangeInResult.start(), curTime.start());
        int newEnd = Math.max(lastTimeRangeInResult.end(), curTime.end());
        
        TimeRange mergedTimeRange = TimeRange.fromStartEnd(newStart, newEnd, true);

        result.remove(result.size() - 1);
        result.add(mergeTimeRange);
      }

      return result;
    }
  }

}
