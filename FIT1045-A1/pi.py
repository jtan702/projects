#NAME : TAN JIAJUN
#MONASH ID : 30503124

#ASSIGNMENT REQUIRED FUNCTIONS=============================================================================================================================================
from math import pi, sqrt      #imports from math library, pi and sqrt

#Basel problem
def basel(precision):
    loop=1                                  #declare a loop
    while True:
        n=1                                 #declares n for nth term
        a=0                                 #declares temp. storage to calc. current term
        list_basel=[]                       #list to store all terms
        for i in range(loop):
            a=1/(n**2)                      #calculates current term
            n+=1
            list_basel.append(a)            #append current term
        x_basel=sqrt(6*sum(list_basel))     #calc. the approximate pi 
        approx_p = x_basel - pi             #calc. the approximate p
        
        if abs(approx_p) <= precision:      #compares absolute approximate p to actual p
            break                           #breaks if approx_p <= p (con)
        
        elif abs(approx_p) > precision:
            loop += 1                       #increment loop so loop will run until con. is reached
            pass
    n_basel=len(list_basel)
    return x_basel, n_basel

#Taylor expression
def taylor(precision):
    loop=1
    while True:
        n=1
        x=0
        list_taylor=[]
        
        for i in range(loop):
            a=1/n
            n=n+2
            
            if i%2==0:                      #if count is even, append positive value
                x=a
                list_taylor.append(x)
            elif i%2!=0:                    #if count is odd, append negative value
                x=-a
                list_taylor.append(x)
                
        x_taylor=4*sum(list_taylor)
        approx_p = x_taylor - pi
        
        if abs(approx_p) <= precision:
            break
        
        elif abs(approx_p) > precision:
            loop+=1
            pass
    n_taylor= len(list_taylor)
    return x_taylor, n_taylor

#Wallis algorithm
def wallis(precision):
    loop = 1
    while True:
        a=2
        b=1
        c=3
        x=1
        product = 1
        list_wallis=[]
        for i in range(loop):
            x=(a**2)/(b*c)
            list_wallis.append(x)
            a+=2
            b+=2
            c+=2

        for elements in list_wallis:
            product *= elements
            
        x_wallis=2*product
        approx_p = x_wallis - pi

        if abs(approx_p) <= precision:
            break

        elif abs(approx_p) > precision:
            loop+=1
            pass
    n_wallis= len(list_wallis)
    return x_wallis, n_wallis

#Spigot algorithm
def spigot(precision):
    loop = 1
    while True:
        list_spigot=[1]
        for loop1 in range(loop):
            a=1                     #term's numerator
            b=3                     #term's denominator
            list_product=[]
            product=1
            
            for loop2 in range(loop1+1):
                x=a/b
                list_product.append(x)
                a+=1
                b+=2
                
            for elements in list_product:
                product*=elements
            list_spigot.append(product)
            
        x_spigot=2*(sum(list_spigot))
        approx_p = x_spigot - pi

        if abs(approx_p) <= precision:
            break

        elif abs(approx_p) > precision:
            loop+=1
            pass
        
    n_spigot=len(list_spigot)
    return x_spigot, n_spigot

#sort function: sorts in ascending n 
def sort(algorithms):
    for i in range(len(algorithms)):        
        min_i = i                                       #assume i is min val for each iteration
        
        for j in range(i, len(algorithms)):             #loop j, from i to the end of list
            if algorithms[j][1] < algorithms[min_i][1]: #compares the n of each algorithm
                min_i=j                                 #stores the actual min val

        temp = algorithms[i]                            #swaps assumed min val at i with actual min val at j
        algorithms[i] = algorithms[min_i]
        algorithms[min_i] = temp



#PROGRAM START=============================================================================================================================================================

precision=float(input("precision: "))                   #user input precision
choice_algo=['basel', 'taylor', 'wallis', 'spigot']     #choices for user input
algorithms=[]                                           #empty list for algorithms
num_algo=1                                              #declare a number that can be allocated for each alg.
while True: 
    if len(algorithms) <= 3:                            #max number of algs is 3
        input_algo=str(input('enter an algorithm:'))    #allows user to input alg    
        if input_algo in choice_algo:                   #checks if input is valid by comparing with choices
            if input_algo == 'basel':                   #alg appended to list to corresponding output
                algo=(num_algo, basel(precision)[1])
                algorithms.append(algo)
                num_algo += 1                           #appends number for next alg to be input
            elif input_algo == 'taylor':
                algo=(num_algo, taylor(precision)[1])
                algorithms.append(algo)
                num_algo += 1
            elif input_algo == 'wallis':
                algo=(num_algo, wallis(precision)[1])
                algorithms.append(algo)
                num_algo += 1    
            elif input_algo == 'spigot':
                algo=(num_algo, spigot(precision)[1])
                algorithms.append(algo)
                num_algo += 1              
        elif input_algo not in choice_algo:             #other options
            if input_algo == 'stop':                    #stops loop if input 'stop'
                break
            else:
                print('Error, please enter again')      #prints an error for an invalid input
    elif len(algorithms) == 4:                          #if lists somehow reaches 5, break the loop
        break

sort(algorithms)        #sort the algorithms, (lists are mutable so return value is not needed)
print(algorithms)       #print the sorted list

