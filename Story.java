class Story
{
    Room currentRoom;
    Feature clickedFeature; // The last feature that was clicked on.
    String text; // The story text that the user sees.
    boolean theEnd;

    Room jungle = new Room("Jungle", "images/jungle.png");
    Room desert = new Room("Desert", "images/desert.png");
    Room oasis = new Room("Oasis", "images/oasis.png");

    Feature treasure = new Feature("treasure", "images/treasure.png", 200, 500);

    // Special events that have happened in the story:
    boolean hasEatenBananas = false;
    boolean hasVisitedOasis = false; 
    boolean hasBeenToldAboutTreasure = false; 

    Story()
    {
        theEnd = false;
        currentRoom = jungle;
        setUpRooms();

        text = "Instructions: Explore the story by clicking on the features in the room.\n" +
        "Click a door to move to another room.";
    }

    void setUpRooms()
    {
        //Add features and exits to each room
        jungle.doors.add(new Door("images/doortodesert.png", 480, 500, desert));
        jungle.features.add(new Feature("bananas", "images/bananas.png", 500, 150));

        desert.doors.add(new Door("images/doortojungle.png", 20, 500, jungle));
        desert.doors.add(new Door("images/doortodesert.png", 480, 500, oasis));
        desert.features.add(treasure);
        treasure.isHidden = true;

        oasis.doors.add(new Door("images/doorbacktodesert.png", 20, 500, desert));
        // You can also add a feature without providing an image.
        // This is useful if the feature is already in the background of the room image.
        oasis.features.add(new Feature("oasis hut", 390, 385, 100, 75));

    }

    void action(double mouseX, double mouseY)
    {
        checkClick(mouseX, mouseY); // checks if the user clicked on a feature or a door

        if (currentRoom == jungle)
        {
            if (clickedFeature == null)
            {
                text = "The jungle has many stinging insects and poisonous plants.\n" + 
                "Be careful out here!";
                return;
            }

            if (clickedFeature.name.equals("bananas"))
            {
                text = "You eat a banana. Yum!";
                hasEatenBananas = true;
            }
        }

        if (currentRoom == desert)
        {
            if (hasEatenBananas == false && hasVisitedOasis == false)
            {
                text = "You wander in the desert and die first from hunger and then from thirst. How sad.\nThe end.";
                theEnd = true;
            }
            else if (hasEatenBananas == true && hasVisitedOasis == false)
            {
                text = "The bananas have given you strength to trek across the scorching sand dunes.";
            }
            else if (hasVisitedOasis == true)
            {
                if (hasBeenToldAboutTreasure == true)
                {
                    text = "The legend was true! You see the treasure that the funny old man promised.";
                }
                else
                {
                    text = "Sand for miles and miles. Maybe someone can help me.";
                }
                
                
                if (clickedFeature != null && clickedFeature.name.equals("treasure"))
                {
                    text = "There is treasure here beyond your wildest dreams!\nYou Win!";
                    theEnd = true;
                }
            }
        }

        if (currentRoom == oasis)
        {
            hasVisitedOasis = true;
            if (clickedFeature == null)
            {
                text = "After wandering in the desert for many hours, you find a refreshing oasis.\n" +
                "Does someone live in that little hut?";
            }
            else if (clickedFeature.name.equals("oasis hut"))
            {
                text = "There is a funny old hermit who lives in the hut.\n" +
                "He tells you about a treasure buried in the desert.\nHe suggests" +
                " that you go find the treasure and leave him alone.";

                hasBeenToldAboutTreasure = true;
                treasure.isHidden = false;
            }
        }
    }

    void checkClick(double mouseX, double mouseY)
    {
        checkFeatures(mouseX, mouseY);
        checkExits(mouseX, mouseY);
    }

    void checkFeatures(double mouseX, double mouseY)
    {
        for (Feature f : currentRoom.features)
        {
            if (f.isHidden == false && f.isClicked(mouseX, mouseY))
            {
                clickedFeature = f;
                return;
            }
        }
    }

    void checkExits(double mouseX, double mouseY)
    {
        for (Door d : currentRoom.doors)
        {
            if (d.isLocked == false && d.isClicked(mouseX, mouseY))
            {
                currentRoom = d.nextRoom;
                clickedFeature = null;
                text = "You have entered " + currentRoom.name;
                return;
            }
        }
    }
}