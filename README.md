# Stand-Up Tracker

Design Document

Anne Catherwood

Trevor Cromwell

Cheghali Elkhalili

## Introduction  

Friday's stand-up is lifetime away on the Monday back from a long weekend. Ever find yourself trying to remember who took which action item? Or who the contact person was for the brand image department? 
When life gets in the way and you have to miss stand-up, do you frantically send a two line update to a team member so that they can deliver it to the larger group? Stand-Up Tracker can help you:
-	Record, view and maintain past stand-up notes.
-	Add a note when you are unable to attend the meeting.
-	Easily access team members contact information.
-	Be aware of upcoming events for a plant: when to water, when growing season ends, etc.
Use your Android device to access your teams Stand-Up tracker. Upload notes or type directly into the on-screen note. Search, sort and filter notes quickly to access the information you need. Stay in touch with team members with their contact infomation right at your finger tips. 


## Storyboard

[Storyboard](https://github.com/acatherwood/IT3048/blob/master/Group_Project_Storyboard.pptx)

![FirstPage](https://user-images.githubusercontent.com/65705891/83110000-6052df00-a090-11ea-9525-fdad4cce17c6.JPG)



# Requirements

## Requirement 100: Remember during that meeting...

### Scenario  

As a software developer, I want to be able to reference a past stand-up note.

### Dependencies  

Previous notes are available

### Assumptions

Note was recorded for that day


### Example  

1.1
-  **Given** I am a logged on user on my team's home page
-  **When** when I click the Notes link on the naviagtion bar  
-  **Then** I expect to be redirected to a scrollable, clickable list of daily stand-up notes.   

1.2
- **Given** I am presented with a list of stand-up notes  
- **When** I type in 5/27/2020 or select from the calendar  
- **Then** I should see the standup notes for Wednesday, 5/27/2020 


## Requirement 101: Add/Update notes

### Scenario

As a memeber of the team who has to miss the meeting, I want to post my stand-up update in a centralized location viewable to all team members. 

### Dependencies
Uploaded team note.

## Assumptions  

Note was recorded for that day 

Examples  
1.1 
- **Given** a team note is available 
- **When** I click "Add Note" 
- **Then**  a Text area appears where I can enter my note and is now viewable to all team members

1.2
- **Given** I need to edit my note
- **When**  I click "edit note" 
- **Then**  the teat area becomes editable and I save my note and the changes are published

## Requirement 102: Where's my team?

### Scenario

As a memeber of the team, I want to have everyone's contact information easily accessible.

### Dependencies
Contact list available

## Assumptions  

Contact list is up to date

Examples  
1.1  
- **Given** I want to email a team memeber
- **When**  I click "Contacts" on the navigation bar 
- **Then**  a list of Team Members and their position and email addresses are listed.
2.1
- **Given** I want to edit my own contact info
- **When**  I click "edit profile" 
- **Then**  I save my changes and my profile is published

# Class Diagram

https://github.com/acatherwood/IT3048/blob/master/IT3048C%20Design%20Diagram.png

## Class Diagram Description  

###StandUp:  The main page  that includes most activities. The user can see members of each  team,   
see standup notes, and find control list. ###Team:  Noun class that represents team members. ###Note:      
Noun class that represents standup notes. ###IStandUp:  Interface for Room to persists Notes data.

# Product Backlog

https://github.com/acatherwood/IT3048/projects/1

https://github.com/acatherwood/IT3048/projects/2

https://github.com/acatherwood/IT3048/projects/3

https://github.com/acatherwood/IT3048/projects/4

# Kanban Board

# Scrum Roles

DevOps/Product Owner/Scrum Master: Anne Catherwood  
Frontend Developer: Cheghali Elkhalili  
Integration Developer: Trevor Cromwell


# Weekly Meeting

Date/time : Wednesday @ 8:15pm  
Meeting link:https://www.google.com/url?q=https://us04web.zoom.us/j/8029734922?pwd%3DSndIL2NmTzNIUGJUOGFERmNaaHY4Zz09&sa=D&usd=2&usg=AOvVaw2wcf6T9kl_3f3VJwI7a1tV   
Meeting number:802 973 4922   
Meeting Password: 9iiyu4    

