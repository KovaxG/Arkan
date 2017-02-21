# Pizza Algorithm

```java
Slices func(Pizza p, Rect r, Slices list) {
  // Exit condition
  if (noMorePizzaLeft(p) || noMoreMushroomsOrTomatoesLeft(p))
    return list; // We found a good soltion, return a list of slices
  
  // Dead Leaf
  if (rectDoesntContainCorrectIngredients(p, r) || sliceTooBig(p, r)) {
    // If reached rightmost leaf, jump back one node
    if (nextRect(r) == null) {
      oldPizza = putBackLastSlice(p, list);
      previouseRect = getPrevRect(list);
      return func(oldPizza, previouseRect, removeLast(list));
    }
    // Otherwise try the next possible rectange
    else return func(p, nextRect(r), list);
  }
  
  // Slice can be removed
  newP = removeSliceFromPizza(p, r);
  list.add(getSliceData(p,r));
  return func(newP, firstRect(), list);
}
```
###Function Definitions

```java
// These are used to check if we have succeeded.
// Yes, these can be combined into one function.
boolean noMorePizzaLeft(Pizza p) { ... }
boolean noMoreMushroomsOrTomatoesLeft(Pizza p) { ... }

// Cut a slice of the pizza and check if it has the required number of ingredients
boolean rectDoesntContainCorrectIngredients(Pizza p, Rect r) { ... }
boolean sliceTooBig(Pizza p, Rect r) { ... } // Check if the pizza is smaller than the slice.

// There is a finite list of possible rectangles generated in the beginning of the form
// (1, 2), (2, 1), (1, 3), (3, 1), (1, 4), (4, 1), (2, 2). There are rectangles with
// area 2, 3 and 4. We check each possible combination, because we know that each slice
// must be a rectangle, and we know the minimum and maximum areas. We create a list
// and just traverse it, and when we get to the end we return null.
Rect nextRect(Rect r) { ... }

// When we reach a dead end, we have to put the last slice we took from the pizza back, 
// luckily it is stored in "list"
Pizza putBackLastSlice(Pizza p, Slices l) { ... }

// When we go back one level, we have to continue checking the rectangles from where we
// left off, i. e. the data is in the last element of the list.
Rect getPrevRect(Slices l) { ... }
Rect firstRect() { ... } // Return the first rectangle to be tried (this means new node)

// Remove the last element of the list
Slices removeLast(Slices s) { ... }

// Returns the pizza with 'X's where r was taken from. To notify about the absence of pizza.
Pizza removeSliceFromPizza(Pizza p, Rect r) { ... }

// Return a one element list, with the rect and the ingredients of the pizza that was in the
// place of rect.
Slice getSliceData(Pizza p, Rect r) { ... }
```

###Type Definitions
```java
class Slices {
  private ArrayList<Rect> rects;       // Contains the size of the rectangle
  private ArrayList<Pizza> pizzaSlice; // Contains the ingredients contained in that rectangle
}

class Rect {
  private int width;  // The width of the slice
  private int height; // The height of the slice
}

class Pizza {
  private char[][] ingredient; // Can be M(mushroom), T(tomato), or X(cut out)
                               // X is used when removing slices from the pizza
}
```
