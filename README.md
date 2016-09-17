# TemperatureMonitor
A simple console Java application intended for body temperature recording.

Data about date, time and temperature value for each measure is recorded into a file on user's desktop.

Default file path is the path to "Temperature.tmp" file on user's desktop. You can change file path during execution.

The application takes 2 arguments:

1. Waiting time (in minutes): the time needed to measure body temperature.
2. Remaining time show rate (in seconds): each user-defined number of seconds, a message will appear in console showing how long you still need to wait.

If argument(s) are missing, default values of 10 minutes wait time and 30 seconds time show rate will be used.

A user is notified when it is time to enter temperature value with both on-screen message and beeps.

There is an option of showing temperature statistics: an average temperature during morning, afternoon, evening and night separately, as well as total average temperature measured.
