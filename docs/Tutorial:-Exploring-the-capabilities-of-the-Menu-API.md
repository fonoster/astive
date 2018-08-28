The biggest headache that an Asterisk-IVR implementor deal with, is the amount of loops and conditionals required to create a simple navigation logic. I will show the problem, and the solution using the API Menu. 

Now, let say that you want to create a two levels IVR to print a text when a DTMF digit is sent(a key pressed in a phone keypad). If key '1' is pressed the program must show "A", if '2' the text "B". To implement this extremely simple program with Asterisk-Java(or PHPAGI, PerlAGI...) we have to do some like this:

```java
answer();
while(true) {
    agi.streamFile("welcome"); // Welcome message
    boolean ok = false;
    int maxFailure = 3;
    int maxFailureLevel2 = 4;

    while(true) {
        String data = agi.getData("menu-item-a-sound"); // To choose option A, press 1

        if (data.equals("1")) {
            System.out.println("Your choice: A");

            // Second level
            while(true) {
                String data2 = agi.getData("menu-item-a1-sound"); // To choose option A1, press 1

                if (data.equals("1")) {
                    System.out.println("Your choice: A1");
                    break;            
                }

                data2 = agi.getData("menu-item-a2-sound"); // To choose option A1, press 2 

                if (data.equals("2")) {
                    System.out.println("Your choice: A2");
                    break;
                }

                data2 = agi.getData("menu-item-a3-sound"); // To choose option A2, press 3

                if (data.equals("3")) {
                    System.out.println("Your choice: A3");
                    break;
                }

                maxFailureLevel--;
                if(maxFailureLevel == 0) {
                    ok = false;
                    hangup();
                    break;
                }
            }

            ok = true;
            break;
        }

        String data = agi.getData("menu-item-b-sound"); // To choose option B, press 1 

        if (data.equals("2")) {
            System.out.println("Your choice: B");
            ok = true;
            break;
        }

        String data = agi.getData("menu-item-c-sound"); // To choose option C, press 3

        if (data.equals("3")) {            
            System.out.println("Your choice: C");
            ok = true;
            break;
        }

        maxFailure--;
        if(maxFailure == 0) {
            ok = false;
            hangup();
            break;
        }
    }
    if(ok) {
        agi.streamFile("goodbye"); // Say goodbye
        break;
    }
}
hangup();
```

With the Menu API you can avoid having all those loops or conditionals in your program. You can separate the controller of your program, from the navigation logic(similar to MVC). With Astive we will have some like this:

```java
answer();
Menu root = new Menu();
root.greetingsFile("welcome");
root.setMaxFailures(3);

// Level one
Mene itemA = new Menu("1", "menu-item-a-sound");
itemA.setMaxFailures(3);
MenuItem itemB = new MenuItem("2", "menu-item-b-sound");
MenuItem itemC = new MenuItem("3", "menu-item-c-sound");

itemA.setAction(new Action() {
    public void actionPerformed() {
        System.out.println("Your choice: A");
    }
})
itemB.setAction(new Action() {
    public void actionPerformed() {
        System.out.println("Your choice: B");
    }
})
itemC.setAction(new Action() {
    public void actionPerformed() {
        System.out.println("Your choice: C");
    }
})

// Level two
MenuItem itemA1 = new MenuItem();
MenuItem itemA2 = new MenuItem();
MenuItem itemA3 = new MenuItem();

itemA1.setAction(new Action() {
    public void actionPerformed() {
        System.out.println("Your choice: A1");
    }
})
itemA2.setAction(new Action() {
    public void actionPerformed() {
        System.out.println("Your choice: A2");
    }
})
itemA3.setAction(new Action() {
    public void actionPerformed() {
        System.out.println("Your choice: A3");
    }
})

itemA.addChild(itemA1);
itemA.addChild(itemA2);
itemA.addChild(itemA3);

root.addChild(itemA);
root.addChild(itemB);
root.addChild(itemC);

MenuNavigator e = new MenuNavigator(response);
try {
     e.run(root);
} catch (AgiException ex) {
            out.println(ex.getMessage());
}

hangup();
```

As you can see there is not loop or conditionals that complicate the navigation logic. Also you can take advance of the event-driven capabilities of Astive. Refer to the tutorial section for more info.