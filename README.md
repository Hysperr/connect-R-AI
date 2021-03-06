# Connect-R
- This is Connect-R, based on the classic game Connect-4.
- Go head-to-head against an improved AI.  
- Specify the dimensions of your board and the "connect-count" needed to win.

## How it works
After each move the human player makes, the AI generates a minimax tree of all the possible moves left in the game. The AI then performs a heuristic analysis on the winning states (represented as a leaves in the tree) to determine which end-state has the highest expected value, and makes it's next move based on the action (piece placement) to achieve that winning state. In doing so the AI is maximizing it's chances of winning while simultaneously minimizing the chances for its adversary, hence the name of the algorithm: minimax tree.
## Ways to play
  * AI v AI
  * Human v AI
  * Human v Human
  * Human v PRNG (pseudo random number generator)
  
