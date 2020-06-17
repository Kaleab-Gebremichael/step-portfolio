
package com.google.sps;

import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;


public final class FindMeetingQuery {

 /**
  * Given the meeting information, this method return the times when a meeting could happen.
  * General Outline:
  *  1. Go through each person's events and add it to a list that holds all 
  *      times when people can't meet
  *  2. Sort that list by start time
  *  3. Merge overlapping intervals if any so that start/end times are clear
  *  4. Start from beginning of day to end and the result will be all the times
  *      in this merged timeranges that are atleast the requested meeting's duration
  *
  * @param  events  A collection of events during the day for people
  * @param  request The meeting request that includes the people and the duration.
  * @return  Collection of Timeranges when the meeting could happen.
  */
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

    ArrayList<TimeRange> mandatoryUnavailableTimes = new ArrayList<>();
    ArrayList<TimeRange> optionalUnavailableTimes = new ArrayList<>();

    for (Event event : events){

      if (!isEventBlockingRequest(event, request, true)){
        mandatoryUnavailableTimes.add(event.getWhen());
      }

      if(!isEventBlockingRequest(event, request, false)){
        optionalUnavailableTimes.add(event.getWhen());
      }
    }

    // System.out.println("OPTIONAL UNAVAILABLE Time" + optionalUnavailableTimes);
    // System.out.println("MANDATORY UNAVAILABLE Time" + mandatoryUnavailableTimes);

    ArrayList<TimeRange> unavailableTimes;

    if (!optionalUnavailableTimes.isEmpty() && mandatoryUnavailableTimes.isEmpty()){
      unavailableTimes = optionalUnavailableTimes;
    } else {
      unavailableTimes = mandatoryUnavailableTimes;
    }

    // if (optionalUnavailableTimes.isEmpty()){
    //   unavailableTimes = mandatoryUnavailableTimes;
    // } else if (mandatoryUnavailableTimes.isEmpty()){
    //   unavailableTimes = optionalUnavailableTimes;
    // } else {
    //   unavailableTimes = new ArrayList<>();
    //   unavailableTimes.addAll(mandatoryUnavailableTimes);
    //   unavailableTimes.addAll(optionalUnavailableTimes);
    // }

    ArrayList<TimeRange> mergedUnavailableTimes = mergeTimeRanges(unavailableTimes);
    ArrayList<TimeRange> availableTimes = findAvailableTimes(mergedUnavailableTimes, request);

    return availableTimes;
  }

 /**
  * Given a list of Timeranges, this method merges overlapping intervals so that
  * they are clear and consecutive.
  *
  * @param  allUnavailableTimes  A list of Timeranges.
  * @return   List of Timeranges that are merged and consecutive.
  */
  public ArrayList<TimeRange> mergeTimeRanges(ArrayList<TimeRange> allUnavailableTimes){

    Collections.sort(allUnavailableTimes, TimeRange.ORDER_BY_START);

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

  public ArrayList<TimeRange> findAvailableTimes(ArrayList<TimeRange> unavailableTimes, MeetingRequest request) {
    ArrayList<TimeRange> availableTimes = new ArrayList<>();
    TimeRange possibleTime;
    int prevEndTime = TimeRange.START_OF_DAY;
    
    for (TimeRange curTime: unavailableTimes){

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

 /**
  * Given an event and a meeting request, this method returns a boolean to make 
  * sure at least one  person in the request is in the event.
  *
  * @param  event  A list of Timeranges.
  * @param  request  A list of Timeranges.
  * @return true if an event is blocking a request, false otherwise
  */
  public boolean isEventBlockingRequest(Event event, MeetingRequest request, Boolean mandatory){
    
    Set<String> eventAttendees = new HashSet<>(event.getAttendees());
    
    if (mandatory){
      eventAttendees.retainAll(request.getAttendees());
    } else {
      eventAttendees.retainAll(request.getOptionalAttendees());
    }

    return eventAttendees.isEmpty();
  }
}

 /**
  * Notes for the optional attendees:
  *  1. if optional attendee can't make the meeting at all, treat them like they don't exist
  *  2. if they can make it to the meeting, treat them like you'd normally treat mandatory ones
  *     even if they decrease the amount of available times
  *  3. if there's only one timeslot available and the optional attendee can't make it then, you can ignore them
  *     otherwise you'd be left with zero available times
  *  4. if there are no mandatory attendees, treat optionals like mandatory ones and return 
  *     every available time
  */