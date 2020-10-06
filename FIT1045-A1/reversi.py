#NAME : TAN JIAJUN
#MONASH ID : 30503124

#ASSIGNMENT REQUIRED FUNCTIONS=============================================================================================================================================
import copy


#produces a fresh new game board
def new_board():
    row_empty = [0 for i in range(8)]           #iniialises empty rows
    board = []                                  #declare an empty table for board
    for i in range(8):                          #loop 8 times (for 8 columns)
        board.append(copy.deepcopy(row_empty))  #deep copies an empty row and append it to the table
    board[3][3], board[4][4] = 2, 2             #reassigns positions for starting stones (w)
    board[3][4], board[4][3] = 1, 1             #reassigns positions for starting stones (b)
    return board                                #returns the board


#prints a readable version of the board
def print_board(board):
    row_header = ['1', '2', '3' , '4', '5', '6', '7', '8']  #headings for each row
    col_header = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h']   #headings for each column
    h_line = ['+']*9        #board's horizontal lines
    e_line = ['-']*7        #board's edge lines
    str_board = []          #declare empty table for string board
    
    for row in board:       #converts board into string format
        str_row = []        #redeclares an empty list for each row
        for i in row:
            if i == 1:      #changes 1 in board to 'B' in string board
                i = 'B'
            elif i == 2:    #changes 2 in board to 'W' in string board
                i = 'W'
            elif i == 0:    #changes 0 in board to ' ' in string board
                i = ' '
            str_row.append(str(i))  #append each element into row 
        str_board.append(str_row)   #append each row into table
        
    print("  +---" + "---".join(e_line) + "---+")                       #prints starting edge
    for i in range(len(str_board)):
        print(row_header[i], "| " + " | ".join(str_board[i]) + " | ")   #prints each row of the board
        if i != len(str_board)-1:
            print("--" + "---".join(h_line))                            #prints board lines
        elif i == len(str_board)-1:
            print("--+---" + "---".join(e_line) + "---+")               #prints ending edge
            print("  | " +" | ".join(col_header))                       #prints last row of column headers


#prints score of both players
def score(board):
    s1 = 0              #initialises both scres
    s2 = 0
    for row in board:               #nested loop checks every element in board
        for i in range(len(row)):
            count1 = row.count(1)   #count 1's (B) in row
            count2 = row.count(2)   #count 2's (W) in row
        s1 += count1                #increment the counts
        s2 += count2
    return(s1, s2)                  #returns the scores


#returns true or false if player stone in position can be enclosed based on three conditions
'''
CON 1: pos must be in an empty spot
CON 2: neightbouring stone must be opponents stone
CON 3: following stones after neighbouring stones must be opponents and end stone must be player stone

for directions (r,c), appending r to pos_r, and c to pos_c will move the position towards the direction by one tile
'board[pos[0]+direct[0]][pos[1]+direct[1]]' is the next stone from initial position
'''
def enclosing(board, player, pos, direct):
    r, c = pos
    if board[r][c] != empty:        #first con: check if current pos is empty or not
        return False        
    elif board[r][c] == empty:      #moves into neighbouring stone if pos is empty
        r += direct[0]              #append into direction
        c += direct[1]
        for i in range(7):
            if 0 <= r <= 7 and 0 <= c <= 7:                             #pos cannot over the range of the board

                if r == pos[0]+direct[0] and c == pos[1]+direct[1]:     #second con: check next stone to see if position is valid
                    if board[r][c] == player:
                        return False
                    elif board[r][c] == empty:
                        return False
                    elif board[r][c] == opp:                            #next stone must be opponent to proceed
                        pass
                    
                elif r != pos[0]+direct[0] or c != pos[1]+direct[1]:    #third con: enclose true when it reaches player tile
                    if board[r][c] == player:                           #player stone must be at the end of the line to be enclosed
                        return True
                    elif board[r][c] == empty:
                        return False
                    elif board[r][c] == opp:
                        pass
                    
            elif r > 7 and c > 7:   #pos cannot over the range of the board
                return False
            
            r += direct[0]          #move to next position
            c += direct[1]


