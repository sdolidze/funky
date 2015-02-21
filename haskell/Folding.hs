module Folding where

main = putStrLn "Hello World"
foldRight :: (a -> b -> b) -> b -> [a] -> b
foldRight _ v [] = v
foldRight f v (x:xs) = f x (foldRight f v xs)

scanRight :: (a -> b -> b) -> b -> [a] -> [b]
scanRight _ v [] = [v]
scanRight f v (x:xs) = f x y : y : ys where (y:ys) = scanRight f v xs

foldLeft :: (a -> b -> a) -> a -> [b] -> a
foldLeft _ v [] = v
foldLeft f v (x:xs) = foldLeft f (f v x) xs

scanLeft :: (a -> b -> a) -> a -> [b] -> [a]
scanLeft _ v [] = [v]
scanLeft f v (x:xs) = v : (scanLeft f (f v x) xs)