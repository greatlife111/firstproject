# My Personal Project

# Project Name: ALERT SYSTEM


- ***What will the application do?***

The alert application is used for viewing alerts and sending reminders at designated times. Information about the 
reminder can be entered in the order of date, name of activity, due time, and how many times it will remind the user. 
Features include checking all alerts in various ways: on the day of, before a date, and before ___ days, adding and 
deleting alerts in your personal account, making changes to account and alert details, and having a notification list 
where you can check off reminders.



- ***Who will use it?***

The target will be students who need to keep track of deadlines and important dates.

- ***Why is this project of interest to me?***

As someone who tends to forget about assignments and is poor at time management myself, I've always wished that there is an
academic-based application that keeps track of all the important dates that I have assignments due. There are 
applications with ample tools to set reminders, differentiate the type of reminders, etc. However, it can be 
overwhelming to look at all the events at once. Thus, I want to create an application that is concise enough to store
all the reminder information and is student-friendly.


## What you can do as a user?

- add  and delete alerts to your alert list
- view all alerts in the next however many days
- view all alerts before a certain date
- view all alerts on a specific date
- edit account information and alert details
- save all alert details before closing the gui and load them when you open the gui again
- view your notifications

## Ways for improvement in the future
- make the Alert Class Abstract and have two classes extending it: alerts that will notify the user (repeat > 0), and
alerts that won't (repeat = 0)
- make the notifications field inside Alert Class its own class and pass it in as a parameter when instantiating an
Alert type object so that the notifications can be saved along with other components of the alert
- Categorize the MAINGUI class by tabs: create a new ui class for each tab to enhance readability of code, hence
reducing the amount of fields in the MAINGUI class