#places all valid positions in a list
def valid_moves(board, player):
    list_dir = [(-1,0), (-1,1), (0,1), (1,1), (1,0), (1,-1), (0,-1), (-1,-1)]   #initialise a list of directions
    v_moves=[]                      #declare an empty list to input moves
    for r in range(len(board)):
        for c in range(len(board)):
            pos=(r,c)                           #initialise position for current index in table
            for direct in list_dir:             #loops for every direction in the list
                if board[r][c] == empty:        #proceed if board is empty
                    if enclosing(board, player, pos, direct) == False:  
                        pass
                    elif enclosing(board, player, pos, direct) == True: #append position if enclosing is true
                        if pos in v_moves:                            #checks if pos have been appended before                  
                            pass
                        elif pos not in v_moves:
                            v_moves.append(pos)
                elif board[r][c] != empty:  #ignore the position if its not empty
                    pass
    return v_moves


#returns the state of the board after stone has been placed
def next_state(board, player, pos):
    list_dir = [(-1,0), (-1,1), (0,1), (1,1), (1,0), (1,-1), (0,-1), (-1,-1)]
    next_board = board                              #declares next_board
    if pos not in valid_moves(board, player):       #prints error for invalid move (move not in valid_moves)
        print('\nInvlaid move, please input valid position\nE.x. top left corner: a0, bottom left corner: h8')
        return board, player
    elif pos in valid_moves(board, player):         #proceed if the move is valid
        for direct in list_dir:
            if enclosing(board, player, pos, direct) == False:  #false, moves on to next direction
                pass
            elif enclosing(board, player, pos, direct) == True: #goes through stones in current direction
                r, c = pos
                while 0 <= r <= 7 and 0 <= c <= 7:      #loops while r and c is in range of table
                    r += direct[0]
                    c += direct[1]
                    if next_board[r][c] == player:      #break if r,c encounters player on board
                        break
                    elif next_board[r][c] != player:    #turns stone by redeclaring them
                        next_board[r][c] = player
        next_board[pos[0]][pos[1]] = player             #puts player stone on position
        
    if player == 1:         #swaps player to next player
        next_player = 2
    elif player == 2:
        next_player = 1
    return next_board, next_player


#converts position input into a string
def position(string):
    pos_row = ['1', '2', '3' , '4', '5', '6', '7', '8']         #initialise list that corresepond to row and column headings
    pos_col = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h']
    pos_string = []                                             #declare empty list for string position
    for i in string:                                            #appends each character in the string to the list
        pos_string.append(i)
    if pos_string[0] in pos_col and pos_string[1] in pos_row:   #check if characters are in the lists
        r = pos_row.index(pos_string[1])                        #r,c takes over the index of the characters
        c = pos_col.index(pos_string[0])
        return (r,c)                                            #return position
    else:
        return False                                            #return false for an invalid input


#run a two player game:
def run_two_players():
    game_quit = False           #initialise quit as false
    board = new_board()         #calls a new board
    global player, opp, empty   #puts variables in global frame to be used by functions 
    while True:
        p_choose = str(input('Is player 1 [B] or [W]? '))   #player chooses stone
        if p_choose == 'W':                                 #assign player to stone
            player = 2
            break
        elif p_choose == 'B':
            player = 1
            break
        else:
            print('Error invalid choice.')                  #returns error for invalid input
            pass

    while True:                                             #game start
        print('\n')
        player, opp, empty = decide_p(player)
        if player == 1:                                     #checks whose stone is being played
            turn = 'B'
        else:
            turn = 'W'
        print(turn + "'s turn")                             #prints the current turn
        print('Scores: \nB:',score(board)[0],'\tW:', score(board)[1])   #calls and prints score
        print_board(board)                                  #prints current board
        vmove_player = valid_moves(board, player)           #lists out all valid moves for both player and opponent
        vmove_opp = valid_moves(board, opp)

        if vmove_player == [] and vmove_opp != []:          #checks if player has no valid moves
            print('No valid moves')
            if player == 1:                                 #changes player turn
                player = 2
            elif player == 2:
                player = 1
            pass
        
        elif vmove_player == [] and vmove_opp == [] or game_quit == True:   #checks if game ends with no valid moves or a quit
            print('\nGAME OVER')
            if score(board)[0] == score(board)[1]:  #checks if game is a tie
                print("Tie")
            elif score(board)[0] > score(board)[1]: #check which player wins
                print("Player 1 wins!")
            else:
                print("Player 2 wins!")
            print('\n')
            break
        
        else:
            while True:
                    string = str(input('position: '))       #input the position from player
                    if string == "q":                       #sets quit to true if input is 'q'
                        game_quit = True
                        break
                    else:                                   
                        pos=position(string)                #converts input into position codable form
                        board, player = next_state(board, player, pos)  #returns next board and player
                        break
                    

