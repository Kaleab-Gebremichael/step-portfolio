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
import java.util.ArrayList;
import static java.lang.System.out;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

    //General Outline:
    //  1. Go through each person's events and add it to a list that holds all 
    //      times when people can't meet
    //  2. Sort that list by start time
    //  3. Merge overlapping intervals if any so that start/end times are clear
    //  4. Start from beginning of day to end and the result will be all the times in this 
    //      merged timeranges that are atleast the requested meeting's duration

    if (request.getDuration() > TimeRange.WHOLE_DAY.duration()){
      return Arrays.asList();
   
    } else if (events.isEmpty()){
      return Arrays.asList(TimeRange.WHOLE_DAY);
    }

    ArrayList<TimeRange> allUnavailableTimes = new ArrayList<>();

    for (Event event : events){
      allUnavailableTimes.add(event.getWhen());
    }

    System.out.println("UNSORTED Collection" + allUnavailableTimes);

    Collections.sort(allUnavailableTimes, TimeRange.ORDER_BY_START);

    System.out.println("SORTED Collection: " + allUnavailableTimes);

    ArrayList<TimeRange> mergedUnavailableTimes = mergeTimeRanges(allUnavailableTimes);

    System.out.println("MERGED Collection: " + mergedUnavailableTimes);

    ArrayList<TimeRange> availableTimes = new ArrayList<>();
    
    TimeRange possibleTime = TimeRange.fromStartDuration(-1,-1); //junk value
    int prevEndTime = TimeRange.START_OF_DAY;  //junk value
    
    for (TimeRange curTime: mergedUnavailableTimes){

      possibleTime = TimeRange.fromStartEnd(prevEndTime, curTime.start(), false);

      System.out.println("POSSIBLE Time" + possibleTime);
      if (possibleTime.duration() >= request.getDuration()){
          availableTimes.add(possibleTime);
      }
      prevEndTime = curTime.end();
    }

    possibleTime = TimeRange.fromStartEnd(prevEndTime, TimeRange.END_OF_DAY, true);
    if (possibleTime.duration() >= request.getDuration()){
          availableTimes.add(possibleTime);
    }

    return availableTimes;
  }

  public ArrayList<TimeRange> mergeTimeRanges(ArrayList<TimeRange> allUnavailableTimes){

    ArrayList<TimeRange> result = new ArrayList<>();

    for (TimeRange curTime: allUnavailableTimes) {
      

      if (result.isEmpty() || !curTime.overlaps(result.get(result.size() - 1))) {
        result.add(curTime);
      
      } else if (curTime.overlaps(result.get(result.size() - 1))) {
        
        TimeRange lastTimeRangeInResult = result.get(result.size() - 1);
        int newStart = Math.min(lastTimeRangeInResult.start(), curTime.start());
        int newEnd = Math.max(lastTimeRangeInResult.end(), curTime.end());
        
        TimeRange mergedTimeRange = TimeRange.fromStartEnd(newStart, newEnd, false);

        result.remove(lastTimeRangeInResult);
        result.add(mergedTimeRange);
      }
    }

    return result;
  }

}
