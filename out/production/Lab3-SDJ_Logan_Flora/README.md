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

 Design Pattern Implementations 
1. Observer Pattern
   1. Currently, my Table Panel calls methods from the Chart and Stats Panel in order to update those displays as well
      Using this pattern will create the sibling relationship these panels should have, as the parent panel is not the 
      table Panel but the frame. 
   2. I implemented this by making the table panel a subject of the Observers Stats and Chart Panels
      I created a Observer interface with a method that updates the panels instead.
      Observers are created and notified by the table panel to update the panels rather than doing it itself. 
2. Factory Pattern
   1. Instead of making the different graphs for when the data is filtered, being that dif. graphs
      work better for some filtered data than others, I will make factories and decide which is picked that way.
   2. Created an interface that holds classes to create the three different types of graphs
   3. And changed the ifs to create those graphs via the method instead of in the class itself.