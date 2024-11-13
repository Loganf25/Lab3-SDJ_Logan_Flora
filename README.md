# Lab3-SDJ_Logan_Flora

##############################   Apache Import Issue Fix   #############################################

 This Program utilizes a csv reading and parsing class from the apache commons website
 Same website recommended by the prof. for stats
 I have added this class as a dependency of the project, and through a lib folder, so you shouldn't have to do anything
 But, if the imports do not work as intended, follow these steps:
     1. Click the 'File' button in ItelliJ 
     2. Navigate to the 'Project Stucture' button and click it
     3. From in here, MY project should be selected on the left side, if not, select it
     4. Off to the right of that is a tab named 'Dependencies,' click on it
     5. Below that is an '+' button, which after clicked will bring up a menu
     6. Click the option that says 'JAR and Directories'
     7. From this file explorer window, navigate to the lib folder, in project root folder, and click once
     8. Click 'OK' to add folder as a dependencies and 'OK' again to close the Stucture window
 This should resolve those imports issues
 If not, ask lflora@cub.uca.edu. Although this is what fixed mine, so idk how much help I'd be beyond this.

#########################################################################################################

1. Observer Pattern
   I wanted to implement this as table pattern should act as a sibling of the other panels, not the parent which controls
    So implementing them as observers is a better relationship to have between them
   In order to do this, I created a new interface that holds a method to be override by the observers and the 
   subject, table panel, has a list of these that notifies them to call that method when notified. 
2. Template Method Pattern
    I wanted my filter logic to be outside my panel like I have seen others do it in their projects.
    It makes total sense to separate these jobs, so to do so I used this pattern.
    I created a new abstract class that filters based on a filter, and a filter class for each of the possible filters
    This consolidates the code, in the backend file and the panel class, for filters to one place for easier changes in teh future. 