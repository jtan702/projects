import { interval, fromEvent} from 'rxjs'
import { map, scan, filter, merge} from 'rxjs/operators'

//Types
type Key = 'ArrowLeft' | 'ArrowRight'
type Event = 'keydown' | 'keyup'

//Constants
const 
CanvasSize = 600,
PaddleW = 10,
PaddleH = 100,
BallW = 10, 
LeftPaddleX = 0,
RightPaddleX = 590,
StartingY = 250,
WinScore = 7

//function to keep objects within the boundary of the canvas
const
  boundY = (v:number) => v<0? 0: v>CanvasSize-PaddleH? CanvasSize-PaddleH: v;

function pong() {
    // Inside this function you will use the classes and functions 
    // from rx.js
    // to add visuals to the svg element in pong.html, animate them, and make them interactive.
    // Study and complete the tasks in observable exampels first to get ideas.
    // Course Notes showing Asteroids in FRP: https://tgdwyer.github.io/asteroids/ 
    // You will be marked on your functional programming style
    // as well as the functionality that you implement.
    // Document your code!  

    //Classes
    class PaddleMove { constructor(public readonly delta:number) {}}
    class Tick { constructor(public readonly elapsed?:number) {} }

    //Interfaces
    //Builds the state of the game and its attributes
    interface GameState {
      readonly userScore: number;
      readonly compScore: number;
      readonly gameOver: boolean;
      readonly user: PaddleState;
      readonly comp: PaddleState;
      readonly ball: BallState;
      readonly winner: String;
    }

    //x and y attributes denote the position of the ball on the canvas.
    //dx and dy denote the speed of which the ball is moving on the respective axis.
    interface BallState {
      readonly dx: number;
      readonly dy: number;
      readonly x: number;
      readonly y: number;
    }

    interface PaddleState {
      readonly x: number;
      readonly y: number;
    }

    //Intital state of the game
    const
    initialState: GameState = { 
      userScore: 0,
      compScore: 0,
      gameOver: false, 
      user: createUser(),
      comp: createComp(),
      ball: createBall(),
      winner: "None"      //winner will be set for every round.
    }

    //object creation functions.
    function createUser(): PaddleState{
      return{
        x: LeftPaddleX,
        y: StartingY        
      }
    }

    function createComp(): PaddleState{
      return{
        x: RightPaddleX,
        y: StartingY        
      }
    }

    function createBall(dx?: number): BallState{
      return{
        dx: dx? dx: -1,
        dy: (Math.random()*2 - 1)%0.5,
        x: CanvasSize/2,
        y: CanvasSize/2
      }
    }
      
    //Functions for deep copying objects. They also serve the purpose of safely changing the attributes.
    const
      newPosPaddle = (o:PaddleState, newX:number, newY:number) => <PaddleState>{
      ...o,
      x: newX,
      y: newY
      },
      newPosBall = (o:BallState, newX:number, newY:number) => <BallState>{
        ...o,
        x: newX,
        y: newY
      }, newDeltaBall = (o:BallState, newDx:number, newDy:number) => <BallState>{
        ...o,
        dx: newDx,
        dy: newDy
      }

    function paddleMove(s:GameState, yDelta:number): GameState {
      return { ...s,
        user: newPosPaddle(s.user, s.user.x, s.user.y + yDelta)
      }
    }


    //key observable for recording user key presses when controlling userPaddle
    const keyObservable = <T>(e:Event, k:Key, result:()=>T)=>
    fromEvent<KeyboardEvent>(document,e)
        .pipe(
          filter(({code})=>code === k),
          map(result)),
    startUpMove = keyObservable('keydown','ArrowLeft',()=>new PaddleMove(-30)),
    startDownMove = keyObservable('keydown','ArrowRight',()=>new PaddleMove(30)),
    stopUpMove = keyObservable('keyup','ArrowLeft',()=>new PaddleMove(0)),
    stopDownMove = keyObservable('keyup','ArrowRight',()=>new PaddleMove(0))

    const compMove = (s:GameState) => {
      return <GameState>{...s,
        comp: newPosPaddle(s.comp, s.comp.x, boundY((s.ball.y*(0.9)-PaddleH/2)))
      }
    }

    //handles change in direction due to collisions between ball, paddle and border.
    //changes angle of the ball depending on where it is hit on the paddle.
    function ballCollision(s: GameState): GameState {
      const
        userPaddleHit = (x:number, y:number) => ((y > s.user.y - BallW && y < s.user.y+PaddleH) && x === LeftPaddleX+BallW),
        compPaddleHit = (x:number, y:number) => ((y > s.comp.y - BallW && y < s.comp.y + PaddleH) && x === RightPaddleX-BallW),
        boundaryHit = (y:number) => (y < 0 || y > CanvasSize-BallW),
        angleHit = (ballY:number, paddleY:number) => ballY-paddleY
      let multY = 0

        if (boundaryHit(s.ball.y)){
          s = <GameState>{...s, ball: newDeltaBall(s.ball,s.ball.dx, -(s.ball.dy))}
        }
        if(compPaddleHit(s.ball.x, s.ball.y) || userPaddleHit(s.ball.x, s.ball.y)){
          if (userPaddleHit(s.ball.x, s.ball.y)){
            let a = angleHit(s.ball.y,s.user.y)
            multY = a<50?-0.5:0.5
          }else{
            let a = angleHit(s.ball.y,s.comp.y)
            let multY = a<50?-0.5:0.5
          }
          s = <GameState>{...s, ball: newDeltaBall(s.ball,-(s.ball.dx), s.ball.dy + multY)}
        }
        return s;
    }

    //handles movement of the ball across the canvas
    const
      ballMove = (s:GameState)=> {
        s = ballCollision(s);

        if (s.ball.x >= 0 && s.ball.x <= CanvasSize) {
          return <GameState>{...s,
            ball: newPosBall(s.ball, s.ball.x + s.ball.dx, s.ball.y + s.ball.dy)}
        } else {
          if(s.ball.x > 0){
            return <GameState>{...s,userScore: s.userScore + 1 ,user: createUser(), ball: createBall(1), winner:"Player"}
          } else {
            return <GameState>{...s,compScore: s.compScore + 1 ,user: createUser(), ball: createBall(-1), winner:"Computer"}
          }
        }
      },

      //provides a sense of time within the game for game functions. Allows for compPaddle and ball movement to work.
      tick = (s:GameState) => {

        s = ballMove(compMove(s))

        if (s.compScore >= WinScore || s.userScore >= WinScore){
          s= <GameState>{...s, gameOver: true}
        }
        return s;
      }

      //combines the instances of GameStates into a single GameState
      const reduceState = (s:GameState, e:PaddleMove|Tick)=>
        e instanceof PaddleMove ? {...s,
          user: newPosPaddle(s.user, s.user.x, boundY(s.user.y + e.delta))
        }:tick(s);
      
      //event listener. Listens to changes in states and passes them to updateView to change visual elements.
      const subscription = interval(1).pipe(
        map(elapsed=>new Tick(elapsed)),
        merge(startUpMove, stopUpMove, startDownMove, stopDownMove),
        scan(reduceState, initialState))
      .subscribe(updateView);

      //updates the visual elements for the user to see.
      function updateView(s:GameState): void {
        const
          svg = document.getElementById("canvas")!,
          ball = document.getElementById("ball")!,
          userPaddle = document.getElementById("paddleLeft")!,
          compPaddle = document.getElementById("paddleRight")!;
        userPaddle.setAttribute('transform', `translate(${s.user.x}, ${s.user.y})`);
        ball.setAttribute('transform', `translate(${s.ball.x}, ${s.ball.y})`);
        compPaddle.setAttribute('transform', `translate(${s.comp.x}, ${s.comp.y})`);
        document.getElementById("userScoreBoard").innerHTML = 'User: ' + s.userScore
        document.getElementById("compScoreBoard").innerHTML = 'Computer: ' + s.compScore

        //end the game: unsubscribe from the observable
        if(s.gameOver) {
          subscription.unsubscribe()
          const
            attr = (e:Element,o:Object) => {for(const k in o) e.setAttribute(k,String(o[k])) }
          const
            gameOverText = document.createElementNS(svg.namespaceURI, "text")!;
          attr(gameOverText,{x:CanvasSize/6,y:CanvasSize/3,class:"gameover"});
          gameOverText.textContent = "Game Over " + s.winner + " wins!";
          svg.appendChild(gameOverText);
        }
    }
}

  // the following simply runs your pong function on window load.  Make sure to leave it in place.
  if (typeof window != 'undefined')
    window.onload = ()=>{
      let
        ball = document.getElementById("ball")!,
        userPaddle = document.getElementById("paddleLeft")!,
        compPaddle = document.getElementById("paddleRight")!;
      ball.setAttribute('transform', `translate(${CanvasSize/2}, ${CanvasSize/2})`);
      userPaddle.setAttribute('transform', `translate(${LeftPaddleX}, ${StartingY})`);
      compPaddle.setAttribute('transform', `translate(${RightPaddleX}, ${StartingY})`);
      document.getElementById("userScoreBoard").innerHTML = 'User: ' + 0
      document.getElementById("compScoreBoard").innerHTML = 'Computer: ' + 0
      console.log(ball)
      pong();
    }