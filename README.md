
#Don't Starve Backup

<p align=center><img src="http://i.imgur.com/k45Rw2H.png"></p>

##About  

Tired of Don't Starve deleting your save? *Don't Starve Backup* is an external tool which automatically monitors and backs up all of your save files as you play. So, when the cold, inevitable embrace of death finally comes for you, simply roll back time and you'll find Maxwell right back where you left him. Give death the middle finger and keep on playing! 

##Why? 

As a guy buried under work and school, my time for gaming is remarkably limited. When I do have time, I enjoy playing a little Don't Starve. However, I do not enjoy dying. And with me being terrible at video games, death is something that happens early and often. So, in order to avoid myself constantly playing the first few days over and over every time I sit down, I started keeping manual backups of my game. When death inevitably struck, I would copy the files back in and be ready to go. As an exercise to learn Scala, I then wrote a small little program to do that process for me. Then [Feature Creep](http://en.wikipedia.org/wiki/Feature_creep) happened. And now it's a little stand-alone app. 

##Wait, isn't this cheating!? 

Why, yes. Yes, it is. And if the primary joy you get from the game is the hard core survival aspect of it, then you probably should not use this. It'll likely kill the fun for you. However, if you're like me, a guy who enjoys the meticulous base creation and world building aspect of it, then, well, it's pretty handy. 

##Instructions: 

###Getting the Project:   

If you just want the executable, you can [download it here.](https://github.com/chriskiehl/DoNotStarveBackupExecutable/blob/master/README.md) 

If you want the source (Be warned, it is not pretty), you can clone the project via: 

    git clone https://github.com/chriskiehl/DoNotStarveBackup.git




###First Run: 
<img src="http://i.imgur.com/JSehft4.png" align=right width="400">
The first time you run the program it will have to do a little find where all of your Don't Starve save files are stored. It could take a little while if you keep your games in a non-standard directory. Once it's found what it needs, a green "Done!" message will appear. You can then close the window and begin using the program. 

<br><br><br><br><br><br><br><br>  

###Basic Usage: 

<img src="http://i.imgur.com/k45Rw2H.png" align="left" width=450>
Click the `Start Backup` button to begin the program. While it is in save mode, it will monitor your save directory for any changes. One a new save is detected, it creates a time stamped restore point for east retrieval later. 


<br><br><br><br><br><br>  


###Restoring a Save: 

<img src="http://i.imgur.com/kOH9Tgi.png" align=right width="450">
If (when) you die, you can restore a previous save by clicking the `restore` button on the main window. This will open up a list of all the available restore points. Simply select the save you want, click `Restore!`, and you're all set! Launch the Game and you find Maxwell alive and well. 


<br><br><br><br>  



###Notes: 

This was an exercise that spiraled out of control. It's written in a language I've never used before, and turned into a .exe using a toolset I've never used before. So... probability of failue is pretty high. If you find a bug, problem, or just want to complain, feel free to submit a bug here on github, or drop me an email at audionautic@gmail.com


