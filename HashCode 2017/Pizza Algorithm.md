# Pizza Algorithm

```java
Slices func(Pizza p, Rect r, Slices list) {
  // Exit condition
  if (noMorePizzaLeft(p) || noMoreMushroomsLeft(p))
    return list;
  
  // Dead Leaf
  if (rectDoesntContainCorrectIngredients(p, r) || sliceTooBig(p, r)) {
    // If reached rightmost leaf, jump back one node
    // However this code is not correct.
    if (nextRect(r) == null) {
      oldPizza = putBackLastSlice(p, list);
      return func(oldPizza, firstRect(), removeLast(list));
    }
    return func(p, nextRect(r), list);
  }
  
  // Slice can be removed
  newP = removeSliceFromPizza(p, r);
  list.add(getSliceAndPizzaData(p,r));
  return func(newP, firstRect(), list);
}
```