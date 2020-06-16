
package com.google.sps;

import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;


public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

    //General Outline:
    //  1. Go through each person's events and add it to a list that holds all 
    //      times when people can't meet
    //  2. Sort that list by start time
    //  3. Merge overlapping intervals if any so that start/end times are clear
    //  4. Start from beginning of day to end and the result will be all the times
    //      in this merged timeranges that are atleast the requested meeting's duration

    ArrayList<TimeRange> allUnavailableTimes = new ArrayList<>();

    for (Event event : events){
      if (validatePerson(event, request)){
        allUnavailableTimes.add(event.getWhen());
      }
      
    }

    Collections.sort(allUnavailableTimes, TimeRange.ORDER_BY_START);

    ArrayList<TimeRange> mergedUnavailableTimes = mergeTimeRanges(allUnavailableTimes);
    ArrayList<TimeRange> availableTimes = new ArrayList<>();
    
    TimeRange possibleTime;
    int prevEndTime = TimeRange.START_OF_DAY;
    
    for (TimeRange curTime: mergedUnavailableTimes){

      possibleTime = TimeRange.fromStartEnd(prevEndTime, curTime.start(), false);

      if (possibleTime.duration() >= request.getDuration()){
        availableTimes.add(possibleTime);
      }

      prevEndTime = curTime.end();
    }

    //check to see if there's a possible time after the end of the last meeting
    possibleTime = TimeRange.fromStartEnd(prevEndTime, TimeRange.END_OF_DAY, true);
    if (possibleTime.duration() >= request.getDuration()){
      availableTimes.add(possibleTime);
    }

    return availableTimes;
  }

  //merges overlapping intervals so that start and end times are clear and consecutive
  public ArrayList<TimeRange> mergeTimeRanges(ArrayList<TimeRange> allUnavailableTimes){

    ArrayList<TimeRange> result = new ArrayList<>();

    for (TimeRange curTime: allUnavailableTimes) {
      
      //if it doesn't overlap with the last timerange, it can simply be added
      if (result.isEmpty() || !curTime.overlaps(result.get(result.size() - 1))) {
        result.add(curTime);
      
      } else {
        
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

  //checks to make sure at least one person in the request is in the event
  public boolean validatePerson(Event event, MeetingRequest request){
    Set<String> eventAttendees = new HashSet<>(event.getAttendees());

    //find intersection between these two sets
    eventAttendees.retainAll(request.getAttendees());

    return !eventAttendees.isEmpty();
  }
}