INSTRUCTIONS:

Testing terminal on terminal:

1) Run ConnectionThread and connect 2 terminals (I used moba to simulate two terminals) but other applications work 
2) Use "nc localhost 1024" to connect via terminals
3) There is a prompt to indicate who goes first and who must wait
4) Play back and fourth until there's a winner

Testing GUI:

1) Run ConnectionThread
2) Run Client
3) Open a terminal and type "nc localhost 1024" 
4) The client plays, since they connected first
5) Click the black buttons on the top of the frame to drop it into a certain column
6) play back and fourth until there's a winner