'''
if position is an invalid option, next_state(board, player, pos) will return board and player without changing the board and player
in this case, the whole program will loop again to the same point until the player inputs a valid move
'''


#run a single player game
def run_single_player():
    game_quit = False
    board = new_board()
    global player, opp ,empty, comp
    while True:     
        p_choose = str(input('Is player [B] or [W]? '))
        if p_choose == 'W':
            player = 2
            comp = 1
            break
        elif p_choose == 'B':
            player = 1
            comp = 2
            break
        else:
            print('Error invalid choice.')
            pass

    while True:
        print('\n')
        player, opp, empty = decide_p(player)
        if player == 1:
            turn = 'B'
        else:
            turn = 'W'
        print(turn + "'s turn")
        print('Scores: \nB:',score(board)[0],'\tW:', score(board)[1])
        print_board(board)
        
        vmove_player = valid_moves(board, player)
        vmove_opp = valid_moves(board, opp)

        if vmove_player == [] and vmove_opp != []:
            print('No valid moves')
            if player == 1:
                player = 2
            elif player == 2:
                player = 1
            pass
        
        elif vmove_player == [] and vmove_opp == [] or game_quit == True:
            print('\nGAME OVER')
            if score(board)[0] == score(board)[1]:
                print("Tie")
            elif score(board)[0] > score(board)[1]:
                print("Player 1 wins!")
            else:
                print("Computer wins!")
            print('\n')
            break
        
        else:
            while True:
                if player == comp:                              #if computer turn
                    scores = []                                 #declare list for scores
                    for pos in vmove_player:
                        #loop every valid position
                        test = copy.deepcopy(board)             #create a test board
                        test, player = next_state(test, player, pos)
                        if comp == 1:                           #appends score of compter's stone
                            scores.append(score(test)[0])
                        elif comp == 2:
                            scores.append(score(test)[1])
                        player = comp                           #redeclare player as comp since nest_state() swaps players
                        
                    k = 0                           #finds the max score index in score list
                    for i in range(len(scores)):    
                        if scores[i] >= scores[k]:  
                            k = i
                            
                    pos = vmove_player[k]           #declare position with index of max score
                    board, player = next_state(board, player, pos)   #carry out computer turn
                    break
                
                else:
                    string = str(input('position: '))

                    if string == "q":
                        game_quit = True
                        break
                    else:
                        pos=position(string)
                        board, player = next_state(board, player, pos)
                        break


#SELF IMPLEMENTED FUNCTIONS================================================================================================================================================

#declares player1, opp, and empty from current player
def decide_p(player):
    if player == 1:
        p1 = 1
        p2 = 2
    elif player == 2:
        p1 = 2
        p2 = 1
    empty = 0
    return p1, p2, empty


#PROGRAM START========================================================================

print('\t+-----------------------+\n\t| WELCOME TO REVERSI!!! |\n\t|  coded by TAN JIAJUN  |\n\t+-----------------------+')
while True:
    print('[1] for single player\n[2] for two players')
    game = str(input('Which gamemode do you want to play? '))        #user input for gamemode to play
    if game == '2':
        run_two_players()
    elif game == '1':
        run_single_player()























  
